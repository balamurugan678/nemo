package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.ContactDetailsCmd;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;

@RunWith(MockitoJUnitRunner.class)
public class CompleteJourneyValidatorTest {

	private static final String TEST_REASON = "Test";

	protected CompleteJourneyValidator completeJourneyValidator;
	
	protected CompleteJourneyCommandImpl commandImpl;

	
	
	@Before
	public void setUp(){
		commandImpl = new CompleteJourneyCommandImpl();
		completeJourneyValidator = new CompleteJourneyValidator();
	}
	
	
	@Test
	public void shouldSupportClass() {
		assertTrue(completeJourneyValidator.supports(CompleteJourneyCommandImpl.class));
	}
	
	@Test
    public void shouldNotSupportClass() {
        assertFalse(completeJourneyValidator.supports(ContactDetailsCmd.class));
    }
	
	@Test
    public void shouldValidateData() {
		commandImpl.setReasonForMissisng(TEST_REASON);
		commandImpl.setMissingStationId(2);
		commandImpl.setPickUpStation(2l);
        

        Errors errors = new BeanPropertyBindingResult(commandImpl, "cmd");
        completeJourneyValidator.validate(commandImpl, errors);
        assertFalse(errors.hasErrors());
    }
	
	@Test
    public void shouldNotValidateEmptyReason() {
		commandImpl.setMissingStationId(2);
		commandImpl.setPickUpStation(2l);
        

        Errors errors = new BeanPropertyBindingResult(commandImpl, "cmd");
        completeJourneyValidator.validate(commandImpl, errors);
        assertTrue(errors.hasErrors());
    }
	
	@Test
    public void shouldNotValidateEmptyMissingStation() {
		commandImpl.setReasonForMissisng(TEST_REASON);
		commandImpl.setPickUpStation(2l);
        

        Errors errors = new BeanPropertyBindingResult(commandImpl, "cmd");
        completeJourneyValidator.validate(commandImpl, errors);
        assertTrue(errors.hasErrors());
    }
	
	@Test
    public void shouldNotValidateEmptyPickupAndPreffereStationStation() {
		commandImpl.setReasonForMissisng(TEST_REASON);
		commandImpl.setMissingStationId(2);
		
        Errors errors = new BeanPropertyBindingResult(commandImpl, "cmd");
        completeJourneyValidator.validate(commandImpl, errors);
        assertTrue(errors.hasErrors());
    }
}
