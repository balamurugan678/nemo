package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.CompletedJourneyAuditReferencesService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.CompletedJourneyAuditService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.JourneyCompletedRefundItemDataService;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

@Service
@Transactional
public class CompletedJourneyAuditServiceImpl implements CompletedJourneyAuditService {

	@Autowired 
	protected CardDataService cardDataService; 
	
	@Autowired
	protected JourneyCompletedRefundItemDataService journeyCompletedRefundItemDataService;
	
	@Autowired
	protected CompletedJourneyAuditReferencesService completedJourneyAuditReferencesService;

    @Override
	public List<JourneyCompletedRefundItemDTO> getAuditLog(Long cardId){
    	final List<JourneyCompletedRefundItemDTO> refundItemList =  journeyCompletedRefundItemDataService.findByCardId(cardId);
    	completedJourneyAuditReferencesService.resovleReferences(refundItemList);
    	return refundItemList;
    }

}
