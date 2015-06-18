package com.novacroft.nemo.tfl.batch.job;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionArray1;
import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionRecord1;
import static com.novacroft.nemo.test_support.FileTestUtil.TEST_FILE_NAME_1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestDirectory1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestFile1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestFile2;
import static com.novacroft.nemo.test_support.FileTestUtil.getTestStringArrayList1;
import static com.novacroft.nemo.test_support.FileTestUtil.getTmpDir;
import static com.novacroft.nemo.test_support.FileTestUtil.isTmpDirOk;
import static com.novacroft.nemo.test_support.JobLogTestUtil.STATUS_1;
import static com.novacroft.nemo.tfl.batch.job.BaseFileImportJob.LOCK_FILE_EXTENSION;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.novacroft.nemo.common.application_service.FileFinderService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.JobTestUtil;
import com.novacroft.nemo.tfl.batch.constant.LogMessage;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.application_service.ProcessLockService;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public class BaseFileImportJobTest {
    
    @BeforeClass
    public static void beforeClass() {
        if (!isTmpDirOk()) {
            fail(String.format("Error: system temporary directory missing [%s]!", getTmpDir()));
        }
    }

    @Before
    public void setUp() {
        File file = new File(getTestFile2().getAbsolutePath() + "." + STATUS_1);
        if (file.exists()) {
            file.delete();
        }
    }

    @After
    public void tearDown() {
        setUp();
    }

    @Test
    public void shouldGetLockFileName() {
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        assertEquals(TEST_FILE_NAME_1 + LOCK_FILE_EXTENSION, job.getLockFileName(getTestFile1()));
    }

    @Test
    public void shouldGetLockFileDirectory() {
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        assertEquals(getTestDirectory1(), job.getLockFileDirectory(getTestFile1()));
    }

    @Test
    public void shouldProcessRecord() {
        ImportRecordDispatcher mockImportRecordDispatcher = mock(ImportRecordDispatcher.class);
        doNothing().when(mockImportRecordDispatcher).dispatch(any(ImportRecord.class));

        BaseFileImportJob job = mock(BaseFileImportJob.class);
        doCallRealMethod().when(job).processRecord(any(ImportRecord.class), any(JobLogDTO.class));
        when(job.getImportRecordDispatcher()).thenReturn(mockImportRecordDispatcher);
        JobLogDTO log = new JobLogDTO();
        CurrentActionRecord currentActionRecord = getTestCurrentActionRecord1();

        job.processRecord(currentActionRecord, log);

        verify(mockImportRecordDispatcher).dispatch(any(ImportRecord.class));
    }

    @Test
    public void shouldProcessRecordWithError() {
        String testErrorMessage = "test-error";
        ImportRecordDispatcher mockImportRecordDispatcher = mock(ImportRecordDispatcher.class);
        doThrow(new ApplicationServiceException(testErrorMessage)).when(mockImportRecordDispatcher)
                .dispatch(any(ImportRecord.class));

        BaseFileImportJob job = mock(BaseFileImportJob.class);
        doCallRealMethod().when(job).processRecord(any(ImportRecord.class), any(JobLogDTO.class));

        when(job.getImportRecordDispatcher()).thenReturn(mockImportRecordDispatcher);

        JobLogDTO log = new JobLogDTO();
        job.processRecord(getTestCurrentActionRecord1(), log);

        verify(mockImportRecordDispatcher).dispatch(any(ImportRecord.class));
        assertThat(log.getLog(), containsString(testErrorMessage));
    }

    @Test
    public void shouldConvertRecord() {
        ImportRecordConverter mockImportRecordConverter = mock(ImportRecordConverter.class);
        when(mockImportRecordConverter.convert(any(String[].class))).thenReturn(getTestCurrentActionRecord1());

        BaseFileImportJob job = mock(BaseFileImportJob.class);
        doCallRealMethod().when(job).convertRecord(any(String[].class));
        when(job.getImportRecordConverter()).thenReturn(mockImportRecordConverter);

        job.convertRecord(getTestCurrentActionArray1());

        verify(mockImportRecordConverter).convert(any(String[].class));
    }

    @Test
    public void isRecordValidShouldReturnTrue() {
        BaseFileImportJob job = mock(BaseFileImportJob.class);
        when(job.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenCallRealMethod();
        when(job.getImportRecordValidator()).thenReturn(getValidatorWithNoErrors());
        assertTrue(job.isRecordValid(getTestCurrentActionArray1(), new JobLogDTO()));
    }

    @Test
    public void isRecordValidShouldReturnFalse() {
        MessageSource mockMessageSource = mock(MessageSource.class);
        when(mockMessageSource.getMessage(any(ObjectError.class), any(Locale.class))).thenReturn("TestErrorMessage");

        ImportRecordConverter mockImportRecordConverter = mock(ImportRecordConverter.class);
        when(mockImportRecordConverter.toCsv(any(String[].class))).thenReturn("dummy,csv");

        BaseFileImportJob job = mock(BaseFileImportJob.class);
        when(job.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenCallRealMethod();
        when(job.getImportRecordValidator()).thenReturn(getValidatorWithErrors());
        when(job.getImportRecordConverter()).thenReturn(mockImportRecordConverter);
        job.messageSource = mockMessageSource;

        assertFalse(job.isRecordValid(getTestCurrentActionArray1(), new JobLogDTO()));
    }

    @Test
    public void shouldHandleValidRecord() {
        BaseFileImportJob job = mock(BaseFileImportJob.class);
        doCallRealMethod().when(job).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        when(job.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenReturn(true);
        when(job.convertRecord(any(String[].class))).thenReturn(getTestCurrentActionRecord1());
        doNothing().when(job).processRecord(any(CurrentActionRecord.class), any(JobLogDTO.class));
        JobLogDTO log = new JobLogDTO();
        job.handleRecord(getTestCurrentActionArray1(), log, new JobDataMap() );
        verify(job).isRecordValid(any(String[].class), any(JobLogDTO.class));
        verify(job).convertRecord(any(String[].class));
        verify(job).processRecord(any(CurrentActionRecord.class), any(JobLogDTO.class));
    }

    @Test
    public void shouldHandleInValidRecord() {
        BaseFileImportJob job = mock(BaseFileImportJob.class);
        doCallRealMethod().when(job).handleRecord(any(String[].class), any(JobLogDTO.class), any(JobDataMap.class));
        when(job.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenReturn(false);
        when(job.convertRecord(any(String[].class))).thenReturn(getTestCurrentActionRecord1());
        doNothing().when(job).processRecord(any(CurrentActionRecord.class), any(JobLogDTO.class));
        JobLogDTO log = new JobLogDTO();
        job.handleRecord(getTestCurrentActionArray1(), log, new JobDataMap());
        verify(job).isRecordValid(any(String[].class), any(JobLogDTO.class));
        verify(job, never()).convertRecord(any(String[].class));
        verify(job, never()).processRecord(any(CurrentActionRecord.class), any(JobLogDTO.class));
    }

    @Test
    public void shouldMarkFileProcessed() {
        JobLogDTO log = new JobLogDTO();
        log.setStatus(STATUS_1);
        File testFile = getTestFile2();
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        job.markFileProcessed(testFile, log);
        File expectedFile = new File(testFile.getAbsolutePath() + "." + STATUS_1);
        assertTrue(expectedFile.exists());
    }

    @Test
    public void isProcessNotLockedShouldReturnTrue() {
        ProcessLockService mockProcessLockService = mock(ProcessLockService.class);
        when(mockProcessLockService.isLocked(any(File.class), anyString())).thenReturn(false);
        doNothing().when(mockProcessLockService).acquireLock(any(File.class), anyString());

        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        job.processLockService = mockProcessLockService;

        JobLogDTO log = new JobLogDTO();

        assertTrue(job.isNotProcessLocked(getTestFile1(), log));
        verify(mockProcessLockService).isLocked(any(File.class), anyString());
        verify(mockProcessLockService).acquireLock(any(File.class), anyString());
    }

    @Test
    public void isProcessNotLockedShouldReturnFalse() {
        ProcessLockService mockProcessLockService = mock(ProcessLockService.class);
        when(mockProcessLockService.isLocked(any(File.class), anyString())).thenReturn(true);
        doNothing().when(mockProcessLockService).acquireLock(any(File.class), anyString());

        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        job.processLockService = mockProcessLockService;

        JobLogDTO log = new JobLogDTO();

        assertFalse(job.isNotProcessLocked(getTestFile1(), log));
        verify(mockProcessLockService).isLocked(any(File.class), anyString());
        verify(mockProcessLockService, never()).acquireLock(any(File.class), anyString());
    }

    @Test
    public void isNotFileAlreadyProcessedReturnTrue() {
        JobLogService mockJobLogService = mock(JobLogService.class);
        when(mockJobLogService.isFileAlreadyProcessed(anyString())).thenReturn(false);
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        job.jobLogService = mockJobLogService;
        assertTrue(job.isNotFileAlreadyProcessed(getTestFile1(), new JobLogDTO()));
    }

    @Test
    public void isNotFileAlreadyProcessedReturnFalse() {
        JobLogService mockJobLogService = mock(JobLogService.class);
        when(mockJobLogService.isFileAlreadyProcessed(anyString())).thenReturn(true);
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        job.jobLogService = mockJobLogService;
        assertFalse(job.isNotFileAlreadyProcessed(getTestFile1(), new JobLogDTO()));
    }

    @Test
    public void shouldFinalise() {
        ProcessLockService mockProcessLockService = mock(ProcessLockService.class);
        doNothing().when(mockProcessLockService).releaseLock(any(File.class), anyString());

        JobLogDataService mockJobLogDataService = mock(JobLogDataService.class);
        doNothing().when(mockJobLogDataService).create(any(JobLogDTO.class));

        BaseFileImportJob job = mock(BaseFileImportJob.class);
        doCallRealMethod().when(job).finaliseLog(any(File.class), any(JobLogDTO.class));
        doNothing().when(job).markFileProcessed(any(File.class), any(JobLogDTO.class));
        job.processLockService = mockProcessLockService;
        job.jobLogDataService = mockJobLogDataService;

        job.finaliseLog(getTestFile1(), new JobLogDTO());
        verify(job).markFileProcessed(any(File.class), any(JobLogDTO.class));
        verify(mockProcessLockService).releaseLock(any(File.class), anyString());
        verify(mockJobLogDataService).create(any(JobLogDTO.class));
    }

    @Test
    public void shouldInitialise() {
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        JobLogDTO log = job.initialiseLog(getTestFile1(), JobTestUtil.TEST_JOB_NAME);
        assertEquals(String.format(LogMessage.FILE_ANNOUNCEMENT.message(), TEST_FILE_NAME_1), log.getLog().trim());
    }
    
    @Test
    public void isFileOkToProcessShouldReturnTrue() {
        BaseFileImportJob job = mock(BaseFileImportJob.class);
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenCallRealMethod();
        when(job.isNotFileAlreadyProcessed(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        when(job.isNotProcessLocked(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        assertTrue(job.isFileOkToProcess(getTestFile1(), new JobLogDTO()));
    }

    @Test
    public void isFileOkToProcessShouldReturnFalse() {
        BaseFileImportJob job = mock(BaseFileImportJob.class);
        when(job.isFileOkToProcess(any(File.class), any(JobLogDTO.class))).thenCallRealMethod();
        when(job.isNotFileAlreadyProcessed(any(File.class), any(JobLogDTO.class))).thenReturn(false);
        when(job.isNotProcessLocked(any(File.class), any(JobLogDTO.class))).thenReturn(true);
        assertFalse(job.isFileOkToProcess(getTestFile1(), new JobLogDTO()));
    }
    
	@Test
    public void isFileHasDataToProcessShouldReturnTrue() {
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        assertTrue(job.isFileHasDataToProcess(getTestStringArrayList1(), new JobLogDTO()));
    }

	@Test
    public void isFileHasDataToProcessShouldReturnFalse() {
        BaseFileImportJob job = mock(BaseFileImportJob.class, CALLS_REAL_METHODS);
        List<String[]> mockEmptyStrArrayListObject = new ArrayList<String[]>();
        assertFalse(job.isFileHasDataToProcess(mockEmptyStrArrayListObject, new JobLogDTO()));
    }
	
    private Validator getValidatorWithNoErrors() {
        return new Validator() {
            @Override
            public boolean supports(Class<?> aClass) {
                return true;
            }

            @Override
            public void validate(Object target, Errors errors) {
                // record is valid - do nothing
            }
        };
    }

    private Validator getValidatorWithErrors() {
        return new Validator() {
            @Override
            public boolean supports(Class<?> aClass) {
                return true;
            }

            @Override
            public void validate(Object target, Errors errors) {
                errors.reject("test-error");
            }
        };
    }

    @Test
    public void testExecute() throws JobExecutionException {

        BaseFileImportJob mockBaseFileImportJob = mock(BaseFileImportJob.class);
        FileFinderService mockFileHandlerService = mock(FileFinderService.class);
        JobExecutionContext mockJobExecutionContext = mock(JobExecutionContext.class);
        JobDataMap mockJobDataMap = mock(JobDataMap.class);
        mockBaseFileImportJob.fileFinderService = mockFileHandlerService;
        List<File> listOfFiles = new ArrayList<File>();
        listOfFiles.add(getTestFile1());
        listOfFiles.add(getTestFile2());
        when(mockJobExecutionContext.getMergedJobDataMap()).thenReturn(mockJobDataMap);
        when(mockFileHandlerService.findFiles(any(File.class), any(FileFilter.class))).thenReturn(listOfFiles);
        doCallRealMethod().when(mockBaseFileImportJob).execute(mockJobExecutionContext);
        when(mockBaseFileImportJob.getImportRootDirectoryParameterName()).thenReturn("");
        when(mockJobDataMap.getString(anyString())).thenReturn("");
        when(mockBaseFileImportJob.getImportFileFilter()).thenReturn(null);
        doNothing().when(mockBaseFileImportJob).handleFile(any(File.class), any(JobDataMap.class));
        mockBaseFileImportJob.execute(mockJobExecutionContext);
        verify(mockBaseFileImportJob, atLeast(2)).handleFile(any(File.class), any(JobDataMap.class));

    }
}