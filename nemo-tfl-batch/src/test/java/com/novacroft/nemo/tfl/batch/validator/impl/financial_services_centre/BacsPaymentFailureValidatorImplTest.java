package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsFailureRecordService;
import com.novacroft.nemo.tfl.batch.constant.financial_services_centre.FieldName;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;
import org.apache.commons.lang3.StringUtils;
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

public class BacsPaymentFailureValidatorImplTest {

    private BacsFailureRecordService mockBacsFailureRecordService;
    private BacsPaymentFailureValidatorImpl validator;
    private Errors mockErrors;

    @Before
    public void setUp() {
        validator = Mockito.spy(new BacsPaymentFailureValidatorImpl());
        mockBacsFailureRecordService = mock(BacsFailureRecordService.class);
        mockErrors = mock(Errors.class);
    }

    @Test
    public void shouldAddErrorsForWrongFormat() throws Exception {
        validator.validateFailedPaymentDate(mockErrors, "22-03-2014");
        verify(mockErrors).reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(),
                new String[]{FieldName.PAYMENT_FAILED_DATE, "22-03-2014"}, null);

    }

    @Test
    public void shouldPassForCorrectFormat() throws Exception {
        validator.validateFailedPaymentDate(mockErrors, "22/03/2014");
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
        doNothing().when(this.validator).validateAmount(any(Errors.class), anyString());
        doNothing().when(this.validator).validateRejectCode(any(Errors.class), anyString());
        doNothing().when(this.validator).validateFailedPaymentDate(any(Errors.class), anyString());
        validator.bacsFailureRecordService = mockBacsFailureRecordService;

        when(this.mockBacsFailureRecordService.getFinancialServicesReferenceNumber(any(String[].class)))
                .thenReturn(EMPTY_STRING);
        when(this.mockBacsFailureRecordService.getAmount(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockBacsFailureRecordService.getBacsRejectCode(any(String[].class))).thenReturn(EMPTY_STRING);
        when(this.mockBacsFailureRecordService.getPaymentFailureDate(any(String[].class))).thenReturn(EMPTY_STRING);

        this.validator.validate(new String[0], this.mockErrors);

        verify(this.validator).validateReferenceNumber(any(Errors.class), anyString());
        verify(this.validator).validateAmount(any(Errors.class), anyString());
        verify(this.validator).validateAmount(any(Errors.class), anyString());
        verify(this.validator).validateFailedPaymentDate(any(Errors.class), anyString());

        verify(this.mockBacsFailureRecordService).getFinancialServicesReferenceNumber(any(String[].class));
        verify(this.mockBacsFailureRecordService).getAmount(any(String[].class));
        verify(this.mockBacsFailureRecordService).getBacsRejectCode(any(String[].class));
        verify(this.mockBacsFailureRecordService).getPaymentFailureDate(any(String[].class));
    }

    @Test
    public void validateRejectCodeShouldAcceptCode() {
        doNothing().when(this.mockErrors).reject(anyString(), any(Object[].class), anyString());
        this.validator.validateRejectCode(this.mockErrors, BACSRejectCodeEnum.C.name());
        verify(this.mockErrors, never()).reject(anyString(), any(Object[].class), anyString());
    }

    @Test
    public void validateRejectCodeShouldRejectBlankCode() {
        doNothing().when(this.mockErrors).reject(anyString(), any(Object[].class), anyString());
        this.validator.validateRejectCode(this.mockErrors, StringUtils.EMPTY);
        verify(this.mockErrors).reject(anyString(), any(Object[].class), anyString());
    }

    @Test
    public void validateRejectCodeShouldRejectLongCode() {
        doNothing().when(this.mockErrors).reject(anyString(), any(Object[].class), anyString());
        this.validator.validateRejectCode(this.mockErrors, "too-long");
        verify(this.mockErrors).reject(anyString(), any(Object[].class), anyString());
    }
}
