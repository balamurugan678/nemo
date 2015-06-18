package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.tfl.common.application_service.LostOrStolenEligibilityService;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.LostStolenOptionType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID_1;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class LostOrStolenValidatorTest {
    private LostOrStolenValidator validator;
    private HotlistReasonValidator mockHostlistReasonValidator;
    private LostOrStolenCardCmdImpl mockCmd;
    private LostOrStolenEligibilityService mockLostOrStolenEligibilityService;
    private Errors mockErrors;

    private static String CMD_STRING = "mockCmd";

    @Before
    public void setUp() {
        validator = mock(LostOrStolenValidator.class);

        mockHostlistReasonValidator = mock(HotlistReasonValidator.class);
        validator.hotlistReasonValidator = mockHostlistReasonValidator;

        this.mockLostOrStolenEligibilityService = mock(LostOrStolenEligibilityService.class);
        this.validator.lostOrStolenEligibilityService = this.mockLostOrStolenEligibilityService;

        this.mockCmd = mock(LostOrStolenCardCmdImpl.class);
        this.mockErrors = mock(Errors.class);
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(LostOrStolenCardCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldValidate() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator)
                .validateCardEligibleToReportLostOrStolen(any(LostOrStolenCardCmdImpl.class), any(Errors.class));
        doNothing().when(this.validator).validateLostStolenOptions(anyString(), any(Errors.class));
        doNothing().when(this.mockHostlistReasonValidator).validate(anyObject(), any(Errors.class));

        when(this.mockErrors.hasErrors()).thenReturn(Boolean.FALSE);
        when(this.mockCmd.getLostStolenOptions()).thenReturn(EMPTY);

        this.validator.validate(this.mockCmd, this.mockErrors);

        verify(this.validator).validateCardEligibleToReportLostOrStolen(any(LostOrStolenCardCmdImpl.class), any(Errors.class));
        verify(this.validator).validateLostStolenOptions(anyString(), any(Errors.class));
        verify(this.mockHostlistReasonValidator).validate(anyObject(), any(Errors.class));

        verify(this.mockErrors).hasErrors();
        verify(this.mockCmd).getLostStolenOptions();
    }

    @Test
    public void shouldNotValidateWithIneligibleCard() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator)
                .validateCardEligibleToReportLostOrStolen(any(LostOrStolenCardCmdImpl.class), any(Errors.class));
        doNothing().when(this.validator).validateLostStolenOptions(anyString(), any(Errors.class));
        doNothing().when(this.mockHostlistReasonValidator).validate(anyObject(), any(Errors.class));

        when(this.mockErrors.hasErrors()).thenReturn(Boolean.TRUE);
        when(this.mockCmd.getLostStolenOptions()).thenReturn(EMPTY);

        this.validator.validate(this.mockCmd, this.mockErrors);

        verify(this.validator).validateCardEligibleToReportLostOrStolen(any(LostOrStolenCardCmdImpl.class), any(Errors.class));
        verify(this.validator, never()).validateLostStolenOptions(anyString(), any(Errors.class));
        verify(this.mockHostlistReasonValidator, never()).validate(anyObject(), any(Errors.class));

        verify(this.mockErrors).hasErrors();
        verify(this.mockCmd, never()).getLostStolenOptions();
    }

    @Test
    public void validateLostStolenOptionsShouldValidate() {
        doCallRealMethod().when(this.validator).validateLostStolenOptions(anyString(), any(Errors.class));
        doNothing().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());

        this.validator.validateLostStolenOptions(LostStolenOptionType.NEW_CARD.code(), this.mockErrors);

        verify(this.validator, never()).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
    }

    @Test
    public void validateLostStolenOptionsShouldNotValidate() {
        doCallRealMethod().when(this.validator).validateLostStolenOptions(anyString(), any(Errors.class));
        doNothing().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());

        this.validator.validateLostStolenOptions(null, this.mockErrors);

        verify(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
    }

    @Test
    public void validateCardEligibleToReportLostOrStolenShouldValidate() {
        doCallRealMethod().when(this.validator)
                .validateCardEligibleToReportLostOrStolen(any(LostOrStolenCardCmdImpl.class), any(Errors.class));
        when(this.mockLostOrStolenEligibilityService.isCardEligibleToBeReportedLostOrStolen(anyLong()))
                .thenReturn(Boolean.TRUE);
        when(this.mockCmd.getCardId()).thenReturn(CARD_ID_1);
        doNothing().when(this.mockErrors).reject(anyString());

        this.validator.validateCardEligibleToReportLostOrStolen(this.mockCmd, this.mockErrors);

        verify(this.mockLostOrStolenEligibilityService).isCardEligibleToBeReportedLostOrStolen(anyLong());
        verify(this.mockCmd).getCardId();
        verify(this.mockErrors, never()).reject(anyString());
    }

    @Test
    public void validateCardEligibleToReportLostOrStolenShouldNotValidate() {
        doCallRealMethod().when(this.validator)
                .validateCardEligibleToReportLostOrStolen(any(LostOrStolenCardCmdImpl.class), any(Errors.class));
        when(this.mockLostOrStolenEligibilityService.isCardEligibleToBeReportedLostOrStolen(anyLong()))
                .thenReturn(Boolean.FALSE);
        when(this.mockCmd.getCardId()).thenReturn(CARD_ID_1);
        doNothing().when(this.mockErrors).reject(anyString());

        this.validator.validateCardEligibleToReportLostOrStolen(this.mockCmd, this.mockErrors);

        verify(this.mockLostOrStolenEligibilityService).isCardEligibleToBeReportedLostOrStolen(anyLong());
        verify(this.mockCmd).getCardId();
        verify(this.mockErrors).reject(anyString());
    }
}
