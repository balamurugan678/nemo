package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostSettingsCmd;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostSettings;
import org.springframework.stereotype.Service;

/**
 * CyberSource POST settings service
 */
@Service("postSettingsService")
public class PostSettingsServiceImpl implements PostSettingsService {
    @Override
    public PostSettingsCmd getSettings() {
        PostSettings postSettings = PostSettings.getInstance();
        return new PostSettingsCmd(postSettings.getAlive());
    }

    @Override
    public void updateSettings(PostSettingsCmd cmd) {
        PostSettings postSettings = PostSettings.getInstance();
        postSettings.setAlive(cmd.getAlive());
    }
}
