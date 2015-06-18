package com.novacroft.nemo.tfl.services.converter.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.PayAsYouGoTestUtil;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.services.converter.PayAsYouGoConverter;
import com.novacroft.nemo.tfl.services.transfer.Item;

public class PayAsYouGoConverterImplTest {

    private PayAsYouGoConverter converter;
    
    @Before
    public void setUp(){
        converter = new PayAsYouGoConverterImpl();
    }
    
    @Test
    public void shouldConvertToItem(){
        PayAsYouGoDTO dto  = PayAsYouGoTestUtil.getTestPayAsYouGoDTO1();
        Item item = converter.convertToItem(dto);
        assertEquals(item.getName(), dto.getPayAsYouGoName()); 
        assertEquals(item.getPrice(), dto.getTicketPrice()); 
    }
}
