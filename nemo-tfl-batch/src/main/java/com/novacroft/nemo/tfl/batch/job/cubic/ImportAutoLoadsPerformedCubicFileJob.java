package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.file_filter.impl.cubic.AutoLoadsPerformedFileFilter;
import com.novacroft.nemo.tfl.batch.job.BaseCubicFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import java.io.FileFilter;

/**
 * Read and handle the data from CUBIC Auto Loads Performed file(s)
 */
public class ImportAutoLoadsPerformedCubicFileJob extends BaseCubicFileImportJob implements Job {
    protected static final Logger logger = LoggerFactory.getLogger(ImportAutoLoadsPerformedCubicFileJob.class);

    @Autowired
    protected ImportRecordDispatcher autoLoadPerformedDispatcher;

    @Autowired
    protected Validator autoLoadPerformedValidator;

    @Autowired
    protected ImportRecordConverter autoLoadPerformedRecordConverter;

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.autoLoadPerformedDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.autoLoadPerformedValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.autoLoadPerformedRecordConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return new AutoLoadsPerformedFileFilter();
    }

    @Override
    protected String getJobName() {
        return JobName.IMPORT_AUTO_LOADS_PERFORMED_CUBIC_FILE.code();
    }
}
