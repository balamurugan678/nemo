package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_NULL_POSTCODE_ADDRESS;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_POSTCODE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.command.CommonPostcodeCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

@Component("postcodeLookUpValidator")
public class PostcodeLookUpValidator extends BaseValidator {
	
    @Autowired
    protected PAFService pafService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CommonPostcodeCmd.class.equals(targetClass);
    }

	@Override
	public void validate(Object target, Errors errors) {
		CommonPostcodeCmd cmd = (CommonPostcodeCmd) target;
		SelectListDTO listDTO =  this.pafService.getAddressesForPostcodeSelectList(cmd.getPostcode());
    	List<SelectListOptionDTO> listOptionDTOs = listDTO.getOptions();
    	if(listOptionDTOs.size() == 0) {
    		errors.rejectValue(FIELD_POSTCODE, INVALID_NULL_POSTCODE_ADDRESS.errorCode());
    	}
	}

}
