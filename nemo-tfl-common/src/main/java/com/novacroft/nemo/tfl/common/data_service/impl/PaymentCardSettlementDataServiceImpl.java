package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PaymentCardSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PaymentCardSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;

/**
 * Settlement (by Payment Card) data service implementation
 */
@Service("paymentCardSettlementDataService")
@Transactional(readOnly = true)
public class PaymentCardSettlementDataServiceImpl extends BaseDataServiceImpl<PaymentCardSettlement, PaymentCardSettlementDTO>
        implements PaymentCardSettlementDataService {
    @Override
    public List<PaymentCardSettlementDTO> findByOrderId(Long orderId) {
        final String hsql = "from PaymentCardSettlement s where s.orderId = ?";
        return convert(dao.findByQuery(hsql, orderId));
    }

    @Override
    public PaymentCardSettlement getNewEntity() {
        return new PaymentCardSettlement();
    }

    @Autowired
    public void setDao(PaymentCardSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PaymentCardSettlementConverterImpl converter) {
        this.converter = converter;
    }
}
