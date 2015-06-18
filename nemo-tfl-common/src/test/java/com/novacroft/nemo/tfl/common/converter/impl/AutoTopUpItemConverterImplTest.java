package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CartTestUtil.getAutoTopUpItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getAutoTopUpItemDTO;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.PRODUCT_Item_ID_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.data_access.AutoTopUpItemDAO;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;

public class AutoTopUpItemConverterImplTest {
    private AutoTopUpConfigurationItemConverterImpl converter;
    private AutoTopUpItemDAO mockDAO;
    private Long AUTOTOPUPID = 12L;
    
    @Before
    public void setUp() {
        converter = new AutoTopUpConfigurationItemConverterImpl();
        mockDAO = mock(AutoTopUpItemDAO.class);
        converter.autoTopUpItemDAO = mockDAO;
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
    
    @Test
    public void shouldConvertEntityToDto() {
        AutoTopUpConfigurationItem testAutoTopUpItem = (AutoTopUpConfigurationItem)getAutoTopUpItem();
        testAutoTopUpItem.setRelatedItem((AutoTopUpConfigurationItem)getAutoTopUpItem());
        
       AutoTopUpConfigurationItemDTO actualResult = converter.convertEntityToDto(testAutoTopUpItem);
        
        assertNotNull(actualResult);
        assertEquals(AUTOTOPUPID, actualResult.getAutoTopUpId());
        assertNotNull(actualResult.getRelatedItem());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithNoTradedTicket() {
        AutoTopUpConfigurationItem actualResult = converter.convertDtoToEntity((AutoTopUpConfigurationItemDTO)getAutoTopUpItemDTO(), new AutoTopUpConfigurationItem());
        
        assertNotNull(actualResult);
        assertNull(actualResult.getRelatedItem());
        assertEquals(AUTOTOPUPID, actualResult.getAutoTopUpId());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithTradedTicket() {
        when(mockDAO.findById(anyLong())).thenReturn(new AutoTopUpConfigurationItem());
        
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = (AutoTopUpConfigurationItemDTO)getAutoTopUpItemDTO();
        autoTopUpItemDTO.setRelatedItem((AutoTopUpConfigurationItemDTO)getAutoTopUpItemDTO());
        AutoTopUpConfigurationItem actualResult = converter.convertDtoToEntity(autoTopUpItemDTO, new AutoTopUpConfigurationItem());
        
        assertNotNull(actualResult);
        assertNotNull(actualResult.getRelatedItem());
        assertEquals(AUTOTOPUPID, actualResult.getAutoTopUpId());
    }
}
