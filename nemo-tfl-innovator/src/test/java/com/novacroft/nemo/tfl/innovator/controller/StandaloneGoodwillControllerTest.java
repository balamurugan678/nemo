package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_STANDALONE_GOODWILL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardPreferencesTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CommonCardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.ManageGoodwillService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartPaymentValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

public class StandaloneGoodwillControllerTest {
    private StandaloneGoodwillController controller;
    private GoodwillValidator mockGoodwillValidator;
    private CustomerDataService mockCustomerDataService;
    private HttpSession mockSession;
    private RefundSelectListService mockRefundSelectListService;
    private RefundService mockRefundService;
    private RefundPaymentService mockRefundPaymentService;
    private ManageGoodwillService mockManageGoodwillService;
    private BindingResult mockBindingResult;
    private CardSelectListService mockCardSelectListService;
    protected WorkFlowService mockWorkflowService;
    private CartSessionData cartSessionData = new CartSessionData(CART_ID);
    protected RefundCartPaymentValidator mockRefundCartPaymentValidator;
    private ServletRequestDataBinder mockServletRequestBinder;
    private CartService mockCartService;
    private CardPreferencesService mockCardPreferencesService;
    private CardService mockCardService;

    private static String ID = "id";
    public static final Long CART_ID = 1l;

    @Before
    public void setUp() throws Exception {
        controller = new StandaloneGoodwillController();
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockRefundCartPaymentValidator = mock(RefundCartPaymentValidator.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockRefundSelectListService = mock(RefundSelectListService.class);
        mockRefundService = mock(RefundService.class);
        mockRefundPaymentService = mock(RefundPaymentService.class);
        mockSession = mock(HttpSession.class);
        mockManageGoodwillService = mock(ManageGoodwillService.class);
        mockBindingResult = mock(BindingResult.class);
        mockCardSelectListService = mock(CardSelectListService.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockServletRequestBinder = mock(ServletRequestDataBinder.class);
        mockCartService = mock(CartService.class);
        mockCardPreferencesService = mock(CardPreferencesService.class);
        mockCardService = mock(CardService.class);

        doNothing().when(mockGoodwillValidator).validate(anyObject(), any(Errors.class));

        this.controller.customerDataService = mockCustomerDataService;
        this.controller.refundSelectListService = mockRefundSelectListService;
        this.controller.refundService = mockRefundService;
        this.controller.refundPaymentService = mockRefundPaymentService;
        this.controller.manageGoodwillService = mockManageGoodwillService;
        controller.workflowService = mockWorkflowService;
        controller.cardSelectListService = mockCardSelectListService;
        controller.refundCartPaymentValidator = mockRefundCartPaymentValidator;
        controller.cartService = mockCartService;
        controller.cardPreferencesService = mockCardPreferencesService;
        controller.goodwillValidator = mockGoodwillValidator;
        controller.cardService = mockCardService;
    }

    @Test
    public void populateModelAttributesShouldPopulateModelWithPaymentTypes() {
        Model model = new BindingAwareModelMap();
        when(mockRefundSelectListService.getStandaloneGoodwillSelectListModel(any(Model.class)))
                .thenReturn(new BindingAwareModelMap());
        controller.populateGoodwillRefundTypes(model);
        assertNotNull(model);
    }

    @Test
    public void viewStandaloneGoodwillShouldReturnStandaloneGoodwillView() {
        doNothing().when(this.mockRefundService).deleteCartForCustomerId(any(Long.class));
        when(mockRefundService.createCartCmdImplWithCartDTOFromCustomerId(any(Long.class), anyString()))
                .thenReturn(CartCmdTestUtil.getTestCartCmdWithOverlappingZones());
        when(mockRefundPaymentService.createCardsSelectListForAdHocLoad(anyString())).thenReturn(new SelectListDTO());

        ModelAndView result = this.controller.viewStandaloneGoodwill(CUSTOMER_ID_1, mockSession);

        verify(mockRefundService, atLeastOnce()).createCartCmdImplWithCartDTOFromCustomerId(any(Long.class), anyString());
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().containsKey(PageAttribute.FIELD_SELECT_CARD));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void addGoodwillShouldReturnStandaloneGoodwillView() {
        Model modelMap = new BindingAwareModelMap();
        when(mockRefundPaymentService.createCardsSelectListForAdHocLoad(anyString())).thenReturn(new SelectListDTO());
        when(mockManageGoodwillService
                .addGoodwill(any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(Model.class)))
                .thenReturn(new CartCmdImpl());
        when(mockSession.getAttribute(anyString())).thenReturn(cartSessionData);
        ModelAndView result = this.controller
                .addGoodwill(CUSTOMER_ID_1, CartCmdTestUtil.getTestCartCmd1(), mockBindingResult, modelMap, mockSession);
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void deleteGoodwillShouldReturnStandaloneGoodwillView() {
        Model modelMap = new BindingAwareModelMap();
        when(mockRefundPaymentService.createCardsSelectListForAdHocLoad(anyString())).thenReturn(new SelectListDTO());
        when(mockManageGoodwillService.deleteGoodwill(any(CartCmdImpl.class), anyInt(), anyString(), any(Model.class)))
                .thenReturn(new CartCmdImpl());
        when(mockSession.getAttribute(anyString())).thenReturn(cartSessionData);

        ModelAndView result =
                this.controller.deleteGoodwill(CUSTOMER_ID_1, CartCmdTestUtil.getTestCartCmd1(), 0, modelMap, mockSession);
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void continueCartShouldReturnCustomerView() {
        CartCmdImpl cmd = new CartCmdImpl();
        when(mockRefundService.getCartDTOUsingCartSessionDataDTOInSession(mockSession))
                .thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
        ModelAndView result = this.controller.continueCart(null, mockSession, cmd, mockBindingResult);
        assertEquals(PageUrl.INV_REFUND_SUMMARY, ((RedirectView) result.getView()).getUrl());
        assertTrue(result.getModel().containsKey(ID));
    }

    @Test
    public void continueCartShouldReturnCustomerViewWithErrors() {
        CartCmdImpl cmd = new CartCmdImpl();
        when(mockRefundService.getCartDTOUsingCartSessionDataDTOInSession(mockSession))
                .thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
        cmd.setTargetCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        when(mockCardService.getCardDTOById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView result = this.controller.continueCart(null, mockSession, cmd, mockBindingResult);
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
    }

    @Test
    public void getPreferredStationIdShouldReturnStationId() {
        when(mockCardPreferencesService.getPreferencesByCardId(CommonCardTestUtil.TEST_CARDID))
                .thenReturn(CardPreferencesTestUtil.getTestCardPreferencesCmd1());
        String preferredStationId = this.controller.getPreferredStationId(CommonCardTestUtil.TEST_CARDID);
        assertEquals(CommonCardTestUtil.TEST_STATION_ID, preferredStationId);
    }

    @Test
    public void continueCartWithApprovalIdShouldReturnCustomerView() {
        CartCmdImpl cmd = new CartCmdImpl();
        when(mockRefundService.getCartDTOUsingCartSessionDataDTOInSession(mockSession))
                .thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCardWithApprovalId());
        ModelAndView result = this.controller.continueCart(null, mockSession, cmd, mockBindingResult);
        assertEquals(PageUrl.VIEW_WORKFLOW_ITEM, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void initBinderShouldRegisterCustomEditor() {
        doNothing().when(mockServletRequestBinder).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
        controller.initBinder(mockServletRequestBinder);
        verify(mockServletRequestBinder, atLeastOnce()).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
    }

    @Test
    public void viewStandaloneGoodwillForApprovalShouldReturnStandaloneGoodwillView() {
        doNothing().when(this.mockRefundService).updateDateOfRefundInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        doNothing().when(this.mockRefundService).updatePaymentDetailsInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        when(mockCartService.findByApprovalId(CommonCardTestUtil.APPROVAL_ID))
                .thenReturn(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
        when(mockRefundService.createCartCmdImplWithCartDTOFromCustomerId(any(Long.class), anyString()))
                .thenReturn(CartCmdTestUtil.getTestCartCmdWithOverlappingZones());
        when(mockRefundPaymentService.createCardsSelectListForAdHocLoad(anyString())).thenReturn(new SelectListDTO());
        ModelAndView result =
                this.controller.viewStandaloneGoodwillForApproval(CUSTOMER_ID_1, CommonCardTestUtil.APPROVAL_ID, mockSession);
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().containsKey(PageAttribute.FIELD_SELECT_CARD));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void getCardSpecificStationIdShouldReturnStationId() {
        when(mockCardPreferencesService.getPreferencesByCardId(CommonCardTestUtil.TEST_CARDID))
                .thenReturn(CardPreferencesTestUtil.getTestCardPreferencesCmd1());
        String preferredStationId = this.controller.getCardSpecificStationId(CommonCardTestUtil.TEST_CARDID);
        assertEquals(CommonCardTestUtil.CARD_SPECIFIC_STATION_ID, preferredStationId);
    }

}
