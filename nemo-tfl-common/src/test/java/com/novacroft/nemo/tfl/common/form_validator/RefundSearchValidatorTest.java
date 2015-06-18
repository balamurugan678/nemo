package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.RefundSearchTestUtil.*;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AGENT_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AGENT_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_BACS_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CHEQUE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CUSTOMER_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CUSTOMER_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_SAP_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;

@RunWith(Parameterized.class)
public class RefundSearchValidatorTest {
    private RefundSearchValidator validator;
    private Errors errors;
    private RefundSearchCmd cmd;
    private String field;
    private boolean expectedResult;

    @Before
    public void setUp() {
        validator = new RefundSearchValidator();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
    }
    
    public RefundSearchValidatorTest(String testName, RefundSearchCmd cmd, String field, boolean expectedResult) {
        this.cmd = cmd;
        this.field = field;
        this.expectedResult = expectedResult;
    }
    
    @Parameters(name="{index}: test({0}) expected={1}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {"shouldValidate", getSearch(), null, false},
                        {"shouldNotValidateIfCaseNumberHasInvalidCharacters", getInvalidSearch(), FIELD_CASE_NUMBER,true},
                        {"shouldNotValidateIfAgentLastNameHasInvalidCharacters", getInvalidSearch(), FIELD_AGENT_LAST_NAME, true},
                        {"shouldNotValidateIfAgentFirstNameHasInvalidCharacters", getInvalidSearch(), FIELD_AGENT_FIRST_NAME, true},
                        {"shouldNotValidateIfSapNumberHasInvalidCharacters", getInvalidSearch(), FIELD_SAP_NUMBER, true},
                        {"shouldNotValidateIfCustomerLastNameHasInvalidCharacters", getInvalidSearch(), FIELD_CUSTOMER_LAST_NAME, true},
                        {"shouldNotValidateIfCustomerFirstNameHasInvalidCharacters", getInvalidSearch(), FIELD_CUSTOMER_FIRST_NAME, true},
                        {"shouldNotValidateIfCardNumberHasInvalidCharacters", getInvalidSearch(), FIELD_CARD_NUMBER, true},
                        {"shouldNotValidateIfBacsNumberHasInvalidCharacters", getInvalidSearch(), FIELD_BACS_NUMBER, true},
                        {"shouldNotValidateIfChequeNumberHasInvalidCharacters", getInvalidSearch(), FIELD_CHEQUE_NUMBER, true},
                        {"shouldNotValidateIfCaseNumberIsEmpty", getSearchCaseNumberEmpty(), null, true},
                        {"shouldNotValidateIfFirstNameIsEmpty", getSearchFirstNameEmpty(), null, false},
                        {"shouldNotValidateIfLastNameIsEmpty", getSearchLastNameEmpty(), null, false},
                        {"shouldNotValidateIfSapNumberIsEmpty", getSearchSapNumberEmpty(), null, false},
                        {"shouldNotValidateIfCardNumberIsEmpty", getSearchCardNumber(), null, false},
                        {"shouldNotValidateIfBacsNumberIsEmpty", getSearchBacsNumberEmpty(), null, false},
                        {"shouldNotValidateIfChequeNumberIsEmpty", getSearchChequeNumber(), null, false},
                        {"shouldNotValidateFirstLengthLessThanMinimum", getSearchFirstNameLengthLessThanMinimumLength(), null, true},
                        {"shouldNotValidateLastLengthLessThanMinimum", getSearchLastNameLengthLessThanMinimumLength(), null, true}
        });
    }
    
    @Test
    public void validate(){
        validator.validate(cmd, errors);
        assertEquals(this.expectedResult, errors.hasErrors());
        if(this.field != null)
            assertTrue(errors.hasFieldErrors(this.field));
    }

}
