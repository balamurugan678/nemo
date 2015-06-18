package com.novacroft.nemo.tfl.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for ZoneUtil
 */
public class ZoneUtilTest {
    public static final Integer ZONE_1 = 1;
    public static final Integer ZONE_3 = 3;
    public static final Integer ZONE_5 = 5;
    public static final Integer ZONE_10 = 10;
    public static String ZONES = "Zones ";
    public static String TO = " to ";
    public static String ZONE_1_DESCRIPTION = "1";
    public static String ZONE_3_DESCRIPTION = "3";
    public static String ZONE_10_DESCRIPTION = "9 + Watford Junction";
    public static String ZONE_11_DESCRIPTION = "9 + Watford Junction + Broxbourne + Brentwood";
    public static String ZONE_12_DESCRIPTION = "9 + Watford Junction + Broxbourne + Shenfield";

    @Test
    public void shouldReturnZoneOneAsStartZone() {
        assertEquals(ZONE_1, ZoneUtil.convertCubicZoneDescriptionToStartZone(ZONES + ZONE_1_DESCRIPTION + TO + ZONE_3_DESCRIPTION));
    }

    @Test
    public void shouldReturnZoneThreeAsEndZone() {
        assertEquals(ZONE_3, ZoneUtil.convertCubicZoneDescriptionToEndZone(ZONES + ZONE_1_DESCRIPTION + TO + ZONE_3_DESCRIPTION));
    }

    @Test
    public void shouldReturnZoneTenAsStartZone() {
        assertEquals(ZONE_10, ZoneUtil.convertCubicZoneDescriptionToStartZone(ZONES + ZONE_10_DESCRIPTION + TO + ZONE_1_DESCRIPTION));
    }

    @Test
    public void shouldReturnZoneTenAsEndZone() {
        assertEquals(ZONE_10, ZoneUtil.convertCubicZoneDescriptionToEndZone(ZONES + ZONE_1_DESCRIPTION + TO + ZONE_10_DESCRIPTION));
    }
    
    @Test
    public void shouldReturnNullForEndZone() {
        assertEquals(null, ZoneUtil.convertCubicZoneDescriptionToEndZone(ZONES + ZONE_1_DESCRIPTION + TO ));
    }
    
    @Test
    public void shouldReturnnullAsStartZone() {
        assertEquals(null, ZoneUtil.convertCubicZoneDescriptionToStartZone(ZONES));
    }
    @Test
    public void shoudlReturnTrueIfZoneIsGreaterThanEquals(){
        assertTrue(ZoneUtil.isZoneGreaterThanEquals(ZONE_3, ZONE_1));
    }
    
    @Test
    public void shoudlReturnFalseIfZoneIsNotGreaterThanEquals(){
        assertFalse(ZoneUtil.isZoneGreaterThanEquals(ZONE_1, ZONE_3));
    }
    
    @Test
    public void shoudReturnTrueIfZoneLessThanEquals(){
        assertTrue(ZoneUtil.isZoneLessThanEquals(ZONE_3, ZONE_5));
    }
    
    @Test
    public void shoudReturnFalseIfZoneNotLessThanEquals(){
        assertFalse(ZoneUtil.isZoneLessThanEquals(ZONE_3, ZONE_1));
    }
    
    @Test
    public void shouldReturnTrueIfZoneEquals(){
        assertTrue(ZoneUtil.isZoneEquals(ZONE_3, ZONE_3));
    }
    
    @Test
    public void shouldReturnFalseIfZoneNotEquals(){
        assertFalse(ZoneUtil.isZoneEquals(ZONE_3, ZONE_1));
    }

}
