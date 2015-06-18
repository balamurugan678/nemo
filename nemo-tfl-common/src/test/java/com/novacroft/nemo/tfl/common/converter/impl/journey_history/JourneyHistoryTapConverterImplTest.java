package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryTapConverter;
import org.junit.Test;

import static com.novacroft.nemo.test_support.TapTestUtil.getTestTap1;
import static com.novacroft.nemo.test_support.TapTestUtil.getTestTapDTO1;
import static org.junit.Assert.assertEquals;

/**
 * JourneyHistoryTapConverter unit tests
 */
public class JourneyHistoryTapConverterImplTest {
    @Test
    public void shouldConvertModelToDto() {
        JourneyHistoryTapConverter converter = new JourneyHistoryTapConverterImpl();
        assertEquals(getTestTapDTO1(), converter.convertModelToDto(getTestTap1()));
    }
}
