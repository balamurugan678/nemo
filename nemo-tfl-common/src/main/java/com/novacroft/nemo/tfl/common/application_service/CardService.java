package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

public interface CardService {
    void populateManageCardCmdWithCubicCardDetails(String cardNumber, Long customerId, ManageCardCmd cmd);
    Boolean getAutoTopUpVisibleOptionForCard(Long cardId);
    CardDTO getCardDTOById(Long cardId);
    Long getCardIdFromCardNumber(String cardNumber);
    void updatePassengerAndDiscountForCardItemCmd(CartItemCmdImpl cartItemCmdImpl);
}
