package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.converter.impl.journey_history.JourneyHistoryJourneyConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.journey_history.JourneyHistoryResponseConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.journey_history.JourneyHistoryTapConverterImpl;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryJourneyConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryResponseConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryTapConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Fixtures and utilities for Oyster Journey service response tests
 */

public final class JourneyHistoryResponseTestUtil {
    public static GetHistoryResponse getTestGetHistoryResponse1() {
        try {
            File testFixture = new ClassPathResource("get-history-response.xml").getFile();
            JAXBContext jaxbContext = JAXBContext.newInstance(GetHistoryResponse.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (GetHistoryResponse) jaxbUnmarshaller.unmarshal(testFixture);
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static JourneyHistoryResponseDTO getJourneyHistoryResponseDTO1() {
        JourneyHistoryTapConverter journeyHistoryTapConverter = new JourneyHistoryTapConverterImpl();
        JourneyHistoryJourneyConverter journeyHistoryJourneyConverter = new JourneyHistoryJourneyConverterImpl();
        setField(journeyHistoryJourneyConverter, "journeyHistoryTapConverter", journeyHistoryTapConverter);
        JourneyHistoryResponseConverter journeyHistoryResponseConverter = new JourneyHistoryResponseConverterImpl();
        setField(journeyHistoryResponseConverter, "journeyHistoryJourneyConverter", journeyHistoryJourneyConverter);
        return journeyHistoryResponseConverter.convertModelToDto(getTestGetHistoryResponse1());
    }
}
