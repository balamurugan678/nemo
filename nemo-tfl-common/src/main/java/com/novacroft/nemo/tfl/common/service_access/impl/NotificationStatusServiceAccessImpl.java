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
import com.novacroft.nemo.tfl.common.service_access.NotificationStatusServiceAccess;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotifications;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotificationsResponse;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatus;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponse;

@Service
public class NotificationStatusServiceAccessImpl extends BaseSoapServiceAccess implements NotificationStatusServiceAccess {
	
    protected static final Logger logger = LoggerFactory.getLogger(NotificationStatusServiceAccessImpl.class);
    protected static final String GET_NOTIFICATIONS_SOAP_ACTION = "http://tempuri.org/IOysterNotification/GetIncompleteJourneyNotifications"; 
    protected static final String NOTIFYAUTOFIILL_SOAP_ACTION = "http://tempuri.org/IOysterNotification/NotifyAutoFillOfSSRStatus";


	@Autowired(required = false)
	@Qualifier(value="notificationTemplate")
	protected WebServiceTemplate notificationWebServiceTemplate;
	
	@Autowired
	protected NemoUserContext nemoUserContext;
	
	@Override
	public GetIncompleteJourneyNotificationsResponse getIncompleteJourneyNotifications(GetIncompleteJourneyNotifications request) {
		 try {
		return (GetIncompleteJourneyNotificationsResponse)callSoapServiceWithLogging(notificationWebServiceTemplate, GET_NOTIFICATIONS_SOAP_ACTION, request, WebServiceName.NOTIFICATIONS_STATUS_SERVICE.code(), 
				getCustomerId(request.getJourneyRequestCriteria().getValue().getPrestigeId()), this.nemoUserContext.getUserName());
	} catch (SoapFaultClientException | WebServiceIOException e) {
        logger.error(e.getMessage(), e);
        throw new ServiceNotAvailableException(e.getMessage(), e);
    }
	}

	@Override
	public NotifyAutoFillOfSSRStatusResponse notifyAutoFillOfSSRStatus(	NotifyAutoFillOfSSRStatus request) {
		 try {
		return (NotifyAutoFillOfSSRStatusResponse)callSoapServiceWithLogging(notificationWebServiceTemplate, NOTIFYAUTOFIILL_SOAP_ACTION, request, WebServiceName.NOTIFICATIONS_STATUS_SERVICE.code(), 
				getCustomerId(request.getNotifyAutoFillOfSSRRequest().getValue().getPrestigeId()), this.nemoUserContext.getUserName());
	} catch (SoapFaultClientException | WebServiceIOException e) {
       logger.error(e.getMessage(), e);
       throw new ServiceNotAvailableException(e.getMessage(), e);
   }
	}
	
	
}
