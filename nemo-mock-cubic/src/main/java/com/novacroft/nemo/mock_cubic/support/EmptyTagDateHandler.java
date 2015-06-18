package com.novacroft.nemo.mock_cubic.support;

import com.novacroft.nemo.mock_cubic.constant.Constant;

/**
 * Handler for date elements that generate an empty tag on for a null value
 */
public class EmptyTagDateHandler extends BaseEmptyTagDateHandler {
    public EmptyTagDateHandler() {
        super();
    }

    @Override
    protected String getFormat() {
        return Constant.CUBIC_DATE_PATTERN;
    }
}
