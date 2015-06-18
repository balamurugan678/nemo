package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.cubic.AutoLoadChangeConverterImpl;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic.AutoLoadChangeDispatcherImpl;
import com.novacroft.nemo.tfl.batch.validator.impl.cubic.AutoLoadChangeValidatorImpl;
import org.junit.Test;
import org.springframework.validation.Validator;

import static org.junit.Assert.assertEquals;

/**
 * ImportAutoLoadChangesCubicFileJob unit tests
 */
public class ImportAutoLoadChangesCubicFileJobTest {
    @Test
    public void shouldGetCubicRecordDispatcher() {
        ImportAutoLoadChangesCubicFileJob job = new ImportAutoLoadChangesCubicFileJob();
        ImportRecordDispatcher dispatcher = new AutoLoadChangeDispatcherImpl();
        job.autoLoadChangeDispatcher = dispatcher;
        assertEquals(dispatcher, job.getImportRecordDispatcher());
    }

    @Test
    public void shouldGetCubicRecordValidator() {
        ImportAutoLoadChangesCubicFileJob job = new ImportAutoLoadChangesCubicFileJob();
        Validator validator = new AutoLoadChangeValidatorImpl();
        job.autoLoadChangeValidator = validator;
        assertEquals(validator, job.getImportRecordValidator());
    }

    @Test
    public void shouldGetCubicRecordConverter() {
        ImportAutoLoadChangesCubicFileJob job = new ImportAutoLoadChangesCubicFileJob();
        ImportRecordConverter converter = new AutoLoadChangeConverterImpl();
        job.autoLoadChangeRecordConverter = converter;
        assertEquals(converter, job.getImportRecordConverter());
    }
}
