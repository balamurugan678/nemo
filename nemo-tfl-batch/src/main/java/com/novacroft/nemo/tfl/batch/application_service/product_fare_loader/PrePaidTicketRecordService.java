package com.novacroft.nemo.tfl.batch.application_service.product_fare_loader;

import java.util.Date;

import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;

public interface PrePaidTicketRecordService {


	PrePaidTicketRecord createPrepaidRecord(String[] importRecord);

	String getAdhocCode(String[] importRecord);

	String getPrePaidTicketDescription(String[] importRecord);

	Date getPrePaidTicketEffectiveDate(String[] importRecord);

	String getPrePaidTicketStartZone(String[] importRecord);

	String getPrePaidTicketEndZone(String[] importRecord);

	String getPrePaidTicketFromDurationCode(String[] importRecord);

	String getPrePaidTicketToDurationCode(String[] importRecord);

	String getPrePaidTicketDiscountCode(String[] importRecord);

	String getPassengerTypeCode(String[] importRecord);

	Boolean getPrePaidTicketStatus(String[] importRecord);

	Integer getPrePaidTicketPrice(String[] importRecord);
	
	String getPrePaidTicketType(String[] importRecord);
	

}
