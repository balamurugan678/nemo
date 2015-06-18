package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsFailureRecordService;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentFailedRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bacsFailedRecordConverter")
public class BacsFailedPaymentConverterImpl extends BaseConverter implements ImportRecordConverter {

    @Autowired
    protected BacsFailureRecordService bacsFailureRecordService;

    @Override
    public ImportRecord convert(String[] record) {
        return new BacsPaymentFailedRecord(bacsFailureRecordService.getAmountAsFloat(record),
                this.bacsFailureRecordService.getBacsRejectCode(record),
                this.bacsFailureRecordService.getFinancialServicesReferenceNumberAsLong(record),
                this.bacsFailureRecordService.getPaymentFailureDateAsDate(record));
    }

}
