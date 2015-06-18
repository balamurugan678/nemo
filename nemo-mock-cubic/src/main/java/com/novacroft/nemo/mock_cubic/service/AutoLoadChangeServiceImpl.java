package com.novacroft.nemo.mock_cubic.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.novacroft.nemo.mock_cubic.command.AutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.data_service.impl.AutoLoadChangeDataService;
import com.novacroft.nemo.mock_cubic.domain.AutoLoadChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Application service for Auto Load Change
 */
@Service(value = "autoLoadChangeService")
public class AutoLoadChangeServiceImpl extends BaseCubicBatchFileService implements AutoLoadChangeService {

    @Autowired
    AutoLoadChangeDataService autoLoadChangeDataService;

    @Override
    public AutoLoadChangeCmd addEmptyRecords(AutoLoadChangeCmd cmd, Integer numberOfRecordsToAdd) {
        if (cmd.getAutoLoadChanges() == null) {
            cmd.setAutoLoadChanges(new ArrayList<AutoLoadChange>());
        }
        for (int i = 0; i < numberOfRecordsToAdd; i++) {
            cmd.getAutoLoadChanges().add(new AutoLoadChange());
        }
        return cmd;
    }

    @Override
    public String serialiseToCsv(List<AutoLoadChange> autoLoadChanges) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

        for (AutoLoadChange autoLoadChange : autoLoadChanges) {
            if (isNotEmpty(autoLoadChange)) {
                csvWriter.writeNext(toArray(autoLoadChange));
            }
        }

        csvWriter.writeNext(new String[]{calculateChecksum(stringWriter.toString().getBytes())});

        try {
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return stringWriter.toString().trim();
    }

    @Override
    public AutoLoadChangeCmd generateRecords(AutoLoadChangeCmd cmd) {
        cmd.setAutoLoadChanges(this.autoLoadChangeDataService.findRequestedAutoLoadChangeRecords());
        return cmd;
    }

    protected String[] toArray(AutoLoadChange autoLoadChange) {
        return new String[]{autoLoadChange.getPrestigeId(), autoLoadChange.getPickUpLocation(), autoLoadChange.getPickUpTime(),
                autoLoadChange.getRequestSequenceNumber(), autoLoadChange.getPreviousAutoLoadConfiguration(),
                autoLoadChange.getNewAutoLoadConfiguration(), autoLoadChange.getStatusOfAttemptedAction(),
                autoLoadChange.getFailureReasonCode()};
    }

    protected Boolean isNotEmpty(AutoLoadChange autoLoadChange) {
        return !(autoLoadChange.getPrestigeId() == null || "".equals(autoLoadChange.getPrestigeId()));
    }
}
