package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;

/**
 * TfL login command class implementation
 */
public class LoginCmdImpl extends CommonLoginCmd {
    
    protected boolean accountDeactivatedFlag=false;

    public boolean getAccountDeactivatedFlag() {
        return accountDeactivatedFlag;
    }

    public void setAccountDeactivatedFlag(boolean accountDeactivatedFlag) {
        this.accountDeactivatedFlag = accountDeactivatedFlag;
    }

}