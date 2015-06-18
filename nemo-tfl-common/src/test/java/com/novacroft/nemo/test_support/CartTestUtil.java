package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardObject3;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomer2;
import static com.novacroft.nemo.test_support.ItemTestUtil.ITEM_ID;
import static com.novacroft.nemo.test_support.ItemTestUtil.getNewProductItemDTO1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

/**
 * Utilities for Cart tests
 */
public final class CartTestUtil {

    public static final Long CART_ID_1 = 21L;
    public static final Long PURCHASE_CART_ID_1 = 2L;
    public static final Long REFUND_CART_ID_1 = 3L;
    public static final Long RENEW_CART_ID_1 = 4L;
    public static final Long ANONYMOUS_GOODWILL_REFUND_CART_ID_1 = 5L;
    public static final Long CARD_ID_1 = 10456789345L;
    public static final Long WEB_ACCOUNT_ID_1 = 5L;
    public static final String CART_TYPE_1 = "Purchase";
    public static final String CART_TYPE_2 = "Refund";
    public static final String CART_TYPE_FAILED_CARD= "FailedCardRefund";
    public static final String CART_TYPE_DESTROYED_CARD= "DestroyedCardRefund";
    public static final Long CART_ID_2 = 22L;
    public static final Long CUSTOMER_ID_2 = 23L;
    public static final Long WEB_ACCOUNT_ID_2 = 24L;
    public static final String TEST_USER_ID = "CartDAOTest";
    public static final Integer TEST_ADMINISTRATION_FEE_PRICE = 1220;
    public static final Integer TEST_PAY_AS_YOU_GO_PRICE = 1220;
    public static final Long TEST_REFUND_AMOUT = 4220l;
    public static final Long CART_ID_3 = 23L;
    public static final Integer TEST_GOODWILL_PAYMENT_FEE_PRICE = 1420;
    public static final Integer TEST_GOODWILL_PAYMENT_FEE_PRICE2 = 100;
    public static final Integer TEST_PAYASYOUGO_PAYMENT_FEE_PRICE = 20;
    public static final Long GOODWILL_REASON_ID = 1L;
    public static final Integer TEST_PAY_AS_YOU_GO_FEE_PRICE = 220;
    public static final String PRODUCT_NAME_1 = "Monthly Travelcard Zones 1 to 2";
    public static final String PRODUCT_CODE_1 = "2343";
    public static final String ANNUAL_BUS_PASS = "Annual Bus Pass - All London";
    public static final Integer TRAVEL_START_ZONE_1 = 1;
    public static final Integer TRAVEL_END_ZONE_1 = 5;
    public static final Long CUSTOMER_ID = 1L;
    public static final String FIRST_NAME_3 = "Bob";
    public static final String LAST_NAME_3 = "Milk";
    public static final String INITIALS_3 = "D";
    public static final Long CUSTOMER_ADDRESS_ID_3 = 3L;
    public static final Integer TEST_TOPAY_AMOUT = 422;
    public static final Integer TEST_CART_TOTOAL = 4220;
    public static final Integer TEST_SHIPPING_METHOD_ITEM_PRICE = 0;
    public static final Integer TEST_REFUNDABLE_DEPOSIT_PRICE = 500;
    public static final Integer TEST_TRAVEL_CARD_PRICE = 4000;
    public static final Long TEST_APPROVAL_ID = 4000L;
    public static final Long STATION_ID_1 = 1L;
    public static final Long EXTERNAL_CART_ID_1 = 123456L;
    public static final Long ITEM_ID1 = 40L;
    
    public static final int FUTURE_DATE_OF_REFUND_INCREMENT = 1;

    public static final int ONE_DAY = 1;
    
    public static final String PAY_AS_YOU_GO_AUTO_TOP_UP = "payAsYouGoAutoTopUp";
    private static final Integer TEST_FEE_PRICE_ONE = 1200;
    private static final Integer TEST_PRICE_TWO = 40;
    
    public static final String INV_INCOMPLETE_JOURNEYS = "InCompleteJourneysView";    
    public static final String INCOMPLETE_JOURNEY = "IncompleteJourneyView";
    public static final String NOTIFICATION_LIST = "notificationList";

    public static final String AD_HOC_LOAD = "AdhocLoad";
    
    public static final String AD_HOC_LOAD_TEST_DTO_OBJECT = "AdhocLoadTestDTO";
    
    public static final Integer ADMINISTRATION_FEE_PRICE = 6000;
    
    public static final String SESSION_ATTRIBUTE_SHOPPING_CART_DATA = "shoppingCartData";
    
    public static CartDTO getTestCartDTO1() {
        return getTestCartDTO(PURCHASE_CART_ID_1, CARD_ID_1, WEB_ACCOUNT_ID_1, CART_TYPE_1);
    }

    public static CartDTO getTestCartDTO2() {
        return getTestCartDTO(REFUND_CART_ID_1, CARD_ID_1, WEB_ACCOUNT_ID_1, CART_TYPE_2);
    }

    public static CartDTO getTestCartDTOWithPpvPickupLocation() {
        CartDTO cartDTO = getTestCartDTO(REFUND_CART_ID_1, CARD_ID_1, WEB_ACCOUNT_ID_1, CART_TYPE_2);
        cartDTO .setPpvPickupLocationAddFlag(true);
        return cartDTO;
    }

	public static CartDTO getTestCartDTOWithBusAndTravelCardItems() {
        return getTestCartDTOWithItems(REFUND_CART_ID_1, CARD_ID_1, WEB_ACCOUNT_ID_1, CART_TYPE_2, ItemTestUtil.getTestProductItemDTO1(),
                        ItemTestUtil.getTestProductItemDTO2());
    }
    
    public static CartDTO getTestCartDTOWithTravelCardAndPayAsYouGoItems() {
        return getTestCartDTOWithItems(REFUND_CART_ID_1, CARD_ID_1, WEB_ACCOUNT_ID_1, CART_TYPE_2, ItemTestUtil.getTestProductItemDTO1(),
                        ItemTestUtil.getTestProductItemDTO3());
    }
	
    public static Cart getTestCart1() {
        return getTestCart(CART_ID_1, CARD_ID_1, WEB_ACCOUNT_ID_1);
    }

    public static Cart getTestCart2() {
        return getTestCart(CART_ID_2, CUSTOMER_ID_2, WEB_ACCOUNT_ID_2);
    }
    
    public static Cart getTestCartWithApprovalId() {
    	Cart cart = getTestCart(CART_ID_2, CUSTOMER_ID_2, WEB_ACCOUNT_ID_2);
    	cart.setApprovalId(TEST_APPROVAL_ID);
    	return cart;
    }

    public static CartDTO getTestCartDTO(Long purchaseCartId, Long cardId, Long webAccountId, String cartType) {
        CartDTO dto = new CartDTO();
        dto.setId(purchaseCartId);
        dto.setCardId(cardId);
        dto.setWebaccountId(webAccountId);
        dto.setCartType(cartType);
        return dto;
    }

	public static CartDTO getTestCartDTOWithItems(Long purchaseCartId, Long cardId, Long webAccountId, String cartType, ItemDTO item1, ItemDTO item2) {
        CartDTO dto = new CartDTO();
        dto.setId(purchaseCartId);
        dto.setCardId(cardId);
        dto.setWebaccountId(webAccountId);
        dto.setCartType(cartType);
        dto.addCartItem(item1);
        dto.addCartItem(item2);
        return dto;
    }
	
    public static Cart getTestCart(Long purchaseCartId, Long cardId, Long webAccountId) {
        Cart cart = new Cart();
        cart.setId(purchaseCartId);
        cart.setCardId(cardId);
        cart.setWebAccountId(webAccountId);
        return cart;
    }

    public static Customer getNewTestCustomer() {
        return getCustomer2();
    }

    public static Cart getNewCartWithItem(Card card) {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setCardId(card.getId());
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
        cart.addItem(getNewAdminFeeItem(card));
        return cart;
    }

    public static Item getNewAdminFeeItem(Card card) {
        AdministrationFeeItem administrationFeeItem = new AdministrationFeeItem();
        administrationFeeItem.setCreatedUserId(TEST_USER_ID);
        administrationFeeItem.setCreatedDateTime(new Date());
        administrationFeeItem.setCardId(card.getId());
        administrationFeeItem.setPrice(TEST_ADMINISTRATION_FEE_PRICE);
        return administrationFeeItem;
    }

    public static Cart getNewCartWithItem(Customer customer) {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setCustomerId(customer.getId());
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
        cart.addItem(getNewAdminFeeItem(customer));
        return cart;
    }

    public static Item getNewAdminFeeItem(Customer customer) {
        AdministrationFeeItem administrationFeeItem = new AdministrationFeeItem();
        administrationFeeItem.setCreatedUserId(TEST_USER_ID);
        administrationFeeItem.setCreatedDateTime(new Date());
        administrationFeeItem.setCustomerId(customer.getId());
        administrationFeeItem.setPrice(TEST_ADMINISTRATION_FEE_PRICE);
        return administrationFeeItem;
    }

    public static Cart getNewCartWithItem() {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setId(CARD_ID_1);
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
        cart.addItem(getNewAdminFeeItem());
        return cart;
    }

    public static CartDTO getNewCartWithUsedTicketItem() {
        CartDTO cart = new CartDTO();
        getTestCart(cart);
        cart.addCartItem(getNewCartDTOWithProductItemWithUsedTicket());
        return cart;
    }
    
    public static CartDTO getNewCartWithPayAsYouGoItem(){
        CartDTO cart = new CartDTO();
        getTestCart(cart);
        cart.addCartItem(getPayAsYouGoItemDTO());
        cart.addCartItem(getAutoTopUpItemDTO());
        return cart;
    }

    public static CartDTO getNewCartWithItemEndDateBeforeDateOfRefund() {
        CartDTO cart = new CartDTO();
        getTestCart(cart);
        cart.addCartItem(getNewProductItemDTOWithEndDateBeforeDateOfRefund());
        return cart;
    }

    public static Item getNewAdminFeeItem() {
        AdministrationFeeItem administrationFeeItem = new AdministrationFeeItem();
        administrationFeeItem.setCreatedUserId(TEST_USER_ID);
        administrationFeeItem.setCreatedDateTime(new Date());
        administrationFeeItem.setPrice(TEST_ADMINISTRATION_FEE_PRICE);
        return administrationFeeItem;
    }

    public static ItemDTO getNewAdminFeeItemDTO() {
        AdministrationFeeItemDTO administrationFeeItemDTO = new AdministrationFeeItemDTO();
        administrationFeeItemDTO.setCreatedUserId(TEST_USER_ID);
        administrationFeeItemDTO.setCreatedDateTime(new Date());
        administrationFeeItemDTO.setId(ITEM_ID);
        administrationFeeItemDTO.setPrice(TEST_ADMINISTRATION_FEE_PRICE);
        return administrationFeeItemDTO;
    }

    public static ItemDTO getNewPayAsYouGoItemDTO() {
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouItem();
        payAsYouGoItemDTO.setPrice(TEST_PAY_AS_YOU_GO_PRICE);
        return payAsYouGoItemDTO;
    }

    public static ItemDTO getNewProductItemDTO() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedUserId(TEST_USER_ID);
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        productItemDTO.setName(PRODUCT_NAME_1);
        productItemDTO.setEndDate(getTomorrowsDate());
        productItemDTO.setStartZone(TRAVEL_START_ZONE_1);
        productItemDTO.setEndZone(TRAVEL_END_ZONE_1);
        productItemDTO.setTicketUnused(true);
        productItemDTO.setDateOfRefund(DateUtil.addDaysToDate(new Date(), DateTestUtil.FIVE_DAYS));
        return productItemDTO;
    }

    public static ItemDTO getNewCartDTOWithProductItemWithUsedTicket() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedUserId(TEST_USER_ID);
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        productItemDTO.setName(PRODUCT_NAME_1);
        productItemDTO.setEndDate(getTomorrowsDate());
        productItemDTO.setStartZone(TRAVEL_START_ZONE_1);
        productItemDTO.setEndZone(TRAVEL_END_ZONE_1);
        productItemDTO.setTicketUnused(false);
        productItemDTO.setDateOfRefund(new Date());
        productItemDTO.setPrice(TEST_TRAVEL_CARD_PRICE);
        return productItemDTO;
    }

    public static ItemDTO getNewProductItemDTOWithEndDateBeforeDateOfRefund() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedUserId(TEST_USER_ID);
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        productItemDTO.setName(PRODUCT_NAME_1);
        productItemDTO.setEndDate(getTomorrowsDate());
        productItemDTO.setStartZone(TRAVEL_START_ZONE_1);
        productItemDTO.setEndZone(TRAVEL_END_ZONE_1);
        productItemDTO.setEndDate(new Date());
        productItemDTO.setTicketUnused(true);
        productItemDTO.setDateOfRefund(DateUtil.addDaysToDate(new Date(), FUTURE_DATE_OF_REFUND_INCREMENT));
        return productItemDTO;
    }

    public static ItemDTO getNewProductItemDTOWithRefund() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedUserId(TEST_USER_ID);
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        productItemDTO.setName(PRODUCT_NAME_1);
        productItemDTO.setEndDate(getTomorrowsDate());
        productItemDTO.setStartZone(TRAVEL_START_ZONE_1);
        productItemDTO.setEndZone(TRAVEL_END_ZONE_1);
        Refund refund = new Refund();
        refund.setRefundAmount(TEST_REFUND_AMOUT);
        productItemDTO.setRefund(refund);
        return productItemDTO;
    }

    public static ItemDTO getExpiredProductItemDTO() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedUserId(TEST_USER_ID);
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        productItemDTO.setName(PRODUCT_NAME_1);
        productItemDTO.setEndDate(getExpiredDate());
        productItemDTO.setStartZone(TRAVEL_START_ZONE_1);
        productItemDTO.setEndZone(TRAVEL_END_ZONE_1);
        return productItemDTO;
    }

    public static ItemDTO getNewAnnualBusPassProductItemDTO() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedUserId(TEST_USER_ID);
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        productItemDTO.setName(ANNUAL_BUS_PASS);
        productItemDTO.setEndDate(getTomorrowsDate());
        productItemDTO.setPrice(TEST_PAY_AS_YOU_GO_FEE_PRICE);
        Refund refund = new Refund();
        refund.setRefundAmount(TEST_REFUND_AMOUT);
        productItemDTO.setRefund(refund);
        return productItemDTO;
    }

    public static CartDTO getAnnualBusPassCartDTO() {
        CartDTO cartDto = new CartDTO();
        cartDto.setId(CART_ID_1);
        cartDto.setCartItems(Arrays.asList(new ItemDTO[] { getNewAnnualBusPassProductItemDTO() }));
        return cartDto;
    }

    protected static Date getExpiredDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -DateTestUtil.FIVE_DAYS);
        return calendar.getTime();
    }

    public static Date getTomorrowsDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, ONE_DAY);
        return calendar.getTime();
    }
   
      
    public static Date getFiveDaysFromToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, DateTestUtil.FIVE_DAYS);
        return calendar.getTime();
    }
      
    public static Cart getNewCart() {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
        return cart;
    }

    public static Card getNewTestCard() {
        return getTestCardObject3();
    }

    public static Cart getNewCart(Card card) {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setCardId(card.getId());
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
        return cart;
    }

    public static CartDTO getNewCartDTOWithProductItemWithRefund() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getNewProductItemDTOWithRefund());
        return cartDTO;
    }

    public static Cart getNewCart(Customer customer) {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setCustomerId(customer.getId());
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
        return cart;
    }

    public static CartDTO getNewCartDTOWithItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CARD_ID_1);
        cartDTO.addCartItem(getNewAdminFeeItemDTO());
        cartDTO.setApprovalId(1L);
        return cartDTO;
    }

    public static CartDTO getCartDTOWithItemSelectedLocation1() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CARD_ID_1);
        cartDTO.addCartItem(getNewAdminFeeItemDTO());
        cartDTO.setPpvPickupLocationAddFlag(true);
        cartDTO.setApprovalId(1L);
        return cartDTO;
    }

    public static CartDTO getCartDTOWithItemSelectedLocation2() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CARD_ID_1);
        cartDTO.addCartItem(getNewAdminFeeItemDTO());
        cartDTO.setPpvPickupLocationAddFlag(false);
        cartDTO.setApprovalId(1L);
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithAnnualBusPassProductItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CARD_ID_1);
        cartDTO.addCartItem(getNewAnnualBusPassProductItemDTO());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithProductItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getNewProductItemDTO());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithThreeProductItems() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getNewProductItemDTO());
        cartDTO.addCartItem(getNewProductItemDTO());
        cartDTO.addCartItem(getNewProductItemDTO());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithExpiredProductItems() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getExpiredProductItemDTO());
        cartDTO.addCartItem(getExpiredProductItemDTO());

        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithGoodwillItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_3);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getNewGoodwillPaymentItemDTO());
        return cartDTO;
    }
    
    public static CartDTO getNewCartDTOWithShippingItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_3);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getShippingMethodItemDTO());
        return cartDTO;
    }

    public static ItemDTO getPayAsYouGoItemDTO() {
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouItem();
        payAsYouGoItemDTO.setPrice(TEST_PAYASYOUGO_PAYMENT_FEE_PRICE);
        payAsYouGoItemDTO.setPayAsYouGoId(new Long(12));
        return payAsYouGoItemDTO;
    }

    public static ItemDTO getPayAsYouGoItemDTO2() {
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouItem();
        return payAsYouGoItemDTO;
    }

    public static ItemDTO getNewGoodwillPaymentItemDTO() {
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();
        goodwillPaymentItemDTO.setCreatedUserId(TEST_USER_ID);
        goodwillPaymentItemDTO.setCreatedDateTime(new Date());
        goodwillPaymentItemDTO.setId(ITEM_ID);
        goodwillPaymentItemDTO.setPrice(TEST_GOODWILL_PAYMENT_FEE_PRICE);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(getNewGoodwillReasonDTO());
        return goodwillPaymentItemDTO;
    }

    public static GoodwillReasonDTO getNewGoodwillReasonDTO() {
        GoodwillReasonDTO goodwillReasonDTO = new GoodwillReasonDTO();
        goodwillReasonDTO.setReasonId(GOODWILL_REASON_ID);
        return goodwillReasonDTO;
    }

    public static CartDTO getNewCartDTOWithDateOfRefund() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setDateOfRefund(new Date());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithCartRefundTotal() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setDateOfRefund(new Date());
        cartDTO.addCartItem(getNewGoodwillPaymentItemDTO());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithCartRefundTotalForPayASyouGoItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setDateOfRefund(new Date());
        cartDTO.addCartItem(getPayAsYouGoItemDTO());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithPayAsYouGoItem() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setDateOfRefund(new Date());
        cartDTO.addCartItem(getPayAsYouGoItemDTO2());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithItem1() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCustomerId(CUSTOMER_ID);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CARD_ID_1);
        cartDTO.addCartItem(getNewAdminFeeItemDTO());
        return cartDTO;
    }

    public static CartDTO getNewCartDTOWithCardRefundableDepositItemDTO() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setDateOfRefund(new Date());
        cartDTO.addCartItem(getCardRefundableDepositItemDTO());
        return cartDTO;
    }

    public static CardRefundableDepositItemDTO getCardRefundableDepositItemDTO() {
        CardRefundableDepositItemDTO cardRefundableDepositItemDTO = new CardRefundableDepositItemDTO();
        cardRefundableDepositItemDTO.setCreatedUserId(TEST_USER_ID);
        cardRefundableDepositItemDTO.setCreatedDateTime(new Date());
        cardRefundableDepositItemDTO.setId(ITEM_ID);
        cardRefundableDepositItemDTO.setName(PRODUCT_NAME_1);
        cardRefundableDepositItemDTO.setCardRefundableDepositId(CARD_ID_1);
        cardRefundableDepositItemDTO.setPrice(TEST_REFUNDABLE_DEPOSIT_PRICE);
        return cardRefundableDepositItemDTO;
    }

    public static ItemDTO getAutoTopUpItemDTO() {
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
        autoTopUpItemDTO.setCreatedUserId(TEST_USER_ID);
        autoTopUpItemDTO.setCreatedDateTime(new Date());
        autoTopUpItemDTO.setId(ITEM_ID);
        autoTopUpItemDTO.setPrice(TEST_PAYASYOUGO_PAYMENT_FEE_PRICE);
        autoTopUpItemDTO.setAutoTopUpId(new Long(12));
        return autoTopUpItemDTO;
    }
    
    public static CartDTO getNewCartDTOWithPAYGAndATUItems() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CARD_ID_1);
        cartDTO.addCartItem(getNewPayAsYouGoItemDTO());
        cartDTO.addCartItem(getNewAutoTopUpItemDTO());
        cartDTO.setApprovalId(1L);
        return cartDTO;
    }
    
    public static ItemDTO getNewAutoTopUpItemDTO() {
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
        autoTopUpItemDTO.setCreatedUserId(TEST_USER_ID);
        autoTopUpItemDTO.setCreatedDateTime(new Date());
        autoTopUpItemDTO.setId(ITEM_ID);
        autoTopUpItemDTO.setAutoTopUpAmount(AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());
        return autoTopUpItemDTO;
    }
    
    public static ItemDTO getNewFailedAutoTopUpCaseItemDTO() {
    	FailedAutoTopUpCaseItemDTO autoTopUpItemDTO = new FailedAutoTopUpCaseItemDTO(CARD_ID_1, (double) AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());
    	autoTopUpItemDTO.setCreatedUserId(TEST_USER_ID);
    	autoTopUpItemDTO.setCreatedDateTime(new Date());
    	autoTopUpItemDTO.setId(ITEM_ID);
    	return autoTopUpItemDTO;
    }

    public static ItemDTO getShippingMethodItemDTO() {
        ShippingMethodItemDTO shippingItemDTO = new ShippingMethodItemDTO();
        shippingItemDTO.setCreatedUserId(TEST_USER_ID);
        shippingItemDTO.setCreatedDateTime(new Date());
        shippingItemDTO.setId(ITEM_ID);
        shippingItemDTO.setPrice(TEST_SHIPPING_METHOD_ITEM_PRICE);
        return shippingItemDTO;
    }

    public static List<ItemDTO> getNewAnnualBusPassProductItemDTOList() {

        List<ItemDTO> itemDTOlist = new ArrayList<ItemDTO>();
        ProductItemDTO productItemDTO1 = new ProductItemDTO();
        productItemDTO1.setCreatedUserId(TEST_USER_ID);
        productItemDTO1.setCreatedDateTime(new Date());
        productItemDTO1.setId(ITEM_ID);
        productItemDTO1.setName(ANNUAL_BUS_PASS);
        productItemDTO1.setEndDate(getTomorrowsDate());
        productItemDTO1.setPrice(TEST_FEE_PRICE_ONE);
        ProductItemDTO productItemDTO2 = new ProductItemDTO();
        productItemDTO2.setCreatedUserId(TEST_USER_ID);
        productItemDTO2.setCreatedDateTime(new Date());
        productItemDTO2.setId(ITEM_ID);
        productItemDTO2.setName(ANNUAL_BUS_PASS);
        productItemDTO2.setEndDate(getTomorrowsDate());
        productItemDTO2.setPrice(TEST_PRICE_TWO);
        itemDTOlist.add(productItemDTO1);
        itemDTOlist.add(productItemDTO2);
        return itemDTOlist;
    }

    public static CartDTO getCartDTOWithAllItemsForOrderNewCard() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setDateOfRefund(new Date());
        cartDTO.addCartItem(getCardRefundableDepositItemDTO());
        cartDTO.addCartItem(getShippingMethodItemDTO());
        cartDTO.addCartItem(getNewPayAsYouGoItemDTO());
        cartDTO.addCartItem(getNewAutoTopUpItemDTO());
        cartDTO.addCartItem(getNewFailedAutoTopUpCaseItemDTO());
        cartDTO.addCartItem(getNewAnnualBusPassProductItemDTO());
        cartDTO.addCartItem(getNewCartDTOWithProductItemWithUsedTicket());
        return cartDTO;
    }
    
    public static CartDTO getCartDTOWithAllItemsForOrderNewCardWithApprovalId() {
    	CartDTO cartDTO = getCartDTOWithAllItemsForOrderNewCard();
    	cartDTO.setApprovalId(TEST_APPROVAL_ID);
    	return cartDTO;
    }


    public static CartDTO getCartDTOWithProductItemWithRefundAndApprovalId() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CART_ID_1);
        cartDTO.setCreatedUserId(TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.addCartItem(getNewProductItemDTOWithRefund());
        cartDTO.setApprovalId(TEST_APPROVAL_ID);
        return cartDTO;
    }
    
    
    public static CartDTO getNewCartWithCollectPurchaseStartAndEndDates(){
        CartDTO cart = new CartDTO();
        getTestCart(cart);
        cart.addCartItem(getPayAsYouGoItemDTOFutureStartEndDates());
        return cart;
    }
  
    private static void getTestCart(CartDTO cart) {
        cart.setId(null);
        cart.setId(CARD_ID_1);
        cart.setCreatedUserId(TEST_USER_ID);
        cart.setCreatedDateTime(new Date());
    }
    
    public static ItemDTO getPayAsYouGoItemDTOFutureStartEndDates() {
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouItem();
        payAsYouGoItemDTO.setStartDate(getFiveDaysFromToday());
        return payAsYouGoItemDTO;
    }
    
    private static PayAsYouGoItemDTO getTestPayAsYouItem() {
        PayAsYouGoItemDTO payAsYouGoItemDTO = new PayAsYouGoItemDTO();
        payAsYouGoItemDTO.setCreatedUserId(TEST_USER_ID);
        payAsYouGoItemDTO.setCreatedDateTime(new Date());
        payAsYouGoItemDTO.setId(ITEM_ID);
        return payAsYouGoItemDTO;
    }
    
    public static CartDTO getNewCartWithCollectPurchaseStartAndEndDatesTomorrow(){
        CartDTO cart = new CartDTO();
        getTestCart(cart);
        cart.addCartItem(getPayAsYouGoItemDTOTomorrowStartEndDatesTomorrow());
        return cart;
    }
    
    public static ItemDTO getPayAsYouGoItemDTOTomorrowStartEndDatesTomorrow() {
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouItem();
        payAsYouGoItemDTO.setStartDate(getTomorrowsDate());
        return payAsYouGoItemDTO;
    }
    
    public static List<Long> getTestCartIdList() {
        List<Long> cartIdList = new ArrayList<>();
        cartIdList.add(CART_ID_2);
        cartIdList.add(CART_ID_3);
        return cartIdList;
    }
    
    public static Item getAutoTopUpItem() {
        AutoTopUpConfigurationItem autoTopUpItem = new AutoTopUpConfigurationItem();
        autoTopUpItem.setCreatedUserId(TEST_USER_ID);
        autoTopUpItem.setCreatedDateTime(new Date());
        autoTopUpItem.setAutoTopUpId(new Long(12));
        return autoTopUpItem;
    }
    
    public static Item getPayAsYouGoItem() {
        PayAsYouGoItem payAsYouGoItem = new PayAsYouGoItem();
        payAsYouGoItem.setCreatedUserId(TEST_USER_ID);
        payAsYouGoItem.setCreatedDateTime(new Date());
        payAsYouGoItem.setPayAsYouGoId(new Long(12));
        return payAsYouGoItem;
    }
    
    public static ItemDTO getTestItemDTOWithAdminFee() {
        AdministrationFeeItemDTO administrationFeeItemDTO = new AdministrationFeeItemDTO();
        administrationFeeItemDTO.setCreatedUserId(TEST_USER_ID);
        administrationFeeItemDTO.setId(ITEM_ID);
        administrationFeeItemDTO.setPrice(TEST_ADMINISTRATION_FEE_PRICE);
        return administrationFeeItemDTO;
    }
    
    public static ItemDTO getTestAdminFeeItemDTO() {
        AdministrationFeeItemDTO administrationFeeItemDTO = new AdministrationFeeItemDTO();
        administrationFeeItemDTO.setCreatedUserId(TEST_USER_ID);
        administrationFeeItemDTO.setCreatedDateTime(new Date());
        administrationFeeItemDTO.setId(ITEM_ID);
        administrationFeeItemDTO.setPrice(ADMINISTRATION_FEE_PRICE);
        return administrationFeeItemDTO;
    }
    
    public static CartDTO getCartDTOWithProductItems() {
    	CartDTO cartDTO = getNewCartDTOWithProductItem(); 
        cartDTO.addCartItem(getNewProductItemDTO1());
        return cartDTO;
    }
    
    public static CartDTO getUpdatedCartDTOWithProductItems() {
    	CartDTO cartDTO = new CartDTO(); 
        cartDTO.addCartItem(getNewProductItemDTO1());
        return cartDTO;
    }

}
