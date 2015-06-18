package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_INPUT_FIELD_MINIMUM_LENGTH;
import static com.novacroft.nemo.common.constant.CommonContentCode.NOT_EQUAL;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_CONFIRM_SECURITY_ANSWER;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_SECURITY_ANSWER;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_SECURITY_QUESTION;
import static com.novacroft.nemo.common.constant.SecurityQuestions.SECURITY_QUESTION_MEMORABLE_DATE;
import static com.novacroft.nemo.common.constant.SecurityQuestions.SECURITY_QUESTION_MEMORABLE_PLACE;
import static com.novacroft.nemo.common.constant.SecurityQuestions.SECURITY_QUESTION_YOUR_MOTHERS_MAIDEN_NAME;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.common.validator.BaseValidator;

/**
 * Security question and answer validation
 */
@Component("securityQuestionValidator")
public class SecurityQuestionValidator extends BaseValidator {
    protected static final String MEMORABLE_DATE_PATTERN = "^[0-3][0-9]/[0-1][0-9]/[0-9][0-9]$";

    @Override
    public boolean supports(Class<?> targetClass) {
        return SecurityQuestionCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SecurityQuestionCmd cmd = (SecurityQuestionCmd) target;
        rejectIfMandatoryFieldEmpty(errors, FIELD_SECURITY_QUESTION);
        rejectIfMandatoryFieldEmpty(errors, FIELD_SECURITY_ANSWER);
        rejectIfMandatoryFieldEmpty(errors, FIELD_CONFIRM_SECURITY_ANSWER);
        if (!errors.hasErrors() && (cmd.getSecurityQuestion().equals(SECURITY_QUESTION_MEMORABLE_PLACE) || cmd.getSecurityQuestion().equals(SECURITY_QUESTION_YOUR_MOTHERS_MAIDEN_NAME))) {
            rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(errors, FIELD_SECURITY_ANSWER);
            rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(errors, FIELD_CONFIRM_SECURITY_ANSWER);
            if (!errors.hasErrors() && (cmd.getSecurityAnswer().length() < MINIMUM_FIELD_LENGTH || cmd.getConfirmSecurityAnswer().length() < MINIMUM_FIELD_LENGTH)) {
                errors.rejectValue(FIELD_SECURITY_ANSWER, INVALID_INPUT_FIELD_MINIMUM_LENGTH.errorCode());
                errors.rejectValue(FIELD_CONFIRM_SECURITY_ANSWER, INVALID_INPUT_FIELD_MINIMUM_LENGTH.errorCode());
            }
        }
        validateSecurityAnswerConfirmation(cmd, errors);
        validateMemorableDate(cmd, errors);
    }

    protected void validateSecurityAnswerConfirmation(SecurityQuestionCmd cmd, Errors errors) {
        if (answerHasNoErrors(errors) && answerConfirmHasNoErrors(errors) && isAnswerNotConfirmed(cmd)) {
            errors.rejectValue(FIELD_CONFIRM_SECURITY_ANSWER, NOT_EQUAL.errorCode());
        }
    }

    protected void validateMemorableDate(SecurityQuestionCmd cmd, Errors errors) {
        validateMemorableDateIsValidPattern(cmd, errors);
        validateMemorableDateIsValidDate(cmd, errors);
    }

    protected void validateMemorableDateIsValidPattern(SecurityQuestionCmd cmd, Errors errors) {
        if (answerHasNoErrors(errors) && questionHasNoErrors(errors) && isMemorableDateQuestion(cmd)) {
            rejectIfPatternNotMatched(errors, FIELD_SECURITY_ANSWER, INVALID_DATE_PATTERN.errorCode(), MEMORABLE_DATE_PATTERN);
        }
    }

    protected void validateMemorableDateIsValidDate(SecurityQuestionCmd cmd, Errors errors) {
        if (answerHasNoErrors(errors) && questionHasNoErrors(errors) && isMemorableDateQuestion(cmd)) {
            rejectIfInvalidDate(errors, FIELD_SECURITY_ANSWER, cmd.getSecurityAnswer());
        }
    }

    protected boolean questionHasNoErrors(Errors errors) {
        return !errors.hasFieldErrors(FIELD_SECURITY_QUESTION);
    }

    protected boolean answerHasNoErrors(Errors errors) {
        return !errors.hasFieldErrors(FIELD_SECURITY_ANSWER);
    }

    protected boolean answerConfirmHasNoErrors(Errors errors) {
        return !errors.hasFieldErrors(FIELD_CONFIRM_SECURITY_ANSWER);
    }

    protected boolean isMemorableDateQuestion(SecurityQuestionCmd cmd) {
        return cmd.getSecurityQuestion().equalsIgnoreCase(SECURITY_QUESTION_MEMORABLE_DATE);
    }

    protected boolean isAnswerNotConfirmed(SecurityQuestionCmd cmd) {
        return !cmd.getSecurityAnswer().equals(cmd.getConfirmSecurityAnswer());
    }
}
