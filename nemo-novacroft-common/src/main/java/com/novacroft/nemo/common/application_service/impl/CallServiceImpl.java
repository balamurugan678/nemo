package com.novacroft.nemo.common.application_service.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.CallService;
import com.novacroft.nemo.common.command.impl.CallCmd;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.CallDataService;
import com.novacroft.nemo.common.data_service.CallTypeDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CallDTO;
import com.novacroft.nemo.common.transfer.CallTypeDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

/**
 * Service class to handle call information.  
 */

@Service("callService")
public class CallServiceImpl implements CallService {

    @Autowired
    CallDataService callDataService;

    @Autowired
    CallTypeDataService callTypeDataService;

    @Autowired
    AddressDataService addressDataService;

    @Override
    public CallCmd getCall(Long id) {
        CallCmd cmd = new CallCmd();
        try {
            CallDTO call = callDataService.findById(id);
            convert(call, cmd);
            AddressDTO address = addressDataService.findById(call.getAddressId());
            convert(address, cmd);
            // id has been overwritten by AddressDTO
            cmd.setId(call.getId());
        } catch (NullPointerException ne) {
            //do nothing
        }
        return cmd;
    }

    @Override
    public List<CallTypeDTO> getCallTypes() {
        return callTypeDataService.findAll();
    }

    public SelectListDTO getCallTypeSelectList() {
        List<CallTypeDTO> callDTOs = this.callTypeDataService.findAll();
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("CallType");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (CallTypeDTO callTypeDTO : callDTOs) {
            selectListDTO.getOptions()
                    .add(new SelectListOptionDTO(callTypeDTO.getId().toString(), callTypeDTO.getDescription()));
        }
        return selectListDTO;
    }

    @Override
    public CallCmd updateCall(CallCmd cmd) {
        // save the address before saving the call
        if (cmd.getAddressId() == null) {
            AddressDTO address = new AddressDTO();
            convert(cmd, address);
            address = this.addressDataService.createOrUpdate(address);
            cmd.setAddressId(address.getId());
        }
        CallDTO callDTO = new CallDTO();
        convert(cmd, callDTO);
        callDTO = callDataService.createOrUpdate(callDTO);
        return cmd;
    }

}
