package com.novacroft.nemo.mock_cubic.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.novacroft.nemo.mock_cubic.command.AdHocDistributionCmd;
import com.novacroft.nemo.mock_cubic.domain.AdHocDistribution;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Application service for Current Action List
 */
@Service(value = "adHocDistributionService")
public class AdHocDistributionServiceImpl extends BaseCubicBatchFileService implements AdHocDistributionService {

    @Override
    public AdHocDistributionCmd addEmptyRecords(AdHocDistributionCmd cmd, Integer numberOfRecordsToAdd) {
        if (cmd.getAdHocDistributions() == null) {
            cmd.setAdHocDistributions(new ArrayList<AdHocDistribution>());
        }
        for (int i = 0; i < numberOfRecordsToAdd; i++) {
            cmd.getAdHocDistributions().add(new AdHocDistribution());
        }
        return cmd;
    }

    @Override
    public String serialiseToCsv(List<AdHocDistribution> adHocDistributions) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

        for (AdHocDistribution adHocDistribution : adHocDistributions) {
            if (isNotEmpty(adHocDistribution)) {
                csvWriter.writeNext(toArray(adHocDistribution));
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

    protected String[] toArray(AdHocDistribution adHocDistribution) {
        return new String[]{adHocDistribution.getPrestigeId(), adHocDistribution.getPickUpLocation(),
                adHocDistribution.getPickUpTime(), adHocDistribution.getRequestSequenceNumber(),
                adHocDistribution.getProductCode(), adHocDistribution.getPptStartDate(), adHocDistribution.getPptExpiryDate(),
                adHocDistribution.getPrePayValue(), adHocDistribution.getCurrency(),
                adHocDistribution.getStatusOfAttemptedAction(), adHocDistribution.getFailureReasonCode()};
    }

    protected Boolean isNotEmpty(AdHocDistribution adHocDistribution) {
        return !(adHocDistribution.getPrestigeId() == null || "".equals(adHocDistribution.getPrestigeId()));
    }
}
