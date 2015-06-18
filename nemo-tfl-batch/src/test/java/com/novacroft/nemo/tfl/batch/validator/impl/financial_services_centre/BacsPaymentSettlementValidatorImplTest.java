package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsSettlementRecordService;
import com.novacroft.nemo.tfl.batch.constant.financial_services_centre.FieldName;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class BacsPaymentSettlementValidatorImplTest {

    private BacsSettlementRecordService mockBacsSettlementRecordService;

    private BacsPaymentSettlementValidatorImpl validator;

    private Errors mockErrors;

    @Before
    public void setUp() {
        validator = Mockito.spy(new BacsPaymentSettlementValidatorImpl());
        mockBacsSettlementRecordService = mock(BacsSettlementRecordService.class);
        mockErrors = mock(Errors.class);

    }

    @Test
    public void shouldAddErrorsForWrongFormat() throws Exception {
        validator.valiatedatePaymentDate(mockErrors, "22-03-2014");
        verify(mockErrors)
                .reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.PAYMENT_DATE, "22-03-2014"},
                        null);

    }

    @Test
    public void shouldPassForCorrectFormat() throws Exception {
        validator.valiatedatePaymentDate(mockErrors, "22/03/2014");
        verifyZeroInteractions(mockErrors);
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(String[].class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(Integer.class));
    }

    @Test
    public void shouldValidate() throws Exception {
        doNothing().when(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        doNothing().when(this.validator).validateCustomerName(any(Errors.class), anyString());
        doNothing().when(this.validator).validateAmount(any(Errors.class), anyString());
        validator.bacsSettlementRecordService = mockBacsSettlementRecordService;

        when(this.mockBacsSettlementRecordService.getPaymentReferenceNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockBacsSettlementRecordService.getCustomerName(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockBacsSettlementRecordService.getAmount(any(String[].class))).thenReturn(EMPTY_STRING);

        this.validator.validate(new String[0], this.mockErrors);

        verify(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        verify(this.validator).validateCustomerName(any(Errors.class), anyString());
        verify(this.validator).validateAmount(any(Errors.class), anyString());

        verify(this.mockBacsSettlementRecordService).getPaymentReferenceNumber(any(String[].class));
        verify(this.mockBacsSettlementRecordService).getCustomerName(any(String[].class));
        verify(this.mockBacsSettlementRecordService).getAmount(any(String[].class));
    }
}
