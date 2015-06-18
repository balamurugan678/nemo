package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TICKET_TYPE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.IS_CARD_WITH_FAILED_AUTO_TOPUP_CASE;



import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.TicketTypeCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.TicketType;

/**
 * Ticket type validation
 */
@Component("ticketTypeValidator")
public class TicketTypeValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return TicketTypeCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	rejectIfMandatoryFieldEmpty(errors, FIELD_TICKET_TYPE);
    	if(!errors.hasErrors()){
    		CartItemCmdImpl cartCmdImpl = (CartItemCmdImpl)target;
    		rejectIfOysterCardWithFailedAutoTopUp(cartCmdImpl, errors);
    	}
    }
    
    public void rejectIfOysterCardWithFailedAutoTopUp(CartItemCmdImpl cartCmdImpl , Errors errors) {
    	if(cartCmdImpl.getOysterCardWithFailedAutoTopUpCaseCheck() && cartCmdImpl.getTicketType().equalsIgnoreCase(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code())){
            errors.rejectValue(FIELD_TICKET_TYPE, IS_CARD_WITH_FAILED_AUTO_TOPUP_CASE.errorCode());
        }
    }
}
