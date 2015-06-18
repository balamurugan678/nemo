package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadChangeRecordTestUtil.getAutoLoadChangeTestRecord1;
import static org.junit.Assert.assertTrue;

/**
 * AllAutoLoadChangesFilter unit tests
 */
public class AllAutoLoadChangesFilterTest {
    @Test
    public void shouldMatch() {
        AllAutoLoadChangesFilterImpl filter = new AllAutoLoadChangesFilterImpl();
        assertTrue(filter.matches(getAutoLoadChangeTestRecord1()));
    }
}
