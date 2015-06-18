package com.novacroft.nemo.mock_cubic.command;


public class StationCmd {
	protected String prestigeId;
	protected Long stationId;
    protected String action;
    
	public String getPrestigeId() {
		return prestigeId;
	}
	public void setPrestigeId(String prestigeId) {
		this.prestigeId = prestigeId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
    
    
    
}
