package com.novacroft.nemo.tfl.services.controller;

import static com.novacroft.nemo.tfl.services.test_support.ProductDataTestUtil.getTestTravelCard;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.services.application_service.ProductService;
import com.novacroft.nemo.tfl.services.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.services.test_support.ProductDataTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListContainer;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

public class ProductDataControllerTest {

    private ProductController controller;
    private ProductService productDataService;
    
    @Before
    public void setUp() throws Exception {
        controller = new ProductController();
        productDataService = mock(ProductService.class);
        controller.productExternalService = productDataService;
    }
    
    @Test
    public void getTravelCard(){
        when(productDataService.getTravelCard((PrePaidTicket)any())).thenReturn(ItemTestUtil.getTestTravelCardItem1());
        Item item = controller.getTravelCard(getTestTravelCard());
        assertNotNull(item);
        assertNull(item.getErrors());
    }
    
    @Test 
    public void getTravelCardWithErrors(){
        when(productDataService.getTravelCard((PrePaidTicket)any())).thenReturn(ItemTestUtil.getTestItemWithErrors());
        Item item = controller.getTravelCard(getTestTravelCard());
        assertNotNull("Item is null", item);
        assertNotNull("Item has errors", item.getErrors());
    }
    
    @Test 
    public void getReferenceData(){
        when(productDataService.getReferenceData()).thenReturn(new ArrayList<ListContainer>());
        assertNotNull(controller.getReferenceData());
    }


    @Test
    public void getPayAsYouGoShouldFailWithError(){
        when(productDataService.getPayAsYouGo(any(PayAsYouGo.class))).thenReturn(ItemTestUtil.getTestItemWithErrors());
        Item item = controller.getPayAsYouGo(ProductDataTestUtil.getPayAsYouGoInputWith10Amount());
        assertFalse(item.getErrors().getErrors().isEmpty());
    }

    @Test
    public void getPayAsYouGoShouldCompleteSuccessfully(){
        when(productDataService.getPayAsYouGo(any(PayAsYouGo.class))).thenReturn(ItemTestUtil.getTestPayAsYouGoItem());
        Item item = controller.getPayAsYouGo(ProductDataTestUtil.getPayAsYouGoInputWith10Amount());
        assertNull(item.getErrors());
    }
    
    @Test
    public void getBusPassShouldReturnSuccessfully() {
        when(productDataService.getBusPass(any(PrePaidTicket.class))).thenReturn(ItemTestUtil.getTestBusPassItem());
        Item busItem = controller.getBusPass(ProductDataTestUtil.getTestBusPass());
        assertNotNull(busItem);
        assertNotNull(busItem.getProductType());
        assertEquals(ProductItemType.BUS_PASS.databaseCode(), busItem.getProductType());
        
    }

}
