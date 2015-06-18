package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatStringAsDate;
import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.REMINDER_DATE_NONE;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.PRO_RATA;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToEndZone;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToStartZone;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.tfl.common.application_service.ConvertCubicCardDetailsToCartDTOService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.data_service.DiscountTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PassengerTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.DurationUtil;

@Service(value = "convertCubicCardDetailsToCartDTOService")
public class ConvertCubicCardDetailsToCartDTOServiceImpl implements ConvertCubicCardDetailsToCartDTOService {

    private static final Integer PREPAY_TICKET_INVALID_STATE = 0;
    private static final String DEFAULT_PASSENGER_TYPE = "Adult";
    private static final String DEFAULT_DISCOUNT_TYPE = "No Discount";

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected PayAsYouGoDataService payAsYouGoDataService;

    @Autowired
    protected AutoTopUpDataService autoTopUpDataService;

    @Autowired
    protected CardRefundableDepositDataService cardRefundableDepositDataService;

    @Autowired
    protected PassengerTypeDataService passengerTypeDataService;

    @Autowired
    protected DiscountTypeDataService discountTypeDataService;

    @Override
    public CartDTO populateCartItemsToCartDTOFromCubic(CartDTO cartDTO, Long cardId) {

        return updateCartDTOWithProductsFromCubic(cartDTO, cardId);
    }

    protected CartDTO updateCartDTOWithProductsFromCubic(CartDTO cartDTO, Long cardId) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardDetailsFromCubic(cardId);
        if (cardInfoResponseV2DTO != null) {
            cartDTO = updateCartDTOWithTickets(cardInfoResponseV2DTO, cartDTO.getCardId(), cartDTO);
            cartDTO = updateCartDTOWithPayAsYouGoItem(cardInfoResponseV2DTO, cartDTO.getCardId(), cartDTO);
            cartDTO = updateCartDTOWithAutoTopItem(cardInfoResponseV2DTO, cartDTO);
            cartDTO = updateCartDTOWithDepositItem(cardInfoResponseV2DTO, cartDTO);
        }

        return cartDTO;
    }

    @Override
    public CardInfoResponseV2DTO getCardDetailsFromCubic(Long cardId) {
        return getCardProductDTOFromCubic(getCardNumberFromCardId(cardId));
    }

    protected CartDTO updateCartDTOWithAutoTopItem(CardInfoResponseV2DTO cardInfoResponseV2DTO, CartDTO cartDTO) {
        if (cardInfoResponseV2DTO.isAutoTopUpEnabled()) {
            AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
            Integer autoTopUpAmt = AutoLoadState.lookUpAmount(cardInfoResponseV2DTO.getAutoLoadState());
            AutoTopUpDTO autoTopUpDTO = autoTopUpDataService.findByAutoTopUpAmount(autoTopUpAmt);
            autoTopUpItemDTO.setAutoTopUpId(autoTopUpDTO.getId());
            autoTopUpItemDTO.setAutoTopUpAmount(autoTopUpAmt);
            autoTopUpItemDTO.setPrice(autoTopUpAmt);
            autoTopUpItemDTO.setCardId(cartDTO.getCardId());
            cartDTO.getCartItems().add(autoTopUpItemDTO);
        }
        return cartDTO;
    }

    protected CartDTO updateCartDTOWithDepositItem(CardInfoResponseV2DTO cardInfoResponseV2DTO, CartDTO cartDTO) {
        if (cardInfoResponseV2DTO.getCardDeposit() > 0) {
            CardRefundableDepositItemDTO cardRefundableDepositItemDTO = new CardRefundableDepositItemDTO();
            CardRefundableDepositDTO cardRefundableDepositDTO = cardRefundableDepositDataService.findByPrice(cardInfoResponseV2DTO.getCardDeposit());
            cardRefundableDepositItemDTO.setCardRefundableDepositId(cardRefundableDepositDTO.getId());
            cardRefundableDepositItemDTO.setPrice(cardInfoResponseV2DTO.getCardDeposit());
            cardRefundableDepositItemDTO.setCardId(cartDTO.getCardId());
            cartDTO.getCartItems().add(cardRefundableDepositItemDTO);
        }
        return cartDTO;
    }

    protected CartDTO updateCartDTOWithTickets(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartDTO cartDTO) {
        if (cardInfoResponseV2DTO.getPptDetails() != null && cardInfoResponseV2DTO.getPptDetails().getPptSlots() != null) {
            for (PrePayTicketSlot prePayTicketSlot : cardInfoResponseV2DTO.getPptDetails().getPptSlots()) {
                if (isNotBlank(prePayTicketSlot.getProduct()) && (null == prePayTicketSlot.getState() || prePayTicketSlot.getState() != PREPAY_TICKET_INVALID_STATE)) {
                    cartDTO.getCartItems().add(convertPrePayTicketSlotToCartItemDTO(prePayTicketSlot, cardId));
                }
            }
        }
        return cartDTO;
    }


    protected ProductItemDTO convertPrePayTicketSlotToCartItemDTO(PrePayTicketSlot prePayTicketSlot, Long cardId) {
        ProductItemDTO productItemDTO = null;
        Date startDate = formatStringAsDate(prePayTicketSlot.getStartDate());
        Date endDate = formatStringAsDate(prePayTicketSlot.getExpiryDate());

        String travelCardType = getTravelCardDurationFromStartAndEndDate(prePayTicketSlot.getStartDate(), prePayTicketSlot.getExpiryDate());
        Integer startZone = convertCubicZoneDescriptionToStartZone(prePayTicketSlot.getZone());
        Integer endZone = convertCubicZoneDescriptionToEndZone(prePayTicketSlot.getZone());
        PassengerTypeDTO passengerTypeDTO;
        DiscountTypeDTO discountTypeDTO;
        passengerTypeDTO = findPassengerTypeCodeByName(prePayTicketSlot);
        discountTypeDTO = findDiscountTypeCodeByName(prePayTicketSlot);
        String type = StringUtils.containsIgnoreCase(prePayTicketSlot.getProduct(), ProductItemType.BUS_PASS.code()) ? ProductItemType.BUS_PASS.databaseCode() :ProductItemType.TRAVEL_CARD.databaseCode(); 
        ProductDTO productDTO;
        if (!travelCardType.equals(Durations.OTHER.getDurationType())) {
            productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(travelCardType, startZone, endZone, startDate,
                            passengerTypeDTO.getCode(), discountTypeDTO.getCode(), type);
        } else {
            DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(startDate, endDate);
            productDTO = productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(durationPeriod.getFromDurationCode(),
                            durationPeriod.getToDurationCode(), startZone, endZone, startDate, passengerTypeDTO.getCode(), discountTypeDTO.getCode(), type);
        }
        Long productId = null;
        if (null != productDTO) {
            productId = productDTO.getId();
        }
        productItemDTO = new ProductItemDTO(null, prePayTicketSlot.getProduct(), cardId, 0, productId, startDate, endDate, startZone, endZone, null,
                        PRO_RATA.code(), null);
        productItemDTO.setCardId(cardId);
        return productItemDTO;
    }

    protected DiscountTypeDTO findDiscountTypeCodeByName(PrePayTicketSlot prePayTicketSlot) {
        DiscountTypeDTO discountTypeDTO;
        if (null != prePayTicketSlot.getDiscount()) {
            discountTypeDTO = discountTypeDataService.findByName(prePayTicketSlot.getDiscount());
        } else {
            discountTypeDTO = discountTypeDataService.findByName(DEFAULT_DISCOUNT_TYPE);
        }
        return discountTypeDTO;
    }

    protected PassengerTypeDTO findPassengerTypeCodeByName(PrePayTicketSlot prePayTicketSlot) {
        PassengerTypeDTO passengerTypeDTO;
        if (null != prePayTicketSlot.getPassengerType()) {
            passengerTypeDTO = passengerTypeDataService.findByName(prePayTicketSlot.getPassengerType());
        } else {
            passengerTypeDTO = passengerTypeDataService.findByName(DEFAULT_PASSENGER_TYPE);
        }
        return passengerTypeDTO;
    }


    protected CartDTO updateCartDTOWithPayAsYouGoItem(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartDTO cartDTO) {
        if (cardInfoResponseV2DTO.getPpvDetails() != null) {
            Integer creditBalance = cardInfoResponseV2DTO.getPpvDetails().getBalance();
            PayAsYouGoDTO payAsYouGo = payAsYouGoDataService.findByTicketPrice(creditBalance);
            if (null != payAsYouGo) {
                PayAsYouGoItemDTO itemDTO = new PayAsYouGoItemDTO(cardId, creditBalance, payAsYouGo.getId(), null, null, REMINDER_DATE_NONE, null);
                itemDTO.setName(payAsYouGo.getPayAsYouGoName());
                cartDTO.getCartItems().add(itemDTO);
            }
        }
        return cartDTO;
    }

    public String getCardNumberFromCardId(Long cardId) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        return (cardDTO != null) ? cardDTO.getCardNumber() : null;
    }

    protected CardInfoResponseV2DTO getCardProductDTOFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

}
