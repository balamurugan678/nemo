package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapRequestConverter;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CyberSourceSoapRequestTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * CyberSourceSoapRequestConverterImpl unit tests
 */
public class CyberSourceSoapRequestConverterImplTest {

    private CyberSourceSoapRequestConverter converter;

    @Before
    public void setUp() {
        this.converter = new CyberSourceSoapRequestConverterImpl();
    }

    @Test
    public void shouldConvertDtoToModel() {
        RequestMessage result = this.converter.convertDtoToModel(getTestCyberSourceSoapRequestDTO1());
        assertEquals(MERCHANT_ID_1, result.getMerchantID());
        assertEquals(TRANSACTION_UUID_1, result.getMerchantReferenceCode());

        assertEquals(BILL_TO_BUILDING_NUMBER_1, result.getBillTo().getBuildingNumber());
        assertEquals(BILL_TO_CITY_1, result.getBillTo().getCity());
        assertEquals(BILL_TO_CUSTOMER_ID_1, result.getBillTo().getCustomerID());
        assertEquals(BILL_TO_EMAIL_1, result.getBillTo().getEmail());
        assertEquals(BILL_TO_FIRST_NAME_1, result.getBillTo().getFirstName());
        assertEquals(BILL_TO_IP_ADDRESS_1, result.getBillTo().getIpAddress());
        assertEquals(BILL_TO_LAST_NAME_1, result.getBillTo().getLastName());
        assertEquals(BILL_TO_PHONE_NUMBER_1, result.getBillTo().getPhoneNumber());
        assertEquals(BILL_TO_POSTAL_CODE_1, result.getBillTo().getPostalCode());
        assertEquals(BILL_TO_STREET_1_1, result.getBillTo().getStreet1());
        assertEquals(BILL_TO_STREET_2_1, result.getBillTo().getStreet2());

        assertEquals(String.valueOf(RUN_AUTHORIZATION_SERVICE_1), result.getCcAuthService().getRun());

        assertEquals(String.valueOf(RUN_CAPTURE_SERVICE_1), result.getCcCaptureService().getRun());

        assertEquals(ORDER_NUMBER_1, result.getMerchantDefinedData().getMddField().get(0).getValue());

        assertEquals(TOTAL_AMOUNT_IN_PENCE_AS_STRING_1, result.getPurchaseTotals().getGrandTotalAmount());
    }
}
