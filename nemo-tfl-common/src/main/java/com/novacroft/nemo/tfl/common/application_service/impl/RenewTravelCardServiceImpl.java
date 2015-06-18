package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.TravelCardDurationUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RenewTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.EmailReminder;
import com.novacroft.nemo.tfl.common.constant.OysterCardDiscountType;
import com.novacroft.nemo.tfl.common.constant.OysterCardPassengerType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.UserCartValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.common.constant.DateConstant.DAY_WEEK_DATE_PATTERN;
import static com.novacroft.nemo.common.utils.DateUtil.*;
import static com.novacroft.nemo.common.utils.ProductDateUtil.getProductStartDates;
import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.*;
import static com.novacroft.nemo.tfl.common.constant.CartType.RENEW;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.TRAVEL_CARD_OVERLAP;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToEndZone;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.convertCubicZoneDescriptionToStartZone;

@Service("renewTravelCardService")
public class RenewTravelCardServiceImpl implements RenewTravelCardService {

    @Autowired
    protected CardDataService cardDataSerivce;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected UserCartValidator userCartValidator;
    @Autowired
    protected TopUpTicketService topUpTicketService;
    @Autowired
    protected ZoneService zoneService;
    @Autowired
    protected CartService cartService;


    @Override
    public CartCmdImpl getCartItemsFromCubic(CartDTO cartDTO, Long cardId) {
    	if(!cartDTO.getPpvRenewItemAddFlag()){
    		cartService.emptyCart(cartDTO);
    	}
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardProductDTOFromCubic(getCardNumberFromCardId(cardId));
        return persistTravelCardsFromCubicAlongWithCart(cardInfoResponseV2DTO, cardId, cartDTO);
    }

    protected CardInfoResponseV2DTO getCardProductDTOFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    public String getCardNumberFromCardId(Long cardId) {
        CardDTO cardDTO = cardDataSerivce.findById(cardId);
        return (cardDTO != null) ? cardDTO.getCardNumber() : null;
    }

    protected CartCmdImpl persistTravelCardsFromCubicAlongWithCart(CardInfoResponseV2DTO cardInfoResponseV2DTO, Long cardId, CartDTO cartDTO) {
        CartCmdImpl cartCmd = new CartCmdImpl();
        if (cardInfoResponseV2DTO.getPptDetails() != null) {
            List<CartItemCmdImpl> cartItemList = new ArrayList<CartItemCmdImpl>();
            for (PrePayTicketSlot prePayTicketSlot : cardInfoResponseV2DTO.getPptDetails().getPptSlots()) {
                CartItemCmdImpl cartItemCmdImpl = persistTravelCardFromPrePayTicketSlotAlongWithCart(prePayTicketSlot, cardId, cartDTO);
                if (null!=cartItemCmdImpl){
                    cartItemList.add(cartItemCmdImpl);
                }
            }
            cartCmd.setCartItemList(cartItemList);
        }
        return cartCmd;
    }

    protected CartItemCmdImpl persistTravelCardFromPrePayTicketSlotAlongWithCart(PrePayTicketSlot prePayTicketSlot, long cardId, CartDTO cartDTO) {
        CartItemCmdImpl cartItemCmdImpl = null;
        if (isNotBlank(prePayTicketSlot.getProduct()) && isQuickBuyProduct(prePayTicketSlot.getExpiryDate())) {
            cartItemCmdImpl = new CartItemCmdImpl(null, prePayTicketSlot.getProduct(), getStartDate(prePayTicketSlot.getExpiryDate()), getEndDate(prePayTicketSlot.getStartDate(), prePayTicketSlot.getExpiryDate()), EmailReminder.FOUR_DAYS_PRIOR.code(), null, convertCubicZoneDescriptionToStartZone(prePayTicketSlot.getZone()), convertCubicZoneDescriptionToEndZone(prePayTicketSlot.getZone()), null);
            initializeCartItemCmdForTravelCard(cartItemCmdImpl, cardId, prePayTicketSlot.getStartDate(), prePayTicketSlot.getExpiryDate());
            if (!isOverlappingWithCubicProducts(cartItemCmdImpl, cardId) && !cartDTO.getPpvRenewItemAddFlag()) {
            	cartItemCmdImpl.setPassengerType(OysterCardPassengerType.ADULT.code());
            	cartItemCmdImpl.setDiscountType(OysterCardDiscountType.NO_DISCOUNT.code());
                cartService.addItem(cartDTO, cartItemCmdImpl, ProductItemDTO.class);
            }
        }
        return cartItemCmdImpl;
    }

    protected void initializeCartItemCmdForTravelCard(CartItemCmdImpl cartItemCmdImpl, long cardId, String startDate, String endDate) {
        cartItemCmdImpl.setCardId(cardId);
        cartItemCmdImpl.setTicketType(com.novacroft.nemo.tfl.common.constant.TicketType.TRAVEL_CARD.code());
        cartItemCmdImpl.setCartType(RENEW.code());
        cartItemCmdImpl.setTravelCardType(getTravelCardDurationFromStartAndEndDate(startDate, endDate));
    }

    protected boolean isQuickBuyProduct(String expiryDate) {
        int dateDiff = (int) getDateDiffIncludingStartDate(new Date(), parse(expiryDate));
        int quickBuyThreshold = systemParameterService.getIntegerParameterValue(QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        return dateDiff <= quickBuyThreshold && dateDiff >= 0;
    }

    protected String getStartDate(String expiryDate) {
        return formatDate(addDaysToDate(expiryDate, systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)));
    }

    protected String getEndDate(String startDate, String expiryDate) {
        return formatDate(TravelCardDurationUtil.getRenewEndDate(startDate, expiryDate, systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)));
    }

    @Override
    public SelectListDTO getRenewProductStartDateList(String startDateStr) {
        List<Date> startDateList = getProductStartDates(startDateStr, systemParameterService.getIntegerParameterValue(PRODUCT_AVAILABLE_DAYS));
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(START_DATES);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (Date startDate : startDateList) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(formatDate(startDate), formatDate(startDate, DAY_WEEK_DATE_PATTERN)));
        }
        return selectListDTO;
    }

    public void renewProducts(CartCmdImpl cmd, CartDTO cartDTO) {
        int index = 0;
        for (CartItemCmdImpl cartItemCmdImpl : cmd.getCartItemList()) {
            ProductItemDTO itemDTO = (ProductItemDTO) cartDTO.getCartItems().get(index);
			cartItemCmdImpl.setId(itemDTO.getId());
            cartItemCmdImpl.setEndDate(getRenewEndDate(formatDate(itemDTO.getStartDate()), cartItemCmdImpl.getStartDate(), formatDate(itemDTO.getEndDate())));
            cartItemCmdImpl.setStartZone(itemDTO.getStartZone());
            cartItemCmdImpl.setEndZone(itemDTO.getEndZone());
            cartItemCmdImpl.setPassengerType(OysterCardPassengerType.ADULT.code());
            cartItemCmdImpl.setDiscountType(OysterCardDiscountType.NO_DISCOUNT.code());
            cartService.addUpdateItem(cartDTO, cartItemCmdImpl, ProductItemDTO.class);
            index++;
        }
    }

    @Override
    public boolean isOverlappingWithCubicProducts(CartItemCmdImpl cartItem, Long cardId) {
        CartCmdImpl cartCmd = new CartCmdImpl();
        cartCmd.setCardId(cardId);
        cartCmd = topUpTicketService.updateCartItemCmdWithProductsFromCubic(cartCmd);
        List<CartItemCmdImpl> cubicItemList = cartCmd.getCartItemList();

        BindException errors = new BindException(cartItem, "cartItemCmd");

        return validateOverlappingProducts(cartItem, cubicItemList, errors).hasErrors();
    }

    protected BindException validateOverlappingProducts(CartItemCmdImpl cartItem, List<CartItemCmdImpl> cubicItemList, BindException errors) {
        cartItem.setTravelCardType(getTravelCardDurationFromStartAndEndDate(cartItem.getStartDate(), cartItem.getEndDate()));
        checkTravelCardOverlapWithAlreadyAddedTravelCardZones(cubicItemList, cartItem, errors);

        return errors;
    }

    public void checkTravelCardOverlapWithAlreadyAddedTravelCardZones(List<CartItemCmdImpl> cartItems, CartItemCmdImpl cmd, Errors errors) {
        if (cmd.getStartDate() != null && cmd.getStartZone() != null && cmd.getEndZone() != null) {
            Date startDate = parse(cmd.getStartDate());
            Date endDate = DateUtil.parse(getEndDate(cmd.getStartDate(), cmd.getEndDate()));
            Integer startZone = cmd.getStartZone();
            Integer endZone = cmd.getEndZone();

            for (CartItemCmdImpl cartItem : cartItems) {
                if ((cartItem != null) && (cartItem.getItem().contains(TRAVEL_CARD) && zoneService.isZonesOverlapWithTravelCardZones(cartItem, startDate, endDate, startZone, endZone))) {
                    errors.reject(TRAVEL_CARD_OVERLAP.errorCode());
                    break;
                }
            }
        }
    }

    protected String getRenewEndDate(String productStartDate, String cartItemStartDate, String cartItemEndDate) {
        return formatDate(addDaysToDate(cartItemEndDate, (int) getDateDiff(productStartDate, cartItemStartDate)));
    }

}
