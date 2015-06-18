package com.novacroft.nemo.mock_cubic.application_service;

import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;

public interface UpdateResponseService {
    String generateAddSuccessResponse(AddRequestCmd cmd);

    String generateRemoveSuccessResponse(RemoveRequestCmd cmd);

    String generateErrorResponse(Integer errorCode, String errorDescription);
}
