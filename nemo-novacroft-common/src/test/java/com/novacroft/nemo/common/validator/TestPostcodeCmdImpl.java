package com.novacroft.nemo.common.validator;

import com.novacroft.nemo.common.command.CommonPostcodeCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;

/**
 * Postcode implementation for testing postcode validation
 */
public class TestPostcodeCmdImpl extends CommonOrderCardCmd implements CommonPostcodeCmd {
    String postcode;

    public TestPostcodeCmdImpl() {
    }

    public TestPostcodeCmdImpl(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
