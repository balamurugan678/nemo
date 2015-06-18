package com.novacroft.nemo.mock_cubic.command;

import com.novacroft.nemo.mock_cubic.domain.card.CubicCardResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;

import java.util.List;

/**
 * Command for mock auto load change set up
 */
public class EditAutoLoadChangeCmd {
    protected List<CubicCardResponse> responses;
    protected String prestigeId;
    protected AutoLoadResponse successResponse;
    protected RequestFailure failResponse;

    public List<CubicCardResponse> getResponses() {
        return responses;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public void setResponses(List<CubicCardResponse> responses) {
        this.responses = responses;
    }

    public AutoLoadResponse getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(AutoLoadResponse successResponse) {
        this.successResponse = successResponse;
    }

    public RequestFailure getFailResponse() {
        return failResponse;
    }

    public void setFailResponse(RequestFailure failResponse) {
        this.failResponse = failResponse;
    }
}
