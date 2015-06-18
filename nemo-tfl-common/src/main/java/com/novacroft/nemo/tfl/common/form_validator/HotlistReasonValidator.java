package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.HOTLISTED_CARD_MULTIPLE_REFUND_RESTRICTION_CODE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.MANDATORY_FIELD_EMPTY;
import static com.novacroft.nemo.tfl.common.constant.HotlistedCardExportStatus.HOTLIST_STATUS_READYTOEXPORT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_HOTLIST_REASON_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_LOST_STOLEN_OPTIONS;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.LostStolenOptionType;

/**
 * hotlist reason validator
 */
@Component("hotlistReasonValidator")
public class HotlistReasonValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return SelectCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_HOTLIST_REASON_ID, MANDATORY_FIELD_EMPTY.errorCode(), new Object[]{new DefaultMessageSourceResolvable("selectHotlistReason.label")});
        if(!errors.hasErrors()){
            LostOrStolenCardCmdImpl cmd = (LostOrStolenCardCmdImpl) target;
            rejectIfProcessingRefundForHotListedCard(errors, cmd, FIELD_LOST_STOLEN_OPTIONS);
        }
    }

	public void rejectIfProcessingRefundForHotListedCard(Errors errors, LostOrStolenCardCmdImpl cmd, String fieldLostStolenOptions) {
		if(LostStolenOptionType.REFUND_CARD.equals(LostStolenOptionType.lookUpOptionType(cmd.getLostStolenOptions())) && null!= cmd.getHotlistStatus()) {
			if(HOTLIST_STATUS_READYTOEXPORT.getCode().equalsIgnoreCase(cmd.getHotlistStatus()) && cmd.getHotlistedCardReasonId()>0)
				errors.rejectValue(fieldLostStolenOptions, HOTLISTED_CARD_MULTIPLE_REFUND_RESTRICTION_CODE.errorCode());
		}
	}
}
