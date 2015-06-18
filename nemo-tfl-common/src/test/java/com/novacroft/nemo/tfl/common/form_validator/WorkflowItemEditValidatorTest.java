package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.*;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.*;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.*;


import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;

@RunWith(Parameterized.class)
public class WorkflowItemEditValidatorTest {
    private WorkflowItemEditValidator validator;
    private WorkflowEditCmd cmd;
    private String errorField;
    private boolean expectedResult;
    private Errors errors;

    @Before
    public void setUp() {
        validator = new WorkflowItemEditValidator();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
    }
    
    public WorkflowItemEditValidatorTest(String testName, WorkflowEditCmd cmd, String errorField, boolean expectedResul) {
        this.cmd = cmd;
        this.errorField = errorField;
        this.expectedResult = expectedResul;
    }
    
    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {"shouldValidate", getValidWorkflowEditCmd(), null, false},
                        {"shouldNotValidateIfTitleHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_TITLE, true},
                        {"shouldNotValidateIfFirstNameHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_FIRST_NAME, true},
                        {"shouldNotValidateIfInitialsasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_INITIALS, true},
                        {"shouldNotValidateIfLastNameHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_LAST_NAME, true},
                        {"shouldNotValidateIfUsernameHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_USERNAME, true},
                        {"shouldNotValidateIfHouseNameNumberHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_HOUSE_NAME_NUMBER, true},
                        {"shouldNotValidateIfStreetHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_STREET, true},
                        {"shouldNotValidateIfTownHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_TOWN, true},
                        {"shouldNotValidateIfCountryHasInvalidCharacters", getInvalidWorkflowEditCmd(), FIELD_COUNTRY, true},
                        {"shouldNotValidateIfRefundScenarioTypeIsInvalid", getInvalidWorkflowEditCmd(), FIELD_REFUND_SCENARIO_TYPE, true},
                        {"shouldNotValidateIfRefundScenarioSubTypeIsInvalid", getInvalidWorkflowEditCmd(), FIELD_REFUND_SCENARIO_SUB_TYPE, true},
                        {"shouldNotValidateIfStartOfRefundablePeriodIsInvalid", getInvalidWorkflowEditCmd(), FIELD_START_OF_REFUNDABLE_DATE, true},
                        {"shouldNotValidateIfReasonIsEmpty", getInvalidWorkflowEditCmd(), FIELD_REASON, true},
                        {"shouldNotValidateIfStartOfRefundablePeriodIsInvalid", getInvalidWorkflowEditCmd(), FIELD_START_OF_REFUNDABLE_DATE, true},
                        {"shouldNotValidateIfStartOfRefundablePeriodIsInvalid", getInvalidWorkflowEditCmd(), FIELD_START_OF_REFUNDABLE_DATE, true},
                        {"shouldValidateIfFirstNameHasSpaces", getValidFirstLastNamesAndTownWorkflowEditCmd(), null, false},
                        {"shouldNotValidateIfUsernameIsEmpty", getWorkflowEditCmdEmptyUsername(), null, true},
                        {"shouldNotValidateIfHouseNameNumberIsEmpty", getWorkflowEditCmdEmptyHouseNAmeNumber(), null, true},
                        {"shouldNotValidateIfStreetIsEmpty", getWorkflowEditCmdEmptyStreet(), null, true},
                        {"shouldNotValidateIfTownIsEmpty", getWorkflowEditCmdemptyTown(), null, true},
                        {"shouldNotValidateIfCountryIsEmpty", getWorkflowEditCmdEmptyCountry(), null, true},
                        {"shouldNotValidateIfRefundPeriodStartDateIsEmpty", getWorkflowEditCmdEmptyRefundPeriodStartDate(), null, true},
                        {"shouldNotValidateIfTitleIsEmpty", getWorkflowEditCmdEmptyTitle(), null, true},
                        {"shouldNotValidateIfFirstNameIsEmpty", getWorkflowEditCmdEmptyFirstName(), null, true},
                        {"shouldNotValidateIfInitialsIsEmpty", getWorkflowEditCmdEmptyInitials(), null, false},
                        {"shouldNotValidateIfLastNameIsEmpty", getWorkflowEditCmdEmptyLastName(), null, true},
                        {"shouldNotValidateIfReasonExceedsCharacterLimit",getWorkflowEditCmdReasonExceedingLimit(), FIELD_REASON, true}

                        
        });
    }
    
    @Test
    public void validate(){
        validator.validate(cmd, errors);
        assertEquals(this.expectedResult, errors.hasErrors());
        if(this.errorField != null)
            assertTrue(errors.hasFieldErrors(this.errorField));
    }
    
}
