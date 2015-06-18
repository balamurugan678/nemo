package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadPerformedRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.PayForAutoLoadPerformedService;
import com.novacroft.nemo.tfl.common.constant.cubic_import.CubicCurrency;

/**
 * Action to take payment for auto loads performed by CUBIC
 */
@Component("payForAutoLoadPerformedAction")
public class PayForAutoLoadPerformedActionImpl implements ImportRecordAction {
    @Autowired
    protected PayForAutoLoadPerformedService payForAutoLoadPerformedService;

    @Override
    public void handle(ImportRecord record) {
        AutoLoadPerformedRecord autoLoadPerformedRecord = (AutoLoadPerformedRecord) record;
        this.payForAutoLoadPerformedService
                .payForAutoLoadPerformed(autoLoadPerformedRecord.getPrestigeId(), autoLoadPerformedRecord.getPickUpLocation(), autoLoadPerformedRecord.getTopUpAmountAdded(),
                        getIsoCurrencyCode(autoLoadPerformedRecord));
    }

    protected String getIsoCurrencyCode(AutoLoadPerformedRecord autoLoadPerformedRecord) {
        CubicCurrency cubicCurrency = CubicCurrency.lookUpCurrencyByCubicCode(autoLoadPerformedRecord.getCurrency());
        return (cubicCurrency != null) ? cubicCurrency.isoCode() : "";
    }
}
