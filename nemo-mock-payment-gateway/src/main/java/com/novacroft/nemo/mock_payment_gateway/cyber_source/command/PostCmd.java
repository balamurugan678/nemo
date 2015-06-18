package com.novacroft.nemo.mock_payment_gateway.cyber_source.command;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostProfile;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostSecurity;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostRequestParameterName;

import java.util.HashMap;
import java.util.Map;

/**
 * CyberSource Secure Acceptance standard transaction command (MVC-model)
 */
public class PostCmd {
    private PostProfile postProfile;
    private PostSecurity postSecurity;
    private Map<String, String> requestModel = new HashMap<>();
    private Map<String, String> replyModel = new HashMap<>();

    public PostCmd() {
    }

    public PostProfile getPostProfile() {
        return postProfile;
    }

    public void setPostProfile(PostProfile postProfile) {
        this.postProfile = postProfile;
    }

    public PostSecurity getPostSecurity() {
        return postSecurity;
    }

    public void setPostSecurity(PostSecurity postSecurity) {
        this.postSecurity = postSecurity;
    }

    public Map<String, String> getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(Map<String, String> requestModel) {
        this.requestModel = requestModel;
    }

    public Map<String, String> getReplyModel() {
        return replyModel;
    }

    public void setReplyModel(Map<String, String> replyModel) {
        this.replyModel = replyModel;
    }

    public String getRequestModelAttribute(String attributeName) {
        return this.requestModel.get(attributeName);
    }

    public void setRequestModelAttribute(String attributeName, String attributeValue) {
        this.requestModel.put(attributeName, attributeValue);
    }

    public String getReplyModelAttribute(String attributeName) {
        return this.replyModel.get(attributeName);
    }

    public void setReplyModelAttribute(String attributeName, String attributeValue) {
        this.replyModel.put(attributeName, attributeValue);
    }

    public String getReplyUrl() {
        return getRequestModelAttribute(PostRequestParameterName.OVERRIDE_CUSTOM_RECEIPT_PAGE.code());
    }
}
