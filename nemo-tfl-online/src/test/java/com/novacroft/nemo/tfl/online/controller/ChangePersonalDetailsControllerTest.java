package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO2AsJson;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_CUSTOMER_ID;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.createMockResponseDTO;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnTokenService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PersonalDetailsValidator;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;


/**
 * Unit tests for ChangePersonalDetailsController
 */
public class ChangePersonalDetailsControllerTest {
    
    private ChangePersonalDetailsController controller;
    private CountrySelectListService mockCountrySelectListService;
    private SelectListService mockSelectListService;
    private PersonalDetailsService mockPersonalDetailsService;
    private PostcodeValidator mockPostcodeValidator;
    private PersonalDetailsValidator mockPersonalDetailsValidator;
    private PAFService mockPafService;
    private BeanPropertyBindingResult errors;
    private CountryDataService mockCountryDataService;
    private SingleSignOnTokenService mockSsoTokenService;

    private PersonalDetailsCmdImpl cmd;
    private RedirectAttributes redirectAttributes;
    
    @Before
    public void setup(){
        controller = new ChangePersonalDetailsController();
        
        mockCountrySelectListService = mock(CountrySelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        mockPersonalDetailsService = mock(PersonalDetailsService.class);
        mockPostcodeValidator = mock(PostcodeValidator.class);
        mockPersonalDetailsValidator = mock(PersonalDetailsValidator.class);
        mockPafService = mock(PAFService.class);
        mockCountryDataService = mock(CountryDataService.class);
        mockSsoTokenService = mock(SingleSignOnTokenService.class);
        
        controller.countrySelectListService = mockCountrySelectListService;
        controller.selectListService = mockSelectListService;
        controller.personalDetailsService = mockPersonalDetailsService;
        controller.postcodeValidator = mockPostcodeValidator;
        controller.personalDetailsValidator = mockPersonalDetailsValidator;
        controller.pafService = mockPafService;
        controller.countryDataService = mockCountryDataService;
        controller.singleSignOnTokenService = mockSsoTokenService;
    }
    
    @Test
    public void cancelShouldRedirectToDashboard() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldNotSelectAddressWithNoAddressSelected() {
        cmd = getTestPersonalDetailsCmd1();
        cmd.setAddressForPostcode("");


        ModelAndView result = controller.selectAddress(cmd);
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        assertEquals(STREET_1, ((PersonalDetailsCmdImpl) (result.getModel().get(PageCommand.PERSONAL_DETAILS))).getStreet());
    }
    
    @Test
    public void shouldSelectAddressWithAddressSelected() {
        cmd = getTestPersonalDetailsCmd1();
        cmd.setAddressForPostcode(getTestAddressDTO2AsJson());
        when(mockPafService.populateAddressFromJson(any(CommonOrderCardCmd.class), anyString())).thenReturn(getTestPersonalDetailsCmd1());
        ModelAndView result = controller.selectAddress(cmd);
        verify(mockPafService, atLeastOnce()).populateAddressFromJson(any(CommonOrderCardCmd.class), anyString());
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
     }
    
    @Test
    public void shouldNotSaveChangesWithInvalidInput() {
        doNothing().when(mockPersonalDetailsValidator).validate(any(Object.class), any(Errors.class));
        when(mockPersonalDetailsService.updatePersonalDetails(getTestPersonalDetailsCmd1())).thenReturn(getTestPersonalDetailsCmd1());
        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(true);

        redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = controller.saveChanges(getTestPersonalDetailsCmd1(), errors, redirectAttributes);
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
    }
    
    @Test
    public void shouldSaveChangesWithValidInputIfNotSingleSignOn() {
        ChangePersonalDetailsController spyController = spy(controller);
        doReturn(Boolean.FALSE).when(spyController).isSingleSignOnAuthenticationOn();
        doNothing().when(mockPersonalDetailsValidator).validate(any(Object.class), any(Errors.class));

        when(mockPersonalDetailsService.updatePersonalDetails(getTestPersonalDetailsCmd1())).thenReturn(getTestPersonalDetailsCmd1());

        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(false);

        redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = spyController.saveChanges(getTestPersonalDetailsCmd1(), errors, redirectAttributes);
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void ShouldNotSaveChangeIfSingleSignOnUpdateFail() {
        ChangePersonalDetailsController spyController = spy(controller);
        doReturn(Boolean.TRUE).when(spyController).isSingleSignOnAuthenticationOn();
        when(mockCountryDataService.findCountryByCode(anyString())).thenReturn(getTestCountryDTO1());
        
        SingleSignOnResponseDTO jsonResponse = createMockResponseDTO();
        jsonResponse.setIsValid(Boolean.FALSE);
        when(mockSsoTokenService.updateMasterCustomerData(anyMapOf(String.class, String.class))).thenReturn(jsonResponse);

        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(false);

        redirectAttributes = new RedirectAttributesModelMap();

        PersonalDetailsCmdImpl testCmd = getTestPersonalDetailsCmd1();
        testCmd.setTflMasterId(SSO_CUSTOMER_ID);
        ModelAndView result = spyController.saveChanges(testCmd, errors, redirectAttributes);
        
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        verify(mockPersonalDetailsService, never()).updatePersonalDetails(any(PersonalDetailsCmdImpl.class));
    }
    
    @Test
    public void ShouldSaveChangeIfSingleSignOnUpdateSuccess() {
        PersonalDetailsCmdImpl testCmd = getTestPersonalDetailsCmd1();
        testCmd.setTflMasterId(SSO_CUSTOMER_ID);
        ChangePersonalDetailsController spyController = spy(controller);
        doReturn(Boolean.TRUE).when(spyController).isSingleSignOnAuthenticationOn();
        
        when(mockPersonalDetailsService.updatePersonalDetails(testCmd)).thenReturn(testCmd);
        when(mockCountryDataService.findCountryByCode(anyString())).thenReturn(getTestCountryDTO1());
        
        SingleSignOnResponseDTO jsonResponse = createMockResponseDTO();
        jsonResponse.setIsValid(Boolean.TRUE);
        when(mockSsoTokenService.updateMasterCustomerData(anyMapOf(String.class, String.class))).thenReturn(jsonResponse);

        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(false);

        redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = spyController.saveChanges(testCmd, errors, redirectAttributes);
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
        verify(mockPersonalDetailsService).updatePersonalDetails(any(PersonalDetailsCmdImpl.class));
    }
    
    @Test
    public void shouldNotFindAddressesForPostcodeWithInvalidInput() {
        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(true);

        doNothing().when(mockPostcodeValidator).validate(any(Object.class), any(Errors.class));
        when(mockPersonalDetailsService.getPersonalDetails(USERNAME_1)).thenReturn(getTestPersonalDetailsCmd1());

        PAFService mockPafService = mock(PAFService.class);
        when(mockPafService.getAddressesForPostcodeSelectList(anyString())).thenReturn(getTestSelectListDTO());

        ModelAndView result = controller.findAddressesForPostcode(getTestPersonalDetailsCmd1(), errors);
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertFalse(result.getModel().containsKey("addressesForPostcode"));
    }
    
    @Test
    public void shouldPopulateTitlesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());

        Model model = new ExtendedModelMap();

        controller.populateTitlesSelectList(model);

        assertTrue(model.asMap().containsKey(PageAttribute.TITLES));
    }

    @Test
    public void shouldPopulateCountriesSelectList() {
        when(mockCountrySelectListService.getSelectList()).thenReturn(getTestSelectListDTO());

        Model model = new ExtendedModelMap();

        controller.populateCountrySelectList(model);

        assertTrue(model.asMap().containsKey(PageAttribute.COUNTRIES));
    }

    @Test
    public void shouldShowChangePersonalDetails() {
        when(mockPersonalDetailsService.getPersonalDetails(USERNAME_1)).thenReturn(getTestPersonalDetailsCmd1());

        ModelAndView result = controller.showChangePersonalDetails(USERNAME_1);

        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }

    @Test
    public void shouldFindAddressesForPostcodeWithValidInput() {
        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(false);

        doNothing().when(mockPostcodeValidator).validate(any(Object.class), any(Errors.class));

        when(mockPersonalDetailsService.getPersonalDetails(USERNAME_1)).thenReturn(getTestPersonalDetailsCmd1());

        when(mockPafService.getAddressesForPostcodeSelectList(anyString())).thenReturn(getTestSelectListDTO());


        ModelAndView result = controller.findAddressesForPostcode(getTestPersonalDetailsCmd1(), errors);
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertTrue(result.getModel().containsKey("addressesForPostcode"));
    }
    
    @Test
    public void shouldNotFindAddressesForInValidPostCodeWithValidPatternMatched() {
        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(true, false);
        doNothing().when(mockPostcodeValidator).validate(any(Object.class), any(Errors.class));
        when(mockPersonalDetailsService.getPersonalDetails(USERNAME_1)).thenReturn(getTestPersonalDetailsCmd1());
        when(mockPafService.getAddressesForPostcodeSelectList(anyString())).thenReturn(getTestSelectListDTO());
        ModelAndView result = controller.findAddressesForPostcode(getTestPersonalDetailsCmd1(), errors);
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }
    
    @Test
    public void isInvalidPostCodeCheckFlagShouldBeTrueIfNoAddressFound() {
        errors = mock(BeanPropertyBindingResult.class);
        when(errors.hasErrors()).thenReturn(false);
        doNothing().when(mockPostcodeValidator).validate(any(Object.class), any(Errors.class));
        SelectListDTO dtoWithNoOption = getTestSelectListDTO();
        dtoWithNoOption.getOptions().clear();
        when(mockPafService.getAddressesForPostcodeSelectList(anyString())).thenReturn(dtoWithNoOption);

        PersonalDetailsCmdImpl testCmd = getTestPersonalDetailsCmd1();
        ModelAndView result = controller.findAddressesForPostcode(testCmd, errors);
        
        assertEquals(PageView.CHANGE_PERSONAL_DETAILS, result.getViewName());
        assertTrue(testCmd.isInvalidPostCodeCheckFlag());
    }
}
