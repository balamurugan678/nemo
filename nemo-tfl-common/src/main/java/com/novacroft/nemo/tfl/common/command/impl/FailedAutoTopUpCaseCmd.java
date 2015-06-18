package com.novacroft.nemo.tfl.common.command.impl;

public interface FailedAutoTopUpCaseCmd {
    
    Long getFailedAutoTopUpCaseId();
    String getFailedAutoTopUpCaseStatus();
    Boolean getOysterCardWithFailedAutoTopUpCaseCheck();
}
