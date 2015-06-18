package com.novacroft.nemo.mock_cubic.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.novacroft.nemo.mock_cubic.command.CurrentActionListFileCmd;
import com.novacroft.nemo.mock_cubic.domain.CurrentAction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Application service for Current Action List
 */
@Service(value = "currentActionListService")
public class CurrentActionListServiceImpl extends BaseCubicBatchFileService implements CurrentActionListService {
    @Override
    public CurrentActionListFileCmd addEmptyRecords(CurrentActionListFileCmd cmd, Integer numberOfRecordsToAdd) {
        if (cmd.getCurrentActions() == null) {
            cmd.setCurrentActions(new ArrayList<CurrentAction>());
        }
        for (int i = 0; i < numberOfRecordsToAdd; i++) {
            cmd.getCurrentActions().add(new CurrentAction());
        }
        return cmd;
    }

    @Override
    public String serialiseToCsv(List<CurrentAction> currentActions) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

        for (CurrentAction currentAction : currentActions) {
            if (isNotEmpty(currentAction)) {
                csvWriter.writeNext(toArray(currentAction));
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

    protected String[] toArray(CurrentAction currentAction) {
        return new String[]{currentAction.getPrestigeId(), currentAction.getRequestSequenceNumber(),
                currentAction.getProductCode(), currentAction.getFarePaid(), currentAction.getCurrency(),
                currentAction.getPaymentMethodCode(), currentAction.getPrePayValue(), currentAction.getPickUpLocation(),
                currentAction.getPptStartDate(), currentAction.getPptExpiryDate(), currentAction.getAutoLoadState()};
    }

    protected Boolean isNotEmpty(CurrentAction currentAction) {
        return !(currentAction.getPrestigeId() == null || "".equals(currentAction.getPrestigeId()));
    }
}
