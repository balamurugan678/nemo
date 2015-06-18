package com.novacroft.nemo.tfl.batch.application_service.financial_services_centre;

import java.util.Date;

public interface BacsFailureRecordService {
	
	String getAmount(String[] record);
	
	String getFinancialServicesReferenceNumber(String[] record);

	String getPaymentFailureDate(String[] record);
	
	Date getPaymentFailureDateAsDate(String[] record);
	
	Float getAmountAsFloat(final String[] record);	
	
	Long getFinancialServicesReferenceNumberAsLong(String[] record);
	
	String getBacsRejectCode(String[] record);
	
	
}
