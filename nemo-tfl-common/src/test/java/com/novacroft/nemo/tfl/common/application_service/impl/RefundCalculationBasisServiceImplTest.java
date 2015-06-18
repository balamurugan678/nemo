package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.RefundTestUtil.DATE_TODAY;
import static com.novacroft.nemo.test_support.RefundTestUtil.DATE_TODAY_FOR_DAY_LIGHT_SAVINGS;
import static com.novacroft.nemo.test_support.RefundTestUtil.EXPIRY_DATE_MORE_THAN_5_DAYS_FROM_DATE_TODAY;
import static com.novacroft.nemo.test_support.RefundTestUtil.EXPIRY_DATE_MORE_THAN_5_DAYS_FROM_DATE_TODAY_FOR_DAY_LIGHT_SAVINGS;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.ORDINARY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class RefundCalculationBasisServiceImplTest {
	private RefundCalculationBasisServiceImpl service;
	
	private ZoneService mockZoneService;
	private ProductItemDTO mockProductItemDTO;
	private ProductItemDTO mockTradedTicketDTO;
	
	private CartService mockCartService;
	
	private String mockRefundCalculationBasisDummy=null;
	private String mockRefundCalculationBasisDummyCancelAndSurrender=RefundCalculationBasis.ORDINARY.code();
	private String mockProRataRefundCalculationBasisDummy = RefundCalculationBasis.PRO_RATA.code();	
	private String mockEndDate = DateTestUtil.END_DATE;
	private String mockStartDate = DateTestUtil.START_DATE;
	private String mockcartType="";
	private Long mockCardId=1L;
	private Long mockProductBusPassCardId = 2L;
	private String mockTravelCardType = "";
	private String mockOtherCardType = Durations.OTHER.getDurationType();
	private Integer mockStartZone = CartTestUtil.TRAVEL_START_ZONE_1;
	private Integer mockEndZone = CartTestUtil.TRAVEL_END_ZONE_1;
	private Integer mockHigerPrice = 2;
	private Integer mockLowerPrice = 1;
	private Boolean mockDeceased=false;
	
	@Before
	public void setUp() {
		service = new RefundCalculationBasisServiceImpl();
		        
		mockCartService = mock(CartService.class);
		mockZoneService=mock(ZoneService.class);
		mockProductItemDTO = mock(ProductItemDTO.class);
		mockTradedTicketDTO = mock(ProductItemDTO.class);
		
		service.zoneService = mockZoneService;
		service.cartService = mockCartService;
		when(mockCartService.findNotInWorkFlowFlightCartByCardId(mockCardId)).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
		when(mockCartService.findNotInWorkFlowFlightCartByCardId(mockProductBusPassCardId)).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
	}
	
	@Test
	public void shouldGetProRataRefundCalculationBasis() {
		assertEquals(RefundCalculationBasis.PRO_RATA.code(),
		                service.getRefundCalculationBasis(
		                                mockRefundCalculationBasisDummy, mockEndDate, mockcartType,
		                                mockCardId, mockTravelCardType, mockStartDate, mockStartZone,
		                                mockEndZone, mockDeceased));
	}
	
	@Test
	public void shouldGetOrdinaryRefundCalculationBasis() {
	    assertEquals(RefundCalculationBasis.ORDINARY.code(),
	                    service.getRefundCalculationBasis(
	                                    mockRefundCalculationBasisDummyCancelAndSurrender, mockEndDate, mockcartType,
	                                    mockCardId, mockTravelCardType, mockStartDate, mockStartZone,
	                                    mockEndZone, mockDeceased));
	}
	
	@Test
	public void getRefundCalculationBasisIfMoreThanFiveDaysRemainingForTravelCardShouldReturnOrdinaryRefundCalculationBasis() {
	    assertEquals(ORDINARY.code(),
	                    service.getRefundCalculationBasisIfMoreThanFiveDaysRemainingForTravelCard(DATE_TODAY,
	                                    EXPIRY_DATE_MORE_THAN_5_DAYS_FROM_DATE_TODAY));
	}

	@Test
	public void getRefundCalculationBasisIfLessThanFiveDaysRemainingForTravelCardForDaylightSavingsShouldReturnOrdinaryRefundCalculationBasis() {
	    assertEquals(ORDINARY.code(),
	                    service.getRefundCalculationBasisIfMoreThanFiveDaysRemainingForTravelCard(
	                                    DATE_TODAY_FOR_DAY_LIGHT_SAVINGS,
	                                    EXPIRY_DATE_MORE_THAN_5_DAYS_FROM_DATE_TODAY_FOR_DAY_LIGHT_SAVINGS));
	}
	
	@Test
    public void shouldGetProRataRefundCalculationBasiIfNullDeceased() {
        assertEquals(RefundCalculationBasis.PRO_RATA.code(),
                        service.getRefundCalculationBasis(
                                        mockRefundCalculationBasisDummy, mockEndDate, mockcartType,
                                        mockCardId, mockTravelCardType, mockStartDate, mockStartZone,
                                        mockEndZone, null));
    }
	
	@Test
    public void shouldGetProRataRefundCalculationBasisIfDeceased() {
        assertEquals(RefundCalculationBasis.PRO_RATA.code(),
                        service.getRefundCalculationBasis(
                                        mockRefundCalculationBasisDummy, mockEndDate, mockcartType,
                                        mockCardId, mockTravelCardType, mockStartDate, mockStartZone,
                                        mockEndZone, Boolean.TRUE));
    }
	
	@Test
    public void shouldGetProRataRefundCalculationBasisIfLostCartTypeAndOverlapped() {
        when(mockZoneService.isZonesOverlapWithProductItemDTOZones(
                        anyInt(), anyInt(), any(ProductItemDTO.class), any(Date.class), any(Date.class))
            ).thenReturn(true);
        assertEquals(RefundCalculationBasis.PRO_RATA.code(), 
                        service.getRefundCalculationBasis(
                                        mockRefundCalculationBasisDummy, mockEndDate, CartType.LOST_REFUND.code(),
                                        mockProductBusPassCardId, mockTravelCardType, mockStartDate, mockStartZone,
                                        mockEndZone, mockDeceased));
    }
	
	@Test
    public void shouldGetProRataRefundCalculationBasisIfStolenCartTypeAndOverlapped() {
	    when(mockZoneService.isZonesOverlapWithProductItemDTOZones(
                        anyInt(), anyInt(), any(ProductItemDTO.class), any(Date.class), any(Date.class))
            ).thenReturn(true);
        assertEquals(RefundCalculationBasis.PRO_RATA.code(),
                        service.getRefundCalculationBasis(
                                        mockRefundCalculationBasisDummy, mockEndDate, CartType.STOLEN_REFUND.code(),
                                        mockCardId, mockTravelCardType, mockStartDate, mockStartZone,
                                        mockEndZone, mockDeceased));
    }
	
	@Test
	public void shouldGetOrdinaryRefundCalculationBasisIfCancelAndSurrenderCartType() {
	    assertEquals(RefundCalculationBasis.ORDINARY.code(),
	                    service.getRefundCalculationBasis(
	                                    mockProRataRefundCalculationBasisDummy, mockEndDate, CartType.CANCEL_SURRENDER_REFUND.code(),
	                                    mockCardId, mockTravelCardType, mockStartDate, mockStartZone,
	                                    mockEndZone, mockDeceased));
	}
	
	@Test
	public void shouldGetTradeDownRefundCalculationBasis() {
	    when(mockProductItemDTO.getPrice()).thenReturn(mockHigerPrice);
	    when(mockTradedTicketDTO.getPrice()).thenReturn(mockLowerPrice);
	    assertEquals(RefundCalculationBasis.TRADE_DOWN.code(),
                        service.getRefundCalculationBasisForTradedTickets(mockProductItemDTO, mockTradedTicketDTO));
	}
	
	@Test
    public void shouldGetTradeUpRefundCalculationBasis() {
        when(mockProductItemDTO.getPrice()).thenReturn(mockLowerPrice);
        when(mockTradedTicketDTO.getPrice()).thenReturn(mockHigerPrice);
        assertEquals(RefundCalculationBasis.TRADE_UP.code(),
                        service.getRefundCalculationBasisForTradedTickets(mockProductItemDTO, mockTradedTicketDTO));
    }
	
	@Test
	public void checkTravelCardOverlapNonProductItem() {
	    assertFalse(service.checkTravelCardOverlapWithAlreadyAddedTravelCardZones(
	                    mockEndDate, mockRefundCalculationBasisDummy, mockCardId, mockOtherCardType, 
	                    mockEndDate, mockStartZone, mockEndZone));
	}
	
	@Test
    public void checkTravelCardOverlapProductItemBussPassZoneNotOverlapped() {
        assertFalse(service.checkTravelCardOverlapWithAlreadyAddedTravelCardZones(
                        mockEndDate, mockRefundCalculationBasisDummy, mockProductBusPassCardId, mockTravelCardType, 
                        mockEndDate, mockStartZone, mockEndZone));
    }
    
    @Test
    public void checkTravelCardOverlapProductItemBussPassZoneOverlapped() {
        when(mockZoneService.isZonesOverlapWithProductItemDTOZones(
                        anyInt(), anyInt(), any(ProductItemDTO.class), any(Date.class), any(Date.class))
            ).thenReturn(true);
        assertTrue(service.checkTravelCardOverlapWithAlreadyAddedTravelCardZones(
                        mockEndDate, mockRefundCalculationBasisDummy, mockProductBusPassCardId, mockTravelCardType, 
                        mockEndDate, mockStartZone, mockEndZone));
    }
}
