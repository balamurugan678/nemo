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
 * ImportCurrentActionListCubicFileJob unit tests
 */
public class ImportCurrentActionListCubicFileJobTest {
    @Test
    public void shouldGetCubicRecordDispatcher() {
        ImportCurrentActionListCubicFileJob job = new ImportCurrentActionListCubicFileJob();
        ImportRecordDispatcher dispatcher = new AutoLoadChangeDispatcherImpl();
        job.currentActionDispatcher = dispatcher;
        assertEquals(dispatcher, job.getImportRecordDispatcher());
    }

    @Test
    public void shouldGetCubicRecordValidator() {
        ImportCurrentActionListCubicFileJob job = new ImportCurrentActionListCubicFileJob();
        Validator validator = new AutoLoadChangeValidatorImpl();
        job.currentActionValidator = validator;
        assertEquals(validator, job.getImportRecordValidator());
    }

    @Test
    public void shouldGetCubicRecordConverter() {
        ImportCurrentActionListCubicFileJob job = new ImportCurrentActionListCubicFileJob();
        ImportRecordConverter converter = new AutoLoadChangeConverterImpl();
        job.currentActionRecordConverter = converter;
        assertEquals(converter, job.getImportRecordConverter());
    }
}

