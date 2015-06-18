package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.cubic.AdHocDistributionConverterImpl;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic.AdHocDistributionDispatcherImpl;
import com.novacroft.nemo.tfl.batch.validator.impl.cubic.AdHocDistributionValidatorImpl;
import org.junit.Test;
import org.springframework.validation.Validator;

import static org.junit.Assert.assertEquals;

/**
 * ImportAdHocDistributionCubicFileJob unit tests
 */
public class ImportAdHocDistributionCubicFileJobTest {

    @Test
    public void shouldGetCubicRecordDispatcher() {
        ImportAdHocDistributionCubicFileJob job = new ImportAdHocDistributionCubicFileJob();
        ImportRecordDispatcher dispatcher = new AdHocDistributionDispatcherImpl();
        job.adHocDistributionDispatcher = dispatcher;
        assertEquals(dispatcher, job.getImportRecordDispatcher());
    }

    @Test
    public void shouldGetCubicRecordValidator() {
        ImportAdHocDistributionCubicFileJob job = new ImportAdHocDistributionCubicFileJob();
        Validator validator = new AdHocDistributionValidatorImpl();
        job.adHocDistributionValidator = validator;
        assertEquals(validator, job.getImportRecordValidator());
    }

    @Test
    public void shouldGetCubicRecordConverter() {
        ImportAdHocDistributionCubicFileJob job = new ImportAdHocDistributionCubicFileJob();
        ImportRecordConverter converter = new AdHocDistributionConverterImpl();
        job.adHocDistributionRecordConverter = converter;
        assertEquals(converter, job.getImportRecordConverter());
    }
}
