package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeProducedRecordService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ChequeProducedValidatorImplTest {
    private ChequeProducedValidatorImpl validator;
    private ChequeProducedRecordService mockChequeProducedRecordService;
    private Errors mockErrors;

    @Before
    public void setUp() {
        this.validator = mock(ChequeProducedValidatorImpl.class);
        this.mockChequeProducedRecordService = mock(ChequeProducedRecordService.class);
        this.validator.chequeProducedRecordService = this.mockChequeProducedRecordService;

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

        when(this.mockChequeProducedRecordService.getReferenceNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeProducedRecordService.getAmount(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeProducedRecordService.getCustomerName(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeProducedRecordService.getChequeSerialNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeProducedRecordService.getPrintedOn(any(String[].class))).thenReturn(EMPTY_STRING);

        this.validator.validate(new String[0], this.mockErrors);

        verify(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        verify(this.validator).validateAmount(any(Errors.class), anyString());
        verify(this.validator).validateCustomerName(any(Errors.class), anyString());
        verify(this.validator).validateChequeSerialNumber(any(Errors.class), anyString());
        verify(this.validator).validatePrintedOn(any(Errors.class), anyString());

        verify(this.mockChequeProducedRecordService).getReferenceNumber(any(String[].class));
        verify(this.mockChequeProducedRecordService).getAmount(any(String[].class));
        verify(this.mockChequeProducedRecordService).getCustomerName(any(String[].class));
        verify(this.mockChequeProducedRecordService).getChequeSerialNumber(any(String[].class));
        verify(this.mockChequeProducedRecordService).getPrintedOn(any(String[].class));
    }
}