package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.CompletedJourneyAuditService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.SelfServeRefundIncompleteJourneysMonthFilterService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.constant.MonthEnum;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseDTO;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.NotificationsStatusDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

@RunWith(MockitoJUnitRunner.class)
public class IncompleteJourneyServiceImplTest {

	private static final int LINKED_STATION_KEY = 3;


	private static final Date LINKED_TRANSACTION_DATE_TIME = new Date();


	private static final Long CARD_ID = 3l;


	@Mock
    protected SelfServeRefundIncompleteJourneysMonthFilterService selfServeRefundIncompleteJourneysMonthFilterService;
	    

	@Mock
	protected JourneyHistoryService journeyHistoryService;
	
	@Mock
	protected IncompleteJourneyServiceImpl incompleteJourneyServiceImpl;
	
	@Mock
	protected CardDataService cardDataService;
	
	protected List<IncompleteJourneyMonthDTO> incompleteJourneyMonthDTOList;
	
	protected IncompleteJourneyMonthDTO incompleteJourneyMonthDTO;
	
	protected InCompleteJourneyDTO inCompleteJourneyDTO;
	
	protected List<InCompleteJourneyDTO>  inCompleteJourneyDTOList ;
	
	protected IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO;
	@Mock
	protected CompletedJourneyAuditService completedJourneyAuditService;
	@Mock
    protected NotificationsStatusDataService notificationStatusDataService;
	
	protected JourneyDTO journeyDTO = new JourneyDTO();
	
	protected static final Date PAST_JANUARY_DATE = new GregorianCalendar(2013, 0, 5).getTime();
	
	
	
	
	@Before
	public void setUp(){
		incompleteJourneyServiceImpl.selfServeRefundIncompleteJourneysMonthFilterService = selfServeRefundIncompleteJourneysMonthFilterService;
		incompleteJourneyServiceImpl.journeyHistoryService = journeyHistoryService;
	}
	

	
	@SuppressWarnings("unchecked")
	@Test
	public void getSSREligibleJourneyHistoryItemReturnsItemWhenJourneyMonthFound(){
		doCallRealMethod().when(incompleteJourneyServiceImpl).getSSREligibleJourneyHistoryItem(anyLong(), any(Date.class), anyInt());
		incompleteJourneyMonthDTOList = getJourneyMonthList();
		when(incompleteJourneyServiceImpl.getMonthlyCollatedJourneys(anyLong())).thenReturn(incompleteJourneyMonthDTOList);
		when(selfServeRefundIncompleteJourneysMonthFilterService.filterNotificationsForStatusInAMonth(anyList())).thenReturn(incompleteJourneyMonthDTOList);
		doCallRealMethod().when(incompleteJourneyServiceImpl).hasMonth(MonthEnum.JANUARY, incompleteJourneyMonthDTOList);
		when(journeyHistoryService.getJourneyHistoryItem(anyLong(), any(Date.class) , anyInt())).thenReturn(journeyDTO);
		
		assertTrue(incompleteJourneyServiceImpl.getSSREligibleJourneyHistoryItem(3l, PAST_JANUARY_DATE, 3) == journeyDTO);
		
	}



	private List<IncompleteJourneyMonthDTO> getJourneyMonthList() {
		incompleteJourneyMonthDTOList = new ArrayList<>();
		incompleteJourneyMonthDTO = new IncompleteJourneyMonthDTO();
		inCompleteJourneyDTOList = new ArrayList<>();
		inCompleteJourneyDTO = new InCompleteJourneyDTO();
		incompleteJourneyNotificationDTO = new IncompleteJourneyNotificationDTO();
		incompleteJourneyNotificationDTO.setSsrAutoFillNotificationStatus(NotificationStatus.AUTOFILL_NOTIFIED_OF_COMPLETION);
		inCompleteJourneyDTO.setJourneyNotificationDTO(incompleteJourneyNotificationDTO);
		incompleteJourneyMonthDTO.getIncompleteJourneyList().add(inCompleteJourneyDTO);
		incompleteJourneyMonthDTO.setJourneyMonth(MonthEnum.JANUARY);
		incompleteJourneyMonthDTOList.add(incompleteJourneyMonthDTO);
		return incompleteJourneyMonthDTOList;
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSSREligibleJourneyHistoryItemReturnsNullWhenJourneyMonthNotFound(){
		doCallRealMethod().when(incompleteJourneyServiceImpl).getSSREligibleJourneyHistoryItem(anyLong(), any(Date.class), anyInt());
		incompleteJourneyMonthDTOList = new ArrayList<>();
		incompleteJourneyMonthDTO = new IncompleteJourneyMonthDTO();
		inCompleteJourneyDTOList = new ArrayList<>();
		inCompleteJourneyDTO = new InCompleteJourneyDTO();
		incompleteJourneyNotificationDTO = new IncompleteJourneyNotificationDTO();
		incompleteJourneyNotificationDTO.setSsrAutoFillNotificationStatus(NotificationStatus.AUTOFILL_NOTIFIED_OF_COMPLETION);
		inCompleteJourneyDTO.setJourneyNotificationDTO(incompleteJourneyNotificationDTO);
		incompleteJourneyMonthDTO.getIncompleteJourneyList().add(inCompleteJourneyDTO);
		incompleteJourneyMonthDTO.setJourneyMonth(MonthEnum.FEBRUARY);
		incompleteJourneyMonthDTOList.add(incompleteJourneyMonthDTO);
		when(incompleteJourneyServiceImpl.getMonthlyCollatedJourneys(anyLong())).thenReturn(incompleteJourneyMonthDTOList);
		when(selfServeRefundIncompleteJourneysMonthFilterService.filterNotificationsForStatusInAMonth(anyList())).thenReturn(incompleteJourneyMonthDTOList);
		doCallRealMethod().when(incompleteJourneyServiceImpl).hasMonth(MonthEnum.JANUARY, incompleteJourneyMonthDTOList);
		when(journeyHistoryService.getJourneyHistoryItem(anyLong(), any(Date.class) , anyInt())).thenReturn(journeyDTO);
		
		assertTrue(incompleteJourneyServiceImpl.getSSREligibleJourneyHistoryItem(3l, PAST_JANUARY_DATE, 3) == null);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetIncompleteJourneysReturnsJourneysFoundAfterFiltering(){
		incompleteJourneyMonthDTOList = getJourneyMonthList();
		when(incompleteJourneyServiceImpl.getMonthlyCollatedJourneys(anyLong())).thenReturn(incompleteJourneyMonthDTOList);
		when(selfServeRefundIncompleteJourneysMonthFilterService.filterNotificationsForStatusInAMonth(incompleteJourneyMonthDTOList)).thenReturn(incompleteJourneyMonthDTOList);
		doCallRealMethod().when(incompleteJourneyServiceImpl).getIncompleteJourneys(anyLong());
		assertTrue(incompleteJourneyServiceImpl.getIncompleteJourneys(0l).getIncompleteJourneyMonthDTO() == incompleteJourneyMonthDTOList);
		verify(selfServeRefundIncompleteJourneysMonthFilterService).filterNotificationsForStatusInAMonth(anyList());
		verify(incompleteJourneyServiceImpl).getMonthlyCollatedJourneys(anyLong());
		
	}
	
	@Test
	public void testGettIncompleteJourneysForInnovatorReturnsJourneysFoundWithoutAuditLog(){
		List<JourneyCompletedRefundItemDTO> journeyCompletedRefundItems = new ArrayList<>();
		incompleteJourneyServiceImpl.completedJourneyAuditService = completedJourneyAuditService;
		incompleteJourneyMonthDTOList = getJourneyMonthList();
		when(incompleteJourneyServiceImpl.getMonthlyCollatedJourneys(anyLong())).thenReturn(incompleteJourneyMonthDTOList);
		when(completedJourneyAuditService.getAuditLog(anyLong())).thenReturn(journeyCompletedRefundItems);
		doCallRealMethod().when(incompleteJourneyServiceImpl).getIncompleteJourneysForInnovator(anyLong());
		assertTrue(incompleteJourneyServiceImpl.getIncompleteJourneysForInnovator(0l).getIncompleteJourneyMonthDTO() == incompleteJourneyMonthDTOList);
		verify(incompleteJourneyServiceImpl).getMonthlyCollatedJourneys(anyLong());
		verify(completedJourneyAuditService).getAuditLog(anyLong());
	}
	
	@Test
	public void testNotifyAutofillCallAutillAndReturnResponse(){
		NotifyAutoFillOfSSRStatusResponseDTO notifyAutoFillOfSSRStatusResponseDTO = new NotifyAutoFillOfSSRStatusResponseDTO();
		IncompleteJourneyNotificationDTO notificationReturned = new IncompleteJourneyNotificationDTO();
		incompleteJourneyServiceImpl.notificationStatusDataService = notificationStatusDataService;
		incompleteJourneyServiceImpl.cardDataService = cardDataService;
		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardNumber(CARD_ID.toString());
		incompleteJourneyMonthDTOList = getJourneyMonthList();
		when(cardDataService.findById(CARD_ID)).thenReturn(cardDTO);
		when(incompleteJourneyServiceImpl.getIncompleteJourneyNotification(anyLong(), any(Date.class), anyInt())).thenReturn(notificationReturned);
		when(notificationStatusDataService.notifyAutoFillOfJourneySSRStatus(notificationReturned, CARD_ID.toString(), AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED)).thenReturn(notifyAutoFillOfSSRStatusResponseDTO);
		doCallRealMethod().when(incompleteJourneyServiceImpl).notifyAutofill(CARD_ID, LINKED_TRANSACTION_DATE_TIME, LINKED_STATION_KEY, AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED);
		assertTrue(incompleteJourneyServiceImpl.notifyAutofill(CARD_ID, LINKED_TRANSACTION_DATE_TIME, LINKED_STATION_KEY, AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED) == notifyAutoFillOfSSRStatusResponseDTO);
		verify(notificationStatusDataService).notifyAutoFillOfJourneySSRStatus(notificationReturned, CARD_ID.toString(), AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED);
		
	}
	
	@Test
	public void testgetIncompleteJourneyNotificationReturnNullWhenNotFound(){
		IncompleteJourneyNotificationResponseDTO incompleteJourneyNotificationResponseDTO = new IncompleteJourneyNotificationResponseDTO();
		when(incompleteJourneyServiceImpl.retrieveIncompleteJourneysForCard(CARD_ID)).thenReturn(incompleteJourneyNotificationResponseDTO);
		doCallRealMethod().when(incompleteJourneyServiceImpl).getIncompleteJourneyNotification(CARD_ID, LINKED_TRANSACTION_DATE_TIME, LINKED_STATION_KEY);
		assertTrue(incompleteJourneyServiceImpl.getIncompleteJourneyNotification(CARD_ID, LINKED_TRANSACTION_DATE_TIME, LINKED_STATION_KEY) == null);
	}
	
	@Test
	public void testgetIncompleteJourneyNotificationReturnValueWhenFound(){
		IncompleteJourneyNotificationResponseDTO incompleteJourneyNotificationResponseDTO = new IncompleteJourneyNotificationResponseDTO();
		IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO = new IncompleteJourneyNotificationDTO();
		incompleteJourneyNotificationResponseDTO.getInCompleteJourneyNotificationList().add(incompleteJourneyNotificationDTO);
		incompleteJourneyNotificationDTO.setLinkedStationKey(LINKED_STATION_KEY);
		incompleteJourneyNotificationDTO.setLinkedTransactionDateTime(LINKED_TRANSACTION_DATE_TIME);
		when(incompleteJourneyServiceImpl.retrieveIncompleteJourneysForCard(CARD_ID)).thenReturn(incompleteJourneyNotificationResponseDTO);
		doCallRealMethod().when(incompleteJourneyServiceImpl).getIncompleteJourneyNotification(CARD_ID, LINKED_TRANSACTION_DATE_TIME, LINKED_STATION_KEY);
		assertTrue(incompleteJourneyServiceImpl.getIncompleteJourneyNotification(CARD_ID, LINKED_TRANSACTION_DATE_TIME, LINKED_STATION_KEY) == incompleteJourneyNotificationDTO);
	}
		

	
	
	
	
}
