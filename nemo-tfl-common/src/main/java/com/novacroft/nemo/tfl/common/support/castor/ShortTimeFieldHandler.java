package com.novacroft.nemo.tfl.common.support.castor;

import com.novacroft.nemo.tfl.common.constant.PrivateError;
import org.exolab.castor.mapping.GeneralizedFieldHandler;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.formatTimeToMinute;

/**
 * Castor field handler for times using time to minute format
 */
public class ShortTimeFieldHandler extends GeneralizedFieldHandler {
    public ShortTimeFieldHandler() {
        super();
    }

    @Override
    public Object convertUponGet(Object value) {
        return formatTimeToMinute((Date) value);
    }

    @Override
    public Object convertUponSet(Object value) {
        throw new UnsupportedOperationException(String.format(PrivateError.UNSUPPORTED_OPERATION.message(), "convertUponSet"));
    }

    @Override
    public Class getFieldType() {
        return Date.class;
    }
}
