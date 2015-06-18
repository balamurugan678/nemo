package com.novacroft.nemo.tfl.batch.converter.impl;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.cubic.CurrentActionConverterImpl;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * CubicRecordConverter unit tests
 */
public class CurrentActionConverterTest {
    @Test
    public void shouldConvert() {
        ImportRecordConverter converter = new CurrentActionConverterImpl();
        assertEquals(getTestCurrentActionRecord1(), converter.convert(getTestCurrentActionArray1()));
    }

    @Test
    public void shouldToCsv() {
        ImportRecordConverter converter = new CurrentActionConverterImpl();
        assertEquals(getTestCurrentActionCsv1().trim(), converter.toCsv(getTestCurrentActionArray1()).trim());
    }
}
