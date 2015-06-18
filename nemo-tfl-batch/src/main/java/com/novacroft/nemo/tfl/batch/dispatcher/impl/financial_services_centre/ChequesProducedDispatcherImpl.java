package com.novacroft.nemo.tfl.batch.dispatcher.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.dispatcher.impl.BaseImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;
import com.novacroft.nemo.tfl.batch.record_filter.impl.ImportRecordFilterActionMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Dispatcher for Financial Services Centre (FSC) cheques produced import records
 */
@Component("chequesProducedDispatcher")
public class ChequesProducedDispatcherImpl extends BaseImportRecordDispatcher implements ImportRecordDispatcher {
    @Autowired
    protected ImportRecordFilter chequeForExistingOrderFilter;
    @Autowired
    protected ImportRecordAction reconcileChequeProducedAction;

    @PostConstruct
    @Override
    public void initialiseActions() {
        this.actions = new ArrayList<ImportRecordFilterActionMap>();
        this.actions.add(new ImportRecordFilterActionMapImpl(this.chequeForExistingOrderFilter,
                this.reconcileChequeProducedAction));
    }
}
