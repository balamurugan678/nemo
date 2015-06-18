package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems10;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems4;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems5;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems7;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems8;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestCartItems9;
import static com.novacroft.nemo.test_support.CartTestUtil.AD_HOC_LOAD;
import static com.novacroft.nemo.test_support.CartTestUtil.getCartDTOWithItemSelectedLocation1;
import static com.novacroft.nemo.test_support.CartTestUtil.getCartDTOWithItemSelectedLocation2;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.WEB_ACCOUNT_ID_1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.ItemType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Utilities for get Oyster card tests
 */
public final class CartCmdTestUtil {

    public static final String PAGE_NAME_1 = "test-page";
    public static final String SUBMIT_VAL_1 = "test-submit-val";
    public static final String TRANSCATION_ID_1 = "test-transaction-val";
    public static final String ADDRESS_FOR_POSTCODE_1 = "NN3 5UR";
    public static final Boolean AUTO_TOPUP_VISIBLE_1 = Boolean.TRUE;
    public static final String TICKET_TYPE_1 = "test-ticket-type";
    public static final String SHIPPING_TYPE_1 = "test-shipping-type";
    public static final String SELECTED_SHIPPING_TYPE_1 = "test-shipping-type";
    public static final Integer SUB_TOTAL_AMT_1 = 9999;
    public static final Integer SHIPPING_COST_1 = 999;
    public static final Integer REFUNDABLE_DEPOSIT_AMT_1 = 99;
    public static final Integer TOTAL_AMT_1 = 99999;
    public static final Boolean PAYMENT_TERMS_ACCEPTED_1 = Boolean.FALSE;
    public static final Integer AUTO_TOPUP_AMT = 20;
    public static final Date DATE_OF_REFUND_1 = new Date();
    public static final int LINE_NO = 1;
    public static final int CART_ITEM_ID = 1;
    public static final Integer TOTAL_AMOUNT_1 = 9900;
    public static final Integer TOTAL_AMOUNT_2 = 9400;
    public static final Integer TOTAL_AMOUNT_3 = 0;
    public static final Integer TOTAL_AMOUNT_4 = 88300;
    public static final Integer TOTAL_AMOUNT_5 = 91600;

    public static final String FIRST_NAME_2 = "John";
    public static final String LAST_NAME_2 = "Martin";
    public static final String INITIALS_2 = "C";
    public static final String CUSTOMER_ADDRESS_LINE_1 = "test-address-line-1";
    public static final String CUSTOMER_ADDRESS_LINE_2 = "test-address-line-2";
    public static final String CUSTOMER_ADDRESS_STREET = "test-address-street";
    public static final String CUSTOMER_ADDRESS_TOWN = "test-address-town";
    public static final String CUSTOMER_ADDRESS_POST_CODE = "NN3 5UR";
    public static final String CUSTOMER_ADDRESS_INVALID_POST_CODE = "ABC";
    public static final CountryDTO CUSTOMER_ADDRESS_COUNTRY = getTestCountryDTO1();

    public static final String PAYEE_SORT_CODE = "20-03-00";
    public static final String PAYEE_ACCOUNT_NUMBER = "78655678";

    public static final Long STATION_500 = new Long(500);
    public static final Long STATION_507 = new Long(507);

    public static final Integer NEGATIVE_PAYASYOUGO_FEE_VALUE = -15;

    public static CartCmdImpl getTestCartCmd1() {
        return getTestCartCmd(PAGE_NAME_1, SUBMIT_VAL_1, POSTCODE_1, AUTO_TOPUP_VISIBLE_1, CARD_ID_1, TICKET_TYPE_1, SHIPPING_TYPE_1,
                        SELECTED_SHIPPING_TYPE_1, SUB_TOTAL_AMT_1, SHIPPING_COST_1, REFUNDABLE_DEPOSIT_AMT_1, TOTAL_AMT_1, PAYMENT_TERMS_ACCEPTED_1,
                        WEB_ACCOUNT_ID_1);
    }

    public static CartCmdImpl getTestCartCmd2() {
        return getTestCartCmd(PAGE_NAME_1, SUBMIT_VAL_1, POSTCODE_1, AUTO_TOPUP_VISIBLE_1, CARD_ID_1, TICKET_TYPE_1, SHIPPING_TYPE_1,
                        SELECTED_SHIPPING_TYPE_1, SUB_TOTAL_AMT_1, SHIPPING_COST_1, REFUNDABLE_DEPOSIT_AMT_1, TOTAL_AMT_1, PAYMENT_TERMS_ACCEPTED_1,
                        WEB_ACCOUNT_ID_1, DATE_OF_REFUND_1);
    }

    public static CartCmdImpl getTestRenewCartCmd3() {
        CartCmdImpl cartCmdImpl = getTestCartCmd(PAGE_NAME_1, SUBMIT_VAL_1, POSTCODE_1, AUTO_TOPUP_VISIBLE_1, CARD_ID_1, TICKET_TYPE_1,
                        SHIPPING_TYPE_1, SELECTED_SHIPPING_TYPE_1, SUB_TOTAL_AMT_1, SHIPPING_COST_1, REFUNDABLE_DEPOSIT_AMT_1, TOTAL_AMT_1,
                        PAYMENT_TERMS_ACCEPTED_1, WEB_ACCOUNT_ID_1, DATE_OF_REFUND_1);
        cartCmdImpl.getCartItemList().add(CartItemTestUtil.getTestTravelCard1());
        cartCmdImpl.getCartItemList().add(CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1());
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());

        return cartCmdImpl;
    }

    public static CartCmdImpl getTestRenewCartCmd4() {
        CartCmdImpl cartCmdImpl = getTestCartCmd(PAGE_NAME_1, SUBMIT_VAL_1, POSTCODE_1, AUTO_TOPUP_VISIBLE_1, CARD_ID_1, TICKET_TYPE_1,
                        SHIPPING_TYPE_1, SELECTED_SHIPPING_TYPE_1, SUB_TOTAL_AMT_1, SHIPPING_COST_1, REFUNDABLE_DEPOSIT_AMT_1, TOTAL_AMT_1,
                        PAYMENT_TERMS_ACCEPTED_1, WEB_ACCOUNT_ID_1, DATE_OF_REFUND_1);
        cartCmdImpl.getCartItemList().add(CartItemTestUtil.getTestTravelCard11());
        cartCmdImpl.getCartItemList().add(CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1());
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());

        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdWithPaymentCardSettlementDTO() {
        CartCmdImpl cartCmdImpl = getTestCartCmd(PAGE_NAME_1, SUBMIT_VAL_1, POSTCODE_1, AUTO_TOPUP_VISIBLE_1, CARD_ID_1, TICKET_TYPE_1,
                        SHIPPING_TYPE_1, SELECTED_SHIPPING_TYPE_1, SUB_TOTAL_AMT_1, SHIPPING_COST_1, REFUNDABLE_DEPOSIT_AMT_1, TOTAL_AMT_1,
                        PAYMENT_TERMS_ACCEPTED_1, WEB_ACCOUNT_ID_1);
        PaymentCardSettlementDTO paymentCardSettlementDTO = new PaymentCardSettlementDTO(ORDER_ID, TOTAL_AMOUNT_1, TRANSCATION_ID_1);
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        CartDTO cartDTO = new CartDTO();
        cartCmdImpl.setCartDTO(cartDTO);
        cartCmdImpl.getCartDTO().setPaymentCardSettlement(paymentCardSettlementDTO);
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd4() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.getCartItemCmd().setBackdated(true);
        cartCmdImpl.getCartItemCmd().setBackdatedRefundReasonId((long) 1);
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(true);
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd5() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd6() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems1());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd7() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems2());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd8() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems3());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd9() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems4());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd10() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems5());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd11() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd12() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem1());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd13() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd14() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems7());
        cartCmdImpl.setApprovalId(ApprovalTestUtil.APPROVAL_ID);
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        cartCmdImpl.setStationId(STATION_507);
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd15() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestTravelCard8());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd16() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems10());
        cartCmdImpl.setPaymentType(AD_HOC_LOAD);
        cartCmdImpl.setApprovalId(ApprovalTestUtil.APPROVAL_ID);
        cartCmdImpl.setCartDTO(getCartDTOWithItemSelectedLocation1());
        cartCmdImpl.setStationId(STATION_507);
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd17() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemList(getTestCartItems10());
        cartCmdImpl.setPaymentType(AD_HOC_LOAD);
        cartCmdImpl.setApprovalId(ApprovalTestUtil.APPROVAL_ID);
        cartCmdImpl.setCartDTO(getCartDTOWithItemSelectedLocation2());
        cartCmdImpl.setStationId(STATION_500);
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmd18() {
        CartCmdImpl cartCmdImpl = getTestCartCmd14();
        cartCmdImpl.setPayAsYouGoValue(NEGATIVE_PAYASYOUGO_FEE_VALUE);
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdWithOverlappingZones() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        cartCmdImpl.setCartItemList(getTestCartItems7());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdWithOverlappingZones2() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        cartCmdImpl.setCartItemList(getTestCartItems8());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdWithOverlappingZones3() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        cartCmdImpl.setCartItemList(getTestCartItems8());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdWithOverlappingZones4() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        cartCmdImpl.setCartItemList(getTestCartItems9());
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdCartItemIsNull() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartItemCmd(CartItemTestUtil.getTestGoodwillPayment2());
        cartCmdImpl.setCartDTO(getNewCartDTOWithItem());
        List<CartItemCmdImpl> cartItems = getTestCartItems2();
        cartItems.remove(0);
        cartItems.add(null);
        cartCmdImpl.setCartItemList(cartItems);
        return cartCmdImpl;
    }

    public static CartCmdImpl getTestCartCmdWithOrder() {
        CartCmdImpl cmd = getTestCartCmd(PAGE_NAME_1, SUBMIT_VAL_1, POSTCODE_1, AUTO_TOPUP_VISIBLE_1, CARD_ID_1, TICKET_TYPE_1, SHIPPING_TYPE_1,
                        SELECTED_SHIPPING_TYPE_1, SUB_TOTAL_AMT_1, SHIPPING_COST_1, REFUNDABLE_DEPOSIT_AMT_1, TOTAL_AMT_1, PAYMENT_TERMS_ACCEPTED_1,
                        WEB_ACCOUNT_ID_1);
        cmd.setCartDTO(getNewCartDTOWithItem());
        cmd.getCartDTO().setOrder(OrderTestUtil.getTestOrderDTO1());
        return cmd;
    }

    public static CartCmdImpl getTestCartCmd(String pageName, String submitVal, String addressForPostcode, Boolean autoTopupVisible, Long cardId,
                    String ticketType, String shippingType, String selectedShippingType, Integer subTotalAmt, Integer shippingCost,
                    Integer refundableDepositAmt, Integer totalAmt, Boolean paymentTermsAccepted, Long webAccountId) {
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setPageName(pageName);
        cmd.setAddressForPostcode(addressForPostcode);
        cmd.setAutoTopUpVisible(autoTopupVisible);
        cmd.setCardId(cardId);
        cmd.setTicketType(ticketType);
        cmd.setShippingType(shippingType);
        cmd.setSelectedShippingType(selectedShippingType);
        cmd.setSubTotalAmt(subTotalAmt);
        cmd.setShippingCost(shippingCost);
        cmd.setRefundableDepositAmt(refundableDepositAmt);
        cmd.setTotalAmt(totalAmt);
        cmd.setPaymentTermsAccepted(paymentTermsAccepted);
        cmd.setWebAccountId(webAccountId);
        return cmd;
    }

    public static CartCmdImpl getTestCartCmd(String pageName, String submitVal, String addressForPostcode, Boolean autoTopupVisible, Long cardId,
                    String ticketType, String shippingType, String selectedShippingType, Integer subTotalAmt, Integer shippingCost,
                    Integer refundableDepositAmt, Integer totalAmt, Boolean paymentTermsAccepted, Long webAccountId, Date dateOfRefund) {
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setPageName(pageName);
        cmd.setAddressForPostcode(addressForPostcode);
        cmd.setAutoTopUpVisible(autoTopupVisible);
        cmd.setCardId(cardId);
        cmd.setTicketType(ticketType);
        cmd.setShippingType(shippingType);
        cmd.setSelectedShippingType(selectedShippingType);
        cmd.setSubTotalAmt(subTotalAmt);
        cmd.setShippingCost(shippingCost);
        cmd.setRefundableDepositAmt(refundableDepositAmt);
        cmd.setTotalAmt(totalAmt);
        cmd.setPaymentTermsAccepted(paymentTermsAccepted);
        cmd.setWebAccountId(webAccountId);
        cmd.setDateOfRefund(dateOfRefund);
        cmd.setCartDTO(getNewCartDTOWithItem());
        return cmd;
    }

    public static CartCmdImpl getTestCartCmdItemWithDefaultItems() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO2();

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        cmd.setCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1);
        return cmd;
    }

    public static CartCmdImpl getTestCartCmdItemWithDefaultItemsAndOrderDetails() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        OrderDTO orderDto = OrderTestUtil.getTestOrderDTO1();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO2();

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        cmd.getCartDTO().setOrder(orderDto);
        return cmd;
    }

    public static CartCmdImpl getTestCartCmdItemWithDefaultItemsAndPayeeDetails() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO2();
        cmd.setFirstName(FIRST_NAME_2);
        cmd.setLastName(LAST_NAME_2);
        cmd.setPayeeAddress(getTestAddress());
        cmd.setPayeeSortCode(PAYEE_SORT_CODE);
        cmd.setPayeeAccountNumber(PAYEE_ACCOUNT_NUMBER);

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        return cmd;
    }

    public static CartCmdImpl getTestCartCmdItemWithDefaultItemsAndPayeeDetailsNoAccountDetails() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO2();
        cmd.setFirstName(FIRST_NAME_2);
        cmd.setLastName(LAST_NAME_2);
        cmd.setPayeeAddress(getTestAddress());

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        return cmd;
    }

    public static CartCmdImpl getTestCartCmdItemWithDefaultItemsAndPayeeDetailsInvalidAccountDetails() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO2();
        cmd.setFirstName(FIRST_NAME_2);
        cmd.setLastName(LAST_NAME_2);
        cmd.setPayeeAddress(getTestAddress());
        cmd.setPayeeSortCode(FIRST_NAME_2);
        cmd.setPayeeAccountNumber(LAST_NAME_2);

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        return cmd;
    }

    public static CartCmdImpl getTestCartCmdItemWithDefaultItemsAndPayeeDetailsInvalidAccountDetails2() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTODefaultPrice();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO2();
        cmd.setFirstName(FIRST_NAME_2);
        cmd.setLastName(LAST_NAME_2);
        cmd.setPayeeAddress(getTestAddressWithInvalidPostcode());
        cmd.setPayeeSortCode(FIRST_NAME_2);
        cmd.setPayeeAccountNumber(LAST_NAME_2);

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        return cmd;
    }

    public static AddressDTO getTestAddress() {
        AddressDTO address = new AddressDTO();
        address.setHouseNameNumber(CUSTOMER_ADDRESS_LINE_1);
        address.setStreet(CUSTOMER_ADDRESS_STREET);
        address.setTown(CUSTOMER_ADDRESS_TOWN);
        address.setPostcode(CUSTOMER_ADDRESS_POST_CODE);
        address.setCountry(CUSTOMER_ADDRESS_COUNTRY);
        return address;
    }

    public static AddressDTO getTestAddressWithInvalidPostcode() {
        AddressDTO address = new AddressDTO();
        address.setHouseNameNumber(CUSTOMER_ADDRESS_LINE_1);
        address.setStreet(CUSTOMER_ADDRESS_STREET);
        address.setTown(CUSTOMER_ADDRESS_TOWN);
        address.setPostcode(CUSTOMER_ADDRESS_INVALID_POST_CODE);
        address.setCountry(CUSTOMER_ADDRESS_COUNTRY);
        return address;
    }

    public static CartCmdImpl getTestCartCmdForItemWithoutDefaultItems() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = new CartDTO();
        AdministrationFeeItemDTO adminFeeDTO = AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO1();
        PayAsYouGoItemDTO payAsYouItemDto = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1();

        cartDto.addCartItem(payAsYouItemDto);
        cartDto.addCartItem(adminFeeDTO);
        cmd.setCartDTO(cartDto);
        cmd.setCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1);
        return cmd;
    }

    public static CartCmdImpl getRefundCalculationBasisChangedDefaultItems() {

        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDto = getCartDtoWithoutValuesOfRefundCalculationBasis();
        cmd.setCartDTO(cartDto);
        return cmd;
    }

    public static CartCmdImpl getPrePayValueCartCmdImpl() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDTO = CartTestUtil.getNewCartWithPayAsYouGoItem();
        cartDTO.setOrder(OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem());
        cartDTO.setPaymentCardSettlement(SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        cmd.setCartDTO(cartDTO);
        cmd.setStationId(STATION_500);
        return cmd;
    }

    public static CartCmdImpl getPrePayValueCartCmdWithNoPaymentCardSettlementImpl() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDTO = CartTestUtil.getNewCartWithPayAsYouGoItem();
        cartDTO.setOrder(OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem());
        cartDTO.setPaymentCardSettlement(null);
        cmd.setCartDTO(cartDTO);
        cmd.setStationId(STATION_500);
        return cmd;
    }

    public static CartCmdImpl getPrePayTicketCartCmdImpl() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithProductItem();
        cartDTO.setOrder(OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem());
        cartDTO.setPaymentCardSettlement(SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        cmd.setCartDTO(cartDTO);
        cmd.setStationId(STATION_500);
        return cmd;
    }

    public static CartCmdImpl getCartCmdWithPayAsYouGoItem() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithProductItem();
        cartDTO.addCartItem(CartTestUtil.getNewPayAsYouGoItemDTO());
        cartDTO.setOrder(OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem());
        cartDTO.setPaymentCardSettlement(SettlementTestUtil.getTestPaymentCardSettlementDTO1());
        cmd.setCartDTO(cartDTO);
        cmd.setStationId(STATION_500);
        return cmd;
    }

    public static CartDTO getCartDtoValuesWithDefaultfRefundCalculationBasis() {
        CartDTO cartDto = new CartDTO();
        ProductItemDTO productItemDto = new ProductItemDTO();
        productItemDto.setStartDate(DateUtil.parse(DateTestUtil.START_DATE));
        productItemDto.setEndDate(DateUtil.parse(DateTestUtil.END_DATE));
        productItemDto.setStartZone(1);
        productItemDto.setEndZone(2);
        productItemDto.setProductType(ItemType.TRAVEL_CARD.code());
        productItemDto.setDeceasedCustomer(false);
        productItemDto.setCardId(CARD_ID_1);
        productItemDto.setRefundCalculationBasis(RefundCalculationBasis.PRO_RATA.code());
        cartDto.setCartType(CartType.FAILED_CARD_REFUND.code());

        List<ItemDTO> itemDtolist = new ArrayList<ItemDTO>();
        itemDtolist.add(productItemDto);
        cartDto.setCartItems(itemDtolist);
        return cartDto;
    }

    public static CartDTO getCartDtoWithoutValuesOfRefundCalculationBasis() {
        CartDTO cartDto = new CartDTO();
        ProductItemDTO productItemDto = new ProductItemDTO();
        productItemDto.setStartDate(DateUtil.parse(DateTestUtil.START_DATE));
        productItemDto.setEndDate(DateUtil.parse(DateTestUtil.END_DATE));
        productItemDto.setStartZone(1);
        productItemDto.setEndZone(2);
        productItemDto.setProductType(ItemType.TRAVEL_CARD.code());
        productItemDto.setDeceasedCustomer(false);
        productItemDto.setCardId(CARD_ID_1);
        productItemDto.setRefundCalculationBasis(RefundCalculationBasis.ORDINARY.code());
        cartDto.setCartType(CartType.FAILED_CARD_REFUND.code());

        List<ItemDTO> itemDtolist = new ArrayList<ItemDTO>();
        itemDtolist.add(productItemDto);
        cartDto.setCartItems(itemDtolist);
        return cartDto;
    }

    public static CartDTO getAllCartDTOInCartCmdImpl() {

        CartDTO cartDTO = new CartDTO();
        cartDTO.addCartItem(PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1());
        cartDTO.addCartItem(RefundWorkflowTestUtil.getNewGoodwillPaymentItemDTO());
        cartDTO.addCartItem(RefundWorkflowTestUtil.getProductItemDTO());
        cartDTO.addCartItem(AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO3());

        return cartDTO;

    }

    public static CartDTO getAllCartDTOItems() {

        CartDTO cartDTO = new CartDTO();
        cartDTO.addCartItem(PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1());
        cartDTO.addCartItem(RefundWorkflowTestUtil.getNewGoodwillPaymentItemDTO());
        cartDTO.addCartItem(RefundWorkflowTestUtil.getProductItemDTO());
        cartDTO.addCartItem(AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO3());

        return cartDTO;

    }
}
