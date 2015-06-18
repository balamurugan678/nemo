package com.novacroft.nemo.tfl.common.service_access.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.service_access.FareAggregationEngineServiceAccess;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterCharge;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterChargeResponse;
@Service
public class FareAggregationEngineServiceAccessImpl extends BaseSoapServiceAccess implements FareAggregationEngineServiceAccess  {

    protected static final Logger logger = LoggerFactory.getLogger(OysterJourneyHistoryServiceAccessImpl.class);
    protected static final String GET_RECALCULATED_OYSTER_CHARGE_SOAP_ACTION = "http://tempuri.org/IFaeService/GetRecalculatedOysterCharge";

    @Autowired(required =  false)
    @Qualifier(value="fareAggregationEngineTemplate")
    protected WebServiceTemplate fairWebServiceTemplate;

    @Autowired
    protected NemoUserContext nemoUserContext;
    
    @Override
	public GetRecalculatedOysterChargeResponse getRecalculatedOysterCharge(GetRecalculatedOysterCharge request) {
		 try {
	            return (GetRecalculatedOysterChargeResponse) callSoapServiceWithLogging(this.fairWebServiceTemplate,
	            		GET_RECALCULATED_OYSTER_CHARGE_SOAP_ACTION, request, WebServiceName.FAIR_AGGREGATION_ENGINE.code(),
	                    getCustomerId(request.getCard().getValue().getPrestigeID()), this.nemoUserContext.getUserName());
	        } catch (SoapFaultClientException | WebServiceIOException e) {
	            logger.error(e.getMessage(), e);
	            throw new ServiceNotAvailableException(e.getMessage(), e);
	        }
		
	}
}
