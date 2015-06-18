package com.novacroft.nemo.tfl.batch.application_service.financial_services_centre;

import java.util.Date;

/**
 * Service to retrieve fields from cheque settlement records
 */
public interface ChequeSettlementRecordService {
    String getChequeSerialNumber(String[] record);

    Long getChequeSerialNumberAsLong(String[] record);

    String getPaymentReferenceNumber(String[] record);

    Long getPaymentReferenceNumberAsLong(String[] record);

    String getCustomerName(String[] record);

    String getClearedOn(String[] record);

    Date getClearedOnAsDate(String[] record);

    String getCurrency(String[] record);

    String getAmount(String[] record);

    Float getAmountAsFloat(String[] record);
}
