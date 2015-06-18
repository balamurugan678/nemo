package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO2;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards1List;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.TransferTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TransferSourceCardService;
import com.novacroft.nemo.tfl.common.application_service.TransferTargetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.TransferProductValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for TransferProduct Controller
 */
public class TransferProductControllerTest {
    private TransferProductController controller;
    private CartCmdImpl mockCmd;
    private CartService mockCartService;
    private CardDataService mockCardDataService;
    private TransferTargetCardService mockTransferTargetCardService;
    private TransferSourceCardService mockTransferSourceCardService;
    private TransferProductValidator mockTransferProductValidator;
    private HttpSession mockHttpSession;
    private Model mockModel;
    private BeanPropertyBindingResult mockResult;
    private CartDTO cartDTO;
    private List<CardDTO> mockList;
    private List<String> mockSourceList;
    private List<String> mockRuleBreachesList;
    private CardDTO cardDTO;
    
    @Before
    public void setUp() {
        controller = new TransferProductController();

        mockCmd = mock(CartCmdImpl.class);
        mockTransferProductValidator = mock(TransferProductValidator.class);
        mockCartService = mock(CartService.class);
        mockTransferTargetCardService= mock(TransferTargetCardService.class);
        mockTransferSourceCardService=mock(TransferSourceCardService.class);
        mockHttpSession = mock(HttpSession.class);
        mockModel = mock(Model.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        cartDTO = CartTestUtil.getTestCartDTO1();
        mockCardDataService = mock(CardDataService.class);
        mockList = new ArrayList<CardDTO>();
        mockSourceList=new ArrayList<String>();
        mockRuleBreachesList=new ArrayList<String>();
        
        controller.cartService = mockCartService;
        controller.transferProductValidator = mockTransferProductValidator;
        controller.cardDataService = mockCardDataService;
        controller.transferTargetCardService = mockTransferTargetCardService;
        controller.transferSourceCardService=mockTransferSourceCardService;
        
        doNothing().when(mockTransferProductValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(controller.getCartCmd());
    }
    
    @Test
    public void testPopulateCardsSelectList() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        cardDTO = getTestCardDTO1();
        mockList.add(cardDTO);
        mockList.add(getTestCardDTO1());
        mockList.add(getTestCardDTO2());
        when(mockCardDataService.getAllCardsFromUserExceptCurrent(anyString())).thenReturn(mockList);
        when(mockTransferTargetCardService.isEligibleAsTargetCard(anyString(), anyString())).thenReturn(Boolean.TRUE);
        when(mockTransferTargetCardService.isEligibleAsTargetCard(anyString(), anyString())).thenReturn(Boolean.TRUE);
        controller.populateCardsSelectList(mockHttpSession, mockModel);
        verify(mockModel).addAttribute(anyString(), anyObject());
    }
    
    @Test
    public void shouldShowTransferProduct() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockSourceList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, getTestCards1List(), CardTestUtil.getTestCardDTO1().getCardNumber(), TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    }
    
    @Test
    public void shouldShowTransferProductWithRuleBreaches() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_1);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_2);
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockRuleBreachesList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, getTestCards1List(), CardTestUtil.getTestCardDTO1().getCardNumber(), TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    }
    
    @Test
    public void transferShouldShowCollectPurchase() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        ModelAndView result = controller.showCollectPurchase(mockHttpSession, mockModel, mockCmd, mockResult);
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void transferShouldNotShowCollectPurchase() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockResult.hasErrors()).thenReturn(Boolean.TRUE);
        ModelAndView result = controller.showCollectPurchase(mockHttpSession, mockModel, mockCmd, mockResult);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    }
    
    @Test
    public void transferShouldShowAddExistingCardToAccount() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        ModelAndView result = controller.showAddExistingCardToAccount(mockHttpSession, mockCmd, mockResult);
        assertEquals(PageUrl.ADD_EXISTING_CARD_TO_ACCOUNT, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void transferShouldShowCancel() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldShowTransferProductWithDiscountRuleBreaches() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_1);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_2);
        mockRuleBreachesList.add(ContentCode.TRANSFERS_CARD_DISCOUNTED.textCode());
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockRuleBreachesList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, getTestCards1List(), CardTestUtil.getTestCardDTO1().getCardNumber(), TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName()); 
    }
    
    @Test
    public void shouldShowTransferProductWithOnlyDiscountRuleBreaches() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        mockRuleBreachesList.add(ContentCode.TRANSFERS_CARD_DISCOUNTED.textCode());
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockRuleBreachesList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, getTestCards1List(), CardTestUtil.getTestCardDTO1().getCardNumber(), TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName()); 
    }
    
    @Test
    public void getLostOrStolenModeTrue() {
        CartCmdImpl cartCmd= new CartCmdImpl();
        controller.setLostOrStolenMode(TransferConstants.LOST_STOLEN_MODE, cartCmd);
        assertTrue(cartCmd.isLostOrStolenMode());
    }
    
    @Test
    public void getLostOrStolenModeFalse() {
        CartCmdImpl cartCmd= new CartCmdImpl();
        controller.setLostOrStolenMode(TransferConstants.NOT_APPLICABLE, cartCmd);
        assertFalse(cartCmd.isLostOrStolenMode());
    }
}
