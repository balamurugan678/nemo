package com.novacroft.nemo.tfl.common.support.castor;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPenceToPounds;
import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsToPence;

/**
 * Castor field handler for amounts stored as pence
 */
public class PenceFieldHandler extends GeneralizedFieldHandler {
    public PenceFieldHandler() {
        super();
    }

    @Override
    public Object convertUponGet(Object value) {
        return convertPenceToPounds((Integer) value);
    }

    @Override
    public Object convertUponSet(Object value) {
        return convertPoundsToPence((Float) value);
    }

    @Override
    public Class getFieldType() {
        return Integer.class;
    }
}
