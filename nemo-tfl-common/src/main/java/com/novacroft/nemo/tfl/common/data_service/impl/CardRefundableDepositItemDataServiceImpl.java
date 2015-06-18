package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.CardRefundableDepositItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardRefundableDepositItemDAO;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositItemDataService;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDepositItem;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Card refundable deposit item data service implementation
 */
@Service(value = "cardRefundableDepositItemDataService")
@Transactional(readOnly = true)
public class CardRefundableDepositItemDataServiceImpl
        extends BaseDataServiceImpl<CardRefundableDepositItem, CardRefundableDepositItemDTO>
        implements CardRefundableDepositItemDataService {
    static final Logger logger = LoggerFactory.getLogger(CardRefundableDepositItemDataServiceImpl.class);

    public CardRefundableDepositItemDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(CardRefundableDepositItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CardRefundableDepositItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public CardRefundableDepositItem getNewEntity() {
        return new CardRefundableDepositItem();
    }

    @Override
    public CardRefundableDepositItemDTO findByCartIdAndCardId(Long cartId, Long cardId) {
        if (cartId != null && cardId != null) {
            CardRefundableDepositItem cardRefundableDepositItem = new CardRefundableDepositItem();
            cardRefundableDepositItem.setCardId(cardId);
            cardRefundableDepositItem = dao.findByExampleUniqueResult(cardRefundableDepositItem);
            if (cardRefundableDepositItem != null) {
                return this.converter.convertEntityToDto(cardRefundableDepositItem);
            }
        }
        return null;
    }

    @Override
    public CardRefundableDepositItemDTO findByOrderNumber(Long customerOrderId) {
        assert (customerOrderId != null);
        CardRefundableDepositItem cardRefundableDepositItem = new CardRefundableDepositItem();
        cardRefundableDepositItem.setOrderId(customerOrderId);
        cardRefundableDepositItem = dao.findByExampleUniqueResult(cardRefundableDepositItem);
        if (cardRefundableDepositItem != null) {
            return this.converter.convertEntityToDto(cardRefundableDepositItem);
        }
        return null;
    }

}
