package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.*;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DATE_OF_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.*;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;

/**
 * Validator for Cancel and Surrender Cards controller
 */

@Deprecated
@Component("cancelAndSurrenderAjaxValidator")
public class CancelAndSurrenderAjaxValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return CancelAndSurrenderCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CancelAndSurrenderCmd cmd = (CancelAndSurrenderCmd) target;
        DateTime today = new DateTime();
        rejectIfMandatoryFieldEmpty(errors, FIELD_START_DATE);
        
        if (null != cmd.getRefundDate() && cmd.getRefundDate().isAfter(today)){
            errors.rejectValue(FIELD_DATE_OF_REFUND, REFUND_DATE_IS_FUTURE.errorCode());
        }
        
        if (null != cmd.getTicketEndDate() && cmd.getTicketEndDate().isAfter(today)){
            errors.rejectValue(FIELD_END_DATE, TICKET_EXPIRED.errorCode());
        }

    }

}
