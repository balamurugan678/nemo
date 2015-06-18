package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseDTO;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.data_service.NotificationsStatusDataService;
import com.novacroft.nemo.tfl.common.service_access.NotificationStatusServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.*;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.JourneyRequestCriteria;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotifyAutoFillOfSSRRequestCriteria;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.OriginatingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;

import static com.novacroft.nemo.common.utils.OysterCardNumberUtil.getNineDigitNumberAsLong;

@Service
public class NotificationsStatusDataServiceImpl implements NotificationsStatusDataService {

    @Autowired
    protected NotificationStatusServiceAccess notificationStatusServiceAccess;

    @Autowired
    protected IncompleteJourneyNotificationResponseConverter notificationResponseConverter;

    @Autowired
    protected NotifyAutoFillOfSSRStatusResponseConverter notifyAutoFillOfSSRStatusResponseConverter;

    @Override
    public IncompleteJourneyNotificationResponseDTO findByCardNumberForDaysRangeOnline(String cardNumber, int days) {
        GetIncompleteJourneyNotificationsResponse response =
                notificationStatusServiceAccess.getIncompleteJourneyNotifications(createRequest(cardNumber, days));
        return notificationResponseConverter.convertModelToDto(response);
    }

    protected GetIncompleteJourneyNotifications createRequest(String cardNumber, int daysBack) {
        ObjectFactory factory = new ObjectFactory();
        com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.ObjectFactory dataFactory =
                new com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.ObjectFactory();
        GetIncompleteJourneyNotifications request = factory.createGetIncompleteJourneyNotifications();

        JourneyRequestCriteria criteria = dataFactory.createJourneyRequestCriteria();
        criteria.setPrestigeId(getNineDigitNumberAsLong(cardNumber));
        criteria.setDaysBack(Integer.valueOf(daysBack));
        criteria.setSubmittedForTrainingPurposes(Boolean.FALSE);
        JAXBElement<JourneyRequestCriteria> criteriaElement =
                factory.createGetIncompleteJourneyNotificationsJourneyRequestCriteria(criteria);
        request.setJourneyRequestCriteria(criteriaElement);
        return request;
    }

    @Override
    public NotifyAutoFillOfSSRStatusResponseDTO notifyAutoFillOfJourneySSRStatus(
            IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO, String cardNumber,
            AutoFillSSRNotificationStatus autoFillSSRNotificationStatus) {
        NotifyAutoFillOfSSRStatusResponse response = notificationStatusServiceAccess.notifyAutoFillOfSSRStatus(
                createNotifyAutoFillOfSSRStatus(incompleteJourneyNotificationDTO, cardNumber, autoFillSSRNotificationStatus));
        return notifyAutoFillOfSSRStatusResponseConverter.convertModelToDto(response);
    }

    protected NotifyAutoFillOfSSRStatus createNotifyAutoFillOfSSRStatus(
            IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO, String cardNumber,
            AutoFillSSRNotificationStatus autoFillSSRNotificationStatus) {
        final ObjectFactory factory = new ObjectFactory();

        final NotifyAutoFillOfSSRStatus notifyAutoFillOfSSRStatus = factory.createNotifyAutoFillOfSSRStatus();
        final NotifyAutoFillOfSSRRequestCriteria notifyAutoFillOfSSRRequestCriteria = new NotifyAutoFillOfSSRRequestCriteria();
        notifyAutoFillOfSSRRequestCriteria.setLinkedDayKey(incompleteJourneyNotificationDTO.getLinkedDayKey());
        notifyAutoFillOfSSRRequestCriteria.setLinkedSequenceNumber(incompleteJourneyNotificationDTO.getLinkedSequenceNo());
        notifyAutoFillOfSSRRequestCriteria
                .setLinkedRolloverSequenceNo(incompleteJourneyNotificationDTO.getLinkedRolloverSequenceNo());
        notifyAutoFillOfSSRRequestCriteria.setLinkedTransactionDateTime(
                DateUtil.convertDateToXMLGregorian(incompleteJourneyNotificationDTO.getLinkedTransactionDateTime()));
        notifyAutoFillOfSSRRequestCriteria
                .setSSRAutoFillNotificationStatus(NotificationStatus.fromValue(autoFillSSRNotificationStatus.value()));
        notifyAutoFillOfSSRRequestCriteria.setOriginatingSystemOfSSRUpdate(OriginatingSystem.OYSTER_ONLINE);
        notifyAutoFillOfSSRRequestCriteria.setPrestigeId(getNineDigitNumberAsLong(cardNumber));
        notifyAutoFillOfSSRStatus.setNotifyAutoFillOfSSRRequest(
                factory.createNotifyAutoFillOfSSRStatusNotifyAutoFillOfSSRRequest(notifyAutoFillOfSSRRequestCriteria));

        return notifyAutoFillOfSSRStatus;
    }

}
