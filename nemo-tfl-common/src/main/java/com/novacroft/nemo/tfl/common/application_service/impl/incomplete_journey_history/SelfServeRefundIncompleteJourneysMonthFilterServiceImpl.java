package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.SelfServeRefundIncompleteJourneysMonthFilterService;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

@Service
public class SelfServeRefundIncompleteJourneysMonthFilterServiceImpl implements		SelfServeRefundIncompleteJourneysMonthFilterService {

	@Override
    public List<IncompleteJourneyMonthDTO> filterNotificationsForStatusInAMonth(List<IncompleteJourneyMonthDTO> journeyMonthsList){
    	if(journeyMonthsList != null){
    		Iterator<IncompleteJourneyMonthDTO> journeyMonthIterator = journeyMonthsList.iterator();
    		for(;journeyMonthIterator.hasNext();){
    			IncompleteJourneyMonthDTO journeyMonthDto = journeyMonthIterator.next();
    			boolean hasSSRSubmissions = hasCompletedOrCommencedSSR(journeyMonthDto.getIncompleteJourneyList());
    			if(hasSSRSubmissions){
    				journeyMonthIterator.remove();
    			} 
    		}
    		
    	}
    	return journeyMonthsList;
    }
    
    protected  boolean hasCompletedOrCommencedSSR(List<InCompleteJourneyDTO>  list){
    	for(InCompleteJourneyDTO curr : list){
    		final NotificationStatus notificationStatus = curr.getJourneyNotificationDTO().getSsrAutoFillNotificationStatus();
    		if(NotificationStatus.AUTOFILL_NOTIFIED_OF_COMPLETION.equals(notificationStatus) || NotificationStatus.AUTOFILL_NOTIFIED_COMMENCED.equals(notificationStatus) ){
    			return true;
    		}
    	}
    	return false;
    }

}
