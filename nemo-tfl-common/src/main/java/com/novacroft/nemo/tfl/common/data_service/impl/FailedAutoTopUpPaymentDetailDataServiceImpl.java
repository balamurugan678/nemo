package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.FailedAutoTopUpPaymentDetailConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FailedAutoTopUpPaymentDetailDAO;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpPaymentDetailDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpPaymentDetail;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpPaymentDetailDTO;

@Service(value = "failedAutoTopUpPaymentDetailDataService")
@Transactional(readOnly = true)
public class FailedAutoTopUpPaymentDetailDataServiceImpl extends BaseDataServiceImpl<FailedAutoTopUpPaymentDetail, FailedAutoTopUpPaymentDetailDTO>
                implements FailedAutoTopUpPaymentDetailDataService {

    @Override
    public FailedAutoTopUpPaymentDetail getNewEntity() {
        return new FailedAutoTopUpPaymentDetail();
    }

    @Autowired
    public void setDao(FailedAutoTopUpPaymentDetailDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(FailedAutoTopUpPaymentDetailConverterImpl converter) {
        this.converter = converter;
    }

}
