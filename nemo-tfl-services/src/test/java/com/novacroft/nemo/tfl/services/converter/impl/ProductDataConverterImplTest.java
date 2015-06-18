package com.novacroft.nemo.tfl.services.converter.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.test_support.ProductDataTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

public class ProductDataConverterImplTest {

    private ProductDataItemConverterImpl converter;

    @Before
    public void setUp() throws Exception {
        converter = new ProductDataItemConverterImpl();
    }

    @Test
    public void convertToTravelCard() {
        assertNotNull(converter.convertToTravelCard(ProductDataTestUtil.getTestProductDTO()));
    }

    @Test
    public void convertToItem() {
        assertNotNull(converter.convertToItem(ProductDataTestUtil.getTestProductDTO()));
    }

    @Test
    public void convertToProductItemDTO() {
        assertNotNull(converter.convertToProductDTO(ProductDataTestUtil.getTestTravelCard()));
    }

    @Test
    public void convertToErrorResult() {
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(true);
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError(ProductDataTestUtil.ERROR_DURATION_FIELD, ProductDataTestUtil.ERROR_DURATION_MESSAGE));
        when(errors.getAllErrors()).thenReturn(objectErrors);
        assertNotNull(converter.convertToErrorResult(errors));
    }

    @Test
    public void convertToErrorResultNoErrrors() {
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(false);
        assertNull(converter.convertToErrorResult(errors));
    }
    
    @Test
    public void testConvertToProductItemDTO(){
        ProductItemDTO productItemDTO = ProductDataTestUtil.getTestProductItemDTO();
        productItemDTO.setEndDate(DateUtil.parse(com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.END_DATE_1));
        ProductDTO productDTO = ProductDataTestUtil.getTestProductDTO();
        Item item = converter.convertToItem(productDTO, productItemDTO);
        assertNotNull(item);
        assertEquals(productDTO.getTicketPrice(), item.getPrice());
        
    }
    
    @Test
    public void testConvertToItem(){
        PrePaidTicket travelCard = ProductDataTestUtil.getTestTravelCard();
        ProductItemDTO productItemDTO = converter.convertToProductItemDTO(travelCard);
        assertNotNull(productItemDTO);
        assertEquals(travelCard.getStartZone(), productItemDTO.getStartZone().toString());
        assertEquals(travelCard.getEndZone(), productItemDTO.getEndZone().toString());
    }

}
