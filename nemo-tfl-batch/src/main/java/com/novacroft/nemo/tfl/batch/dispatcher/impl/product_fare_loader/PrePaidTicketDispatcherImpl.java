package com.novacroft.nemo.tfl.batch.dispatcher.impl.product_fare_loader;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.dispatcher.impl.BaseImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;
import com.novacroft.nemo.tfl.batch.record_filter.impl.ImportRecordFilterActionMapImpl;

@Component("prePaidTicketDispatcher")
public class PrePaidTicketDispatcherImpl extends BaseImportRecordDispatcher implements ImportRecordDispatcher  {

	@Autowired
	protected ImportRecordFilter prePaidTicketRecordFilter;
	@Autowired
	protected ImportRecordAction prePaidTicketUpdateAction;
	
	
	@PostConstruct 
	@Override
	public void initialiseActions() {
		 this.actions = new ArrayList<ImportRecordFilterActionMap>();
	     this.actions.add(new ImportRecordFilterActionMapImpl(this.prePaidTicketRecordFilter,
	                this.prePaidTicketUpdateAction));
	}
}
