package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public interface GoodwillPaymentItemDataService extends BaseDataService<GoodwillPaymentItem, GoodwillPaymentItemDTO> {

    GoodwillPaymentItem findById(String id);

    GoodwillPaymentItem findByCartIdAndCardId(Long cartId, Long cardId);

    List<GoodwillPaymentItemDTO> findAllByCartIdAndCardId(Long cartId, Long cardId);

    List<GoodwillPaymentItemDTO> findAllByCartIdAndCustomerId(Long cartId, Long customerId);
}
