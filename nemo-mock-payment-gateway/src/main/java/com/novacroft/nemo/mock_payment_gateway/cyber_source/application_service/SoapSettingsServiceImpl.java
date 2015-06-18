package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.SoapSettingsCmd;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.SoapSettings;
import org.springframework.stereotype.Service;

/**
 * CyberSource SOAP settings service
 */
@Service("soapSettingsService")
public class SoapSettingsServiceImpl implements SoapSettingsService {

    @Override
    public SoapSettingsCmd getSettings() {
        SoapSettings settings = SoapSettings.getInstance();
        return new SoapSettingsCmd(settings.getDecision(), settings.getReasonCode(), settings.getMissingFields(),
                settings.getInvalidFields());
    }

    @Override
    public void updateSettings(SoapSettingsCmd cmd) {
        SoapSettings settings = SoapSettings.getInstance();
        settings.setDecision(cmd.getDecision());
        settings.setReasonCode(cmd.getReasonCode());
        settings.setInvalidFields(cmd.getInvalidFieldsAsList());
        settings.setMissingFields(cmd.getMissingFieldsAsList());
    }
}
