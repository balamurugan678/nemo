package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.ProductItemTestUtil.getTestOtherTravelCardProduct1;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.getTestBussPassProduct1;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.getTestOtherTravelCardProductItemDTO1;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.getTestPayAsYouGoProductItemDTO1;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.PRODUCT_Item_ID_2;
import static com.novacroft.nemo.test_support.ProductTestUtil.PRODUCT_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.data_access.ProductItemDAO;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ProductItemConverterImplTest {
    private ProductItemConverterImpl converter;
    private ProductItemDAO mockDAO;
    
    @Before
    public void setUp() {
        converter = new ProductItemConverterImpl();
        mockDAO = mock(ProductItemDAO.class);
        converter.productItemDAO = mockDAO;
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
    
    @Test
    public void shouldConvertEntityToDto() {
        ProductItem testProductItem = getTestOtherTravelCardProduct1();
        testProductItem.setRelatedItem(getTestBussPassProduct1());
        
        ProductItemDTO actualResult = converter.convertEntityToDto(testProductItem);
        
        assertNotNull(actualResult);
        assertEquals(PRODUCT_ID_1, actualResult.getProductId());
        assertNotNull(actualResult.getRelatedItem());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithNoTradedTicket() {
        ProductItem actualResult = converter.convertDtoToEntity(getTestOtherTravelCardProductItemDTO1(), new ProductItem());
        
        assertNotNull(actualResult);
        assertNull(actualResult.getRelatedItem());
        assertEquals(PRODUCT_ID_1, actualResult.getProductId());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithTradedTicket() {
        when(mockDAO.findById(anyLong())).thenReturn(new ProductItem());
        
        ProductItemDTO productItemDTO = getTestOtherTravelCardProductItemDTO1();
        productItemDTO.setRelatedItem(getTestPayAsYouGoProductItemDTO1());
        ProductItem actualResult = converter.convertDtoToEntity(productItemDTO, new ProductItem());
        
        verify(mockDAO).findById(PRODUCT_Item_ID_2);
        assertNotNull(actualResult);
        assertNotNull(actualResult.getRelatedItem());
        assertEquals(PRODUCT_ID_1, actualResult.getProductId());
    }
}
