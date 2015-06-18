package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.ResolveJourneyNotificationsReferencesService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.PseudoTransactionTypeLookupDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

@Service
public class ResolveJourneyNotificationsReferencesServiceImpl extends BaseService implements ResolveJourneyNotificationsReferencesService {
	
	public static final String SSR_FAILED = "SSR.failed.description";
	public static final String AUTOFILL_NOTIFIED_COMMENCED_DESCRIPTION = "AutofillNotified.Commenced.description";
	public static final String AUTOFILL_NOTIFIED_OF_COMPLETION_DESCRIPTION = "AutofillNotified.Completion.description";

	public static final String INCOMPLETE_JOURNEY_DESCRIPTION = "IncompleteJourney.description";


	@Autowired
    protected PseudoTransactionTypeLookupDataService pseudoTransactionTypeLookupDataService;
	

    @Autowired
    protected LocationDataService locationDataService;
    
   
	@Override
	public void resolveReferences(List<IncompleteJourneyMonthDTO> jouneyMonthList) {
		 for (IncompleteJourneyMonthDTO journeyMonthDto :  jouneyMonthList) {
			for (InCompleteJourneyDTO inCompleteJourney : journeyMonthDto.getIncompleteJourneyList()) {
				resolveJourneyReferences(inCompleteJourney);
			}
		}
    }

    protected void resolveJourneyReferences(InCompleteJourneyDTO journeyDTO) {
    	resolveLinkedTransactionType(journeyDTO);
    	resolveStation(journeyDTO);
    	resolveStatusMessage(journeyDTO);
    	resolveSSRSuitability(journeyDTO);
    }

    
    protected void resolveStatusMessage(InCompleteJourneyDTO journeyDTO){
    	NotificationStatus notificationStatus =   journeyDTO.getJourneyNotificationDTO().getSsrAutoFillNotificationStatus();
    	String[] messageArgs = { DateUtil.formatDateTime(journeyDTO.getJourneyNotificationDTO().getLinkedTransactionDateTime()),   journeyDTO.getJourneyDisplayDTO().getPseudoTransactionTypeDisplayDescription(), journeyDTO.getJourneyDisplayDTO().getLinkedStation()};
		String journeyDescription = getContent(INCOMPLETE_JOURNEY_DESCRIPTION, messageArgs);
		String statusDerivedDescription = StringUtil.EMPTY_STRING;
    	switch(notificationStatus) {
    	    case SSR_COMMENCED_BUT_FAILED : 
    		case SSR_VOIDED :
    			statusDerivedDescription = getContent(SSR_FAILED);
    			break;
    		case AUTOFILL_NOTIFIED_COMMENCED:
    			statusDerivedDescription = getContent(AUTOFILL_NOTIFIED_COMMENCED_DESCRIPTION);
    			break;
    		case AUTOFILL_NOTIFIED_OF_COMPLETION:
    			statusDerivedDescription = getContent(AUTOFILL_NOTIFIED_OF_COMPLETION_DESCRIPTION);
    		     break;
    		default:
			
    	}
    	journeyDTO.getJourneyDisplayDTO().setJourneyDescription(journeyDescription.concat(statusDerivedDescription));
    }
    
	protected  void resolveLinkedTransactionType(InCompleteJourneyDTO journeyDTO) {
	  PseudoTransactionTypeLookupDTO transactionTypeDTO =
                this.pseudoTransactionTypeLookupDataService.findById((long) journeyDTO.getJourneyNotificationDTO().getLinkedTransactionType());
	  journeyDTO.getJourneyDisplayDTO().setPseudoTransactionTypeDisplayDescription(
                transactionTypeDTO != null ? transactionTypeDTO.getDisplayDescription() : "");
	
	}
	
	
	protected void resolveStation(InCompleteJourneyDTO journeyDTO){
		LocationDTO locationDTO = locationDataService.findById((long)journeyDTO.getJourneyNotificationDTO().getLinkedStationKey());
		journeyDTO.getJourneyDisplayDTO().setLinkedStation(locationDTO.getName());
	}
	
	 protected  void resolveSSRSuitability(InCompleteJourneyDTO  journeyDTO){
	     if(NotificationStatus.AUTOFILL_NOT_NOTIFIED.equals(journeyDTO.getJourneyNotificationDTO().getSsrAutoFillNotificationStatus())){
			 journeyDTO.setAllowSSR(true);
	    } 
	
	 }
}
