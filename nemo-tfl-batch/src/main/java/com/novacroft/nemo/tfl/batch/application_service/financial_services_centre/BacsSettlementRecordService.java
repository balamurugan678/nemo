package com.novacroft.nemo.tfl.batch.application_service.financial_services_centre;

import java.util.Date;


public interface BacsSettlementRecordService {

	String getPaymentReferenceNumber(String[] record);

	String getAmount(String[] record);

	String getCustomerName(String[] record);

	String getFinancialServicesReferenceNumber(String[] record);

	String getPaymentDate(String[] record);
	
	Long getPaymentReferenceNumberAsLong(String[] record);
	
	Date getPaymentDateAsDate(String[] record);
	
	Float getAmountAsFloat(final String[] record);
	
	Long getFinancialServicesReferenceNumberAsLong(String[] record);
 
}
