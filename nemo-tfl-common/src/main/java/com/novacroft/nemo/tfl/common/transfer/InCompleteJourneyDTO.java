package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDisplayDTO;

public class InCompleteJourneyDTO {

	private String missingStation;

	private boolean submittedByAgent;

	private Long prestigeId;

	private boolean allowSSR;

	public String getMissingStation() {
		return missingStation;
	}

	public void setMissingStation(String missingStation) {
		this.missingStation = missingStation;
	}

	protected IncompleteJourneyNotificationDTO journeyNotificationDTO;

	protected JourneyDisplayDTO journeyDisplayDTO = new JourneyDisplayDTO();

	public IncompleteJourneyNotificationDTO getJourneyNotificationDTO() {
		return journeyNotificationDTO;
	}

	public void setJourneyNotificationDTO(IncompleteJourneyNotificationDTO journeyNotificationDTO) {
		this.journeyNotificationDTO = journeyNotificationDTO;
	}

	public JourneyDisplayDTO getJourneyDisplayDTO() {
		return journeyDisplayDTO;
	}

	public void setJourneyDisplayDTO(JourneyDisplayDTO journeyDisplayDTO) {
		this.journeyDisplayDTO = journeyDisplayDTO;
	}

	public boolean isSubmittedByAgent() {
		return submittedByAgent;
	}

	public void setSubmittedByAgent(boolean submittedByAgent) {
		this.submittedByAgent = submittedByAgent;
	}

	public int getMissingTransactionType() {
		return 0;
	}

	public Long getPrestigeId() {
		return prestigeId;
	}

	public void setPrestigeId(Long prestigeId) {
		this.prestigeId = prestigeId;
	}

	public boolean isAllowSSR() {
		return allowSSR;
	}

	public void setAllowSSR(boolean allowSSR) {
		this.allowSSR = allowSSR;
	}

}
