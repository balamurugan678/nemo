package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public interface DataExportOrchestrator {

    void exportPrestige(JobLogDTO jobLog);

}
