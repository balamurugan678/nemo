package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import au.com.bytecode.opencsv.CSVWriter;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryCSVService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryJourneyConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

/**
 * Journey History CSV service implementation
 */
@Service("journeyHistoryCSVService")
public class JourneyHistoryCSVServiceImpl extends BaseService implements JourneyHistoryCSVService {
    static final Logger logger = LoggerFactory.getLogger(JourneyHistoryCSVServiceImpl.class);

    @Autowired
    protected JourneyHistoryJourneyConverter journeyHistoryJourneyConverter;

    @Override
    public byte[] createCSV(List<JourneyDayDTO> journeyDays) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        writeHeaderRecord(csvWriter);
        writeJourneyDayRecords(csvWriter, journeyDays);
        return stringWriter.toString().getBytes();
    }

    protected void writeHeaderRecord(CSVWriter csvWriter) {
        csvWriter.writeNext(getHeaderRecord());
    }

    protected String[] getHeaderRecord() {
        return new String[]{getContent(ContentCode.JOURNEY_DATE.headingCode()),
                getContent(ContentCode.JOURNEY_START_TIME.headingCode()),
                getContent(ContentCode.JOURNEY_END_TIME.headingCode()),
                getContent(ContentCode.JOURNEY_DESCRIPTION.headingCode()), getContent(ContentCode.JOURNEY_CHARGE.headingCode()),
                getContent(ContentCode.JOURNEY_CREDIT.headingCode()), getContent(ContentCode.JOURNEY_BALANCE.headingCode()),
                getContent(ContentCode.JOURNEY_NOTE.headingCode())};
    }

    protected void writeJourneyDayRecords(CSVWriter csvWriter, List<JourneyDayDTO> journeyDays) {
        for (JourneyDayDTO journeyDay : journeyDays) {
            writeJourneyRecords(csvWriter, journeyDay.getJourneys());
        }
    }

    protected void writeJourneyRecords(CSVWriter csvWriter, List<JourneyDTO> journeys) {
        for (JourneyDTO journey : journeys) {
            writeJourneyRecord(csvWriter, journey);
        }
    }

    protected void writeJourneyRecord(CSVWriter csvWriter, JourneyDTO journey) {
        if (!journey.getSuppressCode()) {
            csvWriter.writeNext(journeyHistoryJourneyConverter.convertDtoToRecord(journey));
        }
    }
}
