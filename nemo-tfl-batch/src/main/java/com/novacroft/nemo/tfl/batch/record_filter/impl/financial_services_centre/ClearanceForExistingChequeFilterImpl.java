package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeSettledRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters cheque settlement (clearance) records for cheques that exist
 */
@Component("clearanceForExistingChequeFilter")
public class ClearanceForExistingChequeFilterImpl implements ImportRecordFilter {

    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    @Override
    public Boolean matches(ImportRecord record) {
        ChequeSettledRecord chequeSettledRecord = (ChequeSettledRecord) record;
        return this.chequeSettlementDataService.findByChequeSerialNumber(chequeSettledRecord.getChequeSerialNumber()) != null;
    }
}
