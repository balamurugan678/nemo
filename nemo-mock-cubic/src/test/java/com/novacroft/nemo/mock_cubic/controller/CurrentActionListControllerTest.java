package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.mock_cubic.command.CurrentActionListFileCmd;
import com.novacroft.nemo.mock_cubic.service.CurrentActionListService;
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
 * CurrentActionListController unit tests
 */
@SuppressWarnings("unchecked")
public class CurrentActionListControllerTest {

    @Test
    public void shouldShowAdHocPage() {
        CurrentActionListFileCmd cmd = new CurrentActionListFileCmd();
        CurrentActionListService mockCurrentActionListService = mock(CurrentActionListService.class);
        when(mockCurrentActionListService.addEmptyRecords(any(CurrentActionListFileCmd.class), anyInt())).thenReturn(cmd);
        CurrentActionListController controller = new CurrentActionListController();
        controller.currentActionListService = mockCurrentActionListService;
        ModelAndView result = controller.showCurrentActionListPage();
        assertViewName(result, "CurrentActionListFileView");
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldAddRows() {
        CurrentActionListFileCmd cmd = new CurrentActionListFileCmd();
        CurrentActionListService mockCurrentActionListService = mock(CurrentActionListService.class);
        when(mockCurrentActionListService.addEmptyRecords(any(CurrentActionListFileCmd.class), anyInt())).thenReturn(cmd);
        CurrentActionListController controller = new CurrentActionListController();
        controller.currentActionListService = mockCurrentActionListService;
        ModelAndView result = controller.addRows(cmd);
        assertViewName(result, "CurrentActionListFileView");
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldGetCsv() throws IOException {
        CurrentActionListFileCmd mockCmd = mock(CurrentActionListFileCmd.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        doNothing().when(mockResponse).setHeader(anyString(), anyString());
        doNothing().when(mockResponse).setContentType(anyString());
        Writer mockWriter = mock(Writer.class);
        doNothing().when(mockWriter).write(anyString());
        doNothing().when(mockWriter).flush();
        CurrentActionListService mockCurrentActionListService = mock(CurrentActionListService.class);
        when(mockCurrentActionListService.serialiseToCsv(any(List.class))).thenReturn("");
        CurrentActionListController controller = new CurrentActionListController();
        controller.currentActionListService = mockCurrentActionListService;
        controller.getCsv(mockCmd, mockResponse, mockWriter);
        verify(mockResponse).setHeader(anyString(), anyString());
        verify(mockResponse).setContentType(anyString());
        verify(mockWriter).write(anyString());
        verify(mockWriter).flush();
        verify(mockCurrentActionListService).serialiseToCsv(any(List.class));
    }
}
