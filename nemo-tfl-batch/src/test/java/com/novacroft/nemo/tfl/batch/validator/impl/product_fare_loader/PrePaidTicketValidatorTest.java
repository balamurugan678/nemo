package com.novacroft.nemo.tfl.batch.validator.impl.product_fare_loader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.tfl.batch.application_service.impl.product_fare_loader.PrePaidTicketRecordServiceImpl;
import com.novacroft.nemo.tfl.batch.application_service.product_fare_loader.PrePaidTicketRecordService;
import com.novacroft.nemo.tfl.batch.job.HibernateBatchJobUpdateStrategyServiceImplTest;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

@RunWith(MockitoJUnitRunner.class)
public class PrePaidTicketValidatorTest {

	private PrePaidTicketValidator prePaidTicketValidator = new PrePaidTicketValidator();

	protected PrePaidTicketRecordService prePaidTicketRecordService = new PrePaidTicketRecordServiceImpl();

	protected Errors errors;

	private String[] record = HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1;

	@Mock
	protected PrePaidTicketDataService mockPaidTicketDataService;

	@Before
	public void setUp() {

		prePaidTicketValidator.paidTicketDataService = mockPaidTicketDataService;
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;

		errors = new BeanPropertyBindingResult(record, "");
	}

	@Test
	public void validateForAValidRecordShouldHaveNoErrors() {
		when(mockPaidTicketDataService.findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
						prePaidTicketRecordService.getPrePaidTicketFromDurationCode(record),
						prePaidTicketRecordService.getPrePaidTicketToDurationCode(record),
						Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketStartZone(record)),
						Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketEndZone(record)),
						prePaidTicketRecordService.getPassengerTypeCode(record),
						prePaidTicketRecordService.getPrePaidTicketDiscountCode(record),
						prePaidTicketRecordService.getPrePaidTicketType(record))).thenReturn(
				new ArrayList<PrePaidTicketDTO>());

		prePaidTicketValidator.validate(record, errors);
		assertFalse(errors.hasErrors());

	}

	@Test
	public void validateAdhocCodeForNullShoulFailVaildation() {
		String[] record = HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1;
		record[0] = null;
		errors = new BeanPropertyBindingResult(record, "");
		prePaidTicketValidator.validate(record, errors);
		assertTrue(errors.hasErrors());

	}

	@Test
	public void validateInvalidPriceShoulFailVaildation() {
		String[] record = HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1;
		record[record.length - 1] = "Price";
		errors = new BeanPropertyBindingResult(record, "");
		prePaidTicketValidator.validate(record, errors);
		assertTrue(errors.hasErrors());

	}

	@Test
	public void isParsableisFalseForIllegalCharacters() {
		String adhocCode = "2,34";
		assertFalse(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void isParsableisFalseForNull() {
		String adhocCode = null;
		assertFalse(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void isParsableisFalseForEmpty() {
		String adhocCode = "";
		assertFalse(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void isParsableisForNegative() {
		String adhocCode = "-3";
		assertTrue(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void isParsableFalseForDecimal() {
		String adhocCode = "-3.0";
		assertFalse(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void isParsableForPositive() {
		String adhocCode = "+3110";
		assertTrue(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void isNotParsableForSpaceAndNumber() {
		String adhocCode = " 30";
		assertFalse(prePaidTicketValidator.isParsableInteger(adhocCode));

	}

	@Test
	public void doesSupport() {
		assertTrue(prePaidTicketValidator.supports(String[].class));
	}

	@Test
	public void doesNotSupport() {
		assertFalse(prePaidTicketValidator.supports(PrePaidTicketDTO.class));
	}

	@Test
	public void shouldNotValidateEffectiveDate() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;
		when(prePaidTicketRecordService.getPrePaidTicketEffectiveDate(any(String[].class))).thenReturn(null);
		prePaidTicketValidator.validateEffectiveDate(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidatePassengerType() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;
		when(prePaidTicketRecordService.getPassengerTypeCode(any(String[].class))).thenReturn(null);
		prePaidTicketValidator.validatePassengerType(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateToDuration() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;
		when(prePaidTicketRecordService.getPrePaidTicketToDurationCode(any(String[].class))).thenReturn(null);
		prePaidTicketValidator.validateToDuration(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateToDurationWhenAnExceptionIsThrown() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = null;
		prePaidTicketValidator.validateToDuration(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateFromDuration() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;
		when(prePaidTicketRecordService.getPrePaidTicketFromDurationCode(any(String[].class))).thenReturn(null);
		prePaidTicketValidator.validateFromDuration(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateFromDurationWhenAnExceptionIsThrown() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = null;
		prePaidTicketValidator.validateFromDuration(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateEndZone() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;
		when(prePaidTicketRecordService.getPrePaidTicketEndZone(any(String[].class))).thenReturn(null);
		prePaidTicketValidator.validateEndZone(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateEndZoneWhenAnExceptionIsThrown() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = null;
		prePaidTicketValidator.validateEndZone(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateStartZone() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = prePaidTicketRecordService;
		when(prePaidTicketRecordService.getPrePaidTicketStartZone(any(String[].class))).thenReturn(null);
		prePaidTicketValidator.validateStartZone(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidateStartZoneWhenAnExceptionIsThrown() {
		prePaidTicketRecordService = mock(PrePaidTicketRecordServiceImpl.class);
		prePaidTicketValidator.prePaidTicketRecordService = null;
		prePaidTicketValidator.validateStartZone(errors, record);
		assertTrue(errors.hasErrors());
	}
	
	@Test
	public void hasOverlap(){
		ArrayList<PrePaidTicketDTO> dtos = new ArrayList<PrePaidTicketDTO>();
		dtos.add(PrePaidTicketTestUtil.getTestPrePaidTicketDTO1());
		dtos.add(PrePaidTicketTestUtil.getTestDuplicatePrePaidTicketDTO1());
		when(mockPaidTicketDataService.findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
				prePaidTicketRecordService.getPrePaidTicketFromDurationCode(record),
				prePaidTicketRecordService.getPrePaidTicketToDurationCode(record),
				Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketStartZone(record)),
				Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketEndZone(record)),
				prePaidTicketRecordService.getPassengerTypeCode(record),
				prePaidTicketRecordService.getPrePaidTicketDiscountCode(record),
				prePaidTicketRecordService.getPrePaidTicketType(record))).thenReturn(
		dtos);
		prePaidTicketValidator.validate(record, errors);
		assertTrue(errors.hasErrors());
		
	}
	
	@Test
	public void doesNotOverlap(){
		ArrayList<PrePaidTicketDTO> dtos = new ArrayList<PrePaidTicketDTO>();
		dtos.add(PrePaidTicketTestUtil.getTestPrePaidTicketDTO1());
		when(mockPaidTicketDataService.findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
				prePaidTicketRecordService.getPrePaidTicketFromDurationCode(record),
				prePaidTicketRecordService.getPrePaidTicketToDurationCode(record),
				Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketStartZone(record)),
				Integer.parseInt(prePaidTicketRecordService.getPrePaidTicketEndZone(record)),
				prePaidTicketRecordService.getPassengerTypeCode(record),
				prePaidTicketRecordService.getPrePaidTicketDiscountCode(record),
				prePaidTicketRecordService.getPrePaidTicketType(record))).thenReturn(
		dtos);
		prePaidTicketValidator.validate(record, errors);
		assertTrue(errors.hasErrors());
	}

}