package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeSettlementRecordService;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeSettledRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Cheque settlement converter: string array record to bean
 */
@Component("chequeSettlementRecordConverter")
public class ChequeSettlementConverterImpl extends BaseConverter implements ImportRecordConverter {

    @Autowired
    protected ChequeSettlementRecordService chequeSettlementRecordService;

    @Override
    public ImportRecord convert(String[] record) {
        return new ChequeSettledRecord(this.chequeSettlementRecordService.getChequeSerialNumberAsLong(record),
                this.chequeSettlementRecordService.getPaymentReferenceNumberAsLong(record),
                this.chequeSettlementRecordService.getCustomerName(record),
                this.chequeSettlementRecordService.getClearedOnAsDate(record),
                this.chequeSettlementRecordService.getCurrency(record),
                this.chequeSettlementRecordService.getAmountAsFloat(record));
    }
}
