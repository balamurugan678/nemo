package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertSyntheticTapResponseDTO {
    private Integer dayKey;
    private Date dayKeyAsDate;
    private Integer prestigeId;
    private Integer totalOriginalTravelSpendInPence;
    private List<TapResponseDTO> tapResponseDtoList = new ArrayList<>();
    
	public Integer getDayKey() {
		return dayKey;
	}
	public void setDayKey(Integer dayKey) {
		this.dayKey = dayKey;
	}
	public Date getDayKeyAsDate() {
		return dayKeyAsDate;
	}
	public void setDayKeyAsDate(Date dayKeyAsDate) {
		this.dayKeyAsDate = dayKeyAsDate;
	}
	public Integer getPrestigeId() {
		return prestigeId;
	}
	public void setPrestigeId(Integer prestigeId) {
		this.prestigeId = prestigeId;
	}
	public Integer getTotalOriginalTravelSpendInPence() {
		return totalOriginalTravelSpendInPence;
	}
	public void setTotalOriginalTravelSpendInPence(
			Integer totalOriginalTravelSpendInPence) {
		this.totalOriginalTravelSpendInPence = totalOriginalTravelSpendInPence;
	}
	public List<TapResponseDTO> getTapResponseDtoList() {
		return tapResponseDtoList;
	}
	public void setTapResponseDtoList(List<TapResponseDTO> tapResponseDtoList) {
		this.tapResponseDtoList = tapResponseDtoList;
	} 
    
    
}
