package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

public interface FareAggregationDataService {

    RecalculatedOysterChargeResponseDTO getRecalculatedOysterCharge(JourneyDayDTO journeyDayDTO, String cardNumber);

}
