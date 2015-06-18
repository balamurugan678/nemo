package com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic;

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
 * Dispatcher for Ad-Hoc Distribution file records
 */
@Component("adHocDistributionDispatcher")
public class AdHocDistributionDispatcherImpl extends BaseImportRecordDispatcher implements ImportRecordDispatcher {
    @Autowired
    protected ImportRecordFilter adHocLoadPickUpWindowExpiredFilter;
    @Autowired
    protected ImportRecordAction adHocLoadPickUpWindowExpiredAction;
    @Autowired
    protected ImportRecordFilter adHocLoadErrorFilter;
    @Autowired
    protected ImportRecordAction adHocLoadErrorAction;
    @Autowired
    protected ImportRecordFilter adHocLoadPickedUpFilter;
    @Autowired
    protected ImportRecordAction adHocLoadPickedUpAction;
    @Autowired
    protected ImportRecordFilter adHocLoadPickUpWindowExpiredNotificationFilter;
    @Autowired
    protected ImportRecordAction adHocLoadPickUpWindowExpiredNotificationAction;
    @Autowired
    protected ImportRecordFilter adHocLoadErrorNotificationFilter;
    @Autowired
    protected ImportRecordAction adHocLoadErrorNotificationAction;
    @Autowired
    protected ImportRecordFilter adHocLoadPickedUpNotificationFilter;
    @Autowired
    protected ImportRecordAction adHocLoadPickedUpNotificationAction;

    @PostConstruct
    @Override
    public void initialiseActions() {
        this.actions = new ArrayList<ImportRecordFilterActionMap>();
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadPickUpWindowExpiredFilter,
                this.adHocLoadPickUpWindowExpiredAction));
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadErrorFilter, this.adHocLoadErrorAction));
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadPickedUpFilter, this.adHocLoadPickedUpAction));

        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadPickUpWindowExpiredNotificationFilter,
                        this.adHocLoadPickUpWindowExpiredNotificationAction));
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadErrorNotificationFilter, this.adHocLoadErrorNotificationAction));
        this.actions.add(new ImportRecordFilterActionMapImpl(this.adHocLoadPickedUpNotificationFilter, this.adHocLoadPickedUpNotificationAction));
    }
}
