package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.OutdatedChequeRecordService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class OutdatedChequeValidatorImplTest {
    private OutdatedChequeValidatorImpl validator;
    private OutdatedChequeRecordService mockOutdatedChequeRecordService;
    private Errors mockErrors;

    @Before
    public void setUp() {
        this.validator = mock(OutdatedChequeValidatorImpl.class);
        this.mockOutdatedChequeRecordService = mock(OutdatedChequeRecordService.class);
        this.validator.outdatedChequeRecordService = this.mockOutdatedChequeRecordService;

        this.mockErrors = mock(Errors.class);
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
    public void shouldValidate() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));

        doNothing().when(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        doNothing().when(this.validator).validateAmount(any(Errors.class), anyString());
        doNothing().when(this.validator).validateCustomerName(any(Errors.class), anyString());
        doNothing().when(this.validator).validateChequeSerialNumber(any(Errors.class), anyString());
        doNothing().when(this.validator).validatePrintedOn(any(Errors.class), anyString());

        when(this.mockOutdatedChequeRecordService.getReferenceNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockOutdatedChequeRecordService.getAmount(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockOutdatedChequeRecordService.getCustomerName(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockOutdatedChequeRecordService.getChequeSerialNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockOutdatedChequeRecordService.getOutdatedOn(any(String[].class))).thenReturn(EMPTY_STRING);

        this.validator.validate(new String[0], this.mockErrors);

        verify(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        verify(this.validator).validateAmount(any(Errors.class), anyString());
        verify(this.validator).validateCustomerName(any(Errors.class), anyString());
        verify(this.validator).validateChequeSerialNumber(any(Errors.class), anyString());
        verify(this.validator).validateOutdatedOn(any(Errors.class), anyString());

        verify(this.mockOutdatedChequeRecordService).getReferenceNumber(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getAmount(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getCustomerName(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getChequeSerialNumber(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getOutdatedOn(any(String[].class));
    }
}