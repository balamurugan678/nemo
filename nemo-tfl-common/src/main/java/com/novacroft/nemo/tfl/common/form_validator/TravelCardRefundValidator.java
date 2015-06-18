
package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffIncludingStartDate;
import static com.novacroft.nemo.common.utils.DateUtil.getDaysDiffExcludingMonths;
import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_ANNUAL_PERIOD;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.LESS_THAN_OTHER_TRAVEL_CARD_MINIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CART_ITEM_CMD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.Refund.DOT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

/**
 * Travel Card validation for refund functionality
 */
@Component("travelCardRefundValidator")
public class TravelCardRefundValidator extends BaseValidator {

    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartItemCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;

        validateMandatoryFields(errors);
        validateIfNotShortDate(errors, FIELD_START_DATE);
        validateStartDate(cmd, errors);
        validateIfOtherTravelCard(cmd, errors);
    }

    protected void validateMandatoryFields(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, getFieldNameWithCartItemCmd(FIELD_TRAVEL_CARD_TYPE));
        rejectIfMandatoryFieldEmpty(errors, getFieldNameWithCartItemCmd(FIELD_START_DATE));
    }

    protected void validateIfNotShortDate(Errors errors, String field) {
        if (!errors.hasFieldErrors(getFieldNameWithCartItemCmd(field))) {
            rejectIfNotShortDate(errors, getFieldNameWithCartItemCmd(field));
        }
    }

    protected void validateIfOtherTravelCard(CartCmdImpl cmd, Errors errors) {
        if (isOtherTravelCard(cmd, errors)) {
            validateEndDate(cmd, errors);
            validateIfNotShortDate(errors, FIELD_END_DATE);
            if (isStartDateAndEndDateHaveNoFieldErrors(errors)) {
                validateStartDateAfterEndDate(cmd, errors);
                if (!errors.hasFieldErrors(getFieldNameWithCartItemCmd(FIELD_END_DATE))) {
                    validateTravelCardDateRange(cmd, errors);
                }
            }
        }
    }

    protected boolean isOtherTravelCard(CartCmdImpl cmd, Errors errors) {
        return !errors.hasFieldErrors(getFieldNameWithCartItemCmd(FIELD_TRAVEL_CARD_TYPE))
                        && cmd.getCartItemCmd().getTravelCardType().contains(Durations.OTHER.getDurationType());
    }

    protected boolean isStartDateAndEndDateHaveNoFieldErrors(Errors errors) {
        return !errors.hasFieldErrors(getFieldNameWithCartItemCmd(FIELD_START_DATE))
                        && !errors.hasFieldErrors(getFieldNameWithCartItemCmd(FIELD_END_DATE));
    }

    protected void validateStartDate(CartCmdImpl cmd, Errors errors) {
        if (!errors.hasFieldErrors(getFieldNameWithCartItemCmd(FIELD_START_DATE))) {
            rejectIfInvalidDate(errors, getFieldNameWithCartItemCmd(FIELD_START_DATE), cmd.getCartItemCmd().getStartDate());
        }
    }

    protected void validateEndDate(CartCmdImpl cmd, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, getFieldNameWithCartItemCmd(FIELD_END_DATE));

        if (!errors.hasFieldErrors(getFieldNameWithCartItemCmd(FIELD_END_DATE))) {
            rejectIfInvalidDate(errors, getFieldNameWithCartItemCmd(FIELD_END_DATE), cmd.getCartItemCmd().getEndDate());
        }
    }

    protected void validateStartDateAfterEndDate(CartCmdImpl cmd, Errors errors) {
        if (isAfter(cmd.getCartItemCmd().getStartDate(), cmd.getCartItemCmd().getEndDate())) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_END_DATE), GREATER_THAN_START_DATE.errorCode());
        }
    }

    protected void validateTravelCardDateRange(CartCmdImpl cmd, Errors errors) {
        int diffMonths = getDateDiffInMonths(cmd.getCartItemCmd().getStartDate(), cmd.getCartItemCmd().getEndDate());
        int diffDaysExcludingMonths = getDaysDiffExcludingMonths(cmd.getCartItemCmd().getStartDate(), cmd.getCartItemCmd().getEndDate());
        long diffDays = getDateDiffIncludingStartDate(cmd.getCartItemCmd().getStartDate(), cmd.getCartItemCmd().getEndDate());
        // Travel card duration should be between one month one day to one year
        if (diffDays > systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_END_DATE), GREATER_THAN_ANNUAL_PERIOD.errorCode());
        } else if (isTicketShorterThanOneMonthOneDay(diffMonths, diffDaysExcludingMonths)) {
            errors.rejectValue(getFieldNameWithCartItemCmd(FIELD_END_DATE), LESS_THAN_OTHER_TRAVEL_CARD_MINIMUM_ALLOWED_MONTHS.errorCode());
        }
    }

    protected String getFieldNameWithCartItemCmd(String fieldName) {
        return FIELD_CART_ITEM_CMD + DOT + fieldName;
    }

    protected boolean isTicketShorterThanOneMonthOneDay(int diffMonths, int diffDaysExcludingMonths) {
        int minimumAllowedMonths = systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS);
        if (diffMonths < minimumAllowedMonths
                        || (diffMonths == minimumAllowedMonths && diffDaysExcludingMonths < systemParameterService
                                        .getIntegerParameterValue(OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS))) {
            return true;
        }
        return false;
    }

}
