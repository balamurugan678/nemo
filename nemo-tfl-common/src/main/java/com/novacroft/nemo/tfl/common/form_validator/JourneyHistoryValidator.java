package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.common.utils.DateUtil.isAfterOrEqualExcludingTimestamp;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.DATE_DIFF_MORE_THAN_56;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_EQUAL_CURRENT_DATE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.START_DATE_OLDER_THAN_56;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.JourneyHistoryCmd;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;

/**
 * Journey History validation
 */
@Component("journeyHistoryValidator")
public class JourneyHistoryValidator extends BaseValidator {

	public static final int CUSTOM_PERIOD_OPTION_CODE = 10;
	public static final int LAST_WEEK_OPTION_CODE = 9;
	public static final int LAST_SEVEN_DAYS_OPTION_CODE = 0;
	private static final int MAXIMUM_DATE_DIFFERENCE = 56;

    @Override
    public boolean supports(Class<?> targetClass) {
        return JourneyHistoryCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        JourneyHistoryCmdImpl cmd = (JourneyHistoryCmdImpl) target;
		if (isCustomPeriodOptionSelected(cmd)) {
        	rejectIfMandatoryFieldEmpty(errors, FIELD_START_DATE);
            rejectIfMandatoryFieldEmpty(errors, FIELD_END_DATE);

            if (!errors.hasFieldErrors(FIELD_START_DATE) && !errors.hasFieldErrors(FIELD_END_DATE)) {
	
	            rejectIfNotShortDate(errors, FIELD_START_DATE);
	            rejectIfNotShortDate(errors, FIELD_END_DATE);
	
	            Date startDate = cmd.getStartDate();
                Date endDate = cmd.getEndDate();
	            Date todayDate = parse(formatDate(new Date()));

                if (isAfterOrEqualExcludingTimestamp(cmd.getStartDate(), todayDate) || isAfterOrEqualExcludingTimestamp(cmd.getEndDate(), todayDate)) {
                    errors.reject(GREATER_THAN_EQUAL_CURRENT_DATE.errorCode());
                }
	
                if (DateUtil.getDateDiff(startDate, endDate) > MAXIMUM_DATE_DIFFERENCE) {
                    errors.reject(DATE_DIFF_MORE_THAN_56.errorCode());
                }

	            if (DateUtil.getDateDiff(startDate, todayDate) > MAXIMUM_DATE_DIFFERENCE) {
                    errors.rejectValue(FIELD_START_DATE, START_DATE_OLDER_THAN_56.errorCode());
                }
	
	            if (isAfter(cmd.getStartDate(), cmd.getEndDate())) {
	                errors.rejectValue(FIELD_END_DATE, GREATER_THAN_START_DATE.errorCode());
	            }
	        }
        }
    }

	private boolean isCustomPeriodOptionSelected(JourneyHistoryCmd cmd) {
		return (null != cmd.getWeekNumberFromToday()
				&& cmd.getWeekNumberFromToday() == CUSTOM_PERIOD_OPTION_CODE); 
	}
}
