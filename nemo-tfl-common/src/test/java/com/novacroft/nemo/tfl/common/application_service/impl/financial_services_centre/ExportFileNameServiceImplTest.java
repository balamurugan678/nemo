package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileType;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;

public class ExportFileNameServiceImplTest {
    private static final Long FILE_SEQUENCE_NUMBER = 1024L;
    private static final String FILE_SUFFIX = ".csv";
    private static final String FILE_PREFIX = "DO";
    private ExportFileNameServiceImpl service;
    private FileExportLogDataService mockFileExportLogDataService;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        this.service = mock(ExportFileNameServiceImpl.class);

        this.mockFileExportLogDataService = mock(FileExportLogDataService.class);
        this.service.fileExportLogDataService = this.mockFileExportLogDataService;

        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.service.systemParameterService = this.mockSystemParameterService;
    }

    @Test
    public void shouldGetFinancialServicesCentreExportFileSequenceNumber() {
        when(this.service.getFinancialServicesCentreExportFileSequenceNumber()).thenCallRealMethod();
        when(this.mockFileExportLogDataService.getNextFinancialServicesCentreExportFileSequenceNumber())
                .thenReturn(FILE_SEQUENCE_NUMBER);
        assertEquals(FILE_SEQUENCE_NUMBER, this.service.getFinancialServicesCentreExportFileSequenceNumber());
    }

    @Test
    public void shouldGetFinancialServicesCentreExportFileSuffix() {
        when(this.service.getFinancialServicesCentreExportFileSuffix()).thenCallRealMethod();
        when(this.mockSystemParameterService.getParameterValue(eq(SystemParameterCode.FSC_EXPORT_FILE_SUFFIX.code())))
                .thenReturn(FILE_SUFFIX);
        assertEquals(FILE_SUFFIX, this.service.getFinancialServicesCentreExportFileSuffix());
    }

    @Test
    public void shouldGetFinancialServicesCentreChequeRequestFileName() {
        when(this.service.getFinancialServicesCentreChequeRequestFileName()).thenCallRealMethod();
        when(this.service.getFinancialServicesCentreExportFileSuffix()).thenReturn(FILE_SUFFIX);
        when(this.service.getFinancialServicesCentreExportFileSequenceNumberForChequeRequest()).thenReturn(FILE_SEQUENCE_NUMBER);
        when(this.mockSystemParameterService
                .getParameterValue(eq(SystemParameterCode.FSC_EXPORT_FILE_CHEQUE_REQUEST_PREFIX.code())))
                .thenReturn(FILE_PREFIX);
        String expectedResult = FILE_PREFIX + FILE_SEQUENCE_NUMBER + FILE_SUFFIX;
        assertEquals(expectedResult, this.service.getFinancialServicesCentreChequeRequestFileName());
    }

    @Test
    public void shouldGetFinancialServicesCentreBacsRequestFileName() {
        when(this.service.getFinancialServicesCentreBacsRequestFileName()).thenCallRealMethod();
        when(this.service.getFinancialServicesCentreExportFileSuffix()).thenReturn(FILE_SUFFIX);
        when(this.service.getFinancialServicesCentreExportFileSequenceNumberForBACSRequest()).thenReturn(FILE_SEQUENCE_NUMBER);
        when(this.mockSystemParameterService
                .getParameterValue(eq(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_PREFIX.code())))
                .thenReturn(FILE_PREFIX);
        String expectedResult = FILE_PREFIX + FILE_SEQUENCE_NUMBER + FILE_SUFFIX;
        assertEquals(expectedResult, this.service.getFinancialServicesCentreBacsRequestFileName());
    }
    
    @Test
    public void getExportFileNameShouldReturnName() {
        when(this.service.getExportFileName(anyString())).thenCallRealMethod();
        when(this.service.callMethodOnThisObject(anyString())).thenReturn("");
        assertNotNull(this.service.getExportFileName(ExportFileType.FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE.code()));
        verify(this.service).callMethodOnThisObject(anyString());
    }

    @Test
    public void getExportFileNameShouldReturnNull() {
        when(this.service.getExportFileName(anyString())).thenCallRealMethod();
        when(this.service.callMethodOnThisObject(anyString())).thenReturn("");
        assertNull(this.service.getExportFileName("bad-file-type"));
        verify(this.service, never()).callMethodOnThisObject(anyString());
    }

    @Test
    public void shouldCallMethodOnThisObject() {
        when(this.service.callMethodOnThisObject(anyString())).thenCallRealMethod();
        when(this.service.getFinancialServicesCentreChequeRequestFileName()).thenReturn("");

        this.service.callMethodOnThisObject("getFinancialServicesCentreChequeRequestFileName");

        verify(this.service).getFinancialServicesCentreChequeRequestFileName();
    }

    @Test(expected = ApplicationServiceException.class)
    public void callMethodOnThisObjectShouldError() {
        when(this.service.callMethodOnThisObject(anyString())).thenCallRealMethod();
        when(this.service.getFinancialServicesCentreChequeRequestFileName()).thenReturn("");

        this.service.callMethodOnThisObject("bad-method-name");
    }
    
    @Test
    public void shouldGetFinancialServicesCentreExportFileSequenceNumberForBACSRequest() {
        when(this.service.getFinancialServicesCentreExportFileSequenceNumberForBACSRequest()).thenCallRealMethod();
        when(this.mockFileExportLogDataService.getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest())
                .thenReturn(FILE_SEQUENCE_NUMBER);
        assertEquals(FILE_SEQUENCE_NUMBER, this.service.getFinancialServicesCentreExportFileSequenceNumberForBACSRequest());
    }
    
    @Test
    public void shouldGetFinancialServicesCentreExportFileSequenceNumberForChequeRequest() {
        when(this.service.getFinancialServicesCentreExportFileSequenceNumberForChequeRequest()).thenCallRealMethod();
        when(this.mockFileExportLogDataService.getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest())
                .thenReturn(FILE_SEQUENCE_NUMBER);
        assertEquals(FILE_SEQUENCE_NUMBER, this.service.getFinancialServicesCentreExportFileSequenceNumberForChequeRequest());
    }
}