package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PaymentCardConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PaymentCardDAO;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Payment card data service implementation
 */
@Service(value = "paymentCardDataService")
@Transactional
public class PaymentCardDataServiceImpl extends BaseDataServiceImpl<PaymentCard, PaymentCardDTO>
        implements PaymentCardDataService {
    static final Logger logger = LoggerFactory.getLogger(PaymentCardDataServiceImpl.class);

    @Override
    public List<PaymentCardDTO> findByCustomerId(Long customerId) {
        PaymentCard examplePaymentCard = new PaymentCard();
        examplePaymentCard.setCustomerId(customerId);
        return convert(this.dao.findByExample(examplePaymentCard));
    }

    @Override
    public PaymentCardDTO findByCustomerIdIfNickNameUsedByAnotherCard(Long customerId, Long paymentCardId, String nickName) {
        final String hsql = "from PaymentCard pc where pc.customerId = ? and pc.id != ? and lower(pc.nickName) = ?";
        PaymentCard paymentCard = dao.findByQueryUniqueResult(hsql, customerId, paymentCardId, nickName.toLowerCase());
        return (paymentCard != null) ? this.converter.convertEntityToDto(paymentCard) : null;
    }

    @Autowired
    public void setDao(PaymentCardDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PaymentCardConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public PaymentCard getNewEntity() {
        return new PaymentCard();
    }
}
