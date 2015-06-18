package com.novacroft.nemo.tfl.common.converter.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class ExternalUserConverterImplTest {
    private ExternalUserConverterImpl converter;
    
    @Before
    public void setUp() {
        converter = new ExternalUserConverterImpl();               
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
}
