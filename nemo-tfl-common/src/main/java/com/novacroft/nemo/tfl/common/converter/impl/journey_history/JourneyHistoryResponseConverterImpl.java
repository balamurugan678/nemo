package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryJourneyConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryResponseConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ArrayOfJourney;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Journey;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Journey history transfer class / web service model converter.
 */
@Component("journeyHistoryResponseConverter")
public class JourneyHistoryResponseConverterImpl implements JourneyHistoryResponseConverter {

    @Autowired
    protected JourneyHistoryJourneyConverter journeyHistoryJourneyConverter;

    @Override
    public JourneyHistoryResponseDTO convertModelToDto(GetHistoryResponse historyResponse) {
        JourneyHistoryResponseDTO responseDTO = new JourneyHistoryResponseDTO();
        for (Journey journey : getJourneyList(getArrayOfJourney(getResponse(historyResponse)))) {
            responseDTO.getJourneys().add(convertJourney(journey));
        }
        return responseDTO;
    }

    protected Response getResponse(GetHistoryResponse historyResponse) {
        return historyResponse.getGetHistoryResult().getValue();
    }

    protected ArrayOfJourney getArrayOfJourney(Response response) {
        return response.getJourneys().getValue();
    }

    protected List<Journey> getJourneyList(ArrayOfJourney arrayOfJourney) {
        return arrayOfJourney.getJourney();
    }

    protected JourneyDTO convertJourney(Journey journey) {
        return this.journeyHistoryJourneyConverter.convertModelToDto(journey);
    }
}
