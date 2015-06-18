package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.file_filter.impl.cubic.AutoLoadsPerformedFileFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

/**
 * ImportAutoLoadsPerformedCubicFileJob unit tests
 */
public class ImportAutoLoadsPerformedCubicFileJobTest {
    private ImportAutoLoadsPerformedCubicFileJob job;
    private ImportRecordDispatcher mockAutoLoadPerformedDispatcher;
    private Validator mockAutoLoadPerformedValidator;
    private ImportRecordConverter mockAutoLoadPerformedRecordConverter;

    @Before
    public void setUp() {
        this.job = mock(ImportAutoLoadsPerformedCubicFileJob.class, CALLS_REAL_METHODS);

        this.mockAutoLoadPerformedDispatcher = mock(ImportRecordDispatcher.class);
        this.mockAutoLoadPerformedValidator = mock(Validator.class);
        this.mockAutoLoadPerformedRecordConverter = mock(ImportRecordConverter.class);

        this.job.autoLoadPerformedDispatcher = this.mockAutoLoadPerformedDispatcher;
        this.job.autoLoadPerformedValidator = this.mockAutoLoadPerformedValidator;
        this.job.autoLoadPerformedRecordConverter = this.mockAutoLoadPerformedRecordConverter;
    }

    @Test
    public void shouldGetCubicRecordDispatcher() {
        assertEquals(this.mockAutoLoadPerformedDispatcher, this.job.getImportRecordDispatcher());
    }

    @Test
    public void shouldGetCubicRecordValidator() {
        assertEquals(this.mockAutoLoadPerformedValidator, this.job.getImportRecordValidator());
    }

    @Test
    public void shouldGetCubicRecordConverter() {
        assertEquals(this.mockAutoLoadPerformedRecordConverter, this.job.getImportRecordConverter());
    }

    @Test
    public void shouldGetFileFilter() {
        assertTrue(this.job.getImportFileFilter() instanceof AutoLoadsPerformedFileFilter);
    }
}
