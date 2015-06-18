package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.mock_cubic.command.CurrentActionListFileCmd;
import com.novacroft.nemo.mock_cubic.domain.CurrentAction;

import java.util.List;

/**
 * Application service for Current Action List
 */
public interface CurrentActionListService {
    CurrentActionListFileCmd addEmptyRecords(CurrentActionListFileCmd cmd, Integer numberOfRecordsToAdd);

    String serialiseToCsv(List<CurrentAction> currentActions);
}
