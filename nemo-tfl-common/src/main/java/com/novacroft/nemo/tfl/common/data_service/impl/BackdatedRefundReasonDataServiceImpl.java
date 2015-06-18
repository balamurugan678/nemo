package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.BackdatedRefundReasonConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.BackdatedRefundReasonDAO;
import com.novacroft.nemo.tfl.common.data_service.BackdatedRefundReasonDataService;
import com.novacroft.nemo.tfl.common.domain.BackdatedRefundReason;
import com.novacroft.nemo.tfl.common.transfer.BackdatedRefundReasonDTO;

/**
 * Backdated refund reason data service implementation
 */
@Service(value = "backdatedrefundReasonDataService")
@Transactional(readOnly = true)
public class BackdatedRefundReasonDataServiceImpl extends BaseDataServiceImpl<BackdatedRefundReason, BackdatedRefundReasonDTO> implements BackdatedRefundReasonDataService {
    @Autowired
    public void setDao(BackdatedRefundReasonDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(BackdatedRefundReasonConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public BackdatedRefundReason getNewEntity() {
        return new BackdatedRefundReason();
    }

   
}
