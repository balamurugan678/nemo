package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.OutdatedChequeRecordService;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.OutdatedChequeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Outdated cheque converter: string array record to bean
 */
@Component("outdatedChequeConverter")
public class OutdatedChequeConverterImpl extends BaseConverter implements ImportRecordConverter {

    @Autowired
    protected OutdatedChequeRecordService outdatedChequeRecordService;

    @Override
    public ImportRecord convert(String[] record) {
        return new OutdatedChequeRecord(this.outdatedChequeRecordService.getReferenceNumberAsLong(record),
                this.outdatedChequeRecordService.getAmountAsFloat(record),
                this.outdatedChequeRecordService.getCustomerName(record),
                this.outdatedChequeRecordService.getChequeSerialNumberAsLong(record),
                this.outdatedChequeRecordService.getOutdatedOnAsDate(record));
    }
}
