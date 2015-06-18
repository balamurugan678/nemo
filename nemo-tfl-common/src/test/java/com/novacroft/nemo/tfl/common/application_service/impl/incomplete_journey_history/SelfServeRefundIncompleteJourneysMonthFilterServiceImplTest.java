package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.tfl.common.constant.MonthEnum;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

@RunWith(MockitoJUnitRunner.class)
public class SelfServeRefundIncompleteJourneysMonthFilterServiceImplTest {
	
	protected List<IncompleteJourneyMonthDTO> incompleteJourneyMonthDTOList;
	
	protected IncompleteJourneyMonthDTO incompleteJourneyMonthDTO;
	
	protected InCompleteJourneyDTO inCompleteJourneyDTO;
	
	protected List<InCompleteJourneyDTO>  inCompleteJourneyDTOList ;
	
	protected IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO;
	
	protected SelfServeRefundIncompleteJourneysMonthFilterServiceImpl selfServeRefundIncompleteJourneysMonthFilterServiceImpl = new SelfServeRefundIncompleteJourneysMonthFilterServiceImpl();
	
	@Test
	public void emptyListfilterNotificationsForStatusInAMonthResturnsEmpty() {
		incompleteJourneyMonthDTOList = new ArrayList<>();
		assertTrue(incompleteJourneyMonthDTOList == selfServeRefundIncompleteJourneysMonthFilterServiceImpl.filterNotificationsForStatusInAMonth(incompleteJourneyMonthDTOList));
		
		
		
	}
	
	@Test
	public void nullFilterNotificationsForStatusInAMonthResturnsNull() {
		incompleteJourneyMonthDTOList = null;
		assertTrue(null == selfServeRefundIncompleteJourneysMonthFilterServiceImpl.filterNotificationsForStatusInAMonth(incompleteJourneyMonthDTOList));
	}
	
	@Test
	public void filterNotificationsForStatusInAMonthRemovesMonthWithCompletedStatus() {
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
		
		assertTrue(selfServeRefundIncompleteJourneysMonthFilterServiceImpl.filterNotificationsForStatusInAMonth(incompleteJourneyMonthDTOList).size() == 0);
	}
	
	@Test
	public void filterNotificationsForStatusInAMonthRetainsMonthWithNoCompletedOrCommenced() {
		incompleteJourneyMonthDTOList = new ArrayList<>();
		incompleteJourneyMonthDTO = new IncompleteJourneyMonthDTO();
		inCompleteJourneyDTOList = new ArrayList<>();
		inCompleteJourneyDTO = new InCompleteJourneyDTO();
		incompleteJourneyNotificationDTO = new IncompleteJourneyNotificationDTO();
		incompleteJourneyNotificationDTO.setSsrAutoFillNotificationStatus(NotificationStatus.AUTOFILL_NOT_NOTIFIED);
		inCompleteJourneyDTO.setJourneyNotificationDTO(incompleteJourneyNotificationDTO);
		incompleteJourneyMonthDTO.getIncompleteJourneyList().add(inCompleteJourneyDTO);
		incompleteJourneyMonthDTO.setJourneyMonth(MonthEnum.JANUARY);
		incompleteJourneyMonthDTOList.add(incompleteJourneyMonthDTO);
		
		assertTrue(selfServeRefundIncompleteJourneysMonthFilterServiceImpl.filterNotificationsForStatusInAMonth(incompleteJourneyMonthDTOList).size() == 1);
	}

}
