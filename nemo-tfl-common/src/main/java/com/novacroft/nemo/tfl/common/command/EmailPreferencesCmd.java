package com.novacroft.nemo.tfl.common.command;

/**
 * Email (card) preferences command specification
 */
public interface EmailPreferencesCmd {
    String getEmailFrequency();

    String getAttachmentType();

    Boolean getStatementTermsAccepted();
}
