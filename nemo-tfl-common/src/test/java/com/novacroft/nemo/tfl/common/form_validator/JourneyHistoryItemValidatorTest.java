package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.JourneyTestUtil;
import com.novacroft.nemo.tfl.common.command.JourneyHistoryItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;

public class JourneyHistoryItemValidatorTest {

    private JourneyHistoryItemValidator validator;
    private JourneyHistoryItemCmdImpl cmd;
    private Errors errors;

    @Before
    public void setUp() {
        validator = new JourneyHistoryItemValidator();
        cmd = new JourneyHistoryItemCmdImpl();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
    }

    @Test
    public void shouldPassValidation() {
        cmd.setCardId(CardTestUtil.CARD_ID_1);
        cmd.setJourneyDate(DateTestUtil.getApr03());
        cmd.setJourneyId(JourneyTestUtil.JOURNEY_ID_1);
        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);
        doNothing().when(validatorSpy).rejectIfNotShortDate(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);

        validatorSpy.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldFailValidationNullCardId() {
        cmd.setCardId(null);
        cmd.setJourneyDate(DateTestUtil.getApr03());
        cmd.setJourneyId(JourneyTestUtil.JOURNEY_ID_1);
        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);
        doNothing().when(validatorSpy).rejectIfNotShortDate(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);

        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldFailValidationNullJourneyId() {
        cmd.setCardId(CardTestUtil.CARD_ID_1);
        cmd.setJourneyDate(DateTestUtil.getApr03());
        cmd.setJourneyId(null);
        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);
        doNothing().when(validatorSpy).rejectIfNotShortDate(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);

        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateErrorsOnFieldJourneyDate() {
        cmd.setCardId(CardTestUtil.CARD_ID_1);
        cmd.setJourneyId(null);
        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfNotShortDate(errors, PageCommandAttribute.FIELD_JOURNEY_DATE);

        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void supportsClass() {
        assertTrue(validator.supports(JourneyHistoryItemCmd.class));
    }

    @Test
    public void notSupportsClass() {
        assertFalse(validator.supports(AddPaymentCardActionValidator.class));
    }

}
