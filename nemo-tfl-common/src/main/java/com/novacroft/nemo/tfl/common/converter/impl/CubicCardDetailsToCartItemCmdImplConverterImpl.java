package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PAY_AS_YOU_GO_CREDIT;
import static com.novacroft.nemo.tfl.common.constant.TicketType.PAY_AS_YOU_GO;
import static com.novacroft.nemo.tfl.common.constant.TicketType.TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToEndZone;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToStartZone;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.converter.CubicCardDetailsToCartItemCmdImplConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * CubicCardDetails to CartItemCmdImpl converter
 */
@Component(value = "cubicCardDetailsToCartItemCmdImplConverter")
public class CubicCardDetailsToCartItemCmdImplConverterImpl implements CubicCardDetailsToCartItemCmdImplConverter {
    static final Logger logger = LoggerFactory.getLogger(CubicCardDetailsToCartItemCmdImplConverterImpl.class);

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;
    
    @Autowired
    protected CardDataService cardDataService;
    
    @Autowired
    protected GetCardService getCardService;
    
    @Override
    public Set<CartItemCmdImpl> convertCubicCardDetailsToCartItemCmdImpls(String cardNumber, String refundCartType) {
        Set<CartItemCmdImpl> itemDTOs = new HashSet<CartItemCmdImpl>();
        itemDTOs.addAll(convertPrePayTicketsToCartItemCmdImpls(cardNumber, refundCartType));
        itemDTOs.add(convertPrePayValueToCartItemCmdImpl(cardNumber, refundCartType));
        return itemDTOs;
    }
    
    protected Set<CartItemCmdImpl> convertPrePayTicketsToCartItemCmdImpls(String cardNumber, String refundCartType) {
        if (isPrePayTicketsExistInCubicCard(cardNumber)) {
            return convertPrePayTicketsFromCubicToCartItemCmdImpls(cardNumber, refundCartType);
        }
        return new HashSet<>();
    }
    
    protected CartItemCmdImpl convertPrePayValueToCartItemCmdImpl(String cardNumber, String refundCartType) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseV2DTOFromCardNumber(cardNumber);
        return convertPrePayValueToCartItemCmdImpl(cardInfoResponseV2DTO.getPpvDetails(), getCardIdFromCardNumber(cardInfoResponseV2DTO.getPrestigeId()), refundCartType);
    }
    
    protected boolean isPrePayTicketsExistInCubicCard(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseV2DTOFromCardNumber(cardNumber);
        return (cardInfoResponseV2DTO.getPptDetails() != null && cardInfoResponseV2DTO.getPptDetails().getPptSlots() != null);
    }
    
    protected Set<CartItemCmdImpl> convertPrePayTicketsFromCubicToCartItemCmdImpls(String cardNumber, String refundCartType) {
        Set<CartItemCmdImpl> itemDTOs = new HashSet<CartItemCmdImpl>();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseV2DTOFromCardNumber(cardNumber);
        for (PrePayTicketSlot prePayTicketSlot : cardInfoResponseV2DTO.getPptDetails().getPptSlots()) {
            if (isNotBlank(prePayTicketSlot.getProduct())) {
                itemDTOs.add(convertPrePayTicketSlotToCartItemCmdImpl(prePayTicketSlot, getCardIdFromCardNumber(cardNumber), refundCartType));
            }
        }
        return itemDTOs;
    }

    protected CardInfoResponseV2DTO getCardInfoResponseV2DTOFromCardNumber(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }
    
    protected CartItemCmdImpl convertPrePayValueToCartItemCmdImpl(PrePayValue prePayValue, Long cardId, String refundCartType) {
        if (isPrePayValueExistInCubicCard(prePayValue)) {
            return new CartItemCmdImpl(PAY_AS_YOU_GO_CREDIT, prePayValue.getBalance(), cardId, PAY_AS_YOU_GO.code());
        } else {
        	return new CartItemCmdImpl(PAY_AS_YOU_GO_CREDIT, 0, cardId, PAY_AS_YOU_GO.code());
        }
    }
    
    protected boolean isPrePayValueExistInCubicCard(PrePayValue prePayValue) {
        return (prePayValue != null && prePayValue.getBalance() != null && prePayValue.getBalance() >= 0);
    }
    
    protected Long getCardIdFromCardNumber(String cardNumber) {
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        return (cardDTO != null) ? cardDTO.getId() : null;
    }
    
    protected CartItemCmdImpl convertPrePayTicketSlotToCartItemCmdImpl(PrePayTicketSlot prePayTicketSlot, Long cardId, String refundCartType) {
        if (isNotBlank(prePayTicketSlot.getProduct())) {
            String travelCardType = getTravelCardDurationFromStartAndEndDate(prePayTicketSlot.getStartDate(), prePayTicketSlot.getExpiryDate());
            Integer startZone = convertCubicZoneDescriptionToStartZone(prePayTicketSlot.getZone());
            Integer endZone = convertCubicZoneDescriptionToEndZone(prePayTicketSlot.getZone());

            return getCartItemCmdImplFromPrePayTicketSlot(prePayTicketSlot, travelCardType, startZone, endZone, cardId, refundCartType);
        }
        return null;
    }
    
    protected CartItemCmdImpl getCartItemCmdImplFromPrePayTicketSlot(PrePayTicketSlot prePayTicketSlot, String travelCardType, Integer startZone, Integer endZone, Long cardId, String refundCartType) {
        CartItemCmdImpl cartItemCmdImpl = new CartItemCmdImpl(null, prePayTicketSlot.getProduct(), prePayTicketSlot.getStartDate(),
                prePayTicketSlot.getExpiryDate(), null, null, startZone, endZone, 
                	refundCalculationBasisService.getRefundCalculationBasis(
                                prePayTicketSlot.getExpiryDate(), refundCartType, cardId, travelCardType,
                                prePayTicketSlot.getStartDate(), startZone, endZone, false,false));
        cartItemCmdImpl.setCardId(cardId);
        cartItemCmdImpl.setTicketType(TRAVEL_CARD.code());
        cartItemCmdImpl.setCartType(refundCartType);
        cartItemCmdImpl.setTravelCardType(travelCardType);
        cartItemCmdImpl.setDiscountType(prePayTicketSlot.getDiscount());
        cartItemCmdImpl.setPassengerType(prePayTicketSlot.getPassengerType());
        return cartItemCmdImpl;
    }
}
