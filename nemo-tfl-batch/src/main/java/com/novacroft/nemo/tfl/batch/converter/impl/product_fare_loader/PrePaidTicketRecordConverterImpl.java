package com.novacroft.nemo.tfl.batch.converter.impl.product_fare_loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.application_service.product_fare_loader.PrePaidTicketRecordService;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

@Component("prePaidTicketRecordConverter")
public class PrePaidTicketRecordConverterImpl extends BaseConverter implements ImportRecordConverter {

	@Autowired
	protected PrePaidTicketRecordService prePaidTicketRecordService;
	
	@Override
	public ImportRecord convert(String[] record) {
		return prePaidTicketRecordService.createPrepaidRecord(record);
	}

}
