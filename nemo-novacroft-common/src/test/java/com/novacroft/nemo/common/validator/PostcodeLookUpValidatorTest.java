package com.novacroft.nemo.common.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.util.HtmlUtils;

import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.application_service.impl.PAFServiceImpl;
import com.novacroft.nemo.common.command.CommonPostcodeCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.test_support.AddressTestUtil;

public class PostcodeLookUpValidatorTest {
	private PostcodeLookUpValidator validator = new PostcodeLookUpValidator();
	private PAFService mockPAFService;
	
	@Before
	public void setUp() {
		mockPAFService = mock(PAFServiceImpl.class);
		validator.pafService = mockPAFService;
	}

	@Test
	public void shouldSupportClass() {
		assertTrue(validator.supports(CommonPostcodeCmd.class));
	}
	
	@Test
	public void shouldNotSupportClass() {
		assertFalse(validator.supports(String.class));
	}
	
	@Test
	public void shouldValidate() {
		SelectListDTO list = new SelectListDTO();
		list.getOptions().add(new SelectListOptionDTO(HtmlUtils.htmlEscape(AddressTestUtil.getTestAddressDTO1().toString()), 0, ""));
		when(mockPAFService.getAddressesForPostcodeSelectList(anyString())).thenReturn(list);
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("M1 1AA");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
	}
	
	@Test
	public void shouldNotValidate() {
		when(mockPAFService.getAddressesForPostcodeSelectList(anyString())).thenReturn(new SelectListDTO());
        TestPostcodeCmdImpl cmd = new TestPostcodeCmdImpl("XXXXX");
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
	}
	
}
