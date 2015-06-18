package com.novacroft.nemo.tfl.batch.converter.impl;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.cubic.AdHocDistributionConverterImpl;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * AdHocDistributionConverterImpl unit tests
 */
public class AdHocDistributionConverterImplTest {
    @Test
    public void shouldConvert() {
        ImportRecordConverter converter = new AdHocDistributionConverterImpl();
        assertEquals(getAdHocDistributionTestRecord1(), converter.convert(getAdHocDistributionRawTestRecord1()));
    }

    @Test
    public void shouldToCsv() {
        ImportRecordConverter converter = new AdHocDistributionConverterImpl();
        assertEquals(getAdHocDistributionCsvTestRecord1(), converter.toCsv(getAdHocDistributionRawTestRecord1()).trim());
    }
}
