package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;

public class ApprovalsRejectionValidatorTest {
    private ApprovalsRejectionValidator validator;
    private WorkflowRejectCmd cmd;

    private static final String REJECT_REASON = "Duplicate Case";
    private static final String VALID_REJECT_FREE_TEXT = "This is valid rejection free text with less than 150 character limit";
    private static String GREATER_THAN_CHAR_LIMIT_REJECT_FREE_TEXT;
    private static int CHARACTER_LIMIT = 150;
    private static char REPEATED_CHAR = 'a';
    private static String CMD_STRING = "cmd";

    @BeforeClass
    public static void classSetUp() {
        GREATER_THAN_CHAR_LIMIT_REJECT_FREE_TEXT = generateGreaterThanCharLimitRejectFreeString();
    }

    @Before
    public void setUp() {
        validator = new ApprovalsRejectionValidator();
        cmd = new WorkflowRejectCmd();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(WorkflowRejectCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setRejectReason(REJECT_REASON);
        cmd.setRejectFreeText(VALID_REJECT_FREE_TEXT);
        Errors errors = new BeanPropertyBindingResult(cmd, CMD_STRING);
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfReasonIsNull() {
        cmd.setRejectFreeText(GREATER_THAN_CHAR_LIMIT_REJECT_FREE_TEXT);
        Errors errors = new BeanPropertyBindingResult(cmd, CMD_STRING);
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithGreaterThanCharLimitRejectFreeText() {
        cmd.setRejectReason(REJECT_REASON);
        cmd.setRejectFreeText(GREATER_THAN_CHAR_LIMIT_REJECT_FREE_TEXT);
        Errors errors = new BeanPropertyBindingResult(cmd, CMD_STRING);
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNoFreeTextIfReasonIsOther() {
        cmd.setRejectReason(RefundConstants.OTHER);
        cmd.setRejectFreeText(StringUtil.EMPTY_STRING);
        Errors errors = new BeanPropertyBindingResult(cmd, CMD_STRING);
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    private static String generateGreaterThanCharLimitRejectFreeString() {
        StringBuilder greaterThanCharLimitRejectFreeString = new StringBuilder();
        for (int i = 0; i <= CHARACTER_LIMIT; i++) {
            greaterThanCharLimitRejectFreeString.append(REPEATED_CHAR);
        }
        return greaterThanCharLimitRejectFreeString.toString();
    }

}
