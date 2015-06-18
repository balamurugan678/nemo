package com.novacroft.nemo.tfl.common.application_service.impl;

import org.junit.Test;

import java.util.Comparator;

import static com.novacroft.nemo.test_support.SelectListTestUtil.*;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for SelectListOptionMeaningComparator
 */
public class SelectListOptionMeaningComparatorTest {

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void shouldReturnLessThanZero() {
        Comparator comparator = new SelectListOptionMeaningComparator();
        int result = comparator.compare(getTestSelectListOptionDTOAlpha(), getTestSelectListOptionDTOBravo());
        assertTrue(result < 0);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void shouldReturnGreaterThanZero() {
        Comparator comparator = new SelectListOptionMeaningComparator();
        int result = comparator.compare(getTestSelectListOptionDTOCharlie(), getTestSelectListOptionDTOBravo());
        assertTrue(result > 0);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void shouldReturnZero() {
        Comparator comparator = new SelectListOptionMeaningComparator();
        int result = comparator.compare(getTestSelectListOptionDTOBravo(), getTestSelectListOptionDTOBravo());
        assertTrue(result == 0);
    }
}
