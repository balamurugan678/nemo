package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadChangeRecord;
import com.novacroft.nemo.tfl.common.application_service.ReconcileAutoLoadChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Action to reconcile all auto load change records from CUBIC
 */
@Component("reconcileAutoLoadChangeAction")
public class ReconcileAutoLoadChangeActionImpl implements ImportRecordAction {
    @Autowired
    protected ReconcileAutoLoadChangeService reconcileAutoLoadChangeService;

    @Override
    public void handle(ImportRecord record) {
        AutoLoadChangeRecord autoLoadChangeRecord = (AutoLoadChangeRecord) record;
        this.reconcileAutoLoadChangeService
                .reconcileChange(autoLoadChangeRecord.getRequestSequenceNumber(), autoLoadChangeRecord.getPrestigeId(),
                        autoLoadChangeRecord.getNewAutoLoadConfiguration(), autoLoadChangeRecord.getStatusOfAttemptedAction(),
                        autoLoadChangeRecord.getFailureReasonCode());
    }
}
