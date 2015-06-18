package com.novacroft.nemo.tfl.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for zone manipulation.
 */
public final class ZoneUtil {

    public static final String TO = " to ";

    public static final int ZONE_10 = 10;
    public static final int ZONE_11 = 11;
    public static final int ZONE_12 = 12;

    public static final String ZONE_10_PATTERN = "9 \\+ Watford Junction";
    public static final String ZONE_11_PATTERN = "9 \\+ Watford Junction \\+ Broxbourne \\+ Brentwood";
    public static final String ZONE_12_PATTERN = "9 \\+ Watford Junction \\+ Broxbourne \\+ Shenfield";

    public static Integer convertCubicZoneDescriptionToStartZone(String cubicZoneDescription) {
        Pattern pattern = Pattern.compile(ZONE_10_PATTERN + TO);
        Matcher matcher = pattern.matcher(cubicZoneDescription);
        if (matcher.find()) {
            return ZONE_10;
        }

        pattern = Pattern.compile(ZONE_11_PATTERN + TO);
        matcher = pattern.matcher(cubicZoneDescription);
        if (matcher.find()) {
            return ZONE_11;
        }

        pattern = Pattern.compile(ZONE_12_PATTERN + TO);
        matcher = pattern.matcher(cubicZoneDescription);
        if (matcher.find()) {
            return ZONE_12;
        }

        pattern = Pattern.compile("\\d+");
        matcher = pattern.matcher(cubicZoneDescription);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        return null;
    }

    public static Integer convertCubicZoneDescriptionToEndZone(String cubicZoneDescription) {
        Pattern pattern = Pattern.compile(TO + ZONE_10_PATTERN);
        Matcher matcher = pattern.matcher(cubicZoneDescription);
        if (matcher.find()) {
            return ZONE_10;
        }

        pattern = Pattern.compile(TO + ZONE_11_PATTERN);
        matcher = pattern.matcher(cubicZoneDescription);
        if (matcher.find()) {
            return ZONE_11;
        }

        pattern = Pattern.compile(TO + ZONE_12_PATTERN);
        matcher = pattern.matcher(cubicZoneDescription);
        if (matcher.find()) {
            return ZONE_12;
        }

        pattern = Pattern.compile("\\d+");
        matcher = pattern.matcher(cubicZoneDescription);

        if (matcher.find() && matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        return null;
    }
    
    public static boolean isZoneGreaterThanEquals(Integer zone1, Integer zone2){
        return zone1.compareTo(zone2) >= 0;
    }
    
    public static boolean isZoneLessThanEquals(Integer zone1, Integer zone2){
        return zone1.compareTo(zone2) <= 0;
    }
    
    public static boolean isZoneEquals(Integer zone1, Integer zone2){
        return zone1.compareTo(zone2) == 0;
    }

    private ZoneUtil() {
    }
}
