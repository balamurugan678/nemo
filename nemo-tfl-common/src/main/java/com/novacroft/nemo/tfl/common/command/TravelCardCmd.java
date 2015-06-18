package com.novacroft.nemo.tfl.common.command;




/**
 * TravelCard command interface TfL definition
 */
public interface TravelCardCmd extends BusPassCmd {
    String getEndDate();
	String getTravelCardType();
	Integer getStartZone();
	void setStartZone(Integer startZone);
	Integer getEndZone();
	void setEndZone(Integer endZone);
	String getProductName();
	void setProductName(String productName);
}
