package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public interface GoodwillPaymentService {

	void deleteGoodwillPayment(CartItemCmdImpl goodwillPaymentItem);

    void deleteGoodwillPayment(GoodwillPaymentItemDTO goodwillPaymentItem);

    GoodwillPaymentItem findById(String id);

    GoodwillPaymentItem findByCartIdAndCardId(Long cartId, Long cardId);

    GoodwillPaymentItem findByCardId(Long id);

    void clearProductCartItems(List<GoodwillPaymentItemDTO> goodwillPaymentItems);

    void populateProductCartItems(List<GoodwillPaymentItemDTO> goodwillItems, List<CartItemCmdImpl> cartItems);

}
