package com.novacroft.nemo.mock_single_sign_on.controller;

import static com.novacroft.nemo.test_support.UserTestUtil.CUSTOMER_ID;
import static com.novacroft.nemo.test_support.UserTestUtil.ID;
import static com.novacroft.nemo.test_support.UserTestUtil.getUser;
import static com.novacroft.nemo.test_support.UserTestUtil.getUserDetailsCmdWithStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.mock_single_sign_on.command.UserDetailsCmd;
import com.novacroft.nemo.mock_single_sign_on.constant.Page;
import com.novacroft.nemo.mock_single_sign_on.converter.UserConverter;
import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityAnswer;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityQuestion;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.service.impl.LogonServiceImpl;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;

public class ResetPasswordControllerTest {
    private ResetPasswordController controller;
    private LogonServiceImpl mockLogonService;
    private HttpServletResponse mockHttpResponse;
    private UserConverter mockUserConverter;
    private CountrySelectListService mockCountrySelectListService;
    private SelectListService mockSelectListService;
    private BindingResult mockErrors;
    private MasterCustomerDataService mockMasterCustomerDataService;

    @Before
    public void setUp() {
        controller = new ResetPasswordController();
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
    public void shouldShowResetPasswordPageWithReturnURL() {
        ModelAndView modelAndView = controller.showResetPassword(getUserDetailsCmdWithStatus(), null, PageAttribute.RETURN_URL);

        assertEquals("ResetPasswordView", modelAndView.getViewName());
        assertNotNull(modelAndView.getModelMap().get("UserDetailsCmd"));
    }

    @Test
    public void shouldShowResetPasswordPageWithCustomerId() {
        when(mockMasterCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(getUser());

        ModelAndView modelAndView = controller.showResetPassword(getUserDetailsCmdWithStatus(), CUSTOMER_ID, null);

        assertEquals("ResetPasswordView", modelAndView.getViewName());
        assertNotNull(modelAndView.getModelMap().get("UserDetailsCmd"));
        verify(mockMasterCustomerDataService).findMasterCustomerById(anyLong());
    }

    @Test
    public void shouldResetPasswordWithCorrectAnswer() {
        when(mockMasterCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(getUser());
        doNothing().when(mockMasterCustomerDataService).updatePassword(anyLong(), anyString());

        ModelAndView modelAndView = controller.resetPassword(mockHttpResponse, getUserDetailsCmdWithStatus(), mockErrors);

        assertEquals("ResetPasswordView", modelAndView.getViewName());
        UserDetailsCmd resultCmd = (UserDetailsCmd) modelAndView.getModelMap().get("UserDetailsCmd");
        assertEquals("Password reset", resultCmd.getStatus());
        verify(mockMasterCustomerDataService).updatePassword(anyLong(), anyString());
    }

    @Test
    public void shouldNotResetPasswordWithIncorrectAnswer() {
        Customer customer = new Customer();
        customer.getSecurityAnswers().add(new SecurityAnswer(ID, "", new SecurityQuestion(ID, "")));
        User user = getUser();
        user.setCustomer(customer);
        when(mockMasterCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(user);

        ModelAndView modelAndView = controller.resetPassword(mockHttpResponse, getUserDetailsCmdWithStatus(), mockErrors);

        assertEquals("ResetPasswordView", modelAndView.getViewName());
        UserDetailsCmd resultCmd = (UserDetailsCmd) modelAndView.getModelMap().get("UserDetailsCmd");
        assertEquals("Incorrect security answer", resultCmd.getStatus());
        verify(mockMasterCustomerDataService, never()).updatePassword(anyLong(), anyString());
    }

    @Test
    public void shouldSetCustomerIdForResetPassword() {
        UserDetailsCmd cmd = getUserDetailsCmdWithStatus();
        cmd.setReturnURL(PageAttribute.RETURN_URL);
        RedirectView result = controller.setCustomerIdForResetPassword(cmd);

        assertEquals(Page.RESET_PASSSWORD, result.getUrl());
    }
}
