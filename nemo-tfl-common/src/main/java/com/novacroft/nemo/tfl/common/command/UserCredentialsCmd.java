package com.novacroft.nemo.tfl.common.command;

/**
 * User Credentials command interface TfL definition
 */
public interface UserCredentialsCmd extends NewPasswordCmd {
    String getUsername();
}
