package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.file_filter.impl.cubic.AdHocDistributionsFileFilter;
import com.novacroft.nemo.tfl.batch.job.BaseCubicFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import java.io.FileFilter;

/**
 * Read and handle the data from CUBIC Ad Hoc Distribution file(s)
 */
public class ImportAdHocDistributionCubicFileJob extends BaseCubicFileImportJob implements Job {
    protected static final Logger logger = LoggerFactory.getLogger(ImportAdHocDistributionCubicFileJob.class);

    @Autowired
    protected ImportRecordDispatcher adHocDistributionDispatcher;

    @Autowired
    protected Validator adHocDistributionValidator;

    @Autowired
    protected ImportRecordConverter adHocDistributionRecordConverter;

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.adHocDistributionDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.adHocDistributionValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.adHocDistributionRecordConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return new AdHocDistributionsFileFilter();
    }

    @Override
    protected String getJobName() {
        return JobName.IMPORT_AD_HOC_DISTRIBUTION_CUBIC_FILE.code();
    }
}
