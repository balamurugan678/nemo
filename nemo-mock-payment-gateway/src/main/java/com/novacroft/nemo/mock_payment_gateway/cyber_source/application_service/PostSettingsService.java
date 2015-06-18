package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostSettingsCmd;

/**
 * CyberSource POST settings service
 */
public interface PostSettingsService {
    PostSettingsCmd getSettings();

    void updateSettings(PostSettingsCmd cmd);
}
