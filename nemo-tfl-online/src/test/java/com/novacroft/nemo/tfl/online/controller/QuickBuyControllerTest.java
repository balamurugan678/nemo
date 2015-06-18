package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards1List;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards2List;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestRenewCartCmd3;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getCartDTOWithProductItems;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithProductItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getUpdatedCartDTOWithProductItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RenewTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.impl.CartServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for QuickBuy Controller
 */
public class QuickBuyControllerTest {
    private QuickBuyController controller;
    private CartCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private CartService mockNewCartService;
    private RenewTravelCardService mockRenewTravelCardService;
    private SelectCardValidator mockSelectCardValidator;
    private HttpSession mockSession;
    private Model mockModel;
    private BindingAwareModelMap model;
    private SelectListService mockSelectListService;
    private QuickBuyController mockController;
    @Before
    public void setUp() {
        controller = new QuickBuyController();

        mockCmd = mock(CartCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockRenewTravelCardService = mock(RenewTravelCardService.class);
        mockSelectCardValidator = mock(SelectCardValidator.class);
        mockNewCartService = mock(CartServiceImpl.class);
        mockSession = mock(HttpSession.class);
        mockModel = mock(Model.class);
        mockSelectListService = mock(SelectListService.class);
        model = new BindingAwareModelMap();
    	mockController = mock(QuickBuyController.class);

        controller.selectCardValidator = mockSelectCardValidator;
        controller.cartService = mockNewCartService;
        controller.renewTravelCardService = mockRenewTravelCardService;
        controller.selectListService = mockSelectListService;
        mockController.cartService = mockNewCartService;  
        		
        doNothing().when(mockSelectCardValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }

    @Test
    public void showQuickBuyShouldShowCardList() {
        ModelAndView result = controller.showQuickBuy(mockSession, mockModel, getTestCards2List(), new CartCmdImpl());
        assertEquals(PageView.QUICK_BUY, result.getViewName());
        assertNull(((CartCmdImpl) result.getModel().get(PageCommand.CART)).getCardId());
    }

    @Test
    public void showQuickBuyShouldAutoSelectSingleCard() {
        when(mockRenewTravelCardService.getCartItemsFromCubic(((CartDTO) anyObject()), anyLong())).thenReturn(getTestRenewCartCmd3());
        when(mockNewCartService.createCartFromCardId(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.showQuickBuy(mockSession, mockModel, getTestCards1List(), new CartCmdImpl());

        verify(mockRenewTravelCardService, atLeastOnce()).getCartItemsFromCubic(((CartDTO) anyObject()), anyLong());
        assertEquals(PageView.QUICK_BUY, result.getViewName());
        assertNotNull(((CartCmdImpl) result.getModel().get(PageCommand.CART)).getCardId());
    }

    @Test
    public void showCardShouldShowErrorWhenNoCardSelected() {
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.selectCard(mockSession, mockModel, new CartCmdImpl(), mockResult);
        assertEquals(PageView.QUICK_BUY, result.getViewName());
    }

    @Test
    public void showCardShouldShowQuickBuyWhenCardSelected() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockRenewTravelCardService.getCartItemsFromCubic(((CartDTO) anyObject()), anyLong())).thenReturn(getTestRenewCartCmd3());
        when(mockNewCartService.createCartFromCardId(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.selectCard(mockSession, mockModel, new CartCmdImpl(), mockResult);

        verify(mockRenewTravelCardService, atLeastOnce()).getCartItemsFromCubic(((CartDTO) anyObject()), anyLong());
        assertEquals(PageView.QUICK_BUY, result.getViewName());
    }

    @Test
    public void renewShouldShowCollectPurchase() {
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.renew(mockSession, mockCmd);
        verify(mockRenewTravelCardService, atLeastOnce()).renewProducts((CartCmdImpl) anyObject(), ((CartDTO) anyObject()));
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void deleteRenewItemShouldShowCart() {
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockNewCartService.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenReturn(
                        ProductItemTestUtil.getTestTravelCardProductDTO1());
        doCallRealMethod().when(mockController).deleteCartItemCmdFromcartCmd(any(CartCmdImpl.class), anyLong());
        doCallRealMethod().when(mockController).deleteRenewItem(any(HttpSession.class), any(CartCmdImpl.class), any(Integer.class));
        when(mockController.addStartDatesSelectListToModelAndView(any(CartCmdImpl.class))).thenCallRealMethod();
        when(mockController.addRenewItemDTOWithCartItems(any(CartCmdImpl.class), any(CartDTO.class))).thenReturn(CartCmdTestUtil.getTestRenewCartCmd4());
        ModelAndView result = mockController.deleteRenewItem(mockSession, mockCmd, 0);
        assertEquals(PageView.QUICK_BUY, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToDashboard() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void testPopulateEmailRemindersSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(null);
        controller.populateEmailRemindersSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.BASKET_EMAIL_REMINDERS));
    }
    
    @Test
    public void shouldDeleteRenewItemWithSpcifiedLineItem() {
    	CartDTO cartDTO  = getCartDTOWithProductItems();
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockNewCartService.findById(anyLong())).thenReturn(cartDTO);
        when(mockController.addRenewItemDTOWithCartItems(any(CartCmdImpl.class), any(CartDTO.class))).thenReturn(CartCmdTestUtil.getTestRenewCartCmd4());
        when(mockNewCartService.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenReturn(
                        ProductItemTestUtil.getTestTravelCardProductDTO1());
        when(mockNewCartService.deleteItem(cartDTO,ItemTestUtil.ITEM_ID)).thenReturn(getUpdatedCartDTOWithProductItems());
        doCallRealMethod().when(mockNewCartService).deleteItem(any(CartDTO.class), anyLong());
        doCallRealMethod().when(mockController).deleteRenewItem(mockSession, mockCmd, 0);
        when(mockController.addStartDatesSelectListToModelAndView(any(CartCmdImpl.class))).thenCallRealMethod();
        ModelAndView result = mockController.deleteRenewItem(mockSession, mockCmd, 0);
        verify(mockNewCartService, atLeastOnce()).deleteItem(((CartDTO) anyObject()), anyLong());
        assertEquals(PageView.QUICK_BUY, result.getViewName());
    }

    @Test
    public void shouldAddRenewItemDTOWithCartItems() {

        when(mockRenewTravelCardService.getCartItemsFromCubic(any(CartDTO.class), anyLong())).thenReturn(
                        CartCmdTestUtil.getTestRenewCartCmd3());
        CartCmdImpl cartCmdImpl = controller.addRenewItemDTOWithCartItems(new CartCmdImpl(),
                        CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems());
        assertEquals(2, cartCmdImpl.getCartItemList().size());
    }

    @Test
    public void shouldDeleteCartItemCmdFromcartCmd() {
        CartItemCmdImpl cartItemCmd = CartItemTestUtil.getTestPayAsYouGo1();
        cartItemCmd.setId(new Long(1));
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestRenewCartCmd3();
        cartCmd.getCartItemList().add(cartItemCmd);
        controller.deleteCartItemCmdFromcartCmd(cartCmd, cartItemCmd.getId());
        assertEquals(2, cartCmd.getCartItemList().size());

    }

}
