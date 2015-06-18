package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.LostOrStolenEligibilityService;
import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.LostStolenOptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_LOST_STOLEN_OPTIONS;

@Component("lostOrStolenValidator")
public class LostOrStolenValidator extends BaseValidator {
    @Autowired
    protected HotlistReasonValidator hotlistReasonValidator;
    @Autowired
    protected LostOrStolenEligibilityService lostOrStolenEligibilityService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return LostOrStolenCardCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LostOrStolenCardCmdImpl cmd = (LostOrStolenCardCmdImpl) target;
        validateCardEligibleToReportLostOrStolen(cmd, errors);
        if (errors.hasErrors()) {
            return;
        }
        validateLostStolenOptions(cmd.getLostStolenOptions(), errors);
        hotlistReasonValidator.validate(cmd, errors);
    }

    protected void validateLostStolenOptions(String lostStolenOptions, Errors errors) {
        if (LostStolenOptionType.lookUpOptionType(lostStolenOptions) == null) {
            rejectIfMandatoryFieldEmpty(errors, FIELD_LOST_STOLEN_OPTIONS);
        }
    }

    protected void validateCardEligibleToReportLostOrStolen(LostOrStolenCardCmdImpl cmd, Errors errors) {
        if (!this.lostOrStolenEligibilityService.isCardEligibleToBeReportedLostOrStolen(cmd.getCardId())) {
            errors.reject(ContentCode.CARD_INELIGIBLE_TO_BE_REPORTED_LOST_OR_STOLEN.errorCode());
        }
    }
}
