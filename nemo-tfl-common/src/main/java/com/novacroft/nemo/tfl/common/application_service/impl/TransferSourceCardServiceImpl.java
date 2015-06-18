package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatStringAsDate;
import static com.novacroft.nemo.common.utils.DateUtil.isAfterOrEqual;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.ADULT;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.PAY_AS_YOU_GO_BALANCE;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.TRAVELCARD;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.TransferSourceCardService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.OysterCardDiscountType;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Service("transferSourceCardService")
public class TransferSourceCardServiceImpl implements TransferSourceCardService {

    @Autowired
    protected CardService cardService;

    @Autowired
    protected CartService cartService;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected CartDataService cartDataService;

    @Autowired
    protected OrderDataService orderDataService;

    @Autowired
    protected TravelCardService travelCardService;

    @Autowired
    protected HotlistService hotlistService;

    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public List<String> isSourceCardEligible(String cardNumber) {

        List<String> rulesBreaches = new ArrayList<String>();

        if (!checkCardHotlisted(cardNumber, rulesBreaches).isEmpty()) {
            return rulesBreaches;
        }
        checkPassengerTypeAdult(cardNumber, rulesBreaches);
        checkProductItemOfTypeBusOrTram(cardNumber, rulesBreaches);
        checkCardDiscounted(cardNumber, rulesBreaches);
        checkTravelCardAtleastOneDayRemaining(cardNumber, rulesBreaches);
        checkIfPayAsYouGoAmountIsMoreThanFifty(cardNumber, rulesBreaches);
        checkPayAsYouGoHasNegativeAmount(cardNumber, rulesBreaches);
        checkIfCardHasItemsToTransfer(cardNumber, rulesBreaches);
        return rulesBreaches;
    }

    protected List<String> checkCardHotlisted(String cardNumber, List<String> ruleBreaches) {
        if (hotlistService.isCardHotlisted(cardNumber)) {
            ruleBreaches.add(ContentCode.HOTLISTED.textCode());
        }
        return ruleBreaches;
    }

    protected void checkPassengerTypeAdult(String cardNumber, List<String> ruleBreaches) {
        if (!getCardInfoResponseFromCubic(cardNumber).getPassengerType().equalsIgnoreCase(ADULT)) {
            ruleBreaches.add(ContentCode.TRANSFERS_IS_PASSENGER_TYPE_ADULT.textCode());
        }
    }

    protected void checkProductItemOfTypeBusOrTram(String cardNumber, List<String> ruleBreaches) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        List<PrePayTicketSlot> prePayTicketList = cardInfoResponseV2DTO.getPptDetails().getPptSlots();
        for (PrePayTicketSlot prePayTicket : prePayTicketList) {
            if (!TRAVELCARD.equalsIgnoreCase(prePayTicket.getProduct())) {
                ruleBreaches.add(ContentCode.TRANSFERS_HAS_PRODUCT_ITEM_OF_TYPE_BUS.textCode());
                break;
            }

        }
    }

    protected void hasTravelCardBeenPreviouslyTraded(String cardNumber, List<String> ruleBreaches) {
        Long cardId = cardService.getCardIdFromCardNumber(cardNumber);
        OrderDTO orderDTO = orderDataService.findById(cardId);
        List<ItemDTO> itemDTOList = orderDTO.getOrderItems();
        for (ItemDTO itemDTO : itemDTOList) {
            if (itemDTO instanceof ProductItemDTO) {
                ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
                if (travelCardService.isTravelCard(itemDTO) && null != productItemDTO.getRelatedItem() && null != productItemDTO.getTradedDate()) {
                    ruleBreaches.add(ContentCode.TRANSFERS_TRAVELCARD_HAS_BEEN_TRADED_BEFORE.textCode());
                }
            }
        }
    }

    protected void isAutoTopEnabledInCard(String cardNumber, List<String> ruleBreaches) {
        if (!getCardInfoResponseFromCubic(cardNumber).isAutoTopUpEnabled()) {
            ruleBreaches.add(ContentCode.TRANSFERS_AUTO_TOP_UP_DISABLED.textCode());
        }
    }

    protected void checkTravelCardAtleastOneDayRemaining(String cardNumber, List<String> ruleBreaches) {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        List<PrePayTicketSlot> listOfPrePayTickets = cardInfoResponseV2DTO.getPptDetails().getPptSlots();
        for (PrePayTicketSlot prePayTicket : listOfPrePayTickets) {
            Date expiryDate = formatStringAsDate(prePayTicket.getExpiryDate());
            if (expiryDate != null) {
                Date currentDate = formatStringAsDate(DateUtil.formatDate(new Date()));
                if (isAfterOrEqual(currentDate, expiryDate)) {
                    ruleBreaches.add(ContentCode.TRANSFERS_TRAVELCARD_ATLEASTONEDAY_REMAINING.textCode());
                    break;
                }                
            }
        }
        
    }

    protected void checkCardDiscounted(String cardNumber, List<String> ruleBreaches) {

        if (isCardDiscountFieldNotBlank(cardNumber)) {
            checkCardEighteenPlusDiscounted(cardNumber, ruleBreaches);
            checkCardApprenticeDiscounted(cardNumber, ruleBreaches);
        }
        checkCardJobCenterPlusDiscounted(cardNumber, ruleBreaches);
    }

    protected Boolean isCardDiscountFieldNotBlank(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        return (isNotBlank(cardInfoResponseV2DTO.getDiscountEntitlement1()) || isNotBlank(cardInfoResponseV2DTO.getDiscountEntitlement2()) || isNotBlank(cardInfoResponseV2DTO
                        .getDiscountEntitlement3()));
    }

    protected void checkCardEighteenPlusDiscounted(String cardNumber, List<String> ruleBreaches) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        if (OysterCardDiscountType.EIGHTEEN_PLUS.code().equalsIgnoreCase(cardInfoResponseV2DTO.getDiscountEntitlement1())
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry1())) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_EIGHTEEN_PLUS_DISCOUNTED.textCode());
        } else if (OysterCardDiscountType.EIGHTEEN_PLUS.code().equalsIgnoreCase(cardInfoResponseV2DTO.getDiscountEntitlement2())
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry2())) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_EIGHTEEN_PLUS_DISCOUNTED.textCode());
        } else if (OysterCardDiscountType.EIGHTEEN_PLUS.code().equalsIgnoreCase(cardInfoResponseV2DTO.getDiscountEntitlement3())
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry3())) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_EIGHTEEN_PLUS_DISCOUNTED.textCode());
        }
    }

    protected Boolean isDiscountEntitlementExpired(String dateOfExpiry) {
        if (isNotBlank(dateOfExpiry)) {
            Date expiryDate = DateUtil.formatStringAsDate(dateOfExpiry);
            int days = Days.daysBetween(new DateTime(new Date()), new DateTime(expiryDate)).getDays();
            return (days >= 0);
        }
        return Boolean.FALSE;
    }

    protected void checkCardJobCenterPlusDiscounted(String cardNumber, List<String> ruleBreaches) {
        if (hasJobCenterPlusDiscountsOnTravelCards(cardNumber)) {
            ruleBreaches.add(ContentCode.TRANSFERS_TRAVELCARD_JOB_CENTRE_PLUS_DISCOUNTED.textCode());
        }
    }

    protected void checkCardApprenticeDiscounted(String cardNumber, List<String> ruleBreaches) {

        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        if (OysterCardDiscountType.APPRENTICE.code().equalsIgnoreCase(cardInfoResponseV2DTO.getDiscountEntitlement1())
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry1())) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_APPRENTICE_DISCOUNTED.textCode());
        } else if (OysterCardDiscountType.APPRENTICE.code().equalsIgnoreCase(cardInfoResponseV2DTO.getDiscountEntitlement2())
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry2())) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_APPRENTICE_DISCOUNTED.textCode());
        } else if (OysterCardDiscountType.APPRENTICE.code().equalsIgnoreCase(cardInfoResponseV2DTO.getDiscountEntitlement3())
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry3())) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_APPRENTICE_DISCOUNTED.textCode());
        }

    }

    protected Boolean hasTravelCards(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        List<PrePayTicket> pendingItemsTicketList = cardInfoResponseV2DTO.getPendingItems().getPpts();
        List<PrePayTicketSlot> prePayTicketList = cardInfoResponseV2DTO.getPptDetails().getPptSlots();
        for (PrePayTicketSlot prePayTicket : prePayTicketList) {
            if (StringUtils.contains(TRAVELCARD, prePayTicket.getProduct())) {
                return Boolean.TRUE;
            }
        }
        for (PrePayTicket pendingPrePayTicket : pendingItemsTicketList) {
            ProductDTO productDTO = productDataService.findByProductCode(pendingPrePayTicket.getProductCode().toString(),
                            formatStringAsDate(pendingPrePayTicket.getStartDate()));
            if (productDTO.getType().contains(TRAVELCARD)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    protected Boolean hasJobCenterPlusDiscountsOnTravelCards(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        List<PrePayTicketSlot> prePayTicketList = cardInfoResponseV2DTO.getPptDetails().getPptSlots();
        for (PrePayTicketSlot prePayTicket : prePayTicketList) {
            if (StringUtils.contains(TRAVELCARD, prePayTicket.getProduct())
                            && OysterCardDiscountType.JOB_CENTRE_PLUS.code().equalsIgnoreCase(prePayTicket.getDiscount())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    protected void checkIfPayAsYouGoAmountIsMoreThanFifty(String cardNumber, List<String> ruleBreaches) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        if ((cardInfoResponseV2DTO.getPpvDetails() != null && getPayAsYouGoWithDeposit(cardNumber) > PAY_AS_YOU_GO_BALANCE)) {
            ruleBreaches.add(ContentCode.TRANSFERS_HAS_PAY_AS_YOU_GO_ITEM_MORE_THAN_FIFTY_POUNDS.textCode());
        }
    }

    protected void checkIfCardHasItemsToTransfer(String cardNumber, List<String> ruleBreaches) {
        if (!hasCardAnyItemsToTransfer(cardNumber)) {
            ruleBreaches.add(ContentCode.TRANSFERS_CARD_HAS_NO_PRODUCTS_TO_TRANSFER.textCode());
        }
    }

    protected Boolean hasCardAnyItemsToTransfer(String cardNumber) {
        return hasTravelCards(cardNumber) || hasPayAsYouGoItem(cardNumber);
    }

    protected Boolean hasPayAsYouGoItem(String cardNumber) {
        return (getPayAsYouGoAmount(cardNumber) > 0);
    }

    protected Boolean hasDepositOnCard(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        return (cardInfoResponseV2DTO.getCardDeposit() > 0);
    }

    protected Boolean isPayAsYouGoAmountInNegative(String cardNumber) {
        return (getPayAsYouGoAmount(cardNumber) < 0);
    }

    protected Integer getPayAsYouGoAmount(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardInfoResponseFromCubic(cardNumber);
        return cardInfoResponseV2DTO.getPpvDetails().getBalance();
    }

    protected Integer getPayAsYouGoWithDeposit(String cardNumber) {
        return getPayAsYouGoAmount(cardNumber) + getCardInfoResponseFromCubic(cardNumber).getCardDeposit();

    }

    protected void checkPayAsYouGoHasNegativeAmount(String cardNumber, List<String> ruleBreaches) {
        if (isPayAsYouGoAmountInNegative(cardNumber)) {
            ruleBreaches.add(ContentCode.TRANSFERS_PAYASYOUGO_HAS_NEGATIVE_AMOUNT.textCode());
        }
    }

    protected CardInfoResponseV2DTO getCardInfoResponseFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

}
