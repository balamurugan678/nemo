package com.novacroft.nemo.mock_cubic.support;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import org.exolab.castor.mapping.GeneralizedFieldHandler;

/**
 * Handler for integer elements that generate an empty tag on for a null value
 */
public class EmptyTagIntegerHandler extends GeneralizedFieldHandler {
    public EmptyTagIntegerHandler() {
        super();
    }

    @Override
    public Object convertUponGet(Object value) {
        return value != null ? value : StringUtil.EMPTY_STRING;
    }

    @Override
    public Object convertUponSet(Object value) {
        throw new UnsupportedOperationException(String.format(PrivateError.UNSUPPORTED_OPERATION.message(), "convertUponSet"));
    }

    @Override
    public Class getFieldType() {
        return Integer.class;
    }

}
