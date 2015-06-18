package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.SystemParameterTestUtil.getTestSystemParameterDTOList2;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.tfl.common.constant.PageView;

public class SystemControllerTest {

    SystemController controller;
    SystemParameterService service;
    Properties mockApplicationProperties;
    List<SystemParameterDTO> parameters;

    @Before
    public void setUp() throws Exception {
        parameters = getTestSystemParameterDTOList2();
        controller = new SystemController();
        service = mock(SystemParameterService.class);
        when(service.getAllProperties()).thenReturn(parameters);
        controller.systemParameterService = service;
    }

    @Test
    public void testView() {
        ModelAndView result = controller.view();
        verify(service).getAllProperties();
        assertEquals(PageView.INV_SYSTEM, result.getViewName());
        assertEquals(parameters, result.getModel().get("parameters"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSaveProperties() {
        doNothing().when(service).saveProperties(any(List.class));
        ModelAndView result = controller.saveProperties("test=test&test2=test2");
        verify(service).getAllProperties();
        verify(service).saveProperties(any(List.class));
        assertEquals(PageView.INV_SYSTEM, result.getViewName());
    }

    @Test
    public void testSavePropertiesWithInvalidInput() {
        controller = mock(SystemController.class);
        when(controller.getContent(anyString(), anyString())).thenReturn("test");
        when(controller.saveProperties(anyString())).thenCallRealMethod();
        when(controller.getParametersFromURL(anyString())).thenCallRealMethod();
        when(controller.decodeURL(anyString())).thenCallRealMethod();
        doNothing().when(service).saveProperties(any(List.class));
        ModelAndView result = controller.saveProperties("defaultAutoTopUpResettlementPeriodDays=test&test2=test2");
        verify(service, never()).getAllProperties();
        verify(service, never()).saveProperties(any(List.class));
        assertEquals(PageView.INV_SYSTEM, result.getViewName());
    }

}
