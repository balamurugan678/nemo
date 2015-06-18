package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.FailedAutoTopUpHistoryConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FailedAutoTopUpHistoryDAO;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpHistoryDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpHistory;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;

@Service(value = "failedAutoTopUpHistoryDataService")
@Transactional(readOnly = true)
public class FailedAutoTopUpHistoryDataServiceImpl extends BaseDataServiceImpl<FailedAutoTopUpHistory, FailedAutoTopUpHistoryDTO> implements
                FailedAutoTopUpHistoryDataService {

    @Override
    public FailedAutoTopUpHistory getNewEntity() {
        return new FailedAutoTopUpHistory();
    }

    @Autowired
    public void setDao(FailedAutoTopUpHistoryDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(FailedAutoTopUpHistoryConverterImpl converter) {
        this.converter = converter;
    }
    
    @Override
	public List<FailedAutoTopUpHistoryDTO> findByCustomerId(FailedAutoTopUpHistory failedAutoTopUpHistory) {
        return this.convert(this.dao.findByExample(failedAutoTopUpHistory));
	}

	@Override
	public List<FailedAutoTopUpHistoryDTO> findFailedAutoTopUpHistoryByCustomerIdAndCaseId(FailedAutoTopUpHistory failedAutoTopUpHistory) {
		return null;
	}

}
