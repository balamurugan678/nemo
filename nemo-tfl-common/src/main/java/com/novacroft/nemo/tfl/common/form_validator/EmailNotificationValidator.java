package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.utils.DateUtil.SEVEN_DAYS;
import static com.novacroft.nemo.common.utils.DateUtil.addDaysToDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.TRAVEL_CARD_EMAIL_NOTIFICATION_IN_PAST;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_EMAIL_REMINDER;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;

@Component("emailNotificationValidator")
public class EmailNotificationValidator extends CommonTravelCardValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return TravelCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TravelCardCmd travelCard = (TravelCardCmd) target;
        validateEmailNotificationForSevenDayTravelCard(errors, travelCard);
    }
    
    protected void validateEmailNotificationForSevenDayTravelCard(Errors errors, TravelCardCmd cmd) {
        if(!errors.hasFieldErrors(FIELD_START_DATE) && Durations.SEVEN_DAYS.getDurationType().equalsIgnoreCase(cmd.getTravelCardType())){
            validateEmailNotificationDateInPast(errors, addDaysToDate(cmd.getStartDate(), SEVEN_DAYS), cmd.getEmailReminder());
        }
    }
    
    protected void validateEmailNotificationDateInPast(Errors errors, Date endDate, String emailReminder){
        Date calculatedEmailNotificationDate = DateUtil.addDaysToDate(endDate, -Integer.parseInt(emailReminder));
        if(DateUtil.isBefore(parse(formatDate(calculatedEmailNotificationDate, DateConstant.SHORT_DATE_PATTERN)), 
                        parse(formatDate(new Date())))){
            errors.rejectValue(FIELD_TRAVEL_CARD_EMAIL_REMINDER, TRAVEL_CARD_EMAIL_NOTIFICATION_IN_PAST.errorCode());
        }
    }
}
