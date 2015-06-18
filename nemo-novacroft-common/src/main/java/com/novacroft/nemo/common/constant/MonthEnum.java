package com.novacroft.nemo.common.constant;

import java.util.Calendar;
import java.util.Date;

public enum MonthEnum {
    JANUARY(0),
    FEBRUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11);
    private int index;

    private MonthEnum(int indexVal) {
        index = indexVal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static MonthEnum getMonthForDate(Date date) {
        final Calendar linkedDayKey = Calendar.getInstance();
        linkedDayKey.setTime(date);
        return determineMonthWithIndex(linkedDayKey.get(Calendar.MONTH));
    }

    public static MonthEnum determineMonthWithIndex(int monthIndex) {
        for (MonthEnum m : MonthEnum.values()) {
            if (m.getIndex() == monthIndex) {
                return m;
            }
        }
        throw new IllegalArgumentException("Invalid month index " + monthIndex);
    }

}