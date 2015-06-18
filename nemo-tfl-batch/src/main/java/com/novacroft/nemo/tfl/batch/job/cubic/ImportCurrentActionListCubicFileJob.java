package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.file_filter.impl.cubic.CurrentActionListFileFilter;
import com.novacroft.nemo.tfl.batch.job.BaseCubicFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import java.io.FileFilter;

/**
 * Read and handle the data from CUBIC Current Action List file(s)
 */
public class ImportCurrentActionListCubicFileJob extends BaseCubicFileImportJob implements Job {
    protected static final Logger logger = LoggerFactory.getLogger(ImportCurrentActionListCubicFileJob.class);

    @Autowired
    protected ImportRecordDispatcher currentActionDispatcher;

    @Autowired
    protected Validator currentActionValidator;

    @Autowired
    protected ImportRecordConverter currentActionRecordConverter;

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.currentActionDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.currentActionValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.currentActionRecordConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return new CurrentActionListFileFilter();
    }

    @Override
    protected String getJobName() {
        return JobName.IMPORT_CURRENT_ACTION_LIST_CUBIC_FILE.code();
    }
}
