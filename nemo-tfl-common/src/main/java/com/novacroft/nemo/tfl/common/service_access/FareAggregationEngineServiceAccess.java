package com.novacroft.nemo.tfl.common.service_access;

import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterCharge;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterChargeResponse;

public interface FareAggregationEngineServiceAccess {

	GetRecalculatedOysterChargeResponse getRecalculatedOysterCharge(GetRecalculatedOysterCharge request);

}
