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
import com.novacroft.nemo.tfl.common.service_access.OysterJourneyHistoryServiceAccess;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistory;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTapResponse;

/**
 * Oyster Journey web service access
 *
 * <p>
 * Consumers of this service must call <code>setOnlineTimeout()</code> or <code>setBatchTimeout()</code> before use.
 * </p>
 */
@Service("oysterJourneyHistoryServiceAccess")
public class OysterJourneyHistoryServiceAccessImpl extends BaseSoapServiceAccess implements OysterJourneyHistoryServiceAccess {
    protected static final Logger logger = LoggerFactory.getLogger(OysterJourneyHistoryServiceAccessImpl.class);
    protected static final String GET_HISTORY_SOAP_ACTION = "http://tempuri.org/IOysterOJSWS/GetHistory";
    protected static final String INSERT_SYNTHETIC_TAP_SOAP_ACTION = "http://tempuri.org/IOysterOJSWS/InsertSyntheticOysterTap";

    @Autowired(required = false)
    @Qualifier(value="oysterJourneyTemplate")
    protected WebServiceTemplate oysterJourneyWebServiceTemplate;

    @Autowired
    protected NemoUserContext nemoUserContext;

    @Override
    public GetHistoryResponse getHistory(GetHistory request) {
        assert (this.oysterJourneyWebServiceTemplate != null);
        try {
            return (GetHistoryResponse) callSoapServiceWithLogging(this.oysterJourneyWebServiceTemplate,
                    GET_HISTORY_SOAP_ACTION, request, WebServiceName.OYSTER_JOURNEY_GET_HISTORY.code(),
                    getCustomerId(request.getPrestigeId()), this.nemoUserContext.getUserName());
        } catch (SoapFaultClientException | WebServiceIOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceNotAvailableException(e.getMessage(), e);
        }
    }

    @Override
    public void setOnlineTimeout() {
        assert (this.oysterJourneyWebServiceTemplate != null);
        setOnlineTimeout(this.oysterJourneyWebServiceTemplate);
    }

    @Override
    public void setBatchTimeout() {
        assert (this.oysterJourneyWebServiceTemplate != null);
        setBatchTimeout(this.oysterJourneyWebServiceTemplate);
    }

	@Override
	public InsertSyntheticOysterTapResponse insertSyntheticTap(InsertSyntheticOysterTap request) {
		try {
            return (InsertSyntheticOysterTapResponse) callSoapServiceWithLogging(this.oysterJourneyWebServiceTemplate,
            		INSERT_SYNTHETIC_TAP_SOAP_ACTION, request, WebServiceName.OYSTER_JOURNEY_GET_HISTORY.code(),
                    getCustomerId((long) request.getSyntheticOysterTapRequest().getValue().getPrestigeId()), this.nemoUserContext.getUserName());
        } catch (SoapFaultClientException | WebServiceIOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceNotAvailableException(e.getMessage(), e);
        }
	}

}
