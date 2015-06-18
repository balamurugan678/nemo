package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeSettlementRecordService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class ChequeSettlementValidatorImplTest {
    private ChequeSettlementValidatorImpl validator;
    private ChequeSettlementRecordService mockChequeSettlementRecordService;
    private Errors mockErrors;

    @Before
    public void setUp() {
        this.validator = mock(ChequeSettlementValidatorImpl.class);
        this.mockChequeSettlementRecordService = mock(ChequeSettlementRecordService.class);
        this.validator.chequeSettlementRecordService = this.mockChequeSettlementRecordService;

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

        doNothing().when(this.validator).validateChequeSerialNumber(any(Errors.class), anyString());
        doNothing().when(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        doNothing().when(this.validator).validateCustomerName(any(Errors.class), anyString());
        doNothing().when(this.validator).validateClearedOn(any(Errors.class), anyString());
        doNothing().when(this.validator).validateCurrency(any(Errors.class), anyString());
        doNothing().when(this.validator).validateAmount(any(Errors.class), anyString());

        when(this.mockChequeSettlementRecordService.getChequeSerialNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeSettlementRecordService.getPaymentReferenceNumber(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeSettlementRecordService.getCustomerName(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeSettlementRecordService.getClearedOn(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeSettlementRecordService.getCurrency(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockChequeSettlementRecordService.getAmount(any(String[].class))).thenReturn(EMPTY_STRING);

        this.validator.validate(new String[0], this.mockErrors);

        verify(this.validator).validateChequeSerialNumber(any(Errors.class), anyString());
        verify(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        verify(this.validator).validateCustomerName(any(Errors.class), anyString());
        verify(this.validator).validateClearedOn(any(Errors.class), anyString());
        verify(this.validator).validateCurrency(any(Errors.class), anyString());
        verify(this.validator).validateAmount(any(Errors.class), anyString());

        verify(this.mockChequeSettlementRecordService).getChequeSerialNumber(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getPaymentReferenceNumber(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getCustomerName(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getClearedOn(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getCurrency(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getAmount(any(String[].class));
    }

}