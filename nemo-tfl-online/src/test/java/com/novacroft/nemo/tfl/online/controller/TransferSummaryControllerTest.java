package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TransferProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for TransferSummary Controller
 */
public class TransferSummaryControllerTest {
    private TransferSummaryController mockController;
    private CartCmdImpl mockCmd;
    private CartService mockCartService;
    private CardService mockCardService;
    private LocationDataService mockLocationDataService;
    private TransferProductService mockTransferProductsPersistenceService;
    private CardDataService mockCardDataService;
    private HttpSession mockHttpSession;
    private BeanPropertyBindingResult mockResult;
    private CartDTO cartDTO;
    
    @Before
    public void setUp() {
        mockController=new TransferSummaryController();
        
        mockCmd = mock(CartCmdImpl.class);
        mockCartService = mock(CartService.class);
        mockCardService = mock(CardService.class);
        mockHttpSession = mock(HttpSession.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        cartDTO = CartTestUtil.getTestCartDTO1();
        mockCardDataService = mock(CardDataService.class);
        mockLocationDataService=mock(LocationDataService.class);
        mockTransferProductsPersistenceService = mock(TransferProductService.class);
                        
        mockController.cardService = mockCardService;
        mockController.locationDataService =mockLocationDataService;
        mockController.transferProductsPersistenceService =mockTransferProductsPersistenceService;
        
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(mockController.getCartCmd());
    }
    
    @Test
    public void transferSummaryCancelShouldShowTransferProduct() {
        ModelAndView result = mockController.cancel();
        assertEquals(PageUrl.TRANSFER_PRODUCT, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldShowTransferSummary() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());
        ModelAndView result = mockController.showTransferSummary(mockHttpSession, mockCmd);
        assertEquals(PageView.TRANSFER_SUMMARY, result.getViewName());
    }
    
    @Test
    public void transferShouldShowTransferConfirmation() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        Map<String, Object> transactionData = new HashMap<String,Object>();
        transactionData.put(TransferConstants.IS_TRANSFER_SUCCESSFUL, Boolean.TRUE);
        when(mockTransferProductsPersistenceService.transferProductFromSourceCardToTargetCard(any(HttpSession.class), anyLong(), anyLong(), anyLong(),anyBoolean())).thenReturn(transactionData);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        ModelAndView result = mockController.showTransferConfirmation(mockHttpSession, mockCmd, redirectAttributes);
        assertEquals(PageUrl.CONFIRMATION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void transferShouldShowTransferProduct() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        Map<String, Object> transactionData = new HashMap<String,Object>();
        transactionData.put(TransferConstants.IS_TRANSFER_SUCCESSFUL, Boolean.FALSE);
        when(mockTransferProductsPersistenceService.transferProductFromSourceCardToTargetCard(any(HttpSession.class), anyLong(), anyLong(), anyLong(),anyBoolean())).thenReturn(transactionData);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        ModelAndView result = mockController.showTransferConfirmation(mockHttpSession, mockCmd, redirectAttributes);
        assertEquals(PageUrl.TRANSFER_PRODUCT, ((RedirectView) result.getView()).getUrl());
    }
    
    
}

