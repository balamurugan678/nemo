package com.novacroft.nemo.mock_cubic.support;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import org.exolab.castor.mapping.GeneralizedFieldHandler;

import java.util.Date;

/**
 * Base handler for date type elements that generate an empty tag on for a null value
 */
public abstract class BaseEmptyTagDateHandler extends GeneralizedFieldHandler {
    public BaseEmptyTagDateHandler() {
        super();
    }

    @Override
    public Object convertUponGet(Object value) {
        return value != null ? DateUtil.formatDate((Date) value, getFormat()) : StringUtil.EMPTY_STRING;
    }

    @Override
    public Object convertUponSet(Object value) {
        throw new UnsupportedOperationException(String.format(PrivateError.UNSUPPORTED_OPERATION.message(), "convertUponSet"));
    }

    @Override
    public Class getFieldType() {
        return Date.class;
    }

    protected abstract String getFormat();
}
