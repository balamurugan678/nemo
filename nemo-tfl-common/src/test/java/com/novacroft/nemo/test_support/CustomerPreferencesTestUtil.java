package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.CustomerPreferences;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;

/**
 * Utilities for Customer Preferences tests
 */
public final class CustomerPreferencesTestUtil {
    public static final Long CUSTOMER_PREFERENCES_ID_1 = 2L;
    public static final Boolean CAN_TFL_CONTACT_1 = Boolean.FALSE;
    public static final Boolean CAN_THIRD_PARTY_CONTACT_1 = Boolean.FALSE;
    public static final Long STATION_ID_1 = 8L;
    public static final String EMAIL_FREQUENCY_1 = "Weekly";
    public static final String ATTACHMENT_TYPE_1 = "pdf";
    public static final Boolean STATEMENT_TERMS_ACCEPTED_1 = Boolean.FALSE;

    public static CustomerPreferencesDTO getTestCustomerPreferencesDTO1() {
        return getTestCustomerPreferencesDTO(CUSTOMER_PREFERENCES_ID_1, CUSTOMER_ID_1, CAN_TFL_CONTACT_1,
                CAN_THIRD_PARTY_CONTACT_1, STATION_ID_1, EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1, STATEMENT_TERMS_ACCEPTED_1);
    }

    public static CustomerPreferencesDTO getTestCustomerPreferencesDTO(Long customerPreferencesId, Long customerId,
                                                                       Boolean canTflContact, Boolean canThirdPartyContact,
                                                                       Long stationId, String emailFrequency,
                                                                       String attachmentType, Boolean statementTermsAccepted) {
        CustomerPreferencesDTO customerPreferencesDTO = new CustomerPreferencesDTO();
        customerPreferencesDTO.setId(customerPreferencesId);
        customerPreferencesDTO.setCustomerId(customerId);
        customerPreferencesDTO.setCanTflContact(canTflContact);
        customerPreferencesDTO.setCanThirdPartyContact(canThirdPartyContact);
        customerPreferencesDTO.setStationId(stationId);
        customerPreferencesDTO.setEmailFrequency(emailFrequency);
        customerPreferencesDTO.setAttachmentType(attachmentType);
        customerPreferencesDTO.setStatementTermsAccepted(statementTermsAccepted);
        return customerPreferencesDTO;
    }
    
    private static CustomerPreferences getTestCustomerPreferences(Long customerPreferencesId, Long customerId,
                    Boolean canTflContact, Boolean canThirdPartyContact,
                    Long stationId, String emailFrequency,
                    String attachmentType, Boolean statementTermsAccepted) {
        CustomerPreferences customerPreferences = new CustomerPreferences();
        customerPreferences.setId(customerPreferencesId);
        customerPreferences.setCustomerId(customerId);
        customerPreferences.setCanTflContact(canTflContact);
        customerPreferences.setCanThirdPartyContact(canThirdPartyContact);
        customerPreferences.setStationId(stationId);
        customerPreferences.setEmailFrequency(emailFrequency);
        customerPreferences.setAttachmentType(attachmentType);
        customerPreferences.setStatementTermsAccepted(statementTermsAccepted);
        return customerPreferences;
    }
    
    public static CustomerPreferences getTestCustomerPreferences1() {
        return getTestCustomerPreferences(CUSTOMER_PREFERENCES_ID_1, CUSTOMER_ID_1, CAN_TFL_CONTACT_1,
                        CAN_THIRD_PARTY_CONTACT_1, STATION_ID_1, EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1, STATEMENT_TERMS_ACCEPTED_1);
    }
}
