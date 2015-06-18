package com.novacroft.nemo.common.command;

/**
 * Security Question command interface TfL definition
 */
public interface SecurityQuestionCmd {
    Long getCardId();

    String getCardNumber();

    String getSecurityQuestion();

    String getSecurityAnswer();

    String getConfirmSecurityAnswer();
}
