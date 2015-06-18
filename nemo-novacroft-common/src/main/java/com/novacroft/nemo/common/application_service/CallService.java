package com.novacroft.nemo.common.application_service;

import com.novacroft.nemo.common.command.impl.CallCmd;
import com.novacroft.nemo.common.transfer.CallTypeDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;

import java.util.List;

public interface CallService {

    CallCmd getCall(Long id);

    List<CallTypeDTO> getCallTypes();

    SelectListDTO getCallTypeSelectList();

    CallCmd updateCall(CallCmd cmd);

}
