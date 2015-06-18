package com.novacroft.nemo.common.command;

/**
 * New password command class common specification
 */
public interface CommonNewPasswordCmd {
    String getNewPassword();

    String getNewPasswordConfirmation();
}
