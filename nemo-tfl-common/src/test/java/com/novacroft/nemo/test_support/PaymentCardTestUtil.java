package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

/**
 * Fixtures and utilities for unit tests that involve payment cards
 */
public class PaymentCardTestUtil {
    public final static Long TEST_PAYMENT_CARD_ID_1 = 99L;
    public final static String TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1 = "XXXXXXXXXXXX1111";
    public final static String TEST_TOKEN_1 = "test-token-1";
    public final static String TEST_STATUS_1 = "Active";
    public final static String TEST_NICK_NAME_1 = "Card A";
    public final static String TEST_EXPIRY_DATE_1 = "08-2013";

    public final static Long TEST_PAYMENT_CARD_ID_2 = 100L;
    public final static String TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_2 = "XXXXXXXXXXXX2222";
    public final static String TEST_TOKEN_2 = "test-token-2";
    public final static String TEST_STATUS_2 = "InActive";
    public final static String TEST_NICK_NAME_2 = "Card B";

    public final static String TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_3 = "XXXXXXXXXXXX3333";
    public final static String TEST_TOKEN_3 = "test-token-3";
    public final static String TEST_STATUS_3 = "Active";
    public final static String TEST_NICK_NAME_3 = "Card C";
    
    public final static String TEST_NEVER_EXPIRED_DATE = "12-2999";
    
    public static ManagePaymentCardCmdImpl getManagePaymentCardCmdImpl1(){
        ManagePaymentCardCmdImpl cmd = new ManagePaymentCardCmdImpl();
        cmd.setPaymentCardId(TEST_PAYMENT_CARD_ID_1);
        cmd.setPaymentCards(Arrays.asList(new PaymentCardCmdImpl[]{}));
        return cmd;
    }
    
    public static PaymentCardDTO getTestExpiredPaymentCardDTO() {
        PaymentCardDTO paymentCardDTO = new PaymentCardDTO();
        paymentCardDTO.setId(TEST_PAYMENT_CARD_ID_1);
        paymentCardDTO.setNickName(TEST_NICK_NAME_1);
        paymentCardDTO.setExpiryDate(TEST_EXPIRY_DATE_1);
        return paymentCardDTO;
    }
    
    public static PaymentCardDTO getTestNotExpiredPaymentCardDTO() {
        PaymentCardDTO paymentCardDTO = new PaymentCardDTO();
        paymentCardDTO.setId(TEST_PAYMENT_CARD_ID_2);
        paymentCardDTO.setNickName(TEST_NICK_NAME_2);
        paymentCardDTO.setExpiryDate(TEST_NEVER_EXPIRED_DATE);
        return paymentCardDTO;
    }
    
    public static List<PaymentCardDTO> getTestPaymentCardList1() {
        return Arrays.asList(getTestExpiredPaymentCardDTO(), getTestNotExpiredPaymentCardDTO());
    }
    
    public static PaymentCard getTestNotExpiredPaymentCard() {
        PaymentCard card = new PaymentCard();
        card.setId(TEST_PAYMENT_CARD_ID_2);
        card.setNickName(TEST_NICK_NAME_2);
        card.setExpiryDate(TEST_NEVER_EXPIRED_DATE);
        return card;
    }
    
	public static List<PaymentCardDTO> makePaymentCardsList() {
    	List<PaymentCardDTO> paymentCardsList = new ArrayList<PaymentCardDTO>();
    	paymentCardsList.add(PaymentCardTestUtil.getTestExpiredPaymentCardDTO());
    	paymentCardsList.add(PaymentCardTestUtil.getTestNotExpiredPaymentCardDTO());
		int oneMonthAfterCurrentMonth= (Calendar.getInstance().get(Calendar.MONTH)!=Calendar.DECEMBER) ?  Calendar.getInstance().get(Calendar.MONTH)+2 : 1;
    	int oneMonthAfterCurrentYear = (Calendar.getInstance().get(Calendar.MONTH)!=Calendar.DECEMBER) ?  Calendar.getInstance().get(Calendar.YEAR) : Calendar.getInstance().get(Calendar.YEAR)+1;
    	String expiryDate = ""+oneMonthAfterCurrentMonth+"-"+oneMonthAfterCurrentYear;
        expiryDate = expiryDate.indexOf("-") == 1 ? "0" + expiryDate : expiryDate;
    	paymentCardsList.add(new PaymentCardDTO(CustomerTestUtil.getCustomer1().getId(), null, null, expiryDate, null, null, null, null));
    	return paymentCardsList;
    }
}
