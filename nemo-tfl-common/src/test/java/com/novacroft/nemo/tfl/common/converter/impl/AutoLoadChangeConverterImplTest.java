package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.converter.AutoLoadChangeConverter;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * AutoLoadChangeConverter unit tests
 */
public class AutoLoadChangeConverterImplTest {

    @Test
    public void shouldConvertToModel() {
        AutoLoadChangeConverter converter = new AutoLoadChangeConverterImpl();
        assertEquals(getTestAutoLoadRequest1(), converter.convertToModel(getTestAutoLoadChangeRequestDTO1()));
    }

    @Test
    public void shouldConvertAutoLoadResponseToDto() {
        AutoLoadChangeConverter converter = new AutoLoadChangeConverterImpl();
        assertEquals(getTestSuccessAutoLoadChangeResponseDTO1(), converter.convertToDto(getTestAutoLoadResponse1()));
    }

    @Test
    public void shouldConvertRequestFailureToDto() {
        AutoLoadChangeConverter converter = new AutoLoadChangeConverterImpl();
        assertEquals(getTestFailAutoLoadChangeResponseDTO1(), converter.convertToDto(getTestRequestFailure1()));
    }
}
