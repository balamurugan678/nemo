package com.novacroft.nemo.tfl.batch.action.impl.product_fare_loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.application_service.product_fare_loader.PrePaidTicketUploadService;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;

@Component("prePaidTicketUpdateAction")
public class PrePaidTicketUpdateActionImpl implements ImportRecordAction {

	@Autowired
	protected PrePaidTicketUploadService  prePaidTicketUploadService;
	
	@Override
	public void handle(ImportRecord record) {
		PrePaidTicketRecord prePaidTicketRecord = (PrePaidTicketRecord)record;
		prePaidTicketUploadService.updatePrePaidDataForRecord(prePaidTicketRecord);
		
	}

}
