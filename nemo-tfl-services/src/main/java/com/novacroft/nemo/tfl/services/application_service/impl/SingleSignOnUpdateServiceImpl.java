package com.novacroft.nemo.tfl.services.application_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.common.data_service.SingleSignOnDataService;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;
import com.novacroft.nemo.tfl.services.application_service.SingleSignOnUpdateService;

@Service("SingleSignOnUpdateService")
public class SingleSignOnUpdateServiceImpl implements SingleSignOnUpdateService {
    private static final String SUCCESS_MESSAGE = "SUCCESS updating ";
    private static final String INVALID_USER_MESSAGE = "Fail to update because user data is invalid.";

    @Autowired
    protected SingleSignOnDataService singleSignOnDataService;

    @Override
    public String updateUser(String jsonResponse) {
        SingleSignOnResponseDTO ssoResponseDTO = createSingleSignOnResponseDTO(jsonResponse);
        if (ssoResponseDTO.getIsValid()) {
            singleSignOnDataService.checkAndUpdateLocalData(ssoResponseDTO);
            return SUCCESS_MESSAGE + ssoResponseDTO.getUser().getUser().getUserAccount().getUserName();
        } else {
            return INVALID_USER_MESSAGE;
        }
    }

    protected SingleSignOnResponseDTO createSingleSignOnResponseDTO(String jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, SingleSignOnResponseDTO.class);
    }

}
