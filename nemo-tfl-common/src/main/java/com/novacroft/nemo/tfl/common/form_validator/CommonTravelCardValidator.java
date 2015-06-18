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
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_DURATION;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@Component("commonTravelCardValidator")
public class CommonTravelCardValidator extends BaseValidator {

    @Autowired
    protected SystemParameterService systemParameterService;

    protected static final String[] MANDATORY_FIELDS = { FIELD_DURATION, FIELD_START_DATE };

    @Override
    public boolean supports(Class<?> targetClass) {
        return ProductItemDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductItemDTO productItemDTO = (ProductItemDTO) target;
        validateMandatoryFields(errors, MANDATORY_FIELDS);
        validateIfNotShortDate(errors, FIELD_START_DATE);
        validateOddPeriodTravelCard(errors, productItemDTO);
    }

    public void validateMandatoryFields(Errors errors, String[] fields) {
        assert (fields != null && fields.length > 0);
        for (String field : fields) {
            rejectIfMandatoryFieldEmpty(errors, field);
        }
    }

    public void validateIfNotShortDate(Errors errors, String field) {
        if (!errors.hasFieldErrors(field)) {
            rejectIfNotShortDate(errors, field);
        }
    }

    protected void validateStartDateAfterEndDate(Errors errors, String startDate, String endDate) {
        if (isAfter(startDate, endDate)) {
            errors.rejectValue(FIELD_END_DATE, GREATER_THAN_START_DATE.errorCode());
        }
    }

    protected void validateOddPeriodTravelCardDurations(Errors errors, String startDate, String endDate) {
        if (!errors.hasFieldErrors(FIELD_END_DATE)) {
            checkTravelCardDurationBetweenOneMonthOneDayAndOneYear(errors, getDateDiffIncludingStartDate(startDate, endDate),
                            getDateDiffInMonths(startDate, endDate), getDaysDiffExcludingMonths(startDate, endDate));
        }
    }

    private void checkTravelCardDurationBetweenOneMonthOneDayAndOneYear(Errors errors, long diffDays, int diffMonths, int diffDaysExcludingMonths) {
        if (diffDays > systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)) {
            errors.rejectValue(FIELD_END_DATE, GREATER_THAN_ANNUAL_PERIOD.errorCode());
        } else if (isTicketShorterThanOneMonthOneDay(diffMonths, diffDaysExcludingMonths)) {
            errors.rejectValue(FIELD_END_DATE, LESS_THAN_OTHER_TRAVEL_CARD_MINIMUM_ALLOWED_MONTHS.errorCode());
        }
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

    private void validateOddPeriodTravelCard(Errors errors, ProductItemDTO productItemDTO) {
        if (!errors.hasFieldErrors(FIELD_DURATION) && productItemDTO.getDuration().equalsIgnoreCase(CartAttribute.OTHER)) {
            validateEndDate(errors);
            if (!errors.hasFieldErrors(FIELD_START_DATE) && !errors.hasFieldErrors(FIELD_END_DATE)) {
                String startDate = DateUtil.formatDate(productItemDTO.getStartDate());
                String endDate = DateUtil.formatDate(productItemDTO.getEndDate());
                validateStartDateAfterEndDate(errors, startDate, endDate);
                validateOddPeriodTravelCardDurations(errors, startDate, endDate);
            }
        }
    }

    protected void validateEndDate(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_END_DATE);
        validateIfNotShortDate(errors, FIELD_END_DATE);
    }

    protected void validateInvalidDate(Errors errors, String field, String date) {
        if (!errors.hasFieldErrors(field)) {
            rejectIfInvalidDate(errors, field, date);
        }
    }
}
