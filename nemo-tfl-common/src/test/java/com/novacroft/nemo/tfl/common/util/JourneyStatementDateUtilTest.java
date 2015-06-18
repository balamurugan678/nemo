package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.get31Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.getMon10Feb;
import static com.novacroft.nemo.test_support.DateTestUtil.getMon17FebAt0000;
import static com.novacroft.nemo.test_support.DateTestUtil.getSun16Feb;
import static com.novacroft.nemo.test_support.DateTestUtil.getSun23Feb;
import static com.novacroft.nemo.test_support.DateTestUtil.getSun23FebAt2359;
import static com.novacroft.nemo.test_support.DateTestUtil.getTue18Feb;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JourneyStatementDateUtil unit tests
 */
public class JourneyStatementDateUtilTest {
    @Test
    public void shouldGetStartOfLastWeekAsOnTuesday() {
        assertEquals(getMon10Feb(), JourneyStatementDateUtil.getStartOfLastWeek(getTue18Feb()));
    }

    @Test
    public void shouldGetStartOfLastWeekAsOnSunday() {
        assertEquals(getMon10Feb(), JourneyStatementDateUtil.getStartOfLastWeek(getSun23Feb()));
    }

    @Test
    public void shouldGetEndOfLastWeekAsOnTuesday() {
        assertEquals(getSun16Feb(), JourneyStatementDateUtil.getEndOfLastWeek(getTue18Feb()));
    }

    @Test
    public void shouldGetEndOfLastWeekAsOnSunday() {
        assertEquals(getSun16Feb(), JourneyStatementDateUtil.getEndOfLastWeek(getSun23Feb()));
    }

    @Test
    public void shouldGetStartOfLastMonth() {
        assertEquals(get1Jan(), JourneyStatementDateUtil.getStartOfLastMonth(getSun23Feb()));
    }

    @Test
    public void shouldGetEndOfLastMonth() {
        assertEquals(get31Jan(), JourneyStatementDateUtil.getEndOfLastMonth(getTue18Feb()));
    }
    
    @Test
    public void testStartOfWeekGivenMonday(){
        assertEquals(getMon10Feb(), JourneyStatementDateUtil.getStartOfWeek(getMon10Feb()));    	    	
    }

    @Test
    public void testStartOfWeekGivenTuesday(){
        assertEquals(getMon17FebAt0000(), JourneyStatementDateUtil.getStartOfWeek(getTue18Feb()));    	    	
    }

    @Test
    public void testStartOfWeekGivenSunday(){
        assertEquals(getMon17FebAt0000(), JourneyStatementDateUtil.getStartOfWeek(getSun23Feb()));    	    	
    }
    
    @Test
    public void testEndOfWeekGivenSunday(){
        assertEquals(getSun23FebAt2359(), JourneyStatementDateUtil.getEndOfWeek(getSun23Feb()));    	    	
    }

    @Test
    public void testEndOfWeekGivenTuesday(){
        assertEquals(getSun23FebAt2359(), JourneyStatementDateUtil.getEndOfWeek(getTue18Feb()));    	    	
    }

    @Test
    public void testEndOfWeekGivenMonday(){
        assertEquals(getSun23FebAt2359(), JourneyStatementDateUtil.getEndOfWeek(getMon17FebAt0000()));    	    	
    }
    
    @Test
    public void testIsMonday(){
        assertTrue(JourneyStatementDateUtil.isMonday(getMon10Feb()));
        assertFalse(JourneyStatementDateUtil.isMonday(getTue18Feb()));
        assertFalse(JourneyStatementDateUtil.isMonday(getSun23Feb()));
    }

    @Test
    public void testEndOfWeek() {
        assertNotNull(JourneyStatementDateUtil.getEndOfWeek(1));
    }

    @Test
    public void testStartOfWeek() {
        assertNotNull(JourneyStatementDateUtil.getStartOfWeek(1));
    }

}
