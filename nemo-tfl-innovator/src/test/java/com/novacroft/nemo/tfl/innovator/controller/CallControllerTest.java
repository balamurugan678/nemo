package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.common.application_service.CallService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.impl.CallCmd;
import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.CallValidator;
import com.novacroft.nemo.common.validator.ContactDetailsValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.novacroft.nemo.test_support.CallTestUtil.ID_1;
import static com.novacroft.nemo.test_support.CallTestUtil.getCallCmd1;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO;
import static com.novacroft.nemo.tfl.innovator.controller.CallController.COMMAND_NAME;
import static com.novacroft.nemo.tfl.innovator.controller.CallController.VIEW_NAME;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallControllerTest {

    private CallController controller;
    private CallService callService;
    private SelectListService selectListService;
    private CustomerNameValidator customerNameValidator;
    private AddressValidator addressValidator;
    private ContactDetailsValidator contactDetailsValidator;
    private CallValidator callValidator;
    private HttpServletRequest request;
    private BindingAwareModelMap model;
    private BeanPropertyBindingResult mockBindingResult;
    private CallCmd callCmd;

    @Before
    public void setUp() throws Exception {
        controller = new CallController();
        request = mock(HttpServletRequest.class);
        callService = mock(CallService.class);
        selectListService = mock(SelectListService.class);
        customerNameValidator = mock(CustomerNameValidator.class);
        addressValidator = mock(AddressValidator.class);
        contactDetailsValidator = mock(ContactDetailsValidator.class);
        callValidator = mock(CallValidator.class);
        //model = mock(Model.class);
        callCmd = mock(CallCmd.class);
        mockBindingResult = new BeanPropertyBindingResult(callCmd, COMMAND_NAME);
        model = new BindingAwareModelMap();
        controller.callService = callService;
        controller.selectListService = selectListService;
        controller.customerNameValidator = customerNameValidator;
        controller.addressValidator = addressValidator;
        controller.contactDetailsValidator = contactDetailsValidator;
        controller.callValidator = callValidator;
    }

    @Test
    public void testPopulateTitlesSelectList() {
        when(selectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        controller.populateTitlesSelectList(model);
        assertTrue(model.containsAttribute("titles"));
    }

    @Test
    public void testPopulateCallTypeSelectList() {
        when(selectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        controller.populateCallTypeSelectList(model);
        assertTrue(model.containsAttribute("callTypes"));
    }

    @Test
    public void testViewReturnModelAndView() {
        ModelAndView result = controller.view(request);
        assertEquals(result.getViewName(), VIEW_NAME);
        assertTrue(result.getModel().containsKey(COMMAND_NAME));
        assertTrue(result.getModel().get(COMMAND_NAME) instanceof CallCmd);
    }

    @Test
    public void testLoadShouldReturnACall() {
        when(callService.getCall(anyLong())).thenReturn(getCallCmd1());
        when(request.getAttribute(anyString())).thenReturn(ID_1);
        ModelAndView result = controller.load(ID_1, request);
        assertEquals(result.getViewName(), VIEW_NAME);
        assertTrue(result.getModel().containsKey(COMMAND_NAME));
        assertTrue(result.getModel().get(COMMAND_NAME) instanceof CallCmd);
        assertEquals(request.getAttribute("id"), ID_1);
    }

    @Test
    public void testLoadShouldReturnError() {
        when(callService.getCall(anyLong())).thenReturn(getCallCmd1());
        when(request.getAttribute(anyString())).thenReturn(null);
        ModelAndView result = controller.load(null, request);
        assertEquals(result.getViewName(), VIEW_NAME);
        assertTrue(result.getModel().containsKey("error"));
        assertTrue(result.getModel().get(COMMAND_NAME) instanceof CallCmd);
        assertEquals(result.getModel().get("error"), "callid.parameter.error");
    }

    @Test
    public void testSave() {
        when(callService.updateCall((CallCmd) anyObject())).thenReturn(getCallCmd1());
        ModelAndView result = controller.save(callCmd, mockBindingResult, request);
        assertEquals(result.getViewName(), VIEW_NAME);
        assertTrue(result.getModel().containsKey(COMMAND_NAME));
        assertTrue(result.getModel().get(COMMAND_NAME) instanceof CallCmd);
        assertFalse(mockBindingResult.getGlobalErrorCount() > 0);
    }

}
