package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.mock_cubic.command.AutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.service.AutoLoadChangeService;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * AutoLoadChangeController unit test
 */
@SuppressWarnings("unchecked")
public class AutoLoadChangeControllerTest {
    @Test
    public void shouldShowPage() {
        AutoLoadChangeCmd cmd = new AutoLoadChangeCmd();
        AutoLoadChangeService mockAutoLoadChangeService = mock(AutoLoadChangeService.class);
        when(mockAutoLoadChangeService.addEmptyRecords(any(AutoLoadChangeCmd.class), anyInt())).thenReturn(cmd);
        AutoLoadChangeController controller = new AutoLoadChangeController();
        controller.autoLoadChangeService = mockAutoLoadChangeService;
        ModelAndView result = controller.showPage();
        assertViewName(result, "AutoLoadChangeView");
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldAddRows() {
        AutoLoadChangeCmd cmd = new AutoLoadChangeCmd();
        AutoLoadChangeService mockAutoLoadChangeService = mock(AutoLoadChangeService.class);
        when(mockAutoLoadChangeService.addEmptyRecords(any(AutoLoadChangeCmd.class), anyInt())).thenReturn(cmd);
        AutoLoadChangeController controller = new AutoLoadChangeController();
        controller.autoLoadChangeService = mockAutoLoadChangeService;
        ModelAndView result = controller.addRows(cmd);
        assertViewName(result, "AutoLoadChangeView");
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldGetCsv() throws IOException {
        AutoLoadChangeCmd mockCmd = mock(AutoLoadChangeCmd.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        doNothing().when(mockResponse).setHeader(anyString(), anyString());
        doNothing().when(mockResponse).setContentType(anyString());
        Writer mockWriter = mock(Writer.class);
        doNothing().when(mockWriter).write(anyString());
        doNothing().when(mockWriter).flush();
        AutoLoadChangeService mockAutoLoadChangeService = mock(AutoLoadChangeService.class);
        when(mockAutoLoadChangeService.serialiseToCsv(any(List.class))).thenReturn("");
        AutoLoadChangeController controller = new AutoLoadChangeController();
        controller.autoLoadChangeService = mockAutoLoadChangeService;
        controller.getCsv(mockCmd, mockResponse, mockWriter);
        verify(mockResponse).setHeader(anyString(), anyString());
        verify(mockResponse).setContentType(anyString());
        verify(mockWriter).write(anyString());
        verify(mockWriter).flush();
        verify(mockAutoLoadChangeService).serialiseToCsv(any(List.class));
    }
}
