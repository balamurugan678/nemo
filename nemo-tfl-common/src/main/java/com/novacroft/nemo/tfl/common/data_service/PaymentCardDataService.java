package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

import java.util.List;

/**
 * Payment card data service specification
 */
public interface PaymentCardDataService extends BaseDataService<PaymentCard, PaymentCardDTO> {
    List<PaymentCardDTO> findByCustomerId(Long customerId);

    PaymentCardDTO findByCustomerIdIfNickNameUsedByAnotherCard(Long customerId, Long paymentCardId, String nickName);
}
