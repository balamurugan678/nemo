package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsSettlementRecordService;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentSettledRecord;

@Component("bacsSettlementRecordConverter")
public class BacsSettlementConverterImpl extends BaseConverter implements ImportRecordConverter {

	@Autowired
	protected BacsSettlementRecordService bacsSettlementRecordService;
	
    @Override
    public ImportRecord convert(String[] record) {
        return new BacsPaymentSettledRecord(this.bacsSettlementRecordService.getPaymentReferenceNumberAsLong(record), bacsSettlementRecordService.getAmountAsFloat(record),
                this.bacsSettlementRecordService.getCustomerName(record), this.bacsSettlementRecordService.getFinancialServicesReferenceNumberAsLong(record),
                this.bacsSettlementRecordService.getPaymentDateAsDate(record)
                );
    }
}
