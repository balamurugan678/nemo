package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.tfl.common.application_service.CollateIncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.CompletedJourneyAuditService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history
        .ResolveJourneyNotificationsReferencesService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history
        .SelfServeRefundIncompleteJourneysMonthFilterService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.constant.MonthEnum;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseDTO;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.NotificationsStatusDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Incomplete Journeys application service implementation
 */
@Service("incompleteJourneyService")
public class IncompleteJourneyServiceImpl implements IncompleteJourneyService {

    private static final String NO_OF_DAYS_BACK_FOR_INCOMPLETE_JOURNEY = "noOfDaysBackForIncompleteJourney";

    @Autowired
    protected NotificationsStatusDataService notificationStatusDataService;

    @Autowired
    protected SystemParameterDataService systemParameterDataService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected CollateIncompleteJourneyService collateIncompleteJourneyService;

    @Autowired
    protected ResolveJourneyNotificationsReferencesService resolveJourneyNotificationsReferencesService;

    @Autowired
    protected CompletedJourneyAuditService completedJourneyAuditService;

    @Autowired
    protected SelfServeRefundIncompleteJourneysMonthFilterService selfServeRefundIncompleteJourneysMonthFilterService;

    @Autowired
    protected JourneyHistoryService journeyHistoryService;

    @Override
    public IncompleteJourneyHistoryDTO getIncompleteJourneys(Long cardId) {
        List<IncompleteJourneyMonthDTO> journeyMonthList = getMonthlyCollatedJourneys(cardId);
        journeyMonthList =
                selfServeRefundIncompleteJourneysMonthFilterService.filterNotificationsForStatusInAMonth(journeyMonthList);
        final IncompleteJourneyHistoryDTO journeyHistoryDto = new IncompleteJourneyHistoryDTO();
        journeyHistoryDto.setIncompleteJourneyMonthDTO(journeyMonthList);
        return journeyHistoryDto;
    }

    @Override
    public IncompleteJourneyHistoryDTO getIncompleteJourneysForInnovator(Long cardId) {
        List<IncompleteJourneyMonthDTO> journeyMonthList = getMonthlyCollatedJourneys(cardId);
        final IncompleteJourneyHistoryDTO journeyHistoryDto = new IncompleteJourneyHistoryDTO();
        journeyHistoryDto.setIncompleteJourneyMonthDTO(journeyMonthList);
        journeyHistoryDto.setJourneyCompletedRefundItems(completedJourneyAuditService.getAuditLog(cardId));
        return journeyHistoryDto;
    }

    protected List<IncompleteJourneyMonthDTO> getMonthlyCollatedJourneys(Long cardId) {
        final IncompleteJourneyNotificationResponseDTO incompleteJourneysResponse = retrieveIncompleteJourneysForCard(cardId);
        final List<IncompleteJourneyNotificationDTO> incompleteNotitifcationList =
                incompleteJourneysResponse.getInCompleteJourneyNotificationList();
        List<IncompleteJourneyMonthDTO> journeyMonthList =
                collateIncompleteJourneyService.collateRefundEligibleJourneysByMonth(incompleteNotitifcationList);
        resolveJourneyNotificationsReferencesService.resolveReferences(journeyMonthList);
        return journeyMonthList;
    }

    protected IncompleteJourneyNotificationResponseDTO retrieveIncompleteJourneysForCard(Long cardId) {
        final CardDTO cardDTO = cardDataService.findById(cardId);
        final SystemParameterDTO systemParameterDto =
                systemParameterDataService.findByCode(NO_OF_DAYS_BACK_FOR_INCOMPLETE_JOURNEY);
        final Integer daysBack = Integer.parseInt(systemParameterDto.getValue());
        return notificationStatusDataService.findByCardNumberForDaysRangeOnline(cardDTO.getCardNumber(), daysBack);
    }

    @Override
    public IncompleteJourneyNotificationDTO retrieveEligibleIncompleteJourney(final Long cardId,
                                                                              final Date linkedTransactionDateTime,
                                                                              final Integer linkedStationKey) {
        final IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO =
                getIncompleteJourneyNotification(cardId, linkedTransactionDateTime, linkedStationKey);
        if (Boolean.TRUE.equals(incompleteJourneyNotificationDTO.getEligibleForSSR()) &&
                NotificationStatus.AUTOFILL_NOT_NOTIFIED
                        .equals(incompleteJourneyNotificationDTO.getSsrAutoFillNotificationStatus())) {
            return incompleteJourneyNotificationDTO;
        }
        return null;
    }

    @Override
    public NotifyAutoFillOfSSRStatusResponseDTO notifyAutofill(Long cardId, Date linkedTransactionDateTime,
                                                               Integer linkedStationKey,
                                                               AutoFillSSRNotificationStatus autoFillSSRNotificationStatus) {
        IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO =
                getIncompleteJourneyNotification(cardId, linkedTransactionDateTime, linkedStationKey);
        final CardDTO cardDTO = cardDataService.findById(cardId);
        return notificationStatusDataService
                .notifyAutoFillOfJourneySSRStatus(incompleteJourneyNotificationDTO, cardDTO.getCardNumber(),
                        autoFillSSRNotificationStatus);
    }

    @Override
    public IncompleteJourneyNotificationDTO getIncompleteJourneyNotification(final Long cardId,
                                                                             final Date linkedTransactionDateTime,
                                                                             final Integer linkedStationKey) {
        final IncompleteJourneyNotificationResponseDTO incompleteJourneysResponse = retrieveIncompleteJourneysForCard(cardId);
        List<IncompleteJourneyNotificationDTO> incompleteJourneyList =
                incompleteJourneysResponse.getInCompleteJourneyNotificationList();
        for (IncompleteJourneyNotificationDTO journeyDto : incompleteJourneyList) {
            if (linkedStationKey.equals(journeyDto.getLinkedStationKey()) &&
                    linkedTransactionDateTime.equals(journeyDto.getLinkedTransactionDateTime())) {
                return journeyDto;
            }
        }
        return null;
    }

    @Override
    public JourneyDTO getSSREligibleJourneyHistoryItem(Long cardId, Date journeyDate, Integer journeyId) {
        final List<IncompleteJourneyMonthDTO> journeyMonthList = getMonthlyCollatedJourneys(cardId);
        selfServeRefundIncompleteJourneysMonthFilterService.filterNotificationsForStatusInAMonth(journeyMonthList);
        final MonthEnum journeyMonth = MonthEnum.getMonthForDate(journeyDate);
        if (hasMonth(journeyMonth, journeyMonthList)) {
            return journeyHistoryService.getJourneyHistoryItem(cardId, journeyDate, journeyId);
        }

        return null;
    }

    protected boolean hasMonth(MonthEnum monthEnum, List<IncompleteJourneyMonthDTO> incompleteJourneyMonthList) {
        if (incompleteJourneyMonthList != null) {
            for (IncompleteJourneyMonthDTO incompleteJourneyMonthDTO : incompleteJourneyMonthList) {
                if (monthEnum.equals(incompleteJourneyMonthDTO.getJourneyMonth())) {
                    return true;
                }
            }
        }
        return false;
    }
}
