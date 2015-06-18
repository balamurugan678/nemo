package com.novacroft.nemo.tfl.batch.application_service.financial_services_centre;

import java.util.Date;

/**
 * Service to retrieve fields from outdated cheque records
 */
public interface OutdatedChequeRecordService {
    String getReferenceNumber(String[] record);

    Long getReferenceNumberAsLong(String[] record);

    String getAmount(String[] record);

    Float getAmountAsFloat(String[] record);

    String getCustomerName(String[] record);

    String getChequeSerialNumber(String[] record);

    Long getChequeSerialNumberAsLong(String[] record);

    String getOutdatedOn(String[] record);

    Date getOutdatedOnAsDate(String[] record);
}
