package com.novacroft.nemo.tfl.common.transfer;

import java.util.List;


/**
 * Transfer class for Incomplete Journeys
 */
public class IncompleteJourneyHistoryDTO  {

	
	protected List<IncompleteJourneyMonthDTO> incompleteJourneyMonthDTO;
	
	protected List<JourneyCompletedRefundItemDTO> JourneyCompletedRefundItems;

	public List<IncompleteJourneyMonthDTO> getIncompleteJourneyMonthDTO() {
		return incompleteJourneyMonthDTO;
	}

	public void setIncompleteJourneyMonthDTO(
			List<IncompleteJourneyMonthDTO> incompleteJourneyMonthDTO) {
		this.incompleteJourneyMonthDTO = incompleteJourneyMonthDTO;
	}

	public List<JourneyCompletedRefundItemDTO> getJourneyCompletedRefundItems() {
		return JourneyCompletedRefundItems;
	}

	public void setJourneyCompletedRefundItems(
			List<JourneyCompletedRefundItemDTO> journeyCompletedRefundItems) {
		JourneyCompletedRefundItems = journeyCompletedRefundItems;
	}
	
	
}
