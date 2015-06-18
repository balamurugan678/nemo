package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.BACSSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.BACSSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
@Transactional
public class BACSSettlementDataServiceImpl extends BaseDataServiceImpl<BACSSettlement, BACSSettlementDTO> implements BACSSettlementDataService {

	@Override
	public  BACSSettlement getNewEntity() {
		return new BACSSettlement();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BACSSettlementDTO> findByStatus(String status) {
		BACSSettlement exampleBACSSettlement = new BACSSettlement();
		exampleBACSSettlement.setStatus(status);
        List<BACSSettlement> bacsSettlement = this.dao.findByExample(exampleBACSSettlement);
        return isNotEmpty(bacsSettlement) ? convert(bacsSettlement) : Collections.EMPTY_LIST;
	}
	
	@Override
	public List<BACSSettlementDTO> findByOrderNumber(Long orderNumber) {
		 final String hsql = "select s from BACSSettlement s, Order o where s.orderId = o.id and o.orderNumber = ?";
	     return convert(dao.findByQuery(hsql, orderNumber));
	}
	
	@Autowired
    public void setDao(BACSSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(BACSSettlementConverterImpl converter) {
        this.converter = converter;
    }

	@Override
	@Transactional(readOnly=true)
	public BACSSettlementDTO findByFinancialServicesReference(	Long financialServicesReferenceNumber) {
		final BACSSettlement exampleBACSSettlement = new BACSSettlement();
		exampleBACSSettlement.setPaymentReference(financialServicesReferenceNumber);
		BACSSettlement bacsSettlement = this.dao.findByExampleUniqueResult(exampleBACSSettlement);
		return bacsSettlement != null ? converter.convertEntityToDto(bacsSettlement) : null;
		
	}

	@Override
	@Transactional(readOnly=true)
	public BACSSettlementDTO findBySettlementNumber(Long settlementNumber) {
			 final String hsql = "select s from BACSSettlement s  where s.settlementNumber = ?";
		     return this.converter.convertEntityToDto(dao.findByQueryUniqueResult(hsql, settlementNumber));
	}

    
}
