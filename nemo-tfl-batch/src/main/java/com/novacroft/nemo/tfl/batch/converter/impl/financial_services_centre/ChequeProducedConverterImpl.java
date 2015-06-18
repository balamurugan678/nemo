package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeProducedRecordService;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeProducedRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Cheque produced converter: string array record to bean
 */
@Component("chequeProducedConverter")
public class ChequeProducedConverterImpl extends BaseConverter implements ImportRecordConverter {

    @Autowired
    protected ChequeProducedRecordService chequeProducedRecordService;

    @Override
    public ImportRecord convert(String[] record) {
        return new ChequeProducedRecord(this.chequeProducedRecordService.getReferenceNumberAsLong(record),
                this.chequeProducedRecordService.getAmountAsFloat(record),
                this.chequeProducedRecordService.getCustomerName(record),
                this.chequeProducedRecordService.getChequeSerialNumberAsLong(record),
                this.chequeProducedRecordService.getPrintedOnAsDate(record));
    }
}
