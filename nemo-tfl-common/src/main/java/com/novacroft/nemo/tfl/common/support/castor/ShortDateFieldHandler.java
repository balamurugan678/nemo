package com.novacroft.nemo.tfl.common.support.castor;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;

/**
 * Castor field handler for dates using 'short' format
 */
public class ShortDateFieldHandler extends GeneralizedFieldHandler {
    public ShortDateFieldHandler() {
        super();
    }

    @Override
    public Object convertUponGet(Object value) {
        return formatDate((Date) value);
    }

    @Override
    public Object convertUponSet(Object value) {
        return parse((String) value);
    }

    @Override
    public Class getFieldType() {
        return Date.class;
    }
}
