package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInMonths;
import static com.novacroft.nemo.common.utils.DateUtil.*;

import java.util.Date;

import com.novacroft.nemo.common.constant.Durations;

public final class TravelCardDurationUtil {
    private TravelCardDurationUtil() {
    }

    public static String getTravelCardDurationFromStartAndEndDate(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        long dateDiff = DateUtil.getDateDiff(startDate, endDate);
        long daysDiffExcludingMonths = DateUtil.getDaysDiffExcludingMonths(startDate, endDate);
        long dateDiffInMonths = DateUtil.getDateDiffInMonths(startDate, endDate);

        return getTravelCardDurationFromStartAndEndDate(dateDiff, daysDiffExcludingMonths, dateDiffInMonths);
    }

    protected static String getTravelCardDurationFromStartAndEndDate(long dateDiff, long daysDiffExcludingMonths, long dateDiffInMonths) {
        if (dateDiffInMonths == Durations.MONTH.getDurationValue() && daysDiffExcludingMonths == 0) {
            return Durations.MONTH.getDurationType();
        } else if (dateDiffInMonths == Durations.THREE_MONTH.getDurationValue() && daysDiffExcludingMonths == 0) {
            return Durations.THREE_MONTH.getDurationType();
        } else if (dateDiffInMonths == Durations.SIX_MONTH.getDurationValue() && daysDiffExcludingMonths == 0) {
            return Durations.SIX_MONTH.getDurationType();
        } else if (dateDiffInMonths == Durations.ANNUAL.getDurationValue() && daysDiffExcludingMonths == 0) {
            return Durations.ANNUAL.getDurationType();
        } else if (dateDiff == Durations.SEVEN_DAYS.getDurationValue()) {
            return Durations.SEVEN_DAYS.getDurationType();
        } else {
            return Durations.OTHER.getDurationType();
        }
    }

    /**
     * Returns the ending date for given startDate and duration
     */
    public static Date getEndDate(String startDate, String duration) {
        if (startDate == null || duration == null) {
            return null;
        }

        Date endDate = null;
        if (duration.equals(Durations.SEVEN_DAYS.getDurationType())) {
            endDate = DateUtil.addDaysToDate(startDate, Durations.SEVEN_DAYS.getDurationValue());
        } else if (duration.equals(Durations.MONTH.getDurationType())) {
            endDate = DateUtil.addMonthsToDateTime(DateUtil.parse(startDate), Durations.MONTH.getDurationValue());
        } else if (duration.equals(Durations.THREE_MONTH.getDurationType())) {
            endDate = DateUtil.addMonthsToDate(startDate, Durations.THREE_MONTH.getDurationValue());
        } else if (duration.equals(Durations.SIX_MONTH.getDurationType())) {
            endDate = DateUtil.addMonthsToDate(startDate, Durations.SIX_MONTH.getDurationValue());
        } else if (duration.equals(Durations.ANNUAL.getDurationType())) {
            endDate = DateUtil.addMonthsToDate(startDate, Durations.ANNUAL.getDurationValue());
        }
        return endDate;
    }

    public static Date getEndDate(String startDate, String endDate, String duration) {
        Date endDate1 = null;
        if (startDate == null || duration == null || endDate == null) {
            return null;
        }
        if (duration.equals(Durations.OTHER.getDurationType())) {
            endDate1 = DateUtil.parse(endDate);
        } else {
            return getEndDate(startDate, duration);
        }
        return endDate1;
    }

    /**
     * Returns the renew ending date for given travel card startDate and endDate
     */
    public static Date getRenewEndDate(String startDateStr, String endDateStr, Integer productStartAfterDays) {
        Date endDate = null;
        if (startDateStr == null || endDateStr == null) {
            return null;
        }
        String duration = getTravelCardDurationFromStartAndEndDate(startDateStr, endDateStr);
        if (duration.equals(Durations.SEVEN_DAYS.getDurationType())) {
            endDate = DateUtil.addDaysToDate(endDateStr, Durations.SEVEN_DAYS.getDurationValue() + productStartAfterDays);
        } else if (duration.equals(Durations.MONTH.getDurationType())) {
            endDate = DateUtil.addMonthsAndDaysToDate(endDateStr, Durations.MONTH.getDurationValue(), productStartAfterDays);
        } else if (duration.equals(Durations.THREE_MONTH.getDurationType())) {
            endDate = DateUtil.addMonthsAndDaysToDate(endDateStr, Durations.THREE_MONTH.getDurationValue(), productStartAfterDays);
        } else if (duration.equals(Durations.SIX_MONTH.getDurationType())) {
            endDate = DateUtil.addMonthsAndDaysToDate(endDateStr, Durations.SIX_MONTH.getDurationValue(), productStartAfterDays);
        } else if (duration.equals(Durations.ANNUAL.getDurationType())) {
            endDate = DateUtil.addMonthsAndDaysToDate(endDateStr, Durations.ANNUAL.getDurationValue(), productStartAfterDays);
        } else if (duration.equals(Durations.OTHER.getDurationType())) {
            int diffDaysExcludingMonths = getDaysDiffExcludingMonths(startDateStr, endDateStr);
            int diffMonths = getDateDiffInMonths(startDateStr, endDateStr);
            if (diffMonths > 0) {
                endDate = DateUtil.addMonthsFirstAndThenDaysToDate(endDateStr, diffMonths, (diffDaysExcludingMonths + productStartAfterDays));
            } else {
                endDate = DateUtil.addMonthsAndDaysToDate(endDateStr, diffMonths, (diffDaysExcludingMonths + productStartAfterDays - 1));
            }
        }
        return endDate;
    }

}
