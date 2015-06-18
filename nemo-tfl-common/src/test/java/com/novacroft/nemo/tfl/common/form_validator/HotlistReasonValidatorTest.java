package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.HOTLISTED_CARD_REASON_ID;
import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.HOTLIST_STATUS_READYTOEXPORT;
import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.LOST_STOLEN_OPTION_TYPE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;

/**
 * BusPassValidator unit tests
 */
public class HotlistReasonValidatorTest {
    private HotlistReasonValidator validator;
    private LostOrStolenCardCmdImpl cmd;

    private static final Integer HOTLIST_REASON_ID = 1;

    @Before
    public void setUp() {
        validator = new HotlistReasonValidator();
        cmd = new LostOrStolenCardCmdImpl();
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(SelectCardCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setHotlistReasonId(HOTLIST_REASON_ID);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullHotlistReasonId() {
        cmd.setHotlistReasonId(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
        
    }
    
    @Test
    public void shouldRejectIfProcessingRefundForHotListedCard(){
        cmd.setHotlistReasonId(HOTLIST_REASON_ID);
    	cmd.setLostStolenOptions(LOST_STOLEN_OPTION_TYPE);
        cmd.setHotlistedCardReasonId(HOTLISTED_CARD_REASON_ID);
        cmd.setHotlistStatus(HOTLIST_STATUS_READYTOEXPORT);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
    }
}
