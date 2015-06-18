package com.novacroft.nemo.common.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.CommonPostcodeCmd;
import com.novacroft.nemo.common.utils.StringUtil;

/**
 * Postcode Validator unit tests
 */

public class PostcodeValidatorTest {
    @Test
    public void shouldSupportClass() {
        PostcodeValidator validator = new PostcodeValidator();
        assertTrue(validator.supports(CommonPostcodeCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        PostcodeValidator validator = new PostcodeValidator();
        assertFalse(validator.supports(Long.class));
    }

    @Test
    public void shouldValidate_AN_NAA_FormatPostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("M1 1AA");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidate_ANN_NAA_FormatPostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("M60 1NW");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidate_AAN_NAA_FormatPostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("CR2 6XH");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidate_AANN_NAA_FormatPostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("DN55 1PT");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidate_ANA_NAA_FormatPostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("W1P 1BB");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidate_AANA_NAA_FormatPostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("EC1A 1BB");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidatePostcodeWithoutSpace() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("EC1A1BB");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidatePostcodeWithInvalidInward() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("1A 1AA");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidatePostcodeWithInvalidOutward() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("M1 Z99");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidate_Null_PostCode() {
        PostcodeValidator validator = new PostcodeValidator();
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldReturnPostCodeWithSpace() {
        String postcode = "NN36UR";
        String total = "";
        if (postcode.indexOf(StringUtil.SPACE) < 0) {
            String postCodeFirstPart = postcode.substring(0, postcode.length() - 3);
            String postCodeSecondPart = postcode.substring(postcode.length() - 3, postcode.length());
            total = postCodeFirstPart + " " + postCodeSecondPart;
        }
        assertEquals("NN3 6UR", total);
    }
    
    @Test
    public void shouldNotValidateIfPostcodeIsBlank(){
        PostcodeValidator validator = new PostcodeValidator();
        assertFalse(validator.validate(""));
    }
    
}