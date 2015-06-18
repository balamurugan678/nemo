package com.novacroft.nemo.tfl.common.transfer;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.constant.MonthEnum;

public class IncompleteJourneyMonthDTO {

	protected MonthEnum journeyMonth;
	protected List<InCompleteJourneyDTO> incompleteJourneyList = new ArrayList<>();
	public MonthEnum getJourneyMonth() {
		return journeyMonth;
	}
	public void setJourneyMonth(MonthEnum journeyMonth) {
		this.journeyMonth = journeyMonth;
	}
	public List<InCompleteJourneyDTO> getIncompleteJourneyList() {
		return incompleteJourneyList;
	}
	public void setIncompleteJourneyList(List<InCompleteJourneyDTO> incompleteJourneyList) {
		this.incompleteJourneyList = incompleteJourneyList;
	}
	
	
}
