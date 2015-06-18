package com.novacroft.nemo.tfl.common.converter.impl;


import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItem2;
import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_CREATED_USER_ID_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_2;
import static com.novacroft.nemo.test_support.ItemTestUtil.BLANK;
import static com.novacroft.nemo.test_support.ItemTestUtil.DOT;
import static com.novacroft.nemo.test_support.ItemTestUtil.DTO;
import static com.novacroft.nemo.test_support.ItemTestUtil.TRANSFER_PACKAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ItemConverterImplTest {
	 private  ItemConverterImpl converter;
	 private NemoUserContext mockNemoUserContext;
	 private GoodwillPaymentItemConverterImpl mockGoodwillConverter;
	 private ProductItemConverterImpl mockProductConverter;
	 private PayAsYouGoItemConverterImpl mockPayAsYouGoConverter;
	 private AutoTopUpConfigurationItemConverterImpl mockAutoTopUpConverter;

	 @Before
	 public void setUp() {
		 converter = new ItemConverterImpl(); 
		 mockNemoUserContext = mock(NemoUserContext.class);
		 mockGoodwillConverter = mock(GoodwillPaymentItemConverterImpl.class);
		 mockProductConverter = mock(ProductItemConverterImpl.class);
		 mockPayAsYouGoConverter = mock(PayAsYouGoItemConverterImpl.class);
		 mockAutoTopUpConverter = mock(AutoTopUpConfigurationItemConverterImpl.class);
		 
		 converter.nemoUserContext = mockNemoUserContext;
		 converter.goodwillPaymentItemConverter = mockGoodwillConverter;
		 converter.productItemConverter = mockProductConverter;
		 converter.autoTopUpConfigurationItemConverter = mockAutoTopUpConverter;
		 converter.payAsYouGoItemConverter = mockPayAsYouGoConverter;
	 }
    
    @Test
    public void shouldConvertEntityToDto() {
        ItemDTO result = converter.convertEntityToDto(getTestAdministrationFeeItem2());
        
        assertEquals(ADMINISTRATION_FEE_PRICE_2, result.getPrice());
    	assertEquals(ADMINISTRATION_FEE_CREATED_USER_ID_2, result.getCreatedUserId());
    	assertTrue(result instanceof AdministrationFeeItemDTO);
    }
    
    @Test
    public void shouldConvertEntityToDtoAsGoodwillPaymentItem() {
        GoodwillPaymentItemDTO mockGoodwillDTO = mock(GoodwillPaymentItemDTO.class);
        when(mockGoodwillConverter.convertEntityToDto(any(Item.class))).thenReturn(mockGoodwillDTO);
        
        assertEquals(mockGoodwillDTO, converter.convertEntityToDto(new GoodwillPaymentItem()));
        verify(mockGoodwillConverter).convertEntityToDto(any(Item.class));
    }
    
    @Test
    public void shouldConvertEntityToDtoAsProductItem() {
        ProductItemDTO mockProductDTO = mock(ProductItemDTO.class);
        when(mockProductConverter.convertEntityToDto(any(ProductItem.class))).thenReturn(mockProductDTO);
        
        assertEquals(mockProductDTO, converter.convertEntityToDto(new ProductItem()));
        verify(mockProductConverter).convertEntityToDto(any(ProductItem.class));
    }

    @Test
    public void shouldConvertDtoToEntity() {
        Item entity = new AdministrationFeeItem();
        
        Item result = converter.convertDtoToEntity(getTestAdministrationFeeItemDTO3(), entity);
        
        assertEquals(ADMINISTRATION_FEE_PRICE_2, result.getPrice());
    	assertEquals(ADMINISTRATION_FEE_CREATED_USER_ID_2, result.getCreatedUserId());
    	assertTrue(result instanceof AdministrationFeeItem);
    }
    
    @Test
    public void shouldConvertDtoToEntityAsGoodwillPaymentItem() {
        Item mockItem = mock(Item.class);
        when(mockGoodwillConverter.convertDtoToEntity(any(ItemDTO.class), any(Item.class))).thenReturn(mockItem);
        
        assertEquals(mockItem, 
                        converter.convertDtoToEntity(new GoodwillPaymentItemDTO(), null));
        verify(mockGoodwillConverter).convertDtoToEntity(any(ItemDTO.class), any(Item.class));
    }
    
    @Test
    public void shouldConvertDtoToEntityAsProductItem() {
        ProductItem mockItem = mock(ProductItem.class);
        when(mockProductConverter.convertDtoToEntity(any(ProductItemDTO.class), any(ProductItem.class))).thenReturn(mockItem);
        
        assertEquals(mockItem, 
                        converter.convertDtoToEntity(new ProductItemDTO(), null));
        verify(mockProductConverter).convertDtoToEntity(any(ProductItemDTO.class), any(ProductItem.class));
    }
    
    @Test
    public void shouldConvertDtoToEntityAsPayAsYouGoItem() {
        PayAsYouGoItem mockItem = mock(PayAsYouGoItem.class);
        when(mockPayAsYouGoConverter.convertDtoToEntity(any(PayAsYouGoItemDTO.class), any(PayAsYouGoItem.class))).thenReturn(mockItem);
        
        assertEquals(mockItem, 
                        converter.convertDtoToEntity(new PayAsYouGoItemDTO(), null));
        verify(mockPayAsYouGoConverter).convertDtoToEntity(any(PayAsYouGoItemDTO.class), any(PayAsYouGoItem.class));
    }
    
    @Test
    public void shouldConvertDtoToEntityAsAutoTopUpItem() {
        AutoTopUpConfigurationItem mockItem = mock(AutoTopUpConfigurationItem.class);
        when(mockAutoTopUpConverter.convertDtoToEntity(any(AutoTopUpConfigurationItemDTO.class), any(AutoTopUpConfigurationItem.class))).thenReturn(mockItem);
        
        assertEquals(mockItem, 
                        converter.convertDtoToEntity(new AutoTopUpConfigurationItemDTO(), null));
        verify(mockAutoTopUpConverter).convertDtoToEntity(any(AutoTopUpConfigurationItemDTO.class), any(AutoTopUpConfigurationItem.class));
    }
    
    @Test
    public void getNewDtoShouldReturnNewAdministrationFeeDto() {
    	Item item = getTestAdministrationFeeItem2();
    	
    	ItemDTO itemDTO = converter.getNewDto(item);
    	
    	assertEquals(item.getClass().getSimpleName(), itemDTO.getClass().getSimpleName().replace(DTO, BLANK));
    	assertEquals(TRANSFER_PACKAGE, itemDTO.getClass().getName().replace(DOT + itemDTO.getClass().getSimpleName(), BLANK));
    	assertTrue(itemDTO instanceof AdministrationFeeItemDTO);
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
}
