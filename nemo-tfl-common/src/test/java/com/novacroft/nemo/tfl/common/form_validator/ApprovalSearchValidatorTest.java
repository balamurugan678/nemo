package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.getInvalidSearch;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getInvalidSearchCharacters;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getInvalidSearchWhiteSpace;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getSearch;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AGENT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REASON;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATUS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TIME_ON_QUEUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.constant.WorkflowFields;

/**
 * CustomerSearchValidator unit tests
 */
public class ApprovalSearchValidatorTest {
    private ApprovalSearchValidator validator;

    @Before
    public void setUp() {
        validator = new ApprovalSearchValidator();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(ApprovalListCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(this.validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidate() {
        ApprovalListCmdImpl cmd = getSearch();

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    protected void validateField(ApprovalListCmdImpl cmd, String field) {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        assertTrue(errors.hasFieldErrors(field));
    }

    @Test
    public void shouldNotValidateIfCaseNumberHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_CASE_NUMBER);
        validateField(getInvalidSearchCharacters(), FIELD_CASE_NUMBER);
    }
    
    @Test
    public void shouldNotValidateIfDateCreatedIsInvalidDate() {
    	validateField(getInvalidSearch(), WorkflowFields.DATE_CREATED);
    }

    @Test
    public void shouldNotValidateIfPaymentMethodHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_PAYMENT_METHOD);
    }

    @Test
    public void shouldNotValidateIfPaymentMethodIsWhiteSpace() {
        validateField(getInvalidSearchWhiteSpace(), FIELD_PAYMENT_METHOD);
    }

    @Test
    public void shouldNotValidateIfTimeOnQueueHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_TIME_ON_QUEUE);
    }

    @Test
    public void shouldNotValidateIfTimeOnQueueIsWhiteSpace() {
        validateField(getInvalidSearchWhiteSpace(), FIELD_TIME_ON_QUEUE);
    }

    @Test
    public void shouldNotValidateIfAgentHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_AGENT);
    }

    @Test
    public void shouldNotValidateIfAgentIsWhiteSpace() {
        validateField(getInvalidSearchWhiteSpace(), FIELD_AGENT);
    }

    @Test
    public void shouldNotValidateIfStatusHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_STATUS);
    }

    @Test
    public void shouldNotValidateIfStatusIsWhiteSpace() {
        validateField(getInvalidSearchWhiteSpace(), FIELD_STATUS);
    }

    @Test
    public void shouldNotValidateIfReasonHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_REASON);
    }

    @Test
    public void shouldNotValidateIfReasonIsWhiteSpace() {
        validateField(getInvalidSearchWhiteSpace(), FIELD_REASON);
    }

    @Test
    public void shouldNotValidateIfAmountHasInvalidCharacters() {
        validateField(getInvalidSearch(), FIELD_AMOUNT);
    }
}
