package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.common.utils.DateUtil.formatDateTime;
import static com.novacroft.nemo.common.utils.DateUtil.formatTimeToMinute;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.novacroft.nemo.tfl.common.constant.JourneyNature;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

/**
 * Journey utilities
 */
public final class JourneyUtil {
    protected static final String SPACE = " ";
    protected static final String HYPHEN = "-";
    protected static final String PERIOD = ".";
    public static final String FILE_NAME_DATE_FORMAT = "yyyy_MM_dd-HH_mm_ss";
    public static final int JOURNEY_DAY_CUT_OFF_HOUR = 4;
    public static final int JOURNEY_DAY_CUT_OFF_MINUTE = 30;
    public static final String NO_TOUCH_IN = "[No touch-in] to ";
    public static final String NO_TOUCH_OUT = "[No touch-out] from ";

    private JourneyUtil() {
    }

    public static String createJourneyDescription(String startLocationName, String endLocationName, String separator, String busRouteId,
                    String routePrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isNotBlank(startLocationName) || isNotBlank(endLocationName)) {
            stringBuilder.append(startLocationName);
            if (isNotBlank(startLocationName) && isNotBlank(endLocationName)) {
                stringBuilder.append(SPACE);
                stringBuilder.append(separator);
                stringBuilder.append(SPACE);
            }
            stringBuilder.append(endLocationName);
        } else {
            stringBuilder.append(routePrefix);
            stringBuilder.append(SPACE);
            stringBuilder.append(busRouteId);
        }
        return stringBuilder.toString();
    }

    public static String createJourneyDescription(String startLocationName, String separator, String prefix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        if (isNotBlank(startLocationName)) {
            stringBuilder.append(SPACE);
            stringBuilder.append(separator);
            stringBuilder.append(SPACE);
        }
        stringBuilder.append(startLocationName);
        return stringBuilder.toString();
    }

    public static String createTimeDescription(Date startAt, Date endAt, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatTimeToMinute(startAt));
        if ((startAt != null) && (endAt != null)) {
            stringBuilder.append(SPACE);
            stringBuilder.append(separator);
            stringBuilder.append(SPACE);
        }
        stringBuilder.append(formatTimeToMinute(endAt));
        return stringBuilder.toString();
    }

    public static String createDatedFileName(String prefix, String suffix) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isNotBlank(prefix)) {
            stringBuilder.append(prefix);
            stringBuilder.append(HYPHEN);
        }
        stringBuilder.append(formatDateTime(new Date(), FILE_NAME_DATE_FORMAT));
        if (isNotBlank(suffix)) {
            stringBuilder.append(PERIOD);
            stringBuilder.append(suffix);
        }
        return stringBuilder.toString();
    }

    public static boolean isAutoTopUpJourney(Integer pseudoTransactionTypeId) {
        return JourneyNature.getNatures(pseudoTransactionTypeId).contains(JourneyNature.TOP_UP);
    }

    public static boolean isWarningJourney(Integer pseudoTransactionTypeId) {
        return JourneyNature.getNatures(pseudoTransactionTypeId).contains(JourneyNature.WARNING);
    }

    public static boolean isDailyCapped(int dailyCappingFlag) {
        return JourneyNature.FARE_CAPPED.identifiers().contains(dailyCappingFlag);
    }

    public static boolean isAutoCompleted(int autoCompletionFlag) {
        return JourneyNature.AUTO_COMPLETED.identifiers().contains(autoCompletionFlag);
    }

    public static boolean isPayAsYouGoUsed(int payAsYouGoUsedFlag) {
        return JourneyNature.PAY_AS_YOU_GO_USED.identifiers().contains(payAsYouGoUsedFlag);
    }

    public static boolean isTfrDiscountsApplied(int tfrDiscountsAppliedFlag) {
        return JourneyNature.TFR_DISCOUNTS_APPLIED.identifiers().contains(tfrDiscountsAppliedFlag);
    }

    public static boolean isTravelCardUsed(int travelCardUsedFlag) {
        return JourneyNature.TRAVEL_CARD_USED.identifiers().contains(travelCardUsedFlag);
    }

    public static boolean isJourneyStartBeforeFourThirtyInTheMorning(Date startAt) {
        if (startAt == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startAt);
        return calendar.get(Calendar.HOUR_OF_DAY) < JOURNEY_DAY_CUT_OFF_HOUR
                        || (calendar.get(Calendar.HOUR_OF_DAY) == JOURNEY_DAY_CUT_OFF_HOUR && calendar.get(Calendar.MINUTE) < JOURNEY_DAY_CUT_OFF_MINUTE);
    }
    
    public static Boolean isJourneyUnStarted(JourneyDTO journey) {
        Set<JourneyNature> journeyNatures = JourneyNature.getNatures(journey.getPseudoTransactionTypeId());
        if (journeyNatures != null) {
            return journeyNatures.contains(JourneyNature.UNSTARTED);
        }
        return false;
    }
    
    public static Boolean isJourneyUnFinished(JourneyDTO journey) {
        Set<JourneyNature> journeyNatures = JourneyNature.getNatures(journey.getPseudoTransactionTypeId());
        if (journeyNatures != null) {
            return journeyNatures.contains(JourneyNature.UNFINISHED);
        }
        return false;
    }
    
}
