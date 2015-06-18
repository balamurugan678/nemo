package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.ImportRecordWithReferenceNumber;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters cheque request records for orders that exist
 */
@Component("chequeForExistingOrderFilter")
public class ChequeForExistingOrderFilterImpl implements ImportRecordFilter {

    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    @Override
    public Boolean matches(ImportRecord record) {
        ImportRecordWithReferenceNumber importRecordWithReferenceNumber = (ImportRecordWithReferenceNumber) record;
        return this.chequeSettlementDataService.findBySettlementNumber(importRecordWithReferenceNumber.getReferenceNumber()) != null;
                
    }
}
