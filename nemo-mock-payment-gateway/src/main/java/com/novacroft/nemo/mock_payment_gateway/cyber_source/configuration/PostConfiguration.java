package com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration;

import java.util.List;

/**
 * CyberSource Secure Acceptance configuration bean
 */
public class PostConfiguration {
    protected List<PostProfile> postProfiles;
    protected PostSecurity postSecurity;

    public PostConfiguration() {
    }

    public List<PostProfile> getPostProfiles() {
        return postProfiles;
    }

    public void setPostProfiles(List<PostProfile> postProfiles) {
        this.postProfiles = postProfiles;
    }

    public PostSecurity getPostSecurity() {
        return postSecurity;
    }

    public void setPostSecurity(PostSecurity postSecurity) {
        this.postSecurity = postSecurity;
    }
}
