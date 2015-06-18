package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.mock_cubic.command.AutoLoadPerformedCmd;
import com.novacroft.nemo.mock_cubic.domain.AutoLoadPerformed;

import java.util.List;

/**
 * Auto Load Performed service
 */
public interface AutoLoadPerformedService {
    AutoLoadPerformedCmd addEmptyRecords(AutoLoadPerformedCmd cmd, Integer numberOfRecordsToAdd);

    String serialiseToCsv(List<AutoLoadPerformed> autoLoadsPerformed);
}
