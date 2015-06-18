package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.test_support.ProductTestUtil;
import com.novacroft.nemo.test_support.RefundTestUtil;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundEngineService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ProductItemRefundCalculationServiceImplTest {

    private ProductItemRefundCalculationServiceImpl refundService;

    private ProductDataService mockProductDataService;
    private RefundEngineService mockRefundEngineService;
    private RefundCalculationBasisService mockRefundCalculationBasisService;

    private Refund mockNonTradedTicketRefund;
    private Refund mockOtherTradedTicketRefund;
    private Refund mockAnnualTradedTicketRefund;

    @Before
    public void setUp() {
        mockNonTradedTicketRefund = new Refund();
        mockOtherTradedTicketRefund = RefundTestUtil.getTestRefund1();
        mockAnnualTradedTicketRefund = RefundTestUtil.getTestRefund1();

        mockProductDataService = mock(ProductDataService.class);
        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(ProductTestUtil.getTestProductDTO1());
        when(
                        mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                                        any(Date.class), anyString(), anyString(), anyString())).thenReturn(new ProductDTO());

        mockRefundEngineService = mock(RefundEngineService.class);
        when(
                        mockRefundEngineService.calculateRefund(any(DateTime.class), any(DateTime.class), any(DateTime.class),
                                        any(RefundCalculationBasis.class), any(ProductDTO.class))).thenReturn(mockNonTradedTicketRefund);

        mockRefundCalculationBasisService = mock(RefundCalculationBasisService.class);
        when(mockRefundCalculationBasisService.getRefundCalculationBasisForTradedTickets(anyInt(), anyInt())).thenReturn(
                        RefundCalculationBasis.PRO_RATA.code());

        refundService = new ProductItemRefundCalculationServiceImpl();
        refundService.productDataService = mockProductDataService;
        refundService.refundEngineService = mockRefundEngineService;
        refundService.refundCalculationBasisService = mockRefundCalculationBasisService;
    }

    @Test
    public void testCalculateRefundIfNonTradedTicket() {
        ProductItemDTO zeroZoneProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        zeroZoneProductItem.setStartZone(0);
        zeroZoneProductItem.setEndZone(0);
        zeroZoneProductItem.setDateOfRefund(new Date());
        assertSame(mockNonTradedTicketRefund, refundService.calculateRefund(zeroZoneProductItem));
    }
    
    @Test
    public void testCalculateRefundWithNullTicketPriceForOtherDuration() {
        ProductDTO productDTO = ProductTestUtil.getTestProductDTO1();
        productDTO.setDuration(Durations.OTHER.getDurationType());
        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(productDTO);
        ProductItemDTO zeroZoneProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        zeroZoneProductItem.setStartZone(0);
        zeroZoneProductItem.setEndZone(0);
        zeroZoneProductItem.setPrice(null);
        assertSame(mockNonTradedTicketRefund.getRefundAmount(), refundService.calculateRefund(zeroZoneProductItem).getRefundAmount());
    }
    
    @Test
    public void testCalculateRefundWithStartDateSameAsDateOfRefund() {
        ProductDTO productDTO = ProductTestUtil.getTestProductDTO1();
        productDTO.setDuration(Durations.MONTH.getDurationType());
        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(productDTO);
        when(mockRefundEngineService.calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), any(ProductDTO.class),
                                        any(ProductDTO.class), any(DateTime.class), any(DateTime.class), any(RefundCalculationBasis.class)))
                        .thenReturn(mockAnnualTradedTicketRefund);
        ProductItemDTO zeroZoneProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        zeroZoneProductItem.setStartZone(0);
        zeroZoneProductItem.setEndZone(0);
        zeroZoneProductItem.setDateOfRefund(zeroZoneProductItem.getStartDate());
        assertEquals(new Long(productDTO.getTicketPrice()), refundService.calculateRefund(zeroZoneProductItem).getRefundAmount());
    }
    
    @Test
    public void testCalculateRefundIfTradedTicketOtherType() {
        when(mockRefundEngineService.findTravelCardTypeByDuration(any(DateTime.class), any(DateTime.class))).thenReturn(Durations.OTHER.getDurationType());
        when(
                        mockRefundEngineService.calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), any(ProductDTO.class),
                                        any(ProductDTO.class), any(DateTime.class), any(DateTime.class), any(RefundCalculationBasis.class)))
                        .thenReturn(mockOtherTradedTicketRefund);
        ProductItemDTO otherTravelProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        otherTravelProductItem.setDateOfRefund(new Date());
        otherTravelProductItem.setRelatedItem(ProductItemTestUtil.getTestTravelCardProductDTO_RefundExample1());
        assertSame(mockOtherTradedTicketRefund, refundService.calculateRefund(otherTravelProductItem));
    }

    @Test
    public void testCalculateRefundIfTradedTicketNonOtherType() {
        when(mockRefundEngineService.findTravelCardTypeByDuration(any(DateTime.class), any(DateTime.class))).thenReturn(RefundConstants.ANNUAL);
        when(
                        mockRefundEngineService.calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), any(ProductDTO.class),
                                        any(ProductDTO.class), any(DateTime.class), any(DateTime.class), any(RefundCalculationBasis.class)))
                        .thenReturn(mockAnnualTradedTicketRefund);
        ProductItemDTO annualTravelProductItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        annualTravelProductItem.setDateOfRefund(new Date());
        annualTravelProductItem.setRelatedItem(ProductItemTestUtil.getTestTravelCardProductDTO_RefundExample1());
        assertSame(mockAnnualTradedTicketRefund, refundService.calculateRefund(annualTravelProductItem));
    }

    @Test
    public void testCalculateRefundForTradedTicketIfRefundBasisIsTradeUp() {
        when(mockRefundCalculationBasisService.getRefundCalculationBasisForTradedTickets(anyInt(), anyInt())).thenReturn(
                        RefundCalculationBasis.TRADE_UP.code());
        when(mockRefundEngineService.findTravelCardTypeByDuration(any(DateTime.class), any(DateTime.class))).thenReturn(Durations.OTHER.getDurationType());
        when(
                        mockRefundEngineService.calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), any(ProductDTO.class),
                                        any(ProductDTO.class), any(DateTime.class), any(DateTime.class), any(RefundCalculationBasis.class)))
                        .thenReturn(mockOtherTradedTicketRefund);
        ProductItemDTO otherTravelProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        otherTravelProductItem.setRefundCalculationBasis(RefundCalculationBasis.TRADE_UP.code());
        otherTravelProductItem.setDateOfRefund(new Date());
        otherTravelProductItem.setRelatedItem(ProductItemTestUtil.getTestTravelCardProductDTO_RefundExample1());
        assertSame(mockOtherTradedTicketRefund, refundService.calculateRefund(otherTravelProductItem));
    }
    
    @Test
    public void testCalculateRefundForTradedTicketWithNotNullTradedDate() {
        when(mockRefundCalculationBasisService.getRefundCalculationBasisForTradedTickets(anyInt(), anyInt())).thenReturn(
                        RefundCalculationBasis.TRADE_UP.code());
        when(mockRefundEngineService.findTravelCardTypeByDuration(any(DateTime.class), any(DateTime.class))).thenReturn(Durations.OTHER.getDurationType());
        when(
                        mockRefundEngineService.calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), any(ProductDTO.class),
                                        any(ProductDTO.class), any(DateTime.class), any(DateTime.class), any(RefundCalculationBasis.class)))
                        .thenReturn(mockOtherTradedTicketRefund);
        ProductItemDTO otherTravelProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        otherTravelProductItem.setRefundCalculationBasis(RefundCalculationBasis.TRADE_UP.code());
        otherTravelProductItem.setDateOfRefund(new Date());
        ProductItemDTO realtedItem = ProductItemTestUtil.getTestTravelCardProductDTO_RefundExample1();
        realtedItem.setTradedDate(new Date());
        otherTravelProductItem.setRelatedItem(realtedItem);
        assertSame(mockOtherTradedTicketRefund, refundService.calculateRefund(otherTravelProductItem));
    }

    @Test
    public void testCalculateRefundForTradedTicketIfRefundBasisIsTradeDown() {
        when(mockRefundCalculationBasisService.getRefundCalculationBasisForTradedTickets(anyInt(), anyInt())).thenReturn(
                        RefundCalculationBasis.TRADE_DOWN.code());
        when(mockRefundEngineService.findTravelCardTypeByDuration(any(DateTime.class), any(DateTime.class))).thenReturn(Durations.OTHER.getDurationType());
        when(
                        mockRefundEngineService.calculateRefundTradeUpOrDown(any(DateTime.class), any(DateTime.class), any(ProductDTO.class),
                                        any(ProductDTO.class), any(DateTime.class), any(DateTime.class), any(RefundCalculationBasis.class)))
                        .thenReturn(mockOtherTradedTicketRefund);
        ProductItemDTO otherTravelProductItem = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        otherTravelProductItem.setRefundCalculationBasis(RefundCalculationBasis.TRADE_DOWN.code());
        otherTravelProductItem.setDateOfRefund(new Date());
        otherTravelProductItem.setRelatedItem(ProductItemTestUtil.getTestTravelCardProductDTO_RefundExample1());
        assertSame(mockOtherTradedTicketRefund, refundService.calculateRefund(otherTravelProductItem));
    }

    @Test
    public void testUpdateOrdinaryRefundCalculationBasisAfterTradeUp() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItemDTO.setRefundCalculationBasis(RefundCalculationBasis.ORDINARY.code());
        RefundCalculationBasis actualRefundBasis = refundService.updateRefundCalculationBasisAfterTradeUp(RefundCalculationBasis.ORDINARY,
                        productItemDTO);
        assertEquals(RefundCalculationBasis.ORDINARY_AFTER_TRADE_UP, actualRefundBasis);

    }

    @Test
    public void testUpdateProrataRefundCalculationBasisAfterTradeUp() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItemDTO.setRefundCalculationBasis(RefundCalculationBasis.PRO_RATA.code());
        RefundCalculationBasis actualRefundBasis = refundService.updateRefundCalculationBasisAfterTradeUp(RefundCalculationBasis.PRO_RATA,
                        productItemDTO);
        assertEquals(RefundCalculationBasis.TRADE_UP, actualRefundBasis);

    }

    @Test
    public void testUpdateOrdinaryRefundCalculationBasisAfterTradeDown() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItemDTO.setRefundCalculationBasis(RefundCalculationBasis.ORDINARY.code());
        RefundCalculationBasis actualRefundBasis = refundService.updateRefundCalculationBasisAfterTradeDown(RefundCalculationBasis.ORDINARY,
                        productItemDTO);
        assertEquals(RefundCalculationBasis.ORDINARY_AFTER_TRADE_DOWN, actualRefundBasis);

    }

    @Test
    public void testUpdateProrataRefundCalculationBasisAfterTradeDown() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItemDTO.setRefundCalculationBasis(RefundCalculationBasis.PRO_RATA.code());
        RefundCalculationBasis actualRefundBasis = refundService.updateRefundCalculationBasisAfterTradeDown(RefundCalculationBasis.PRO_RATA,
                        productItemDTO);
        assertEquals(RefundCalculationBasis.TRADE_DOWN, actualRefundBasis);
    }

    @Test
    public void testGetProductForProductItemDTO() {
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItemDTO.setProductId(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductTestUtil.getTestProductDTO1());
        ProductDTO productDTO = refundService.getProductForProductItemDTO(productItemDTO, new DateTime(), new DateTime());
        assertNotNull(productDTO);
    }
    
    @Test
    public void shouldReturnNullGetProductForProductItemDTO(){
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setStartZone(0);
        productItemDTO.setEndZone(0);
        ProductDTO productDTO = refundService.getProductForProductItemDTO(productItemDTO, new DateTime(), new DateTime());
        assertNull(productDTO);
    }
}
