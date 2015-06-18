package com.novacroft.nemo.tfl.common.converter.fare_aggregation_converter;

import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterChargeResponse;

public interface  FareAggregationEngineResponseConverter {

	RecalculatedOysterChargeResponseDTO convertModelToDto(GetRecalculatedOysterChargeResponse model);
}
