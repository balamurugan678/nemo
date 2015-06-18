package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.exception.ServiceAccessException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.TransferTargetCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.constant.cubic_import.CubicCurrency;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Component(value = "transferTargetCardService")
public class TransferTargetCardServiceImpl implements TransferTargetCardService {
    static final Logger logger = LoggerFactory.getLogger(TransferTargetCardServiceImpl.class);

    @Autowired
    protected CardService cardService;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected SystemParameterService systemParameterService;

    @Autowired
    protected HotlistService hotlistService;

    @Autowired
    protected CardDataService cardDataService;

    protected Boolean isTargetCardHotlisted(String targetCardNumber) {
        return hotlistService.isCardHotlisted(targetCardNumber);
    }

    protected int calculateBusyPrePayTicketSlots(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        List<PrePayTicketSlot> prePayTicketList = cardInfoResponseV2DTO.getPptDetails().getPptSlots();
        int busySlots = 0;
        for (PrePayTicketSlot prePayTicket : prePayTicketList) {
            if (prePayTicket.getSlotNumber() != null) {
                busySlots++;
            }
        }
        return busySlots;
    }

    protected int calculateBusyPrePayPendingTicketSlots(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        List<PrePayTicket> pendingPrePayTicket = cardInfoResponseV2DTO.getPendingItems().getPpts();
        int busySlots = 0;
        for (PrePayTicket prePayTicket : pendingPrePayTicket) {
            if (prePayTicket.getRequestSequenceNumber() != null && !prePayTicket.getRequestSequenceNumber().equals(StringUtil.EMPTY_STRING)) {
                busySlots++;
            }
        }
        return busySlots;
    }

    protected int calculateTotalNumberOfBusyPrePayTicketSlotsOfSourceAndTargetCard(String sourceCardNumber, String targetCardNumber) {
        return calculateBusyPrePayTicketSlots(sourceCardNumber) + calculateBusyPrePayTicketSlots(targetCardNumber)
                        + calculateBusyPrePayPendingTicketSlots(targetCardNumber);
    }

    protected Boolean hasTargetCardReachedItsMaxLimitOfTravelCards(String sourceCardNumber, String targetCardNumber) {
        return calculateTotalNumberOfBusyPrePayTicketSlotsOfSourceAndTargetCard(sourceCardNumber, targetCardNumber) <= systemParameterService
                        .getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS);
    }

    protected Boolean hasTooMuchPayAsYouGo(String sourceCardNumber, String targetCardNumber) {
        Integer currency1 = getCardInfoResponseFromCubic(sourceCardNumber).getPpvDetails().getCurrency();
        Integer currency2 = getCardInfoResponseFromCubic(targetCardNumber).getPpvDetails().getCurrency();
        CardInfoResponseV2DTO cardInfoResponseSourceCard = getCardInfoResponseFromCubic(sourceCardNumber);
        CardInfoResponseV2DTO cardInfoResponseTargetCard = getCardInfoResponseFromCubic(targetCardNumber);
        if ((null == currency1 || currency1.equals(CubicCurrency.GBP.cubicCode()))
                        && (null == currency2 || currency2.equals(CubicCurrency.GBP.cubicCode()))) {
            int totalPayAsYouGo = cardInfoResponseSourceCard.getPpvDetails().getBalance() + cardInfoResponseSourceCard.getCardDeposit()
                            + cardInfoResponseTargetCard.getPpvDetails().getBalance() + getTotalPendingPrePayValue(cardInfoResponseTargetCard);
            return (totalPayAsYouGo > Integer.parseInt(systemParameterService.getParameterValue(SystemParameterCode.PAY_AS_YOU_GO_LIMIT.code())));
        } else {
            logger.error("At least one of the currencies in the Oyster cards is not GBP.");
            throw new ServiceAccessException("At least one of the currencies in the Oyster cards is not GBP.");
        }
    }

    protected Integer getTotalPendingPrePayValue(CardInfoResponseV2DTO cardInfoResponseV2DTO) {

        int totalPendingPrePayValue = 0;
        List<PrePayValue> listOfPendingPrePayItems = cardInfoResponseV2DTO.getPendingItems().getPpvs();
        if (listOfPendingPrePayItems != null) {
            for (PrePayValue prePayValue : listOfPendingPrePayItems) {
                totalPendingPrePayValue = totalPendingPrePayValue + prePayValue.getPrePayValue();
            }
        }
        return totalPendingPrePayValue;
    }

    public Boolean isEligibleAsTargetCard(String sourceCardNumber, String targetCardNumber) {
        if (isTargetCardHotlisted(targetCardNumber)) {
            return false;
        }
        if (hasTooMuchPayAsYouGo(sourceCardNumber, targetCardNumber)
                        || !hasTargetCardReachedItsMaxLimitOfTravelCards(sourceCardNumber, targetCardNumber)) {
            return false;
        }
        return true;
    }

    protected CardInfoResponseV2DTO getCardInfoResponseFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    @Override
    public void populateCardsSelectList(String sourceCardNumber, Model model) {
        List<CardDTO> cardDTOs = cardDataService.getAllCardsFromUserExceptCurrent(sourceCardNumber);
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(PageAttribute.CARDS);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (CardDTO cardDTO : cardDTOs) {
            if (null != cardDTO.getCardNumber() && isEligibleAsTargetCard(sourceCardNumber, cardDTO.getCardNumber())) {
                selectListDTO.getOptions().add(new SelectListOptionDTO(cardDTO.getId().toString(), cardDTO.getCardNumber()));
            }
        }
        if (!selectListDTO.getOptions().isEmpty()) {
            model.addAttribute(PageAttribute.CARDS, selectListDTO);
        }
    }

}
