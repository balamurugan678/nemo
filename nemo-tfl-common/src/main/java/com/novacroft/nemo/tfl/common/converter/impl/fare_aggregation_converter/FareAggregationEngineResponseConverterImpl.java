package com.novacroft.nemo.tfl.common.converter.impl.fare_aggregation_converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.fare_aggregation_converter.FareAggregationEngineResponseConverter;
import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterChargeResponse;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.RecalculatedOysterChargeResponse;
@Component
public class FareAggregationEngineResponseConverterImpl implements	FareAggregationEngineResponseConverter {

	@Override
	public RecalculatedOysterChargeResponseDTO convertModelToDto(GetRecalculatedOysterChargeResponse responseModel) {
		RecalculatedOysterChargeResponseDTO recalculatedChargedto = new RecalculatedOysterChargeResponseDTO();
		RecalculatedOysterChargeResponse recalulatedCharge =  responseModel.getGetRecalculatedOysterChargeResult().getValue();
		recalculatedChargedto.setPrestigeID(recalulatedCharge.getPrestigeID());
		recalculatedChargedto.setCustomerType(recalulatedCharge.getCustomerType().value());
		recalculatedChargedto.setTravelDate(DateUtil.convertXMLGregorianToDate(recalulatedCharge.getTravelDate()));
		recalculatedChargedto.setDayPricePence(recalulatedCharge.getDayPricePence());
		return recalculatedChargedto;
	}
	
}
