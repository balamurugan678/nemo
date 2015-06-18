package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.JourneyHistoryCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;

/**
 * JourneyHistoryValidator unit tests
 */
public class JourneyHistoryValidatorTest {

    private JourneyHistoryValidator validator;
    private JourneyHistoryCmdImpl cmd;

    @Before
    public void setUp() {
        validator = new JourneyHistoryValidator();
        cmd = new JourneyHistoryCmdImpl();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(JourneyHistoryCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldValidateWithThisWeekSelection() {
        cmd.setWeekNumberFromToday(Integer.valueOf(0));
        cmd.setStartDate(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(!errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithCustomPeriodSelection() {
        cmd.setWeekNumberFromToday(Integer.valueOf(10));
        cmd.setStartDate(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithDateDiffMoreThan56() {
        cmd.setWeekNumberFromToday(Integer.valueOf(10));
        cmd.setStartDate(DateUtil.parse("01/01/2014"));
        cmd.setEndDate(DateUtil.parse("01/05/2014"));
        JourneyHistoryValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(any(Errors.class), any(String.class));
        doNothing().when(validatorSpy).rejectIfNotShortDate(any(Errors.class), any(String.class));
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithStartDateDiffOver56Days() {
        cmd.setWeekNumberFromToday(Integer.valueOf(10));
        cmd.setStartDate(DateUtil.parse("01/04/2014"));
        cmd.setEndDate(DateUtil.parse("01/05/2014"));
        JourneyHistoryValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(any(Errors.class), any(String.class));
        doNothing().when(validatorSpy).rejectIfNotShortDate(any(Errors.class), any(String.class));
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithStartDateAfterToday() {
        cmd.setWeekNumberFromToday(Integer.valueOf(10));
        cmd.setStartDate(new Date());
        cmd.setEndDate(DateUtil.getBusinessDay(new Date(), +30));
        JourneyHistoryValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(any(Errors.class), any(String.class));
        doNothing().when(validatorSpy).rejectIfNotShortDate(any(Errors.class), any(String.class));
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithStartDateDiffLessThan56() {
        cmd.setWeekNumberFromToday(Integer.valueOf(7));
        cmd.setStartDate(DateUtil.getBusinessDay(new Date(), -50));
        cmd.setEndDate(DateUtil.getBusinessDay(new Date(), -30));
        JourneyHistoryValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(any(Errors.class), any(String.class));
        doNothing().when(validatorSpy).rejectIfNotShortDate(any(Errors.class), any(String.class));
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validatorSpy.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithStartDateAfterEndDate() {
        cmd.setWeekNumberFromToday(Integer.valueOf(10));
        cmd.setStartDate(DateUtil.parse("02/05/2014"));
        cmd.setEndDate(DateUtil.parse("01/05/2014"));
        JourneyHistoryValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(any(Errors.class), any(String.class));
        doNothing().when(validatorSpy).rejectIfNotShortDate(any(Errors.class), any(String.class));
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validatorSpy.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithNoWeekNumberFromTodayTest() {
        cmd.setWeekNumberFromToday(null);
        cmd.setStartDate(DateUtil.parse("02/05/2014"));
        cmd.setEndDate(DateUtil.parse("08/05/2014"));
        JourneyHistoryValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(any(Errors.class), any(String.class));
        doNothing().when(validatorSpy).rejectIfNotShortDate(any(Errors.class), any(String.class));
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validatorSpy.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
}
