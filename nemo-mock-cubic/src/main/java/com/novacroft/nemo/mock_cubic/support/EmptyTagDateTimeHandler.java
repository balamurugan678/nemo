package com.novacroft.nemo.mock_cubic.support;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.mock_cubic.constant.Constant;
import com.novacroft.nemo.tfl.common.constant.PrivateError;

/**
 * Handler for date elements that generate an empty tag on for a null value
 */
public class EmptyTagDateTimeHandler extends BaseEmptyTagDateHandler {
    public EmptyTagDateTimeHandler() {
        super();
    }

    @Override
    protected String getFormat() {
        return Constant.CUBIC_DATE_AND_TIME_PATTERN;
    }
    
    @Override
    public Object convertUponGet(Object value) {
        return value != null ? value : DateUtil.EMPTY_DATE;
    }

    @Override
    public Object convertUponSet(Object value) {
        throw new UnsupportedOperationException(String.format(PrivateError.UNSUPPORTED_OPERATION.message(), "convertUponSet"));
    }

    @Override
    public Class<Date> getFieldType() {
        return Date.class;
    }
}
