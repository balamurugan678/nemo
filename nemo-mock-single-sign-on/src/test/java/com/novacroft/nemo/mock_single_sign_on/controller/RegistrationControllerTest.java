package com.novacroft.nemo.mock_single_sign_on.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.PASSWORD_3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.mock_single_sign_on.command.UserDetailsCmd;
import com.novacroft.nemo.mock_single_sign_on.converter.UserConverter;
import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.service.impl.LogonServiceImpl;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.test_support.UserTestUtil;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;

public class RegistrationControllerTest {
    private RegistrationController controller;
    private LogonServiceImpl mockLogonService;
    private HttpServletResponse mockHttpResponse;
    private CountrySelectListService mockCountrySelectListService;
    private SelectListService mockSelectListService;
    private BindingResult mockErrors;
    private MasterCustomerDataService mockMasterCustomerDataService;
    private UserConverter mockConverter;

    @Before
    public void setUp() {
        controller = new RegistrationController();

        mockCountrySelectListService = mock(CountrySelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        mockLogonService = mock(LogonServiceImpl.class);
        mockMasterCustomerDataService = mock(MasterCustomerDataService.class);
        mockConverter = mock(UserConverter.class);

        controller.logonService = mockLogonService;
        controller.countrySelectListService = mockCountrySelectListService;
        controller.selectListService = mockSelectListService;
        controller.masterCustomerDataService = mockMasterCustomerDataService;
        controller.converter = mockConverter;

        mockHttpResponse = mock(HttpServletResponse.class);
        mockErrors = mock(BindingResult.class);
    }

    @Test
    public void shouldPopulateModelAttributes() {
        Model model = new BindingAwareModelMap();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", "entry");
        when(mockLogonService.convert(anyString())).thenReturn(map);
        when(mockCountrySelectListService.getSelectList()).thenReturn(SelectListTestUtil.getTestSelectListDTO());
        when(mockSelectListService.getSelectList(anyString())).thenReturn(SelectListTestUtil.getTestSelectListDTO());
        controller.populateModelAttributes(model);
        assertTrue(model.containsAttribute("appList"));
        assertTrue(model.containsAttribute(PageAttribute.COUNTRIES));
        assertTrue(model.containsAttribute(PageAttribute.TITLES));
    }

    @Test
    public void shouldShowRegistrationPage() {
        ModelAndView modelAndView = controller.showRegister(UserTestUtil.getUserDetailsCmdWithStatus());
        assertEquals("RegisterView", modelAndView.getViewName());
    }

    @Test
    public void shouldRegisterUser() {
        UserDetailsCmd testCmd = UserTestUtil.getUserDetailsCmdWithStatus();
        User testUser = UserTestUtil.getUser();
        when(mockConverter.convert(testCmd)).thenReturn(testUser);
        when(mockMasterCustomerDataService.createMasterCustomer(any(User.class), anyString())).thenReturn(testUser);

        ModelAndView modelAndView = controller.register(mockHttpResponse, testCmd, mockErrors);

        assertEquals("RegisterView", modelAndView.getViewName());
        verify(mockMasterCustomerDataService).createMasterCustomer(testUser, PASSWORD_3);
    }
}
