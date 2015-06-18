package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileNameService;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileService;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.innovator.command.ExportFileCmd;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.FileTestUtil.TEST_FILE_NAME_1;
import static org.mockito.Mockito.*;

public class ExportFileControllerTest {
    private ExportFileController controller;
    private SelectListService mockSelectListService;
    private ExportFileNameService mockExportFileNameService;
    private ExportFileService mockExportFileService;

    private Model mockModel;
    private SelectListDTO mockSelectListDTO;
    private ExportFileCmd mockExportFileCmd;
    private HttpServletResponse mockHttpServletResponse;
    private OutputStream mockOutputStream;

    @Before
    public void setUp() {
        this.controller = mock(ExportFileController.class);
        this.mockSelectListService = mock(SelectListService.class);
        this.controller.selectListService = this.mockSelectListService;
        this.mockExportFileNameService = mock(ExportFileNameService.class);
        this.controller.exportFileNameService = this.mockExportFileNameService;
        this.mockExportFileService = mock(ExportFileService.class);
        this.controller.exportFileService = this.mockExportFileService;

        this.mockModel = mock(Model.class);
        this.mockSelectListDTO = mock(SelectListDTO.class);
        this.mockExportFileCmd = mock(ExportFileCmd.class);
        this.mockHttpServletResponse = mock(HttpServletResponse.class);
        this.mockOutputStream = mock(OutputStream.class);
    }

    @Test
    public void shouldPopulateSelectList() {
        when(this.mockModel.addAttribute(anyString(), any(SelectListDTO.class))).thenReturn(this.mockModel);
        when(this.mockSelectListService.getSelectList(anyString())).thenReturn(this.mockSelectListDTO);
        doCallRealMethod().when(this.controller).populateSelectList(this.mockModel);

        this.controller.populateSelectList(this.mockModel);

        verify(this.mockModel).addAttribute(anyString(), any(SelectListDTO.class));
        verify(this.mockSelectListService).getSelectList(anyString());
    }

    @Test
    public void shouldShowPage() {
        when(this.controller.showPage()).thenCallRealMethod();
        ModelAndViewAssert.assertViewName(this.controller.showPage(), PageView.INV_EXPORT_FILE);
    }

    @Test
    public void shouldExport() {
        doCallRealMethod().when(this.controller)
                .export(any(ExportFileCmd.class), any(HttpServletResponse.class), any(OutputStream.class));
        doNothing().when(this.controller)
                .streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(),
                        any(byte[].class));
        when(this.mockExportFileNameService.getExportFileName(anyString())).thenReturn("");
        when(this.mockExportFileService.exportFile(anyString(), anyString())).thenReturn(new byte[0]);
        when(this.mockExportFileCmd.getExportFileType()).thenReturn(TEST_FILE_NAME_1);

        this.controller.export(this.mockExportFileCmd, this.mockHttpServletResponse, this.mockOutputStream);

        verify(this.mockExportFileNameService).getExportFileName(anyString());
        verify(this.mockExportFileService).exportFile(anyString(), anyString());
        verify(this.controller).streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(),
                any(byte[].class));
    }

    @Test(expected = AssertionError.class)
    public void exportShouldError() {
        doCallRealMethod().when(this.controller)
                .export(any(ExportFileCmd.class), any(HttpServletResponse.class), any(OutputStream.class));
        doNothing().when(this.controller)
                .streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(),
                        any(byte[].class));
        when(this.mockExportFileNameService.getExportFileName(anyString())).thenReturn("");
        when(this.mockExportFileService.exportFile(anyString(), anyString())).thenReturn(new byte[0]);
        when(this.mockExportFileCmd.getExportFileType()).thenReturn(EMPTY_STRING);

        this.controller.export(this.mockExportFileCmd, this.mockHttpServletResponse, this.mockOutputStream);
    }
}
