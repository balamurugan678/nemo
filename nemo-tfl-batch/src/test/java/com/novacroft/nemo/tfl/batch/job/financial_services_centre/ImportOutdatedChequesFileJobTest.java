package com.novacroft.nemo.tfl.batch.job.financial_services_centre;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.common.constant.JobName;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Validator;

import java.io.FileFilter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class ImportOutdatedChequesFileJobTest {
    private ImportOutdatedChequesFileJob job;

    private FileFilter mockFileFilter;
    private Validator mockValidator;
    private ImportRecordConverter mockImportRecordConverter;
    private ImportRecordDispatcher mockImportRecordDispatcher;

    @Before
    public void setUp() {
        this.mockFileFilter = mock(FileFilter.class);
        this.mockValidator = mock(Validator.class);
        this.mockImportRecordConverter = mock(ImportRecordConverter.class);
        this.mockImportRecordDispatcher = mock(ImportRecordDispatcher.class);

        this.job = mock(ImportOutdatedChequesFileJob.class, CALLS_REAL_METHODS);
        this.job.outdatedChequesFileFilter = this.mockFileFilter;
        this.job.outdatedChequeValidator = this.mockValidator;
        this.job.outdatedChequeConverter = this.mockImportRecordConverter;
        this.job.outdatedChequeDispatcher = this.mockImportRecordDispatcher;
    }

    @Test
    public void shouldGetJobName() {
        assertEquals(JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_OUTDATED_CHEQUES.code(), this.job.getJobName());
    }

    @Test
    public void shouldGetImportFileFilter() {
        assertEquals(this.mockFileFilter, this.job.getImportFileFilter());
    }

    @Test
    public void shouldGetImportRecordValidator() {
        assertEquals(this.mockValidator, this.job.getImportRecordValidator());
    }

    @Test
    public void shouldGetImportRecordConverter() {
        assertEquals(this.mockImportRecordConverter, this.job.getImportRecordConverter());
    }

    @Test
    public void shouldGetImportRecordDispatcher() {
        assertEquals(this.mockImportRecordDispatcher, this.job.getImportRecordDispatcher());
    }
}