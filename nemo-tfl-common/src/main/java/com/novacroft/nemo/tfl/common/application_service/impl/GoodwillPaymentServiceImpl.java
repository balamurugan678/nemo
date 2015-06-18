package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GOODWILL_REASON;
import static com.novacroft.nemo.tfl.common.constant.Refund.SPACE;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.application_service.GoodwillPaymentService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.GoodwillPaymentItemDataService;
import com.novacroft.nemo.tfl.common.data_service.GoodwillReasonDataService;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

/**
 * Goodwill payments service implementation
 */
@Service("goodwillPaymentService")
public class GoodwillPaymentServiceImpl extends BaseService implements GoodwillPaymentService {
    static final Logger logger = LoggerFactory.getLogger(GoodwillPaymentServiceImpl.class);

    @Autowired
    protected GoodwillPaymentItemDataService goodwillPaymentItemDataService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected ItemDataService itemDataService;
    @Autowired
    protected GoodwillReasonDataService goodwillReasonDataService;


    @Override
    public void deleteGoodwillPayment(CartItemCmdImpl goodwillPaymentItem) {
        GoodwillPaymentItemDTO copy = new GoodwillPaymentItemDTO();
        Converter.convert(goodwillPaymentItem, copy);
        deleteGoodwillPayment(copy);
    }

    @Override
    public void deleteGoodwillPayment(GoodwillPaymentItemDTO goodwillPaymentItem) {
        if (goodwillPaymentItem != null) {
            goodwillPaymentItemDataService.delete(goodwillPaymentItem);
        }
    }

    @Override
    public GoodwillPaymentItem findById(String id) {
        return goodwillPaymentItemDataService.findById(id);
    }

    @Override
    public GoodwillPaymentItem findByCartIdAndCardId(Long cartId, Long cardId) {
        GoodwillPaymentItem goodwillPaymentItem = new GoodwillPaymentItem();
        Converter.convert(goodwillPaymentItemDataService.findByCartIdAndCardId(cartId, cardId), goodwillPaymentItem);
        return goodwillPaymentItem;
    }

    @Override
    public GoodwillPaymentItem findByCardId(Long id) {
        GoodwillPaymentItem goodwillPaymentItem = new GoodwillPaymentItem();
        Converter.convert(goodwillPaymentItemDataService.findById(id), goodwillPaymentItem);
        return goodwillPaymentItem;
    }


    @Override
    public void clearProductCartItems(List<GoodwillPaymentItemDTO> goodwillPaymentItems) {
        for (GoodwillPaymentItemDTO goodwillPaymentItem : goodwillPaymentItems) {
            goodwillPaymentItemDataService.delete(goodwillPaymentItem);
        }
    }

    @Override
    public void populateProductCartItems(List<GoodwillPaymentItemDTO> goodwillItems, List<CartItemCmdImpl> cartItems) {
        if (CollectionUtils.isNotEmpty(goodwillItems)) {
            for (GoodwillPaymentItemDTO goodwillPaymentItem : goodwillItems) {
                CartItemCmdImpl cartItem = new CartItemCmdImpl();
                Converter.convert(goodwillPaymentItem, cartItem);
                if (goodwillPaymentItem.getGoodwillReasonDTO() != null) {
                    cartItem.setItem(getContent(GOODWILL.textCode()) + SPACE + goodwillPaymentItem.getGoodwillReasonDTO().getDescription());
                } else {
                    cartItem.setItem(getContent(GOODWILL_REASON.textCode()));
                }
                if (goodwillPaymentItem != null && goodwillPaymentItem.getGoodwillReasonDTO() != null && goodwillPaymentItem.getGoodwillReasonDTO().getReasonId() != null) {
                    cartItem.setGoodwillPaymentId(goodwillPaymentItem.getGoodwillReasonDTO().getReasonId());
                }
                cartItems.add(cartItem);
            }
        }
    }

}
