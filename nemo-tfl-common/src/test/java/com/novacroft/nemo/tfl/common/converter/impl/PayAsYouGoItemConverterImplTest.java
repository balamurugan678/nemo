package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CartTestUtil.getPayAsYouGoItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getPayAsYouGoItemDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.data_access.PayAsYouGoItemDAO;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

public class PayAsYouGoItemConverterImplTest {
    private PayAsYouGoItemConverterImpl converter;
    private PayAsYouGoItemDAO mockDAO;
    private Long PAYASYOUID = 12L;
    
    @Before
    public void setUp() {
        converter = new PayAsYouGoItemConverterImpl();
        mockDAO = mock(PayAsYouGoItemDAO.class);
        converter.payAsYouGoItemDAO = mockDAO;
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
    
    @Test
    public void shouldConvertEntityToDto() {
        PayAsYouGoItem testPayAsYouGoItem = (PayAsYouGoItem)getPayAsYouGoItem();
        testPayAsYouGoItem.setRelatedItem((PayAsYouGoItem)getPayAsYouGoItem());
        
       PayAsYouGoItemDTO actualResult = converter.convertEntityToDto(testPayAsYouGoItem);
        
        assertNotNull(actualResult);
        assertEquals(PAYASYOUID, actualResult.getPayAsYouGoId());
        assertNotNull(actualResult.getRelatedItem());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithNoRelatedItem() {
        PayAsYouGoItem actualResult = converter.convertDtoToEntity((PayAsYouGoItemDTO)getPayAsYouGoItemDTO(), new PayAsYouGoItem());
        
        assertNotNull(actualResult);
        assertNull(actualResult.getRelatedItem());
        assertEquals(PAYASYOUID, actualResult.getPayAsYouGoId());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithRelatedItem() {
        when(mockDAO.findById(anyLong())).thenReturn(new PayAsYouGoItem());
        
        PayAsYouGoItemDTO payAsYouGoItemDTO = (PayAsYouGoItemDTO)getPayAsYouGoItemDTO();
        payAsYouGoItemDTO.setRelatedItem((PayAsYouGoItemDTO)getPayAsYouGoItemDTO());
        PayAsYouGoItem actualResult = converter.convertDtoToEntity(payAsYouGoItemDTO, new PayAsYouGoItem());
        
        assertNotNull(actualResult);
        assertNotNull(actualResult.getRelatedItem());
        assertEquals(PAYASYOUID, actualResult.getPayAsYouGoId());
    }
}
