package com.novacroft.nemo.test_support;

import java.util.Date;

import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpCase;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomer1;


public class FailedAutoTopUpCaseTestUtil {

    public static final Integer FAILED_AUTO_TOPUP_AMOUNT = 30;
    public static final Integer NULL_FAILED_AUTO_TOPUP_AMOUNT=null;
    public static final String TEST_CARD_NUMBER= "100000000001";
    public static final Long CUSTOMER_ID = 5001L;


    public static final Long CASE_ID = 01L;
    public static final Long CASE_REFERENCE_NUMBER = 100001L;
    public static final String CASE_STATUS = "OPEN";
    public static final String CASE_PROGRESSION_STATUS = "RESETTLEMENT_PENDING";
    public static final String CUSTOMER_LIABILITY = "CNL";
    public static final String CARD_NUMBER = "030000000516";
    public static final Double FATU_AMOUNT = 30.0;
    public static final Date RESETTLEMENT_END_DATE = DateTestUtil.getFailedAutoTopUpResettlementEndDate();
    public static final Integer RESETTLEMENT_PERIOD_GREATER_THAN_LIMIT = 100;
    public static final Integer RESETTLEMENT_PERIOD_LESS_THAN_LIMIT = 25;
    

    public static FailedAutoTopUpCase getTestFailedAutoTopUpCase() {
        return getTestFailedAutoTopUpCase(CASE_ID, CASE_REFERENCE_NUMBER, CASE_STATUS, CASE_PROGRESSION_STATUS, CUSTOMER_LIABILITY, CARD_NUMBER, FATU_AMOUNT,RESETTLEMENT_END_DATE);
    }
    
    public static FailedAutoTopUpCase getTestFailedAutoTopUpCase(Long id,Long caseReferenceNumber, String caseStatus, String caseProgressionStatus, 
    								String customerLiability, String cardNumber, Double failedAutoTopUpAmount, Date resettlementDate ) {
    	
    	FailedAutoTopUpCase failedAutoTopUpCase = new FailedAutoTopUpCase();
    	failedAutoTopUpCase.setId(id);
    	failedAutoTopUpCase.setCaseReferenceNumber(caseReferenceNumber);
    	failedAutoTopUpCase.setCaseStatus(caseStatus);
    	failedAutoTopUpCase.setCaseProgressionStatus(caseProgressionStatus);
    	failedAutoTopUpCase.setCustomerLiability(customerLiability);
    	failedAutoTopUpCase.setCardNumber(cardNumber);
    	failedAutoTopUpCase.setFailedAutoTopUpAmount(failedAutoTopUpAmount);
    	failedAutoTopUpCase.setResettlementEndDate(resettlementDate);
    	return failedAutoTopUpCase;
    }

    public static FailedAutoTopUpCase getTestFailedAutoTopUpCaseWithCustomer() {
    	FailedAutoTopUpCase failedAutoTopUpCase = getTestFailedAutoTopUpCase();
    	failedAutoTopUpCase.setCustomerId(getCustomer1().getId());
    	return failedAutoTopUpCase;
    }
}
