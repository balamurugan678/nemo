package com.novacroft.nemo.tfl.common.application_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Card service implementation.
 */
@Service(value = "cardService")
public class CardServiceImpl implements CardService {

    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected OysterCardValidator oysterCardValidator;

    @Override
    public void populateManageCardCmdWithCubicCardDetails(String cardNumber, Long customerId, ManageCardCmd cmd) {
        CardDTO card = cardDataService.findByCardNumber(cardNumber);
        checkAndPopulateManageCardCmd(customerId, card.getId(), cmd);
    }

    protected void checkAndPopulateManageCardCmd(Long customerId, Long cardId, ManageCardCmd cmd) {
        if (customerService.validateCustomerOwnsCard(customerId, cardId)) {
            CardDTO cardDTO = cardDataService.findById(cardId);
            CardInfoResponseV2DTO cubicCard = getCardService.getCard(cardDTO.getCardNumber());
            cmd.setCardId(cardDTO.getId());
            cmd.setCardNumber(cardDTO.getCardNumber());
            cmd.setAutoTopUpEnabled(cubicCard.isAutoTopUpEnabled());
            cmd.setAutoTopUpState(AutoLoadState.lookUpAmount(cubicCard.getAutoLoadState()));
        }
    }

    @Override
    public Boolean getAutoTopUpVisibleOptionForCard(Long cardId) {
        String cardNumber = cardDataService.findById(cardId).getCardNumber();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
        if (cardInfoResponseV2DTO != null && cardInfoResponseV2DTO.isAutoTopUpEnabled()) {
            return true;
        }
        return false;
    }

    @Override
    public CardDTO getCardDTOById(Long cardId) {
        return cardDataService.findById(cardId);
    }

    @Override
    public Long getCardIdFromCardNumber(String cardNumber) {
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        return (cardDTO != null) ? cardDTO.getId() : null;
    }

    @Override
    public void updatePassengerAndDiscountForCardItemCmd(CartItemCmdImpl cartItemCmdImpl) {
        cartItemCmdImpl.setPassengerType(CubicConstant.PASSENGER_TYPE_ADULT_CODE);
        cartItemCmdImpl.setDiscountType(CubicConstant.NO_DISCOUNT_TYPE_CODE);
    }
}
