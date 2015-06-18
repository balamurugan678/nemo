package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import au.com.bytecode.opencsv.CSVWriter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryJourneyConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import org.junit.Test;

import static com.novacroft.nemo.test_support.JourneyTestUtil.*;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class JourneyHistoryCSVServiceImplTest {

    @Test
    public void writeJourneyRecordShouldWriteNotSuppressedRecord() {
        JourneyDTO testJourney = getTestJourneyDTO1();
        testJourney.setSuppressCode(Boolean.FALSE);

        JourneyHistoryJourneyConverter mockJourneyHistoryJourneyConverter = mock(JourneyHistoryJourneyConverter.class);
        when(mockJourneyHistoryJourneyConverter.convertDtoToRecord(any(JourneyDTO.class))).thenReturn(new String[]{});

        CSVWriter mockCsvWriter = mock(CSVWriter.class);
        doNothing().when(mockCsvWriter).writeNext(any(String[].class));

        JourneyHistoryCSVServiceImpl service = new JourneyHistoryCSVServiceImpl();
        service.journeyHistoryJourneyConverter = mockJourneyHistoryJourneyConverter;

        service.writeJourneyRecord(mockCsvWriter, testJourney);
        verify(mockJourneyHistoryJourneyConverter, atLeastOnce()).convertDtoToRecord(any(JourneyDTO.class));
    }

    @Test
    public void writeJourneyRecordShouldNotWriteSuppressedRecord() {
        JourneyDTO testJourney = getTestJourneyDTO1();
        testJourney.setSuppressCode(Boolean.TRUE);

        JourneyHistoryJourneyConverter mockJourneyHistoryJourneyConverter = mock(JourneyHistoryJourneyConverter.class);
        when(mockJourneyHistoryJourneyConverter.convertDtoToRecord(any(JourneyDTO.class))).thenReturn(new String[]{});

        CSVWriter mockCsvWriter = mock(CSVWriter.class);
        doNothing().when(mockCsvWriter).writeNext(any(String[].class));

        JourneyHistoryCSVServiceImpl service = new JourneyHistoryCSVServiceImpl();
        service.journeyHistoryJourneyConverter = mockJourneyHistoryJourneyConverter;

        service.writeJourneyRecord(mockCsvWriter, testJourney);
        verify(mockJourneyHistoryJourneyConverter, never()).convertDtoToRecord(any(JourneyDTO.class));
    }

    @Test
    public void shouldWriteJourneyRecords() {
        CSVWriter mockCsvWriter = mock(CSVWriter.class);
        doNothing().when(mockCsvWriter).writeNext(any(String[].class));

        JourneyHistoryCSVServiceImpl service = mock(JourneyHistoryCSVServiceImpl.class);
        doCallRealMethod().when(service).writeJourneyRecords(any(CSVWriter.class), anyList());
        doNothing().when(service).writeJourneyRecord(any(CSVWriter.class), any(JourneyDTO.class));

        service.writeJourneyRecords(mockCsvWriter, getTestJourneyListDTO2());

        verify(service, atLeastOnce()).writeJourneyRecord(any(CSVWriter.class), any(JourneyDTO.class));
    }

    @Test
    public void shouldWriteJourneyDayRecords() {
        CSVWriter mockCsvWriter = mock(CSVWriter.class);

        JourneyHistoryCSVServiceImpl service = mock(JourneyHistoryCSVServiceImpl.class);
        doCallRealMethod().when(service).writeJourneyDayRecords(any(CSVWriter.class), anyList());
        doNothing().when(service).writeJourneyRecords(any(CSVWriter.class), anyList());

        service.writeJourneyDayRecords(mockCsvWriter, getTestJourneyDayDtoList2());

        verify(service, atLeastOnce()).writeJourneyDayRecords(any(CSVWriter.class), anyList());
    }

    @Test
    public void shouldGetHeaderRecord() {
        JourneyHistoryCSVServiceImpl service = mock(JourneyHistoryCSVServiceImpl.class);
        when(service.getHeaderRecord()).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn("test");
        assertArrayEquals(new String[]{"test", "test", "test", "test", "test", "test", "test", "test"},
                service.getHeaderRecord());
    }

    @Test
    public void shouldWriteHeaderRecord() {
        CSVWriter mockCsvWriter = mock(CSVWriter.class);
        doNothing().when(mockCsvWriter).writeNext(any(String[].class));

        JourneyHistoryCSVServiceImpl service = mock(JourneyHistoryCSVServiceImpl.class);
        doCallRealMethod().when(service).writeHeaderRecord(any(CSVWriter.class));
        when(service.getHeaderRecord()).thenReturn(new String[]{});

        service.writeHeaderRecord(mockCsvWriter);

        verify(service).getHeaderRecord();
        verify(mockCsvWriter).writeNext(any(String[].class));
    }

    @Test
    public void shouldCreateCSV() {
        JourneyHistoryCSVServiceImpl service = mock(JourneyHistoryCSVServiceImpl.class);
        when(service.createCSV(anyList())).thenCallRealMethod();
        doNothing().when(service).writeHeaderRecord(any(CSVWriter.class));
        doNothing().when(service).writeJourneyDayRecords(any(CSVWriter.class), anyList());

        service.createCSV(getTestJourneyDayDtoList2());

        verify(service).writeHeaderRecord(any(CSVWriter.class));
        verify(service).writeJourneyDayRecords(any(CSVWriter.class), anyList());
    }
}
