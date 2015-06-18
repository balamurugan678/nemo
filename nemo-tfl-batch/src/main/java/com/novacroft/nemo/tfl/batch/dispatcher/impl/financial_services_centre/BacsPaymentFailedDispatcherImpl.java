package com.novacroft.nemo.tfl.batch.dispatcher.impl.financial_services_centre;

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

/**
 * Dispatcher for Financial Services Centre (FSC) BACS Failed  Payment  import records
 */
@Component("bacsPaymentFailedDispatcher")
public class BacsPaymentFailedDispatcherImpl extends BaseImportRecordDispatcher implements ImportRecordDispatcher {

	@Autowired
	protected ImportRecordFilter bacsFailedPaymentsFilter;
	@Autowired
	protected ImportRecordAction reconcileBacsPaymentFailedAction;
	
	
	@PostConstruct 
	@Override
	public void initialiseActions() {
		 this.actions = new ArrayList<ImportRecordFilterActionMap>();
	     this.actions.add(new ImportRecordFilterActionMapImpl(this.bacsFailedPaymentsFilter,
	                this.reconcileBacsPaymentFailedAction));
	}

}
