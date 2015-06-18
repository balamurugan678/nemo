package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO7;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO8;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for CollectPurchaseController
 */
public class CollectPurchaseControllerTest {
    private CollectPurchaseController controller;
    private CollectPurchaseController mockController;
 
    private CartCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private PickUpLocationValidator mockPickUpLocationValidator;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private CardInfoResponseV2DTO mockCardInfoResponseV2DTO;
    private PendingItems mockPendingItems;
    private HttpSession mockSession;
    private CartSessionData cartSessionData;
    private CartDTO cartDTO;
    private CartService mockCartService;
    private Model mockModel;
    private LocationSelectListService mockLocationSelectListService;
    private CartCmdImpl cartCmd;
    private CardService mockCardService;
    
    @Before
    public void setUp() {
        controller = new CollectPurchaseController();
        mockController = mock(CollectPurchaseController.class);
        mockCmd = mock(CartCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockPickUpLocationValidator = mock(PickUpLocationValidator.class);
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockCardInfoResponseV2DTO = mock(CardInfoResponseV2DTO.class);
        mockPendingItems = mock(PendingItems.class);
        mockSession = mock(HttpSession.class);
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO(CardTestUtil.CARD_ID);
        cartDTO = CartTestUtil.getTestCartDTO1();
        mockCartService = mock(CartService.class);
        mockCardService = mock(CardService.class);
        mockModel = mock(Model.class);
        mockLocationSelectListService = mock(LocationSelectListService.class);
        cartCmd = new CartCmdImpl();
        
        controller.pickUpLocationValidator = mockPickUpLocationValidator;
        mockController.pickUpLocationValidator = mockPickUpLocationValidator;
        mockController.cardDataService = mockCardDataService;
        mockController.getCardService = mockGetCardService;
        mockController.cartService = mockCartService;
        mockController.locationSelectListService = mockLocationSelectListService;
        mockController.cardService=mockCardService;

        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);

    }

    @Test
    public void showCollectPurchaseWhenPageNotNull() {
        when(mockCmd.getPageName()).thenReturn(Page.COLLECT_PURCHASE);
        doNothing().when(mockController).checkPendingItems(any(CartCmdImpl.class));
        doCallRealMethod().when(mockController).showCollectPurchase(mockSession, mockModel);
        ModelAndView result = mockController.showCollectPurchase(mockSession, mockModel);
        verify(mockCmd, never()).setPageName(anyString());
        assertEquals(PageView.COLLECT_PURCHASE, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
        assertNotNull(((CartCmdImpl) result.getModel().get(PageCommand.CART)).getCardId());
    }

    @Test
    public void collectPurchaseShouldShowCheckoutOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        doCallRealMethod().when(mockController).collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        when(mockController.getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd)).thenReturn(CardTestUtil.CARD_ID);
        when(mockController.setRedirectViewCheckOrTransferSummary(any(CartCmdImpl.class),any(CartSessionData.class))).thenReturn(new ModelAndView(PageUrl.CHECKOUT));
        ModelAndView result = mockController.collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageUrl.CHECKOUT, result.getViewName());
    }

    @Test
    public void collectPurchaseShouldShowCollectPurchaseOnInValidInput() {
        when(mockResult.hasErrors()).thenReturn(true);
        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        doCallRealMethod().when(mockController).collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        when(mockController.getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd)).thenReturn(CardTestUtil.CARD_ID);
        ModelAndView result = mockController.collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageView.COLLECT_PURCHASE, result.getViewName());
    }

    @Test
    public void checkPendingItemsWhenCardDTONotNullAndHasPendingItems() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO7());
        doCallRealMethod().when(mockController).checkPendingItems(mockCmd);
        doNothing().when(mockController).setPendingItemsLocationId(any(CartCmdImpl.class), any(CardInfoResponseV2DTO.class));
        mockController.checkPendingItems(mockCmd);
        verify(mockController, atLeastOnce()).setPendingItemsLocationId(any(CartCmdImpl.class), any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void checkPendingItemsWhenCardDTONotNullAndHasNoPendingItems() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO1());
        doCallRealMethod().when(mockController).checkPendingItems(mockCmd);
        mockController.checkPendingItems(mockCmd);
        verify(mockController, never()).setPendingItemsLocationId(any(CartCmdImpl.class), any(CardInfoResponseV2DTO.class));
    }

    @Test
    public void checkPendingItemsWhenCardDTOIsNull() {
        when(mockCardDataService.findById(anyLong())).thenReturn(null);
        doCallRealMethod().when(mockController).checkPendingItems(mockCmd);
        mockController.checkPendingItems(mockCmd);
        verify(mockGetCardService, never()).getCard(anyString());
    }

    @Test
    public void setPendingItemsLocationIdHasPendingItemsPpv() {
        when(mockCardInfoResponseV2DTO.getPendingItems()).thenReturn(mockPendingItems);
        when(mockPendingItems.getPpvs()).thenReturn(getTestCardInfoResponseV2DTO7().getPendingItems().getPpvs());
        doCallRealMethod().when(mockController).setPendingItemsLocationId(mockCmd, mockCardInfoResponseV2DTO);
        mockController.setPendingItemsLocationId(mockCmd, mockCardInfoResponseV2DTO);
        verify(mockCmd, atLeastOnce()).setStationId(mockPendingItems.getPpvs().get(0).getPickupLocation().longValue());
    }

    @Test
    public void setPendingItemsLocationIdHasPendingItemsPpt() {
        when(mockCardInfoResponseV2DTO.getPendingItems()).thenReturn(mockPendingItems);
        when(mockPendingItems.getPpts()).thenReturn(getTestCardInfoResponseV2DTO6().getPendingItems().getPpts());
        doCallRealMethod().when(mockController).setPendingItemsLocationId(mockCmd, mockCardInfoResponseV2DTO);
        mockController.setPendingItemsLocationId(mockCmd, mockCardInfoResponseV2DTO);
        verify(mockCmd, atLeastOnce()).setStationId(mockPendingItems.getPpts().get(0).getPickupLocation().longValue());
    }

    @Test
    public void setPendingItemsLocationIdHasNoListPpt() {
        when(mockCardInfoResponseV2DTO.getPendingItems()).thenReturn(mockPendingItems);
        when(mockPendingItems.getPpts()).thenReturn(null);
        doCallRealMethod().when(mockController).setPendingItemsLocationId(mockCmd, mockCardInfoResponseV2DTO);
        mockController.setPendingItemsLocationId(mockCmd, mockCardInfoResponseV2DTO);
        verify(mockCmd, never()).setStationId(anyLong());
    }

    @Test
    public void setPendingItemsLocationIdHasListNoListItemPpt() {
        doCallRealMethod().when(mockController).setPendingItemsLocationId(mockCmd, getTestCardInfoResponseV2DTO8());
        mockController.setPendingItemsLocationId(mockCmd, getTestCardInfoResponseV2DTO8());
        verify(mockCmd, never()).setStationId(anyLong());
    }
    
    @Test
    public void getCartCmdShouldReturnCartCmdImpl() {
        CartCmdImpl cartCmdImpl = controller.getCartCmd();
        assertNotNull(cartCmdImpl);
    }

    @Test
    public void populateLocationsSelectList() {
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(new SelectListDTO());
        doCallRealMethod().when(mockController).populateLocationsSelectList(mockModel);
        mockController.populateLocationsSelectList(mockModel);
        verify(mockModel).addAttribute(anyString(), anyObject());
    }

    @Test
    public void getTicketTypeShouldSetAutoTopUp() {
        CartDTO cartDTO = CartTestUtil.getNewCartWithPayAsYouGoItem();
        controller.getTicketType(cartDTO, cartSessionData);
        assertEquals(CartTestUtil.PAY_AS_YOU_GO_AUTO_TOP_UP, cartSessionData.getTicketType());
    }

    @Test
    public void getCardIdForQuickBuyTopupTicketMode() {
        CartSessionData cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTOForQuickBuyMode(CartSessionDataTestUtil.CART_ID_1);
        when(mockController.getFromSession((HttpSession) anyObject(), anyString())).thenReturn(CartTestUtil.CARD_ID_1);
        doCallRealMethod().when(mockController).getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd);
        Long cardId = mockController.getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd);
        assertNotNull(cardId);
        assertEquals(CartTestUtil.CARD_ID_1, cardId);
    }
 
    @Test
    public void getCardIdForQuickBuyTopupTicketModeUsingNewCartService() {
        CartSessionData cartSessionData = CartSessionDataTestUtil
                        .getTestCartSessionDataDTOForQuickBuyModeWithFalse(CartSessionDataTestUtil.CART_ID_1);        
        doCallRealMethod().when(mockController).getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd);
        Long cardId = mockController.getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd);
        assertNotNull(cardId);
        assertEquals(CartTestUtil.CARD_ID_1, cardId);
    }
    
    @Test
    public void getCardIdForTransfetProductModeUsingCardService() {
        CartSessionData cartSessionData = CartSessionDataTestUtil
                        .getTestCartSessionDataDTOForQuickBuyModeWithFalse(CartSessionDataTestUtil.CART_ID_1);        
        doCallRealMethod().when(mockController).getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd);
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CartTestUtil.CARD_ID_1);
        cartSessionData.setTransferProductMode(Boolean.TRUE);
        Long cardId = mockController.getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd);
        assertNotNull(cardId);
        assertEquals(CartTestUtil.CARD_ID_1, cardId);
    }
    
    @Test
    public void getCardIdFromSessionObjectsUsingCartService() {
        CartSessionData cartSessionData = CartSessionDataTestUtil
                        .getTestCartSessionDataDTOForQuickBuyModeWithFalse(CartSessionDataTestUtil.CART_ID_1);        
        doCallRealMethod().when(mockController).getCardIdFromSessionObjects(mockSession, cartSessionData);
        Long cardId = mockController.getCardIdFromSessionObjects(mockSession, cartSessionData);
        assertNotNull(cardId);
        assertEquals(CartTestUtil.CARD_ID_1, cardId);
    }
    
    @Test
    public void getCardIdFromSessionObjectsUsingSession() {
        CartSessionData cartSessionData = CartSessionDataTestUtil
                        .getTestCartSessionDataDTOForQuickBuyModeWithFalse(CartSessionDataTestUtil.CART_ID_1);
        when(mockCartService.findById(anyLong())).thenReturn(null);
        when(mockController.getFromSession((HttpSession) anyObject(), anyString())).thenReturn(CartTestUtil.CARD_ID_1);
        doCallRealMethod().when(mockController).getCardIdFromSessionObjects(mockSession, cartSessionData);
        Long cardId = mockController.getCardIdFromSessionObjects(mockSession, cartSessionData);
        assertNotNull(cardId);
        assertEquals(CartTestUtil.CARD_ID_1, cardId);
    }
    
    @Test
    public void cancelShouldRedirectToShoppingCartTest() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.USER_CART, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void cancelForPayAsYouGoAutoTopUpShouldRedirectToTopUpPage(){
        ModelAndView result = controller.cancelForPayAsYouGoAutoTopUp();
        assertEquals(PageUrl.TOP_UP_TICKET, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void setCollectPurchaseStartAndEndDatesForPayAsYouGoItem() {
        doCallRealMethod().when(mockController).setCollectPurchaseStartAndEndDates(any(CartDTO.class), any(CartCmdImpl.class));
        doCallRealMethod().when(mockController).setPickWindowStartAndEndDates(any(CartCmdImpl.class), any(Date.class));
        cartDTO = CartTestUtil.getNewCartWithPayAsYouGoItem();

        mockController.setCollectPurchaseStartAndEndDates(cartDTO, cartCmd);
        verify(mockController, atLeastOnce()).setPickWindowStartAndEndDates(any(CartCmdImpl.class));
    }

    @Test
    public void setCollectPurchaseStartAndEndDatesForProductItem() {
        doCallRealMethod().when(mockController).setCollectPurchaseStartAndEndDates(any(CartDTO.class), any(CartCmdImpl.class));
        doCallRealMethod().when(mockController).setPickWindowStartAndEndDates(any(CartCmdImpl.class), any(Date.class));
        cartDTO = CartTestUtil.getNewCartDTOWithProductItem();

        mockController.setCollectPurchaseStartAndEndDates(cartDTO, cartCmd);
        verify(mockController, atLeastOnce()).setPickWindowStartAndEndDates(any(CartCmdImpl.class), any(Date.class));
    }

    @Test
    public void addCartDTOToModel() {
        when(mockModel.addAttribute(anyString(), any(CartDTO.class))).thenReturn(mockModel);
        doCallRealMethod().when(mockController).addCartDTOToModel(any(Model.class), any(CartDTO.class));

        mockController.addCartDTOToModel(mockModel, cartDTO);
        verify(mockModel).addAttribute(anyString(), any(CartDTO.class));
    }
    
    @Test
    public void collectPurchaseShouldShowCollectPurchaseOnInValidInputSetPickStartEndDates() {
        when(mockResult.hasErrors()).thenReturn(true);
        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        doCallRealMethod().when(mockController).collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        doCallRealMethod().when(mockController).setCollectPurchaseStartAndEndDates(any(CartDTO.class), any(CartCmdImpl.class));
        doCallRealMethod().when(mockController).setPickWindowStartAndEndDates(any(CartCmdImpl.class), any(Date.class));
        cartDTO = CartTestUtil.getNewCartDTOWithProductItem();
        
        ModelAndView result = mockController.collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageView.COLLECT_PURCHASE, result.getViewName());
    }
    
    @Test
    public void collectPurchaseShouldShowTransferSummaryOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        doCallRealMethod().when(mockController).collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        when(mockController.getCardIdForQuickBuyTopupTicketMode(mockSession, cartSessionData, mockCmd)).thenReturn(CardTestUtil.CARD_ID);
        when(mockController.setRedirectViewCheckOrTransferSummary(any(CartCmdImpl.class),any(CartSessionData.class))).thenReturn(new ModelAndView(PageUrl.TRANSFER_SUMMARY));
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO4());
        ModelAndView result = mockController.collectPurchase(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageUrl.TRANSFER_SUMMARY, result.getViewName());
    }
    
    @Test
    public void collectPurchaseShouldSetRedirectViewTransferSummary() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO4());
        when(mockController.setRedirectViewCheckOrTransferSummary(any(CartCmdImpl.class), any(CartSessionData.class))).thenCallRealMethod();
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO4();
        ModelAndView result = mockController.setRedirectViewCheckOrTransferSummary(mockCmd, cartSessionData);
        assertEquals(PageUrl.TRANSFER_SUMMARY, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void collectPurchaseShouldSetRedirectViewCheckOut() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO4());
        when(mockController.setRedirectViewCheckOrTransferSummary(any(CartCmdImpl.class), any(CartSessionData.class))).thenCallRealMethod();
        ModelAndView result = mockController.setRedirectViewCheckOrTransferSummary(mockCmd, cartSessionData);
        assertEquals(PageUrl.CHECKOUT, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void setCollectPurchaseStartAndEndDatesForTransferProduct() {
        doCallRealMethod().when(mockController).setCollectPurchaseStartAndEndDates(any(CartDTO.class), any(CartCmdImpl.class));
        cartDTO = CartTestUtil.getNewCartDTOWithProductItem();
        cartCmd.setPageName(Page.TRANSFER_PRODUCT);
        mockController.setCollectPurchaseStartAndEndDates(cartDTO, cartCmd);
        verify(mockController, atLeastOnce()).setCollectPurchaseStartAndEndDates(any(CartDTO.class), any(CartCmdImpl.class));
    }
    
    @Test
    public void setCollectPurchasesetPickWindowStartAndEndDates() {
        doCallRealMethod().when(mockController).setPickWindowStartAndEndDates(any(CartCmdImpl.class));
        cartCmd.setPageName(Page.TRANSFER_PRODUCT);
        mockController.setPickWindowStartAndEndDates(cartCmd);
        verify(mockController, atLeastOnce()).setPickWindowStartAndEndDates(any(CartCmdImpl.class));
    }
    
    @Test
    public void cancelShouldRedirectToTransferProduct() {
        ModelAndView result = controller.cancelForTransferProduct();
        assertEquals(PageUrl.TRANSFER_PRODUCT, ((RedirectView) result.getView()).getUrl());
    }
}
