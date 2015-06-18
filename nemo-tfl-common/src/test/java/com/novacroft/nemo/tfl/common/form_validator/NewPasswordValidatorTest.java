package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NewPasswordValidatorTest {
    
    private NewPasswordValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new NewPasswordValidator();
    }

    @Test
    public void isTooShortBlankPassword() {
       assertFalse(validator.isTooShort(""));
    }
    
    @Test
    public void doesNotContainAnUpperCaseLetterBlankPassword(){
        assertFalse(validator.doesNotContainAnUpperCaseLetter(""));
    }
    
    @Test 
    public void doesNotContainALowerCaseLetterBlankPassword(){
        assertFalse(validator.doesNotContainALowerCaseLetter(""));
    }
    
    @Test
    public void doesNotContainADigitBlankPassword(){
        assertFalse(validator.doesNotContainADigit(""));
    }
    
    @Test
    public void doesContainSpecialCharactersBlankPassword(){
        assertFalse(validator.doesContainSpecialCharacters(""));
    }

}
