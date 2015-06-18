package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.SoapSettingsCmd;

/**
 * CyberSource SOAP settings service
 */
public interface SoapSettingsService {
    SoapSettingsCmd getSettings();

    void updateSettings(SoapSettingsCmd cmd);
}
