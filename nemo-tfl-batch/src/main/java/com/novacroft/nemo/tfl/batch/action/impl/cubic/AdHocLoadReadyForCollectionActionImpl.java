package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.common.application_service.AdHocLoadReadyForCollectionStatusService;

/**
 * Action that is performed when a CUBIC batch import current action file ready for collection record is encountered.
 */
@Component("adHocLoadReadyForCollectionAction")
public class AdHocLoadReadyForCollectionActionImpl implements ImportRecordAction {
    @Autowired
    protected AdHocLoadReadyForCollectionStatusService adHocLoadReadyForCollectionStatusService;

    @Override
    public void handle(ImportRecord record) {
        CurrentActionRecord currentActionRecord = (CurrentActionRecord) record;
        this.adHocLoadReadyForCollectionStatusService
                .updateAdHocLoadSettlementStatus(currentActionRecord.getRequestSequenceNumber(),
                                currentActionRecord.getPrestigeId());
    }
}
