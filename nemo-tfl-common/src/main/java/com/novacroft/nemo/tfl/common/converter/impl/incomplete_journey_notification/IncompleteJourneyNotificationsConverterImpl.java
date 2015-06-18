package com.novacroft.nemo.tfl.common.converter.impl.incomplete_journey_notification;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationsConverter;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.IncompleteJourneysNotificationsList;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;

@Component
public class IncompleteJourneyNotificationsConverterImpl implements	IncompleteJourneyNotificationsConverter {

	@Override
	public IncompleteJourneyNotificationDTO convertModelToDto(IncompleteJourneysNotificationsList model) {
        return createIncompleteJouneyNotificationDTO(model);
    }

	private IncompleteJourneyNotificationDTO createIncompleteJouneyNotificationDTO(IncompleteJourneysNotificationsList value) {
		IncompleteJourneyNotificationDTO journeyNotification = null;
		if(value != null){
			journeyNotification = new IncompleteJourneyNotificationDTO();
			journeyNotification.setAgentUserName(getStringValue(value.getAgentUserName()));
			journeyNotification.setLinkedDayKeyAsDate(DateUtil.convertXMLGregorianToDate(value.getLinkedDayKeyAsDate().getValue()));
			journeyNotification.setEligibleForSSR(value.isEligibleForSSR());
			journeyNotification.setLinkedDayKey(getIntegerValue(value.getLinkedDayKey()));
			journeyNotification.setIneligibilityNarrative(getStringValue(value.getIneligibilityNarrative()));
			journeyNotification.setLinkedCappingScheme(getIntegerValue(value.getLinkedCappingScheme()));
			journeyNotification.setLinkedCardTypeKey(getIntegerValue(value.getLinkedCardTypeKey()));
			journeyNotification.setLinkedDailyCappingFlag(getIntegerValue(value.getLinkedDailyCappingFlag()));
			journeyNotification.setLinkedPassengerAgeKey(getIntegerValue(value.getLinkedPassengerAgeKey()));
			journeyNotification.setLinkedPPTProductCodeKey(getIntegerValue(value.getLinkedPPTProductCodeKey()));
			journeyNotification.setLinkedPrepayUsed(getIntegerValue(value.getLinkedPrepayUsed()));
			journeyNotification.setLinkedPassengerType(getIntegerValue(value.getLinkedPassengerType()));
			journeyNotification.setLinkedRolloverSequenceNo(getIntegerValue(value.getLinkedRolloverSequenceNo()));
			journeyNotification.setLinkedSequenceNo(getIntegerValue(value.getLinkedSequenceNo()));
			journeyNotification.setLinkedStationKey(getIntegerValue(value.getLinkedStationKey()));
			journeyNotification.setLinkedSubsystemID(getIntegerValue(value.getLinkedSubsystemID()));
			journeyNotification.setLinkedTKTUsed(getIntegerValue(value.getLinkedTKTUsed()));
			journeyNotification.setLinkedTransactionDateTime(DateUtil.convertXMLGregorianToDate(value.getLinkedTransactionDateTime().getValue()));
			journeyNotification.setLinkedTransactionType(getIntegerValue(value.getLinkedTransactionType()));
			journeyNotification.setSsrAutoFillNotificationStatus(value.getSSRAutoFillNotificationStatus());
			journeyNotification.setSsrCaseReference(getStringValue(value.getSSRCaseReference()));
			journeyNotification.setSsrAutoFillNotificationStatusChangeDateTime(DateUtil.convertXMLGregorianToDate(value.getSSRAutoFillNotificationStatusChangeDateTime().getValue()));
			journeyNotification.setSsrSubmittedByAgent(value.isSSRSubmittedByAgent());
		}
				
		return journeyNotification;
	}


	private String getStringValue(JAXBElement<String> element){
		if(element != null){
			return element.getValue();
		}
		return null;
	}
	
	private Integer getIntegerValue(JAXBElement<Integer> element){
		if(element != null){
			return element.getValue();
		}
		return null;
	}
}
