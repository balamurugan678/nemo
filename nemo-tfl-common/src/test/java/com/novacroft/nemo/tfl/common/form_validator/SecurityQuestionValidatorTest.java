package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.SecurityQuestions.SECURITY_QUESTION_MEMORABLE_DATE;
import static com.novacroft.nemo.common.constant.SecurityQuestions.SECURITY_QUESTION_MEMORABLE_PLACE;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.CONFIRM_SECURITY_ANSWER_1;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.SECURITY_ANSWER_1;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmd1;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmdMemorablePlaceAlphabetic;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmdMemorablePlaceNotAlphabetic;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmdMemorablePlaceNotAlphabeticAndNotMinimumFieldlength;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.tfl.common.command.EmailPreferencesCmd;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;

/**
 * SecurityQuestionValidator unit tests
 */
public class SecurityQuestionValidatorTest {
    private SecurityQuestionValidator validator;
    private SecurityQuestionValidator mockValidator;
    private Errors mockErrors;
    private Errors errors;
    private SecurityQuestionCmd mockCmd;
    private SecurityQuestionCmdImpl cmd;

    @Before
    public void setUp() {
        validator = new SecurityQuestionValidator();
        mockValidator = mock(SecurityQuestionValidator.class);

        mockErrors = mock(Errors.class);
        mockCmd = mock(SecurityQuestionCmd.class);
    }

    @Test
    public void shouldSupportClass() {
        when(mockValidator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(mockValidator.supports(com.novacroft.nemo.common.command.SecurityQuestionCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(mockValidator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(mockValidator.supports(EmailPreferencesCmd.class));
    }

    @Test
    public void shouldValidateIfSecurityQuestionIsMemorableDate() {
        doCallRealMethod().when(mockValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockValidator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        when(mockCmd.getSecurityQuestion()).thenReturn(SECURITY_QUESTION_MEMORABLE_DATE);
        doNothing().when(mockValidator).validateSecurityAnswerConfirmation(any(SecurityQuestionCmd.class), any(Errors.class));
        doNothing().when(mockValidator).validateMemorableDate(any(SecurityQuestionCmd.class), any(Errors.class));

        mockValidator.validate(getTestSecurityQuestionCmd1(), mockErrors);

        verify(mockValidator, times(3)).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        verify(mockValidator).validateSecurityAnswerConfirmation(any(SecurityQuestionCmd.class), any(Errors.class));
        verify(mockValidator).validateMemorableDate(any(SecurityQuestionCmd.class), any(Errors.class));
    }

    @Test
    public void shouldValidateIfSecurityQuestionIsNotMemorableDate() {
        doCallRealMethod().when(mockValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockValidator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        when(mockCmd.getSecurityQuestion()).thenReturn(SECURITY_QUESTION_MEMORABLE_PLACE);
        doNothing().when(mockValidator).rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        doNothing().when(mockValidator).validateSecurityAnswerConfirmation(any(SecurityQuestionCmd.class), any(Errors.class));
        doNothing().when(mockValidator).validateMemorableDate(any(SecurityQuestionCmd.class), any(Errors.class));

        mockValidator.validate(getTestSecurityQuestionCmdMemorablePlaceAlphabetic(), mockErrors);

        verify(mockValidator, times(3)).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        verify(mockValidator, times(2)).rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(any(Errors.class), anyString());
        verify(mockValidator).validateSecurityAnswerConfirmation(any(SecurityQuestionCmd.class), any(Errors.class));
        verify(mockValidator).validateMemorableDate(any(SecurityQuestionCmd.class), any(Errors.class));
    }

    @Test
    public void shouldNotValidateIfSecurityQuestionIsNotMemorableDateAndNotAlphabetic() {
        cmd = getTestSecurityQuestionCmdMemorablePlaceNotAlphabetic();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfSecurityQuestionIsNotMemorableDateAndNotMinimumFieldlength() {
        cmd = getTestSecurityQuestionCmdMemorablePlaceNotAlphabeticAndNotMinimumFieldlength();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validateSecurityAnswerConfirmationShouldAccept() {
        doCallRealMethod().when(mockValidator).validateSecurityAnswerConfirmation(any(SecurityQuestionCmd.class), any(Errors.class));
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.answerConfirmHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.isAnswerNotConfirmed(any(SecurityQuestionCmd.class))).thenReturn(Boolean.FALSE);
        doNothing().when(mockErrors).rejectValue(anyString(), anyString());

        mockValidator.validateSecurityAnswerConfirmation(mockCmd, mockErrors);

        verify(mockErrors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void validateSecurityAnswerConfirmationShouldReject() {
        doCallRealMethod().when(mockValidator).validateSecurityAnswerConfirmation(any(SecurityQuestionCmd.class), any(Errors.class));
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.answerConfirmHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.isAnswerNotConfirmed(any(SecurityQuestionCmd.class))).thenReturn(Boolean.TRUE);
        doNothing().when(mockErrors).rejectValue(anyString(), anyString());

        mockValidator.validateSecurityAnswerConfirmation(mockCmd, mockErrors);

        verify(mockErrors).rejectValue(anyString(), anyString());
    }

    @Test
    public void shouldValidateMemorableDate() {
        doCallRealMethod().when(mockValidator).validateMemorableDate(any(SecurityQuestionCmd.class), any(Errors.class));
        doNothing().when(mockValidator).validateMemorableDateIsValidPattern(any(SecurityQuestionCmd.class), any(Errors.class));
        doNothing().when(mockValidator).validateMemorableDateIsValidDate(any(SecurityQuestionCmd.class), any(Errors.class));

        mockValidator.validateMemorableDate(mockCmd, mockErrors);

        verify(mockValidator).validateMemorableDateIsValidPattern(any(SecurityQuestionCmd.class), any(Errors.class));
        verify(mockValidator).validateMemorableDateIsValidDate(any(SecurityQuestionCmd.class), any(Errors.class));
    }

    @Test
    public void validateMemorableDateIsValidPatternShouldCheckPattern() {
        doCallRealMethod().when(mockValidator).validateMemorableDateIsValidPattern(any(SecurityQuestionCmd.class), any(Errors.class));
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.questionHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.isMemorableDateQuestion(any(SecurityQuestionCmd.class))).thenReturn(Boolean.TRUE);
        doNothing().when(mockValidator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());

        mockValidator.validateMemorableDateIsValidPattern(mockCmd, mockErrors);

        verify(mockValidator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
    }

    @Test
    public void validateMemorableDateIsValidPatternShouldNotCheckPattern() {
        doCallRealMethod().when(mockValidator).validateMemorableDateIsValidPattern(any(SecurityQuestionCmd.class), any(Errors.class));
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.questionHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.isMemorableDateQuestion(any(SecurityQuestionCmd.class))).thenReturn(Boolean.FALSE);
        doNothing().when(mockValidator).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());

        mockValidator.validateMemorableDateIsValidPattern(mockCmd, mockErrors);

        verify(mockValidator, never()).rejectIfPatternNotMatched(any(Errors.class), anyString(), anyString(), anyString());
    }

    @Test
    public void validateMemorableDateIsValidDateShouldCheckDate() {
        doCallRealMethod().when(mockValidator).validateMemorableDateIsValidDate(any(SecurityQuestionCmd.class), any(Errors.class));
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.questionHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.isMemorableDateQuestion(any(SecurityQuestionCmd.class))).thenReturn(Boolean.TRUE);
        doNothing().when(mockValidator).rejectIfInvalidDate(any(Errors.class), anyString(), anyString());

        mockValidator.validateMemorableDateIsValidDate(mockCmd, mockErrors);

        verify(mockValidator).rejectIfInvalidDate(any(Errors.class), anyString(), anyString());
    }

    @Test
    public void validateMemorableDateIsValidDateShouldNotCheckDate() {
        doCallRealMethod().when(mockValidator).validateMemorableDateIsValidDate(any(SecurityQuestionCmd.class), any(Errors.class));
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.questionHasNoErrors(any(Errors.class))).thenReturn(Boolean.TRUE);
        when(mockValidator.isMemorableDateQuestion(any(SecurityQuestionCmd.class))).thenReturn(Boolean.FALSE);
        doNothing().when(mockValidator).rejectIfInvalidDate(any(Errors.class), anyString(), anyString());

        mockValidator.validateMemorableDateIsValidDate(mockCmd, mockErrors);

        verify(mockValidator, never()).rejectIfInvalidDate(any(Errors.class), anyString(), anyString());
    }

    @Test
    public void questionHasNoErrorsShouldReturnTrue() {
        when(mockValidator.questionHasNoErrors(any(Errors.class))).thenCallRealMethod();
        when(mockErrors.hasFieldErrors(anyString())).thenReturn(Boolean.FALSE);
        assertTrue(mockValidator.questionHasNoErrors(mockErrors));
    }

    @Test
    public void questionHasNoErrorsShouldReturnFalse() {
        when(mockValidator.questionHasNoErrors(any(Errors.class))).thenCallRealMethod();
        when(mockErrors.hasFieldErrors(anyString())).thenReturn(Boolean.TRUE);
        assertFalse(mockValidator.questionHasNoErrors(mockErrors));
    }

    @Test
    public void answerHasNoErrorsShouldReturnTrue() {
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenCallRealMethod();
        when(mockErrors.hasFieldErrors(anyString())).thenReturn(Boolean.FALSE);
        assertTrue(mockValidator.answerHasNoErrors(mockErrors));
    }

    @Test
    public void answerHasNoErrorsShouldReturnFalse() {
        when(mockValidator.answerHasNoErrors(any(Errors.class))).thenCallRealMethod();
        when(mockErrors.hasFieldErrors(anyString())).thenReturn(Boolean.TRUE);
        assertFalse(mockValidator.answerHasNoErrors(mockErrors));
    }

    @Test
    public void answerConfirmHasNoErrorsShouldReturnTrue() {
        when(mockValidator.answerConfirmHasNoErrors(any(Errors.class))).thenCallRealMethod();
        when(mockErrors.hasFieldErrors(anyString())).thenReturn(Boolean.FALSE);
        assertTrue(mockValidator.answerConfirmHasNoErrors(mockErrors));
    }

    @Test
    public void answerConfirmationHasNoErrorsShouldReturnFalse() {
        when(mockValidator.answerConfirmHasNoErrors(any(Errors.class))).thenCallRealMethod();
        when(mockErrors.hasFieldErrors(anyString())).thenReturn(Boolean.TRUE);
        assertFalse(mockValidator.answerConfirmHasNoErrors(mockErrors));
    }

    @Test
    public void isMemorableDateQuestionShouldReturnTrue() {
        when(mockValidator.isMemorableDateQuestion(any(SecurityQuestionCmd.class))).thenCallRealMethod();
        when(mockCmd.getSecurityQuestion()).thenReturn(SECURITY_QUESTION_MEMORABLE_DATE);
        assertTrue(mockValidator.isMemorableDateQuestion(mockCmd));
    }

    @Test
    public void isMemorableDateQuestionShouldReturnFalse() {
        when(mockValidator.isMemorableDateQuestion(any(SecurityQuestionCmd.class))).thenCallRealMethod();
        when(mockCmd.getSecurityQuestion()).thenReturn(EMPTY);
        assertFalse(mockValidator.isMemorableDateQuestion(mockCmd));
    }

    @Test
    public void isAnswerNotConfirmedShouldReturnTrue() {
        when(mockValidator.isAnswerNotConfirmed(any(SecurityQuestionCmd.class))).thenCallRealMethod();
        when(mockCmd.getSecurityAnswer()).thenReturn(SECURITY_ANSWER_1);
        when(mockCmd.getConfirmSecurityAnswer()).thenReturn(CONFIRM_SECURITY_ANSWER_1);
        assertTrue(mockValidator.isAnswerNotConfirmed(mockCmd));
    }

    @Test
    public void isAnswerNotConfirmedShouldReturnFalse() {
        when(mockValidator.isAnswerNotConfirmed(any(SecurityQuestionCmd.class))).thenCallRealMethod();
        when(mockCmd.getSecurityAnswer()).thenReturn(SECURITY_ANSWER_1);
        when(mockCmd.getConfirmSecurityAnswer()).thenReturn(SECURITY_ANSWER_1);
        assertFalse(mockValidator.isAnswerNotConfirmed(mockCmd));
    }

    @Test
    public void shouldAcceptValidMemorableDate() {
        SecurityQuestionValidator securityQuestionValidator = new SecurityQuestionValidator();
        SecurityQuestionCmdImpl cmd = getTestSecurityQuestionCmd1();
        cmd.setSecurityQuestion(SECURITY_QUESTION_MEMORABLE_DATE);
        cmd.setSecurityAnswer("23/08/14");
        cmd.setConfirmSecurityAnswer(cmd.getSecurityAnswer());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        securityQuestionValidator.validateMemorableDate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldRejectInvalidMemorableDate() {
        SecurityQuestionValidator securityQuestionValidator = new SecurityQuestionValidator();
        SecurityQuestionCmdImpl cmd = getTestSecurityQuestionCmd1();
        cmd.setSecurityQuestion(SECURITY_QUESTION_MEMORABLE_DATE);
        cmd.setSecurityAnswer("39/08/14");
        cmd.setConfirmSecurityAnswer(cmd.getSecurityAnswer());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        securityQuestionValidator.validateMemorableDate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldRejectBadlyFormattedMemorableDate() {
        SecurityQuestionValidator securityQuestionValidator = new SecurityQuestionValidator();
        SecurityQuestionCmdImpl cmd = getTestSecurityQuestionCmd1();
        cmd.setSecurityQuestion(SECURITY_QUESTION_MEMORABLE_DATE);
        cmd.setSecurityAnswer("1/8/14");
        cmd.setConfirmSecurityAnswer(cmd.getSecurityAnswer());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        securityQuestionValidator.validateMemorableDate(cmd, errors);

        assertTrue(errors.hasErrors());
    }
}
