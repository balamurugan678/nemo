package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.BANK_ACCOUNT;
import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.SORT_CODE;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TITLE_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.INITIALS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATION_ID;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;

public class PayeePaymentTestUtil {

	public static PayeePaymentDTO getPayeePaymentDTO1(){
		return getPayeePaymentDTO(getTestAddressDTO1(), Boolean.FALSE, SORT_CODE, BANK_ACCOUNT, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, PaymentType.WEB_ACCOUNT_CREDIT.code(), 0, OYSTER_NUMBER_1, STATION_ID, Boolean.FALSE);
	}

	public static PayeePaymentDTO getPayeePaymentDTOWithBACS(){
		return getPayeePaymentDTO(getTestAddressDTO1(), Boolean.FALSE, SORT_CODE, BANK_ACCOUNT, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, PaymentType.BACS.code(), 0, OYSTER_NUMBER_1, STATION_ID, Boolean.FALSE);
	}
	
	public static PayeePaymentDTO getPayeePaymentDTO(AddressDTO payeeAddress, boolean overwriteAddress, String payeeSortCode, String payeeAccountNumber, String title,
			String firstName, String initials, String lastname, String paymentType, Integer webCreditAvailableAmount,
			String targetCardNumber, Long stationId, Boolean isEdited){
		return new PayeePaymentDTO(payeeAddress, overwriteAddress, payeeSortCode, payeeAccountNumber, title, firstName, initials, lastname, paymentType, webCreditAvailableAmount, targetCardNumber, stationId, isEdited);
	}
}
