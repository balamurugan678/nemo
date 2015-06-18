package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.CARD_ALREADY_ASSOCIATED_ERROR;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.CARD_CANNOT_BE_EMPTY_ERROR;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_NUMBER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.common.application_service.UserService;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicCardService;
import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

@Component
public class AttachCardValidator implements Validator {
    
	@Autowired
	protected CubicCardService cubicService;
	
	@Autowired
	protected CustomerDataService customerDetailsService;
	
	@Autowired
	protected CardDataService cardOysterOnlineService;
	
    @Autowired
    protected PersonalDetailsService personalDetailsService;
    
    @Autowired
    protected UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerSearchCmdImpl.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AddUnattachedCardCmdImpl model = (AddUnattachedCardCmdImpl) target;
		
		if (model.getCardNumber() == null){
			errors.rejectValue(FIELD_CARD_NUMBER, CARD_CANNOT_BE_EMPTY_ERROR.errorCode());
		}else{

			checkCardNotAlreadyAssociated(errors, model);

		}
	}

    private void checkCardNotAlreadyAssociated(Errors errors, AddUnattachedCardCmdImpl model) {
        CardDTO cardDataOysterOnline = cardOysterOnlineService.findByCardNumber(model.getCardNumber());
        if(cardDataOysterOnline != null && cardDataOysterOnline.getWebaccountId() != null){
        	errors.rejectValue(FIELD_CARD_NUMBER, CARD_ALREADY_ASSOCIATED_ERROR.errorCode());
        }
    }
}
