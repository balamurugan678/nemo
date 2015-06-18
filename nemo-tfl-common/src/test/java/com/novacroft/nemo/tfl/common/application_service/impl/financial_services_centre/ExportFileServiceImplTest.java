package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileGenerator;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.novacroft.nemo.test_support.FileTestUtil.TEST_FILE_NAME_1;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ExportFileServiceImplTest {
    private ExportFileServiceImpl service;
    private ExportFileGenerator mockChequeRequestFileGeneratorService;
    private Map<String, ExportFileGenerator> mockFileTypeGeneratorMap;

    @Before
    public void setUp() {
        this.service = mock(ExportFileServiceImpl.class);
        this.mockChequeRequestFileGeneratorService = mock(ChequeRequestFileGeneratorServiceImpl.class);
        this.service.chequeRequestFileGeneratorService = this.mockChequeRequestFileGeneratorService;

        this.mockFileTypeGeneratorMap = new HashMap<String, ExportFileGenerator>();
        this.mockFileTypeGeneratorMap.put(ExportFileType.FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE.code(),
                this.mockChequeRequestFileGeneratorService);
    }

    @Test
    public void shouldInitialiseFileTypeGeneratorMap() {
        doCallRealMethod().when(this.service).initialiseFileTypeGeneratorMap();
        this.service.initialiseFileTypeGeneratorMap();
        assertTrue(this.service.fileTypeGeneratorMap
                .containsKey(ExportFileType.FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE.code()));
    }

    @Test
    public void shouldExportFile() {
        when(this.service.exportFile(anyString(), anyString())).thenCallRealMethod();
        when(this.mockChequeRequestFileGeneratorService.generateExportFile(anyString())).thenReturn(new byte[0]);
        this.service.fileTypeGeneratorMap = this.mockFileTypeGeneratorMap;
        this.service.exportFile(ExportFileType.FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE.code(), TEST_FILE_NAME_1);
        verify(this.mockChequeRequestFileGeneratorService).generateExportFile(anyString());
    }

    @Test(expected = ApplicationServiceException.class)
    public void exportFileShouldError() {
        when(this.service.exportFile(anyString(), anyString())).thenCallRealMethod();
        this.service.fileTypeGeneratorMap = this.mockFileTypeGeneratorMap;
        this.service.exportFile("bad-file-type", TEST_FILE_NAME_1);
    }
}