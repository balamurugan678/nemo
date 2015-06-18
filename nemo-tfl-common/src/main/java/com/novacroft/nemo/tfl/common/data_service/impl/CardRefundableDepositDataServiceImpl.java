package com.novacroft.nemo.tfl.common.data_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.CardRefundableDepositConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardRefundableDepositDAO;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDeposit;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;

/**
 * Card refundable deposit data service implementation
 */
@Service(value = "cardRefundableDepositDataService")
@Transactional(readOnly = true)
public class CardRefundableDepositDataServiceImpl extends BaseDataServiceImpl<CardRefundableDeposit, CardRefundableDepositDTO>
        implements CardRefundableDepositDataService {
    static final Logger logger = LoggerFactory.getLogger(CardRefundableDepositDataServiceImpl.class);

    public CardRefundableDepositDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(CardRefundableDepositDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CardRefundableDepositConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public CardRefundableDeposit getNewEntity() {
        return new CardRefundableDeposit();
    }

    @Override
    public CardRefundableDepositDTO findRefundableDepositAmount() {
        CardRefundableDeposit cardRefundableDeposit = new CardRefundableDeposit();
        cardRefundableDeposit.setEndDate(null);
        cardRefundableDeposit = dao.findByExampleUniqueResult(cardRefundableDeposit);
        if (cardRefundableDeposit != null) {
            return this.converter.convertEntityToDto(cardRefundableDeposit);
        }
        return null;
    }
    
    @Override
    public CardRefundableDepositDTO findByPrice(Integer price) {
        CardRefundableDeposit cardRefundableDeposit = new CardRefundableDeposit();
        cardRefundableDeposit.setPrice(price);
        cardRefundableDeposit.setEndDate(null);
        cardRefundableDeposit = dao.findByExampleUniqueResult(cardRefundableDeposit);
        if (cardRefundableDeposit != null) {
            return this.converter.convertEntityToDto(cardRefundableDeposit);
        }
        return null;
    }


}
