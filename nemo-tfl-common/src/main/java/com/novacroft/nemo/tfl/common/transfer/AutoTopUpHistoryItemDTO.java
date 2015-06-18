package com.novacroft.nemo.tfl.common.transfer;


public class AutoTopUpHistoryItemDTO extends ItemDTO {

    private static final long serialVersionUID = 1742621135855345919L;

    protected String autoTopUpActivity;
    protected String stationName;
    protected String activityDateTime;
    protected Integer autoTopUpAmount;
    protected String settlementDateTime;
    protected String settlementStatus;    
    
    public AutoTopUpHistoryItemDTO() {
        
    }

    public AutoTopUpHistoryItemDTO(String autoTopUpActivity, String stationName, String activityDateTime, Integer autoTopUpAmount,
                    String settlementDateTime, String settlementStatus) {
        this.autoTopUpActivity = autoTopUpActivity;
        this.stationName = stationName;
        this.activityDateTime = activityDateTime;
        this.autoTopUpAmount = autoTopUpAmount;
        this.settlementDateTime = settlementDateTime;
        this.settlementStatus = settlementStatus;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }

    public String getActivityDateTime() {
        return activityDateTime;
    }

    public void setActivityDateTime(String activityDateTime) {
        this.activityDateTime = activityDateTime;
    }

    public String getSettlementDateTime() {
        return settlementDateTime;
    }

    public void setSettlementDateTime(String settlementDateTime) {
        this.settlementDateTime = settlementDateTime;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

}
