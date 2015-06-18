package com.novacroft.nemo.tfl.batch.job;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionArrayList2;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestFile1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayList1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayList2;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayVal3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.quartz.JobDataMap;

import com.novacroft.nemo.common.application_service.impl.CsvFileWithCheckSumReaderServiceImpl;
import com.novacroft.nemo.tfl.batch.constant.JobParameterName;
import com.novacroft.nemo.tfl.common.application_service.CubicChecksumVerificationService;
import com.novacroft.nemo.tfl.common.constant.JobStatus;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * BaseCubicFileImportJob unit tests
 */
public class BaseCubicFileImportJobTest {

    @Test
    public void isChecksumVerifiedShouldReturnTrue() {
        CubicChecksumVerificationService mockCubicChecksumVerificationService = mock(CubicChecksumVerificationService.class);
        when(mockCubicChecksumVerificationService.isChecksumVerified(any(File.class))).thenReturn(Boolean.TRUE);
        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class, CALLS_REAL_METHODS);
        job.cubicChecksumVerificationService = mockCubicChecksumVerificationService;
        assertTrue(job.isChecksumVerified(getTestFile1(), new JobLogDTO()));
    }

    @Test
    public void isChecksumVerifiedShouldReturnFalse() {
        CubicChecksumVerificationService mockCubicChecksumVerificationService = mock(CubicChecksumVerificationService.class);
        when(mockCubicChecksumVerificationService.isChecksumVerified(any(File.class))).thenReturn(Boolean.FALSE);
        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class, CALLS_REAL_METHODS);
        job.cubicChecksumVerificationService = mockCubicChecksumVerificationService;
        assertFalse(job.isChecksumVerified(getTestFile1(), new JobLogDTO()));
    }

    @Test
    public void shouldHandleFileThatIsOkToProcess() {
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());

        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class);
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(new JobLogDTO());
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        doNothing().when(job).finaliseLog(any(File.class), any(JobLogDTO.class));
        job.csvFileWithCheckSumReaderService = mockFileReaderService;

        job.handleFile(getTestFile1(), new JobDataMap());

        verify(job).initialiseLog(any(File.class), anyString());
        verify(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class));
        verify(job).finaliseLog(any(File.class), any(JobLogDTO.class));
    }

    @Test
    public void shouldHandleFileThatIsNotOkToProcess() {
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());

        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class);
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(new JobLogDTO());
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(false);
        doNothing().when(job).finaliseLog(any(File.class), any(JobLogDTO.class));
        job.csvFileWithCheckSumReaderService = mockFileReaderService;

        job.handleFile(getTestFile1(), new JobDataMap());

        verify(job).initialiseLog(any(File.class), anyString());
        verify(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class));
        verify(job).finaliseLog(any(File.class), any(JobLogDTO.class));
    }
    
    @Test
    public void shouldGetImportRootDirectoryParameterName() {
        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class);
        when(job.getImportRootDirectoryParameterName()).thenCallRealMethod();
        assertEquals(JobParameterName.CUBIC_IMPORT_ROOT_DIR, job.getImportRootDirectoryParameterName());
    }
    
    @Test
    public void shouldHandleFileThatHasDataToProcess() { 
    	BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class);
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        job.csvFileWithCheckSumReaderService = mockFileReaderService;
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        JobLogDTO log = new JobLogDTO();
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(log);
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.isChecksumVerified(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.csvFileWithCheckSumReaderService.readDataFromFile(any(File.class))).thenReturn(getTestStringArrayList1());
        when(job.isFileHasDataToProcess(any(List.class), any(JobLogDTO.class))).thenReturn(true);
        job.handleFile(getTestFile1(), new JobDataMap());
        verify(job).initialiseLog(any(File.class), anyString());
        verify(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class));
        verify(job).finaliseLog(any(File.class), any(JobLogDTO.class));
        assertEquals(JobStatus.COMPLETE.code(),log.getStatus());
    	
    }  

    @Test (expected = Exception.class)
    public void shouldHandleFileThatHasDataToProcessWithException() { 
        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class);
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        job.csvFileWithCheckSumReaderService = mockFileReaderService;
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(new JobLogDTO());
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.isChecksumVerified(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.csvFileWithCheckSumReaderService.readDataFromFile(any(File.class))).thenReturn(getTestStringArrayList2());
        when(job.isFileHasDataToProcess(any(List.class), any(JobLogDTO.class))).thenReturn(true);
        doThrow(new Exception()).when(job).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        job.handleRecord(getTestStringArrayVal3(), new JobLogDTO(), null);
        job.handleFile(getTestFile1(), new JobDataMap());
        verify(job).initialiseLog(any(File.class), anyString());
        verify(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class)); 
        verify(job).finaliseLog(any(File.class), any(JobLogDTO.class));
    } 

    @Test
    public void shouldLogStatusInCompleteOnException() {
        BaseCubicFileImportJob job = mock(BaseCubicFileImportJob.class);
        JobLogDTO log = new JobLogDTO();
        CsvFileWithCheckSumReaderServiceImpl mockFileReaderService = mock(CsvFileWithCheckSumReaderServiceImpl.class);
        job.csvFileWithCheckSumReaderService = mockFileReaderService;
        when(mockFileReaderService.readDataFromFile(any(File.class))).thenReturn(getTestCurrentActionArrayList2());
        doCallRealMethod().when(job).handleFile(any(File.class), any(JobDataMap.class));
        when(job.initialiseLog(any(File.class), anyString())).thenReturn(log);
        doThrow(new RuntimeException()).when(job).isFileOkToProcess(any(File.class), any(JobLogDTO.class));
        job.handleFile(getTestFile1(), new JobDataMap());
        assertEquals(JobStatus.IN_COMPLETE.code(), log.getStatus());
    }
      
}
