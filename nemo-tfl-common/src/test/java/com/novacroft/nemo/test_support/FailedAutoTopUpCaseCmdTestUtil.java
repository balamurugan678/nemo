package com.novacroft.nemo.test_support;

import java.util.Date;

import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;

/**
 * Utilities for get Oyster card tests
 */
public final class FailedAutoTopUpCaseCmdTestUtil {
	
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
    public static final Integer FATU_AMOUNT = 30;
    public static final Date RESETTLEMENT_END_DATE = DateTestUtil.getFailedAutoTopUpResettlementEndDate();
        
    public static FailedAutoTopUpCaseCmdImpl getTestFailedAutoTopUpCmd() {
        return getTestCartCmd(CUSTOMER_ID, CASE_REFERENCE_NUMBER, CASE_STATUS, CASE_PROGRESSION_STATUS, CUSTOMER_LIABILITY, CARD_NUMBER, FATU_AMOUNT,RESETTLEMENT_END_DATE);
    }

    public static FailedAutoTopUpCaseCmdImpl getTestCartCmd(Long id,Long caseReferenceNumber, String caseStatus, String caseProgressionStatus, 
			String customerLiability, String cardNumber, Integer failedAutoTopUpAmount, Date resettlementDate ) {
    	FailedAutoTopUpCaseCmdImpl cmd = new FailedAutoTopUpCaseCmdImpl();
    	cmd.setCustomerId(id);
    	cmd.setCaseReferenceNumber(caseReferenceNumber);
    	cmd.setCaseStatus(caseStatus);
    	cmd.setCaseProgressionStatus(caseProgressionStatus);
    	cmd.setCustomerLiability(customerLiability);
    	cmd.setCardNumber(cardNumber);
    	cmd.setFailedAutoTopUpAmount(failedAutoTopUpAmount);
    	cmd.setResettlementEndDate(resettlementDate);
        return cmd;
    }
}
