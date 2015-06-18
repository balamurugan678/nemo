package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.mock_cubic.command.AdHocDistributionCmd;
import com.novacroft.nemo.mock_cubic.service.AdHocDistributionService;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * AdHocDistributionController unit tests
 */
@SuppressWarnings("unchecked")
public class AdHocDistributionControllerTest {

    @Test
    public void shouldShowAdHocPage() {
        AdHocDistributionCmd cmd = new AdHocDistributionCmd();
        AdHocDistributionService mockAdHocDistributionService = mock(AdHocDistributionService.class);
        when(mockAdHocDistributionService.addEmptyRecords(any(AdHocDistributionCmd.class), anyInt())).thenReturn(cmd);
        AdHocDistributionController controller = new AdHocDistributionController();
        controller.adHocDistributionService = mockAdHocDistributionService;
        ModelAndView result = controller.showAdHocPage();
        assertViewName(result, "AdHocDistributionView");
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldAddRows() {
        AdHocDistributionCmd cmd = new AdHocDistributionCmd();
        AdHocDistributionService mockAdHocDistributionService = mock(AdHocDistributionService.class);
        when(mockAdHocDistributionService.addEmptyRecords(any(AdHocDistributionCmd.class), anyInt())).thenReturn(cmd);
        AdHocDistributionController controller = new AdHocDistributionController();
        controller.adHocDistributionService = mockAdHocDistributionService;
        ModelAndView result = controller.addRows(cmd);
        assertViewName(result, "AdHocDistributionView");
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldGetCsv() throws IOException {
        AdHocDistributionCmd mockCmd = mock(AdHocDistributionCmd.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        doNothing().when(mockResponse).setHeader(anyString(), anyString());
        doNothing().when(mockResponse).setContentType(anyString());
        Writer mockWriter = mock(Writer.class);
        doNothing().when(mockWriter).write(anyString());
        doNothing().when(mockWriter).flush();
        AdHocDistributionService mockAdHocDistributionService = mock(AdHocDistributionService.class);
        when(mockAdHocDistributionService.serialiseToCsv(any(List.class))).thenReturn("");
        AdHocDistributionController controller = new AdHocDistributionController();
        controller.adHocDistributionService = mockAdHocDistributionService;
        controller.getCsv(mockCmd, mockResponse, mockWriter);
        verify(mockResponse).setHeader(anyString(), anyString());
        verify(mockResponse).setContentType(anyString());
        verify(mockWriter).write(anyString());
        verify(mockWriter).flush();
        verify(mockAdHocDistributionService).serialiseToCsv(any(List.class));
    }
}
