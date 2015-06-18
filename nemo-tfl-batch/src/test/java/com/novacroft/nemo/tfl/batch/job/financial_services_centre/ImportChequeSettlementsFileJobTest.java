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

public class ImportChequeSettlementsFileJobTest {
    private ImportChequeSettlementsFileJob job;

    private FileFilter mockFileFilter;
    private ImportRecordDispatcher mockImportRecordDispatcher;
    private Validator mockValidator;
    private ImportRecordConverter mockImportRecordConverter;

    @Before
    public void setUp() {
        this.mockFileFilter = mock(FileFilter.class);
        this.mockImportRecordDispatcher = mock(ImportRecordDispatcher.class);
        this.mockValidator = mock(Validator.class);
        this.mockImportRecordConverter = mock(ImportRecordConverter.class);

        this.job = mock(ImportChequeSettlementsFileJob.class, CALLS_REAL_METHODS);
        this.job.chequeSettlementsFileFilter = this.mockFileFilter;
        this.job.chequeSettlementDispatcher = this.mockImportRecordDispatcher;
        this.job.chequeSettlementValidator = this.mockValidator;
        this.job.chequeSettlementRecordConverter = this.mockImportRecordConverter;
    }

    @Test
    public void shouldGetImportFileFilter() {
        assertEquals(this.mockFileFilter, this.job.getImportFileFilter());
    }

    @Test
    public void shouldGetImportRecordConverter() {
        assertEquals(this.mockImportRecordConverter, this.job.getImportRecordConverter());
    }

    @Test
    public void shouldGetImportRecordValidator() {
        assertEquals(this.mockValidator, this.job.getImportRecordValidator());
    }

    @Test
    public void shouldGetImportRecordDispatcher() {
        assertEquals(this.mockImportRecordDispatcher, this.job.getImportRecordDispatcher());
    }

    @Test
    public void shouldGetJobName() {
        assertEquals(JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUE_SETTLEMENTS.code(), this.job.getJobName());
    }

}