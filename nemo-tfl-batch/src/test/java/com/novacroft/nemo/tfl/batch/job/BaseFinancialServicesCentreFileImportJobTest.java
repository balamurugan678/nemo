package com.novacroft.nemo.tfl.batch.job;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionArrayList2;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestFile1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayList1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayList2;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayVal3;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDataMap;

import com.novacroft.nemo.common.application_service.CsvFileReaderService;
import com.novacroft.nemo.common.application_service.impl.CsvFileWithCheckSumReaderServiceImpl;
import com.novacroft.nemo.test_support.ChequeSettlementTestUtil;
import com.novacroft.nemo.tfl.batch.constant.JobParameterName;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public class BaseFinancialServicesCentreFileImportJobTest {

    private BaseFinancialServicesCentreFileImportJob job;
    private CsvFileReaderService mockCsvFileReaderService;

    private JobLogDTO mockJobLogDTO;
    private List<String[]> mockData;
    private File mockFile;

    @Before
    public void setUp() {
        this.job = mock(BaseFinancialServicesCentreFileImportJob.class);
        doCallRealMethod().when(this.job).handleFile(any(File.class), any(JobDataMap.class));

        this.mockCsvFileReaderService = mock(CsvFileReaderService.class);
        this.job.csvFileReaderService = this.mockCsvFileReaderService;
        this.mockJobLogDTO = mock(JobLogDTO.class);
        this.mockData = new ArrayList<String[]>();
        this.mockData.add(ChequeSettlementTestUtil.TEST_CHEQUE_PRODUCED_IMPORT_RECORD);
        this.mockFile = mock(File.class);
    }

    @Test
    public void shouldGetImportRootDirectoryParameterName() {
        when(this.job.getImportRootDirectoryParameterName()).thenCallRealMethod();
        assertEquals(JobParameterName.FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR,this.job.getImportRootDirectoryParameterName());
    }

    @Test
    public void shouldHandleFile() {
        doCallRealMethod().when(this.job).handleFile(any(File.class), any(JobDataMap.class));
        when(this.job.initialiseLog(any(File.class), anyString())).thenReturn(this.mockJobLogDTO);
        when(this.job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(this.mockCsvFileReaderService.readDataFromFile(any(File.class))).thenReturn(this.mockData);
        doNothing().when(this.job).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        doNothing().when(this.job).finaliseLog(any(File.class), any(JobLogDTO.class));
        this.job.handleFile(this.mockFile, null);
        verify(this.job).initialiseLog(any(File.class), anyString());
        verify(this.job).isFileOkToProcess(any(File.class), any(JobLogDTO.class)); 
        verify(this.mockCsvFileReaderService).readDataFromFile(any(File.class));
        verify(this.job, never()).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        verify(this.job).finaliseLog(any(File.class), any(JobLogDTO.class));
    }

    @Test
    public void handleFileShouldNotProcessNotOkFile() {
        doCallRealMethod().when(this.job).handleFile(any(File.class), any(JobDataMap.class));
        when(this.job.initialiseLog(any(File.class), anyString())).thenReturn(this.mockJobLogDTO);
        when(this.job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(false);
        when(this.mockCsvFileReaderService.readDataFromFile(any(File.class))).thenReturn(this.mockData);
        doNothing().when(this.job).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        doNothing().when(this.job).finaliseLog(any(File.class), any(JobLogDTO.class));
        this.job.handleFile(this.mockFile, null);
        verify(this.job).initialiseLog(any(File.class), anyString());
        verify(this.job).isFileOkToProcess(any(File.class), any(JobLogDTO.class));
        verify(this.mockCsvFileReaderService, never()).readDataFromFile(any(File.class));
        verify(this.job, never()).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        verify(this.job).finaliseLog(any(File.class), any(JobLogDTO.class));
    }
    
    @Test
    public void shouldHandleFileThatHasDataToProcess() {
    	BaseFinancialServicesCentreFileImportJob job = mock(BaseFinancialServicesCentreFileImportJob.class);
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        job.csvFileReaderService = mockFileReaderService;
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(new JobLogDTO());
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.csvFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestStringArrayList1());
        when(job.isFileHasDataToProcess(any(List.class), any(JobLogDTO.class))).thenReturn(true);
        job.handleFile(getTestFile1(), new JobDataMap());
        verify(job).initialiseLog(any(File.class), anyString());
        verify(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class));
        verify(job).finaliseLog(any(File.class), any(JobLogDTO.class));
    }  

    @Test (expected = Exception.class)
    public void shouldHandleFileThatHasDataToProcessWithException() { 
    	BaseFinancialServicesCentreFileImportJob job = mock(BaseFinancialServicesCentreFileImportJob.class);
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        job.csvFileReaderService = mockFileReaderService;
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(new JobLogDTO());
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.csvFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestStringArrayList2());
        when(job.isFileHasDataToProcess(any(List.class), any(JobLogDTO.class))).thenReturn(true);
        doThrow(new Exception()).when(job).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        job.handleRecord(getTestStringArrayVal3(),new JobLogDTO(), new JobDataMap());
        job.handleFile(getTestFile1(), new JobDataMap());
        verify(job).initialiseLog(any(File.class), anyString());
        verify(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class)); 
        verify(job).finaliseLog(any(File.class), any(JobLogDTO.class));
    } 
    
}