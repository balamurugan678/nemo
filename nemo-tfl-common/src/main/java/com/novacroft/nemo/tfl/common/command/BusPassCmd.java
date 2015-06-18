package com.novacroft.nemo.tfl.common.command;


/**
 * Bus & tram pass command interface TfL definition
 */
public interface BusPassCmd {
	String getStartDate();
    String getEmailReminder();
	String getPassengerType();
	void setPassengerType(String passengerType);
	String getDiscountType();
	void setDiscountType(String discountType); 
	Long getPrePaidTicketId();
	void setPrePaidTicketId(Long id);
}
