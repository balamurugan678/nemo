package com.novacroft.nemo.mock_cubic.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.novacroft.nemo.mock_cubic.command.AutoLoadPerformedCmd;
import com.novacroft.nemo.mock_cubic.domain.AutoLoadPerformed;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto Load Performed service
 */
@Service(value = "autoLoadPerformedService")
public class AutoLoadPerformedServiceImpl extends BaseCubicBatchFileService implements AutoLoadPerformedService {
    @Override
    public AutoLoadPerformedCmd addEmptyRecords(AutoLoadPerformedCmd cmd, Integer numberOfRecordsToAdd) {
        if (cmd.getAutoLoadsPerformed() == null) {
            cmd.setAutoLoadsPerformed(new ArrayList<AutoLoadPerformed>());
        }
        for (int i = 0; i < numberOfRecordsToAdd; i++) {
            cmd.getAutoLoadsPerformed().add(new AutoLoadPerformed());
        }
        return cmd;
    }

    @Override
    public String serialiseToCsv(List<AutoLoadPerformed> autoLoadsPerformed) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

        for (AutoLoadPerformed autoLoadPerformed : autoLoadsPerformed) {
            if (isNotEmpty(autoLoadPerformed)) {
                csvWriter.writeNext(toArray(autoLoadPerformed));
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

    protected String[] toArray(AutoLoadPerformed autoLoadPerformed) {
        return new String[]{autoLoadPerformed.getPrestigeId(), autoLoadPerformed.getPickUpLocation(),
                autoLoadPerformed.getBusRouteId(), autoLoadPerformed.getPickUpTime(),
                autoLoadPerformed.getAutoLoadConfiguration(), autoLoadPerformed.getTopUpAmountAdded(),
                autoLoadPerformed.getCurrency()};
    }

    protected Boolean isNotEmpty(AutoLoadPerformed autoLoadPerformed) {
        return !(autoLoadPerformed.getPrestigeId() == null || "".equals(autoLoadPerformed.getPrestigeId()));
    }
}
