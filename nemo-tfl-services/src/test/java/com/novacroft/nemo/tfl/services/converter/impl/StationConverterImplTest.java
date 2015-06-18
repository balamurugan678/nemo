package com.novacroft.nemo.tfl.services.converter.impl;

import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.services.converter.StationConverter;
import com.novacroft.nemo.tfl.services.test_support.TestSupportUtilities;
import com.novacroft.nemo.tfl.services.transfer.Station;

/**
 * StationConverter unit tests
 */
public class StationConverterImplTest {

    private StationConverter converter;

    @Before
    public void setUp() {
        this.converter = mock(StationConverterImpl.class);
    }

    @Test
    public void shouldConvertDto() {
        when(this.converter.convert(any(LocationDTO.class))).thenCallRealMethod();
        Station result = this.converter.convert(getTestLocationDTO1());
        assertEquals(getTestLocationDTO1().getId(), result.getId());
        assertEquals(getTestLocationDTO1().getName(), result.getName());
        assertEquals(getTestLocationDTO1().getStatus(), result.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertList() {
        when(this.converter.convert(anyList())).thenCallRealMethod();
        when(this.converter.convert(any(LocationDTO.class))).thenReturn(new Station());
        this.converter.convert(TestSupportUtilities.getTestLocationDTOList());
        verify(this.converter).convert(any(LocationDTO.class));
    }
    
    @Test
    public void testInitialisation(){
        assertNotNull(new StationConverterImpl());
    }
    
}
