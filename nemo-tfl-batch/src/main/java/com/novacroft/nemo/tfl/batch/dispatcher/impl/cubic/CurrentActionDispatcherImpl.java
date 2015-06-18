package com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.dispatcher.impl.BaseImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;
import com.novacroft.nemo.tfl.batch.record_filter.impl.ImportRecordFilterActionMapImpl;

/**
 * Dispatcher for Current Action List file records
 */
@Component("currentActionDispatcher")
public class CurrentActionDispatcherImpl extends BaseImportRecordDispatcher implements ImportRecordDispatcher {
    protected static final Logger logger = LoggerFactory.getLogger(CurrentActionDispatcherImpl.class);

    @Autowired
    protected ImportRecordFilter adHocLoadReadyForCollectionFilter;
    @Autowired
    protected ImportRecordAction adHocLoadReadyForCollectionAction;
    @Autowired
    protected ImportRecordFilter adHocLoadReadyForCollectionNotificationFilter;
    @Autowired
    protected ImportRecordAction adHocLoadReadyForCollectionNotificationAction;

    @PostConstruct
    @Override
    public void initialiseActions() {
        this.actions = new ArrayList<ImportRecordFilterActionMap>();
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadReadyForCollectionFilter,
                this.adHocLoadReadyForCollectionAction));
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadReadyForCollectionNotificationFilter,
                        this.adHocLoadReadyForCollectionNotificationAction));
    }
}
