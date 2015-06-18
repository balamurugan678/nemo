package com.novacroft.nemo.mock_single_sign_on.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.test_support.UserTestUtil;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;

public class UpdatePersonalDetailsControllerTest {
    private UpdatePersonalDetailsController controller;
    private LogonServiceImpl mockLogonService;
    private HttpServletResponse mockHttpResponse;
    private UserConverter mockUserConverter;
    private CountrySelectListService mockCountrySelectListService;
    private SelectListService mockSelectListService;
    private User mockUser;
    private BindingResult mockErrors;
    private MasterCustomerDataService mockMasterCustomerDataService;

    @Before
    public void setUp() {
        controller = new UpdatePersonalDetailsController();
        mockUserConverter = mock(UserConverter.class);
        mockCountrySelectListService = mock(CountrySelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        mockLogonService = mock(LogonServiceImpl.class);
        mockMasterCustomerDataService = mock(MasterCustomerDataService.class);

        controller.logonService = mockLogonService;
        controller.converter = mockUserConverter;
        controller.countrySelectListService = mockCountrySelectListService;
        controller.selectListService = mockSelectListService;
        controller.masterCustomerDataService = mockMasterCustomerDataService;

        mockHttpResponse = mock(HttpServletResponse.class);
        mockErrors = mock(BindingResult.class);

        mockUser = mock(User.class);
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
    public void shouldShowUpdatePage() {
        when(mockMasterCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(mockUser);
        when(mockUserConverter.convert(any(User.class))).thenReturn(UserTestUtil.getUserDetailsCmdWithStatus());
        when(mockMasterCustomerDataService.getPassword(anyLong())).thenReturn(CustomerTestUtil.PASSWORD_3);

        ModelAndView modelAndView = controller.showUpdatePersonalDetails(UserTestUtil.getUserDetailsCmdWithStatus(), UserTestUtil.CUSTOMER_ID);
        assertEquals("UpdateView", modelAndView.getViewName());

        verify(mockMasterCustomerDataService).findMasterCustomerById(anyLong());
        verify(mockUserConverter).convert(any(User.class));
        verify(mockMasterCustomerDataService).getPassword(anyLong());
    }

    @Test
    public void shouldNotUpdateUserIfPasswordEmpty() {
        UserDetailsCmd cmdWithoutPassword = UserTestUtil.getUserDetailsCmdWithStatus();
        cmdWithoutPassword.setPassword(null);

        ModelAndView modelAndView = controller.update(mockHttpResponse, cmdWithoutPassword, mockErrors);

        assertEquals("UpdateView", modelAndView.getViewName());
        verify(mockMasterCustomerDataService, never()).updateMasterCustomer(any(User.class));
    }

    @Test
    public void shouldUpdateUser() {
        when(mockMasterCustomerDataService.updateMasterCustomer(any(User.class))).thenReturn(true);
        when(mockUserConverter.convert(any(UserDetailsCmd.class))).thenReturn(UserTestUtil.getUser());

        ModelAndView modelAndView = controller.update(mockHttpResponse, UserTestUtil.getUserDetailsCmdWithStatus(), mockErrors);

        assertEquals("UpdateView", modelAndView.getViewName());
        verify(mockMasterCustomerDataService).updateMasterCustomer(any(User.class));
    }
}
