package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.mock_cubic.command.AutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.domain.AutoLoadChange;

import java.util.List;

/**
 * Application service for Auto Load Change
 */
public interface AutoLoadChangeService {
    AutoLoadChangeCmd addEmptyRecords(AutoLoadChangeCmd cmd, Integer numberOfRecordsToAdd);

    AutoLoadChangeCmd generateRecords(AutoLoadChangeCmd cmd);

    String serialiseToCsv(List<AutoLoadChange> currentActions);
}
