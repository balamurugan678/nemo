package com.novacroft.nemo.tfl.batch.application_service.financial_services_centre;

import java.util.Date;

/**
 * Service to retrieve fields from cheque produced records
 */
public interface ChequeProducedRecordService {
    String getReferenceNumber(String[] record);

    Long getReferenceNumberAsLong(String[] record);

    String getAmount(String[] record);

    Float getAmountAsFloat(String[] record);

    String getCustomerName(String[] record);

    String getChequeSerialNumber(String[] record);

    Long getChequeSerialNumberAsLong(String[] record);

    String getPrintedOn(String[] record);

    Date getPrintedOnAsDate(String[] record);
}
