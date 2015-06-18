package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.tfl.common.constant.FailedAutoTopUpStatus;
import com.novacroft.nemo.tfl.common.converter.impl.FailedAutoTopUpCaseConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FailedAutoTopUpCaseDAO;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpCase;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;

@Service(value = "failedAutoTopUpCaseDataService")
@Transactional(readOnly = true)
public class FailedAutoTopUpCaseDataServiceImpl extends BaseDataServiceImpl<FailedAutoTopUpCase, FailedAutoTopUpCaseDTO> implements
                FailedAutoTopUpCaseDataService {

    @Override
    public FailedAutoTopUpCase getNewEntity() {
        return new FailedAutoTopUpCase();
    }
    
    @Autowired
    public void setDao(FailedAutoTopUpCaseDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(FailedAutoTopUpCaseConverterImpl converter) {
        this.converter = converter;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(FailedAutoTopUpCaseDTO failedAutoTopUpCaseDTO) {
        createOrUpdate(failedAutoTopUpCaseDTO);
    }

	@Override
	public FailedAutoTopUpCaseDTO findByFATUCaseId(Long caseId) {
		 final String hsql = "select FATU from FailedAutoTopUpCase FATU WHERE FATU.id = ?";
		 FailedAutoTopUpCase failedAutoTopUpCase = this.dao.findByQueryUniqueResult(hsql, caseId);
		 return (failedAutoTopUpCase != null) ? this.converter.convertEntityToDto(failedAutoTopUpCase) : null;
	}
 
	@Override
	public List<FailedAutoTopUpCaseDTO> findByCustomerId(FailedAutoTopUpCase failedAutoTopUpCase) {
        return this.convert(this.dao.findByExample(failedAutoTopUpCase));
	}
	 
	@Override
	public List<FailedAutoTopUpCaseDTO> findPendingItemsByCustomerId(Long customerId) {
		FailedAutoTopUpCase exampleAutoTopUpCase = new FailedAutoTopUpCase();
		exampleAutoTopUpCase.setCustomerId(customerId);
		exampleAutoTopUpCase.setCaseStatus(FailedAutoTopUpStatus.OPEN.code());
		return this.convert(this.dao.findByExample(exampleAutoTopUpCase));
	}
	
	@Override
	public int findPendingAmountByCustomerId(Long customerId) {
    	List<FailedAutoTopUpCaseDTO> pendingATUs = findPendingItemsByCustomerId(customerId);
    	int amount = 0;
    	for (FailedAutoTopUpCaseDTO pendingATU: pendingATUs) {
    		amount += CurrencyUtil.convertPenceToPounds(new Float(pendingATU.getFailedAutoTopUpAmount()));
    	}
    	return amount;
	}

	@Override
	public boolean isOysterCardWithFailedAutoTopUpCase(String cardNumber) {
		 final String hsql = "select FATU from FailedAutoTopUpCase FATU WHERE FATU.cardNumber= ?";
		 FailedAutoTopUpCase failedAutoTopUpCase = this.dao.findByQueryUniqueResult(hsql, cardNumber);
		 return (failedAutoTopUpCase != null);
	}
	
	@Override
	public FailedAutoTopUpCaseDTO findByCardNumber(String oysterCardNumber) {
		 final String hsql = "select FATU from FailedAutoTopUpCase FATU WHERE FATU.cardNumber = ?";
		 FailedAutoTopUpCase failedAutoTopUpCase = this.dao.findByQueryUniqueResult(hsql, oysterCardNumber);
		 return (failedAutoTopUpCase != null) ? this.converter.convertEntityToDto(failedAutoTopUpCase) : null;
	}
	
	@Override
    @Transactional(readOnly = true)
    public FailedAutoTopUpCaseDTO findByCustomerIdWithCaseDetails(Long customerId) {
        final String hsql = "from FailedAutoTopUpCase FATU where FATU.customerId = ?";
        FailedAutoTopUpCase failedAutoTopUpCase = dao.findByQueryUniqueResult(hsql, customerId);
        return (null != failedAutoTopUpCase) ? this.converter.convertEntityToDto(failedAutoTopUpCase) : null;
    }

    
}
