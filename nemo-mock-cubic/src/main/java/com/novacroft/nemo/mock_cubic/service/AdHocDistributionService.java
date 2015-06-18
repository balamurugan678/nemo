package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.mock_cubic.command.AdHocDistributionCmd;
import com.novacroft.nemo.mock_cubic.domain.AdHocDistribution;

import java.util.List;

/**
 * Application service for Ad Hoc Distribution
 */
public interface AdHocDistributionService {
    AdHocDistributionCmd addEmptyRecords(AdHocDistributionCmd cmd, Integer numberOfRecordsToAdd);

    String serialiseToCsv(List<AdHocDistribution> currentActions);
}
