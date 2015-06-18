package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.file_filter.impl.cubic.AutoLoadChangesFileFilter;
import com.novacroft.nemo.tfl.batch.job.BaseCubicFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import java.io.FileFilter;

/**
 * Read and handle the data from CUBIC Auto Load Changes file(s)
 */
public class ImportAutoLoadChangesCubicFileJob extends BaseCubicFileImportJob implements Job {
    protected static final Logger logger = LoggerFactory.getLogger(ImportAutoLoadChangesCubicFileJob.class);

    @Autowired
    protected ImportRecordDispatcher autoLoadChangeDispatcher;

    @Autowired
    protected Validator autoLoadChangeValidator;

    @Autowired
    protected ImportRecordConverter autoLoadChangeRecordConverter;

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.autoLoadChangeDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.autoLoadChangeValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.autoLoadChangeRecordConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return new AutoLoadChangesFileFilter();
    }

    @Override
    protected String getJobName() {
        return JobName.IMPORT_AUTO_LOAD_CHANGES_CUBIC_FILE.code();
    }
}
