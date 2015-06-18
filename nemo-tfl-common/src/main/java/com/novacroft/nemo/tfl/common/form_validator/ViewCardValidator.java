package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * view oyster card validator
 */
@Component("viewCardValidator")
public class ViewCardValidator extends BaseValidator {    
    
    @Autowired
    protected CardDataService cardDataService;
    
    
    @Override
    public boolean supports(Class<?> targetClass) {
        return SelectCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Long cardId = (Long) target;
        CardDTO cardDTO = cardDataService.findById(cardId);
        if (cardDTO == null) {
            errors.rejectValue(null, ContentCode.CUSTOMER_INVALID_OYSTER_CARD.errorCode(), null, null);
        }
    }
}
