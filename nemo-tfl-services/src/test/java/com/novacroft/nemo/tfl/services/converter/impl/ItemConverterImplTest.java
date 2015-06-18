package com.novacroft.nemo.tfl.services.converter.impl;

import static com.novacroft.nemo.common.constant.DateConstant.DAYINMONTH_SHORTMONTH_YEAR;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.getTestAutoTopUpItemDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getListOfItems;
import static com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.getTestTravelCardProductDTO1;
import static com.novacroft.nemo.tfl.services.test_support.ItemTestUtil.USER_PRODUCT_START_AFTER_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Item;

/**
 * StationConverter unit tests
 */
public class ItemConverterImplTest {

    private ItemConverterImpl converter;
    private TravelCardService mockTravelCardService;
    private PrePaidTicketDataService mockPrePaidTicketDataService;
    private ProductService mockProductService;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        this.converter = mock(ItemConverterImpl.class);
        mockTravelCardService = mock(TravelCardService.class);
        mockPrePaidTicketDataService = mock(PrePaidTicketDataService.class);
        mockProductService = mock(ProductService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        
        converter.travelCardService = mockTravelCardService;
        converter.prePaidTicketDataService = mockPrePaidTicketDataService;
        converter.productService = mockProductService;
        converter.systemParameterService = mockSystemParameterService;
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
    }

    @Test
    public void shouldConvertDTOIfItemDTOIsInstanceOfProductItemDTO() {
        when(mockProductService.getDurationFromPrePaidTicketDTO(any(PrePaidTicketDTO.class))).thenReturn(Durations.MONTH.getDurationType());
        when(this.converter.convert(any(ItemDTO.class))).thenCallRealMethod();
        when(mockTravelCardService.isTravelCard(any(ItemDTO.class))).thenReturn(true);
        when(mockPrePaidTicketDataService.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        doCallRealMethod().when(converter).convertProductItem(any(ProductItemDTO.class), any(Item.class));
        Item result = this.converter.convert(getTestTravelCardProductDTO1());
        assertEquals(getTestTravelCardProductDTO1().getName(), result.getName());
        assertEquals(getTestTravelCardProductDTO1().getPrice(), result.getPrice());
        assertEquals(getTestTravelCardProductDTO1().getStartZone(), result.getStartZone());
        assertEquals(getTestTravelCardProductDTO1().getEndZone(), result.getEndZone());
    }

    @Test
    public void shouldConvertDTOIfItemDTOIsInstanceOfAutTopUpItemDTO() {
        when(this.converter.convert(any(ItemDTO.class))).thenCallRealMethod();
        when(mockPrePaidTicketDataService.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        Item result = this.converter.convert(getTestAutoTopUpItemDTO1());
        assertEquals(getTestAutoTopUpItemDTO1().getName(), result.getName());
        assertEquals(getTestAutoTopUpItemDTO1().getPrice(), result.getPrice());
        assertEquals(getTestAutoTopUpItemDTO1().getAutoTopUpAmount(), result.getAutoTopUpAmount());
    }
    
    @Test
    public void shouldConvertDTOIfItemDTOIsInstanceOfPayAsYouGoItemDTO() {
        when(this.converter.convert(any(ItemDTO.class))).thenCallRealMethod();
        when(mockPrePaidTicketDataService.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        Item result = this.converter.convert(getTestPayAsYouGoItemDTO1());
        assertEquals(getTestPayAsYouGoItemDTO1().getName(), result.getName());
        assertEquals(getTestPayAsYouGoItemDTO1().getPrice(), result.getPrice());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertList() {
        when(this.converter.convert(anyList())).thenCallRealMethod();
        when(this.converter.convert(any(ItemDTO.class))).thenReturn(new Item());
        this.converter.convert(getListOfItems());
        verify(this.converter).convert(any(ItemDTO.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvertListWithOrderDate() {
    	doNothing().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
        when(this.converter.convert(anyList(),any(Date.class))).thenCallRealMethod();
        when(this.converter.convert(any(ItemDTO.class))).thenReturn(new Item());
        this.converter.convert(ItemTestUtil.getListOfPayAsYouGoItems(),OrderTestUtil.PRODUCT_ORDER_DATE);
        verify(this.converter).convert(any(ItemDTO.class));
    }
    
    @Test
    public void shouldValidateActivationWindowStartAndEndDateForPayAsYouGo() {
    	Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
    	when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
    	Item item = ItemTestUtil.getTestPayAsYouGoItem();
    	converter.setActivationWindowStartAndEndDates(item,OrderTestUtil.PRODUCT_ORDER_DATE);
    	assertNotNull(item.getActivationWindowStartDate());
    	assertNotNull(item.getActivationWindowExpiryDate());
    }    
    
    @Test
    public void shouldValidateActivationWindowStartAndEndDateForAutoTopUp() {
    	Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
    	when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
    	Item item = ItemTestUtil.getTestAutoTopUpItem();
    	converter.setActivationWindowStartAndEndDates(item,OrderTestUtil.PRODUCT_ORDER_DATE);
    	assertNotNull(item.getActivationWindowStartDate());
    	assertNotNull(item.getActivationWindowExpiryDate());
    } 
    
    @Test
    public void shouldValidateActivationWindowStartAndEndDateForTravelCard() {
    	Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
    	when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
    	Item item = ItemTestUtil.getTestTravelCardItem1();
    	converter.setActivationWindowStartAndEndDates(item,OrderTestUtil.PRODUCT_ORDER_DATE);
    	assertNotNull(item.getActivationWindowStartDate());
    	assertNotNull(item.getActivationWindowExpiryDate());
    } 
    
    @Test
    public void shouldValidateActivationWindowStartAndEndDateForBusPass() {
    	Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
    	when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
    	Item item = ItemTestUtil.getTestBusPassItem();
    	converter.setActivationWindowStartAndEndDates(item,OrderTestUtil.PRODUCT_ORDER_DATE);
    	assertNotNull(item.getActivationWindowStartDate());
    	assertNotNull(item.getActivationWindowExpiryDate());
    } 
    
    @Test
    public void shouldCalculateActivationWindowAs9thAugto16thAugForTravelcard() {
        
        Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class));
        Item item = ItemTestUtil.getTestTravelCardItem1();
        item.setStartDate(DateTestUtil.getAUG_14_2014_AT_10_45());
        
        converter.setActivationWindowStartAndEndDates(item,DateTestUtil.getAugust_1st());
        
        assertEquals(DateTestUtil.getDate(DAYINMONTH_SHORTMONTH_YEAR, item.getActivationWindowStartDate()),DateTestUtil.getAug09_AT_0000());
        assertEquals(DateTestUtil.getDate(DAYINMONTH_SHORTMONTH_YEAR, item.getActivationWindowExpiryDate()),DateTestUtil.getAug16_AT_0000());
    } 
    
    @Test
    public void shouldCalculateActivationWindowAs2ndAugto09thAugForAutoTopUp() {
        Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
        Item item = ItemTestUtil.getTestAutoTopUpItem();
        item.setStartDate(DateTestUtil.getAUG_14_2014_AT_10_45());
        
        converter.setActivationWindowStartAndEndDates(item,DateTestUtil.getAugust_1st());
        
        assertEquals(DateTestUtil.getDate(DAYINMONTH_SHORTMONTH_YEAR,item.getActivationWindowStartDate()),DateTestUtil.getAug02());
        assertEquals(DateTestUtil.getDate(DAYINMONTH_SHORTMONTH_YEAR, item.getActivationWindowExpiryDate()),DateTestUtil.getAug09_AT_0000());
    } 
    
    @Test
    public void shouldCalculateActivationWindowAs2ndAugto09thAugForPayAsYouGo() {
        Mockito.doCallRealMethod().when(converter).setActivationWindowStartAndEndDates(any(Item.class),any(Date.class) );
        Item item = ItemTestUtil.getTestPayAsYouGoItem();
        item.setStartDate(DateTestUtil.getAUG_14_2014_AT_10_45());
        
        converter.setActivationWindowStartAndEndDates(item,DateTestUtil.getAugust_1st());
        
        assertEquals(DateTestUtil.getDate(DAYINMONTH_SHORTMONTH_YEAR,item.getActivationWindowStartDate()),DateTestUtil.getAug02());
        assertEquals(DateTestUtil.getDate(DAYINMONTH_SHORTMONTH_YEAR, item.getActivationWindowExpiryDate()),DateTestUtil.getAug09_AT_0000());
    }    
    
    @Test
    public void testInitialisation(){
        assertNotNull(new ItemConverterImpl());
    }
}
