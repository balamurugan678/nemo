package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

public class EmailNotificationValidatorTest {

    private EmailNotificationValidator validator;
    private CartItemCmdImpl cmd;
    @Before
    public void setUp(){
        validator = new EmailNotificationValidator();
        cmd = new CartItemCmdImpl();
    }
    
    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(TravelCardCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }
    
    @Test
    public void shouldValidateTest(){
        cmd.setStartDate(formatDate(DateUtil.addDaysToDate(new Date(), 15)));
        cmd.setTravelCardType(Durations.SEVEN_DAYS.getDurationType());
        cmd.setEmailReminder("14");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateTest(){
        cmd.setStartDate(formatDate(DateUtil.addDaysToDate(new Date(), 5)));
        cmd.setTravelCardType(Durations.SEVEN_DAYS.getDurationType());
        cmd.setEmailReminder("18");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
}
