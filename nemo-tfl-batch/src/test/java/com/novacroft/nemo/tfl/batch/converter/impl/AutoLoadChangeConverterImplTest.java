package com.novacroft.nemo.tfl.batch.converter.impl;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.cubic.AutoLoadChangeConverterImpl;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadChangeRecordTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * AutoLoadChangeConverterImpl unit tests
 */
public class AutoLoadChangeConverterImplTest {
    @Test
    public void shouldConvert() {
        ImportRecordConverter converter = new AutoLoadChangeConverterImpl();
        assertEquals(getAutoLoadChangeTestRecord1(), converter.convert(getAutoLoadChangeRawTestRecord1()));
    }

    @Test
    public void shouldToCsv() {
        ImportRecordConverter converter = new AutoLoadChangeConverterImpl();
        assertEquals(getAutoLoadChangeCsvTestRecord1(), converter.toCsv(getAutoLoadChangeRawTestRecord1()).trim());
    }
}
