package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.BALANCE_1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public class CubicCardDetailsToCartItemCmdImplConverterImplTest {
    private static final String TEST_PREPAY_TICKET_NUMBER = "10001";
    private static final String TEST_PREPAY_VALUE_NUMBER = "20002";
    
    private CubicCardDetailsToCartItemCmdImplConverterImpl converter;
    private GetCardService mockGetCardService;
    private CardDataService mockCardDataService;
    private RefundCalculationBasisService mockRefundService;
    
    private CardInfoResponseV2DTO prepayTicketInfo;
    private CardInfoResponseV2DTO prepayValueInfo;
    
    @Before
    public void setUp() {
        converter = new CubicCardDetailsToCartItemCmdImplConverterImpl();
        mockGetCardService = mock(GetCardService.class);
        mockCardDataService = mock(CardDataService.class);
        mockRefundService = mock(RefundCalculationBasisService.class);
        
        converter.getCardService = mockGetCardService;
        converter.cardDataService = mockCardDataService;
        converter.refundCalculationBasisService = mockRefundService;
        
        prepayTicketInfo = getTestCardInfoResponseV2DTO3();
        prepayValueInfo = getTestCardInfoResponseV2DTO2();
        
        when(mockRefundService.getRefundCalculationBasis(anyString(), anyString(), anyLong(), 
                        anyString(), anyString(), anyInt(), anyInt(), anyBoolean(), anyBoolean()))
                .thenReturn(RefundCalculationBasis.PRO_RATA.code());
    }
    
    @Test
    public void shouldConvertToCartItemCmdImplIfCardNotExist() {
        when(mockGetCardService.getCard(anyString())).thenReturn(new CardInfoResponseV2DTO());
        
        Set<CartItemCmdImpl> actualResult = converter.convertCubicCardDetailsToCartItemCmdImpls(null, null);
        
        verify(mockGetCardService, atLeastOnce()).getCard(null);
        assertEquals(1, actualResult.size());
        
        CartItemCmdImpl actualCmdImpl = actualResult.iterator().next();
        assertEquals(Integer.valueOf(0), actualCmdImpl.getCreditBalance());
    }
    
    @Test
    public void shouldConvertToCartItemCmdImplIfPrepayTicket() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(prepayTicketInfo);
        
        Set<CartItemCmdImpl> actualResult = 
                        converter.convertCubicCardDetailsToCartItemCmdImpls(TEST_PREPAY_TICKET_NUMBER, "");
        
        verify(mockGetCardService, atLeastOnce()).getCard(TEST_PREPAY_TICKET_NUMBER);
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(TEST_PREPAY_TICKET_NUMBER);
        assertEquals(4, actualResult.size());
    }
    
    @Test
    public void shouldConvertToCartItemCmdImplIfPrepayValue() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(prepayValueInfo);
        
        Set<CartItemCmdImpl> actualResult = 
                        converter.convertCubicCardDetailsToCartItemCmdImpls(TEST_PREPAY_VALUE_NUMBER, "");
        
        verify(mockGetCardService, atLeastOnce()).getCard(TEST_PREPAY_VALUE_NUMBER);
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(prepayValueInfo.getPrestigeId());
        assertEquals(1, actualResult.size());
        
        CartItemCmdImpl actualCmdImpl = actualResult.iterator().next();
        assertEquals(BALANCE_1, actualCmdImpl.getCreditBalance());
    }
}
