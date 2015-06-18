package com.novacroft.nemo.tfl.common.application_service.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.test_support.ProductTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.OddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.ProductDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class TradedTravelCardServiceImplTest {
    
    private TradedTravelCardServiceImpl tradedTravelCardServiceImpl;
    private ProductDataService mockProductDataService;
    private RefundCalculationBasisService mockRefundCalculationBasisService;
    private OddPeriodTravelCardService mockOddPeriodTravelCardService;
    private AddUnlistedProductService mockAddUnlistedProductService;
    private ZoneService mockZoneService;
    
    @Before
    public void setUp() {
        tradedTravelCardServiceImpl = new TradedTravelCardServiceImpl();
        mockProductDataService = mock(ProductDataServiceImpl.class);
        mockRefundCalculationBasisService = mock(RefundCalculationBasisServiceImpl.class);
        mockOddPeriodTravelCardService = mock(OddPeriodTravelCardServiceImpl.class);
        mockAddUnlistedProductService = mock(AddUnlistedProductServiceImpl.class);
        mockZoneService = mock(ZoneService.class);
        
        tradedTravelCardServiceImpl.productDataService = mockProductDataService;
        tradedTravelCardServiceImpl.refundCalculationBasisService = mockRefundCalculationBasisService;
        tradedTravelCardServiceImpl.oddPeriodTravelCardService = mockOddPeriodTravelCardService;
        tradedTravelCardServiceImpl.addUnlistedProductService = mockAddUnlistedProductService;
        tradedTravelCardServiceImpl.zoneService = mockZoneService;
    }
    
    @Test
    public void getTravelCardItemForTradedTicket() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        CartItemCmdImpl cartItemCmd = CartItemTestUtil.getTestTopUpTicketTravelCardWithExchangeCmd1();
        cartItemCmd.getTradedTicket().setExchangedDate(new DateTime());
        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductTestUtil.getTestProductDTO1());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductTestUtil.getTestProductDTO1());
        doNothing().when(mockAddUnlistedProductService).setTravelcardTypeByFormTravelCardType(any(CartItemCmdImpl.class));
        when(mockRefundCalculationBasisService.getRefundCalculationBasis(anyString(),anyString(), anyLong(), anyString(), anyString(),any(Integer.class),any(Integer.class),anyBoolean(),anyBoolean()))
        .thenReturn(RefundCalculationBasis.ORDINARY.code());
        when(mockOddPeriodTravelCardService.getOddPeriodTicketPrice(any(CartItemCmdImpl.class))).thenReturn(29840);
        when(mockZoneService.isZonesOverlapWithProductItemDTOZones(anyInt(), anyInt(), any(ProductItemDTO.class), any(Date.class), any(Date.class))).thenReturn(Boolean.TRUE);
        
       ProductItemDTO tradedProductItemDTO = tradedTravelCardServiceImpl.getTravelCardItemForTradedTicket(cartItemCmd, productItemDTO);
       
       assertNotNull(tradedProductItemDTO);
       assertTrue(tradedProductItemDTO.isTicketOverlapped());
    }
    
    @Test
    public void getTravelCardItemForTradedTicket1() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        CartItemCmdImpl cartItemCmd = CartItemTestUtil.getTestTopUpTicketTravelCardWithExchangeCmd1();
        cartItemCmd.getTradedTicket().setTravelCardType(Durations.SEVEN_DAYS.getDurationType());
        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductTestUtil.getTestProductDTO1());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductTestUtil.getTestProductDTO1());
        doNothing().when(mockAddUnlistedProductService).setTravelcardTypeByFormTravelCardType(any(CartItemCmdImpl.class));
        when(mockRefundCalculationBasisService.getRefundCalculationBasis(anyString(),anyString(), anyLong(), anyString(), anyString(),any(Integer.class),any(Integer.class),anyBoolean(),anyBoolean()))
        .thenReturn(RefundCalculationBasis.ORDINARY.code());
        when(mockOddPeriodTravelCardService.getOddPeriodTicketPrice(any(CartItemCmdImpl.class))).thenReturn(29840);
       ProductItemDTO tradedProductItemDTO = tradedTravelCardServiceImpl.getTravelCardItemForTradedTicket(cartItemCmd, productItemDTO);
       
        assertEquals(cartItemCmd.getTradedTicket().getStartZone(),tradedProductItemDTO.getStartZone());
        assertEquals(cartItemCmd.getTradedTicket().getEndZone(),tradedProductItemDTO.getEndZone());
    }
    
    
    @Test
    public void shouldreturnTrueForOtherTravelcardType(){
       assertTrue(tradedTravelCardServiceImpl.isTravelcardTypeOther(Durations.OTHER.getDurationType()));
    }
    
    @Test
    public void shouldreturnFalseForNonOtherTravelcardType(){
       assertFalse(tradedTravelCardServiceImpl.isTravelcardTypeOther(CartAttribute.OTHER_TRAVEL_CARD));
    }

}
