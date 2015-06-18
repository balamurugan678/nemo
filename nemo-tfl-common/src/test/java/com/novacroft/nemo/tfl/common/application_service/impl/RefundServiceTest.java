package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.BANK_ACCOUNT;
import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.SORT_CODE;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getCardWitDeposit;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.INITIALS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TITLE_1;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.generateWorkflowItem;
import static com.novacroft.nemo.tfl.common.util.PayeePaymentTestUtil.getPayeePaymentDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CardUpdateRequestTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CommonCardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.test_support.RefundTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.application_service.fare_aggregation_service.FareAggregationService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.constant.RefundType;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.CubicCardDetailsToCartItemCmdImplConverter;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.JourneyCompletedRefundItemDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.RefundOrchestrationResultDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

public class RefundServiceTest {
    private RefundServiceImpl service;
    private RefundServiceImpl spyService;
    private CardDataService mockCardDataService;
    private CubicCardDetailsToCartItemCmdImplConverter mockCubicCardDetailsToCartItemCmdImplConverter;
    private CustomerDataService mockCustomerDataService;
    private GetCardService mockGetCardService;
    private CartService mockCartService;
    private TravelCardService mockTravelCardService;
    private Set<CartItemCmdImpl> cartCmdItemImplSet;
    private OrderDataService mockOrderDataService;
    private CardService mockCardService;
    private CartAdministrationService mockNewCartAdministrationService;
    private FareAggregationService mockFareAggregationService;
    private CardUpdateService mockCardUpdateService;
    private WebCreditService mockWebCreditService;
    private JourneyCompletedRefundItemDataService mockJourneyCompletedRefundItemDataService;
    private IncompleteJourneyService mockIncompleteJourneyService;
    private HttpSession mockSession;
    private CartCmdImpl mockCartCmdImpl;
    WorkflowItemDTO workflowItemDTO;
    private CartDTO mockCart;
    private WorkFlowService mockWorkflowService;
    private WorkflowItemConverter mockWorkflowItemConverter;
    private EditRefundPaymentService mockEditRefundPaymentService;
    private AddressDataService mockAddressDataService;
    private TradedTravelCardServiceImpl mockTradedTravelCardServiceImpl;
    @Before
    public void setUp() {
        service = new RefundServiceImpl();
        mockCardDataService = mock(CardDataService.class);
        mockCubicCardDetailsToCartItemCmdImplConverter = mock(CubicCardDetailsToCartItemCmdImplConverter.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockCartService = mock(CartService.class);
        mockTravelCardService = mock(TravelCardService.class);
        mockOrderDataService = mock(OrderDataService.class);
        mockCardService = mock(CardService.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);
        mockFareAggregationService = mock(FareAggregationService.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockWebCreditService = mock(WebCreditService.class);
        mockJourneyCompletedRefundItemDataService = mock(JourneyCompletedRefundItemDataService.class);
        mockIncompleteJourneyService = mock(IncompleteJourneyService.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockWorkflowItemConverter = mock(WorkflowItemConverter.class);
        mockEditRefundPaymentService = mock(EditRefundPaymentService.class);
        mockSession = mock(HttpSession.class);
        mockCartCmdImpl = mock(CartCmdImpl.class);
        mockCart = mock(CartDTO.class); 
        mockAddressDataService = mock(AddressDataService.class);
        workflowItemDTO = generateWorkflowItemForTest();
        mockTradedTravelCardServiceImpl = mock(TradedTravelCardServiceImpl.class);
        		
        service.cardDataService = mockCardDataService;
        service.cubicCardDetailsToCartItemCmdImplConverter = mockCubicCardDetailsToCartItemCmdImplConverter;
        service.customerDataService = mockCustomerDataService;
        service.getCardService = mockGetCardService;
        service.cartService = mockCartService;
        service.travelCardService = mockTravelCardService;
        service.orderDataService = mockOrderDataService;
        service.cardService = mockCardService;
        service.cartAdministrationService = mockNewCartAdministrationService;
        service.fareAggregationService = mockFareAggregationService;
        service.cardUpdateService = mockCardUpdateService;
        service.webCreditService = mockWebCreditService;
        service.journeyCompletedRefundItemDataService = mockJourneyCompletedRefundItemDataService;
        service.incompleteJourneyService = mockIncompleteJourneyService;
        service.workFlowService = mockWorkflowService;
        service.workflowItemConverter = mockWorkflowItemConverter;
        service.editRefundPaymentService = mockEditRefundPaymentService;
        service.addressDataService = mockAddressDataService;
        service.tradedTravelCardService = mockTradedTravelCardServiceImpl;
        
        spyService = spy(service);
        doNothing().when(spyService).doNotDeleteCartIfItsFromApproval(anyLong(), anyBoolean());
        doAnswer(returnsFirstArg()).when(spyService).getDetailsFromCubic(any(CartDTO.class), anyString(), anyString(), anyBoolean());
        doAnswer(returnsFirstArg()).when(spyService).updateDateOfRefund(any(CartDTO.class));
        doAnswer(returnsFirstArg()).when(spyService).updateCartDTOWithDepositItem(any(CartDTO.class), anyString());
        
        cartCmdItemImplSet = new HashSet<CartItemCmdImpl>();
        CartItemCmdImpl cartItem = CartItemTestUtil.getTestTravelCard1();
        cartItem.setTicketType(TicketType.TRAVEL_CARD.code());
        cartCmdItemImplSet.add(cartItem);

        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        when(mockCartService.postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class))).then(returnsFirstArg());
    }

    @Test
    public void shouldCreateCartCmdImplWithCartDTO() {
        when(mockCartService.createCart()).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        CartCmdImpl cartCmd = service.createCartCmdImplWithCartDTO();
        verify(mockCartService).createCart();
        assertTrue(cartCmd.getCartDTO() != null);
    }

    @Test
    public void shouldCreateCartCmdImplWithCartDTOFromCustomerId() {
        when(mockCartService.createCartFromCustomerId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        CartCmdImpl cartCmd = service.createCartCmdImplWithCartDTOFromCustomerId(CustomerTestUtil.CUSTOMER_ID_1, CartTestUtil.CART_TYPE_1);
        assertTrue(cartCmd.getCartDTO() != null);
    }

    @Test
    public void shouldUpdateCartDTOWithCardDetailsInCubic() {
        when(mockCubicCardDetailsToCartItemCmdImplConverter.convertCubicCardDetailsToCartItemCmdImpls(anyString(), anyString())).thenReturn(cartCmdItemImplSet);
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ItemDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        assertEquals(CartDTO.class, service.updateCartDTOWithCardDetailsInCubic(CartTestUtil.getNewCartDTOWithCartRefundTotal(), CardTestUtil.OYSTER_NUMBER_1, CartType.FAILED_CARD_REFUND.code()).getClass());
        verify(mockGetCardService).getCard(anyString());
        verify(mockCubicCardDetailsToCartItemCmdImplConverter).convertCubicCardDetailsToCartItemCmdImpls(anyString(), anyString());
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldUpdateCartDTOWithCardDetailsInCubicForAutoTopUpTicketThrowsIllegalArgumentException() {

        Set<CartItemCmdImpl> cartCmdItemImplSet1 = new HashSet<CartItemCmdImpl>();
        CartItemCmdImpl cartItem = CartItemTestUtil.getTestTravelCard1();
        cartItem.setTicketType(TicketType.AUTO_TOP_UP.code());
        cartCmdItemImplSet1.add(cartItem);

        when(mockCubicCardDetailsToCartItemCmdImplConverter.convertCubicCardDetailsToCartItemCmdImpls(anyString(), anyString())).thenReturn(cartCmdItemImplSet1);
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ItemDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        assertEquals(CartDTO.class, service.updateCartDTOWithCardDetailsInCubic(CartTestUtil.getNewCartDTOWithCartRefundTotal(), CardTestUtil.OYSTER_NUMBER_1, CartType.FAILED_CARD_REFUND.code()).getClass());
        verify(mockGetCardService).getCard(anyString());
        verify(mockCubicCardDetailsToCartItemCmdImplConverter).convertCubicCardDetailsToCartItemCmdImpls(anyString(), anyString());
        verify(mockCartService, atLeastOnce()).findById(anyLong());
    }

    @Test
    public void shouldAddTravelCardToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        service.addTravelCardToCart(CartTestUtil.getNewCartDTOWithCartRefundTotal(), CartCmdTestUtil.getTestCartCmd1(), CartType.FAILED_CARD_REFUND.code());
        verify(mockCartService).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
    }

    @Test
    public void shouldGetCardInfoResponseV2DTOFromCardNumber() {
        service.getCardInfoResponseV2DTOFromCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        verify(mockGetCardService).getCard(anyString());
    }

    @Test
    public void shouldPersistCartItemCmdImpls() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        service.persistCartItemCmdImpls(CartTestUtil.getNewCartDTOWithCartRefundTotal(), cartCmdItemImplSet);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
    }

    @Test
    public void shouldGetPayAsYouGoItemDTOInstanceForCartItemCmdImpl() {
        CartItemCmdImpl cartItem = CartItemTestUtil.getTestPayAsYouGo1();
        cartItem.setTicketType(TicketType.PAY_AS_YOU_GO.code());
        assertEquals(PayAsYouGoItemDTO.class, service.getItemDTOSubclassInstanceForCartItemCmdImpl(cartItem));
    }

    @Test
    public void shouldGetProductItemDTOInstanceForCartItemCmdImp() {
        CartItemCmdImpl cartItem = CartItemTestUtil.getTestTravelCard1();
        cartItem.setTicketType(TicketType.TRAVEL_CARD.code());
        assertEquals(ProductItemDTO.class, service.getItemDTOSubclassInstanceForCartItemCmdImpl(cartItem));
    }

    @Test
    public void shouldReturnTrueIfCustomerExists() {
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        assertTrue(service.isCustomerExistsWithCustomerId(CustomerTestUtil.CUSTOMER_ID_1));
        verify(mockCustomerDataService).findById(any(Long.class));
    }

    @Test
    public void shouldReturnFalseIfCustomerDoesNotExist() {
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(null);
        assertFalse(service.isCustomerExistsWithCustomerId(CustomerTestUtil.CUSTOMER_ID_1));
        verify(mockCustomerDataService).findById(any(Long.class));
    }

    @Test
    public void shouldReturnTrueIfCardExists() {
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        assertTrue(service.isCardExistsWithCardId(CardTestUtil.CARD_ID));
        verify(mockCardDataService).findById(anyLong());
    }

    @Test
    public void shouldReturnFalseIfCardDoesNotExist() {
        when(mockCardDataService.findById(anyLong())).thenReturn(null);
        assertFalse(service.isCardExistsWithCardId(CardTestUtil.CARD_ID));
        verify(mockCardDataService).findById(anyLong());
    }

    @Test
    public void shouldDeleteCartFromCustomerId() {
        doNothing().when(mockCartService).deleteCartForCustomerId(anyLong());
        service.deleteCartForCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        verify(mockCartService).deleteCartForCustomerId(anyLong());
    }

    @Test
    public void shouldReturnPriceFromItemDTO() {
        int itemPrice = service.getRefundAmountInItemDTO(ItemTestUtil.getTestItemDTO1());
        assertEquals(ItemTestUtil.PRICE_1.intValue(), itemPrice);
    }

    @Test
    public void shouldUpdateRefundCalculationBasisInProductItemDTO() {
        doNothing().when(mockCartService).updateRefundCalculationBasis(anyLong(), anyLong(), anyString());
        service.updateProductItemDTO(CartTestUtil.REFUND_CART_ID_1, ProductItemTestUtil.PRODUCT_Item_ID_1, RefundCalculationBasis.PRO_RATA.code());
        verify(mockCartService).updateRefundCalculationBasis(anyLong(), anyLong(), anyString());
    }

    @Test
    public void shouldUpdatePriceInPayAsYouGoItemDTO() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem());
        when(mockCartService.updatePrice(any(CartDTO.class), anyLong(), anyInt())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem());
        service.updatePayAsYouGoItemDTO(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem(), PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        verify(mockCartService).updatePrice(any(CartDTO.class), anyLong(), anyInt());
    }

    @Test
    public void updatePayAsYouGoItemDTOShouldReturn0() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem());
        when(mockCartService.updatePrice(any(CartDTO.class), anyLong(), anyInt())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem());
        service.updatePayAsYouGoItemDTO(CartTestUtil.getNewCartDTOWithItem(), PayAsYouGoTestUtil.TICKET_PAY_AS_YOU_GO_PRICE_1);
        verify(mockCartService).updatePrice(any(CartDTO.class), anyLong(), anyInt());
    }

    @Test
    public void shouldUpdatePrceInAdministrationFeeItemDTO() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCartService.updatePrice(any(CartDTO.class), anyLong(), anyInt())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        service.updateAdministrationFeeItemDTO(CartTestUtil.getNewCartDTOWithItem(), 0);
        verify(mockCartService).updatePrice(any(CartDTO.class), anyLong(), anyInt());
    }

    @Test
    public void shouldUpdateDateOfRefund() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithProductItem();
        Date dateOfRefund = new Date();
        when(mockCartService.updateCart(any(CartDTO.class))).thenReturn(cartDTO);
        when(mockCartService.findById(any(Long.class))).thenReturn(cartDTO);
        service.updateDateOfRefund(cartDTO, dateOfRefund);

        verify(mockCartService).updateCart(any(CartDTO.class));
        ProductItemDTO product = (ProductItemDTO) cartDTO.getCartItems().get(0);
        assertEquals(dateOfRefund.getTime(), product.getDateOfRefund().getTime());
    }
    
    @Test
    public void shouldUpdateDateOfRefundWithRefundCalculationBasis() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithProductItem();
        Date dateOfRefund = new Date();
        when(mockCartService.updateCart(any(CartDTO.class))).thenReturn(cartDTO);
        when(mockCartService.findById(any(Long.class))).thenReturn(cartDTO);
        service.updateDateOfRefundAndRefundCalculationBasis(cartDTO, dateOfRefund, RefundCalculationBasis.ORDINARY.code());

        verify(mockCartService).updateCart(any(CartDTO.class));
        ProductItemDTO product = (ProductItemDTO) cartDTO.getCartItems().get(0);
        assertEquals(dateOfRefund.getTime(), product.getDateOfRefund().getTime());
    }

    @Test
    public void shouldGetUpdatedCart() {
        service.getUpdatedCart(CartTestUtil.CART_ID_1);
        verify(mockCartService).findById(anyLong());
    }

    @Test
    public void findAllRefundsForCustomerShouldReturnFoundRefunds() {
        RefundOrderItemDTO refund = new RefundOrderItemDTO(4L, 3333l, "444444", new Date());
        List<RefundOrderItemDTO> refundsList = new ArrayList<>();
        refundsList.add(refund);
        when(mockOrderDataService.findAllRefundsForCustomer(anyLong())).thenReturn(refundsList);
        List<RefundOrderItemDTO> refunds = service.findAllRefundsForCustomer(1L);
        assertTrue(refunds.size() == 1);
        assertTrue(refund.equals(refundsList.get(0)));
    }

    @Test
    public void shouldUpdateCartItemCmdImplFromCartDTO() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithDateOfRefund();
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();

        service.updateCartItemCmdImplFromCartDTO(cartDTO, cartCmdImpl, RefundType.FAILED_CARD.code());

        assertEquals(cartCmdImpl.getCartItemCmd().getDateOfRefund(), cartDTO.getDateOfRefund());
        assertEquals(cartCmdImpl.getCartItemCmd().getRefundType(), RefundType.FAILED_CARD.code());
    }

    @Test
    public void isCardDetailsAvailableFromCubicShouldReturnTrue() {
        when(mockGetCardService.getCard(CardTestUtil.OYSTER_NUMBER_1)).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());

        Boolean isCardDetailsAvailableFromCubic = service.isCardDetailsAvailableFromCubic(CardTestUtil.OYSTER_NUMBER_1);

        assertTrue(isCardDetailsAvailableFromCubic);
    }

    @Test
    public void isCardDetailsAvailableFromCubicShouldReturnFalse() {
        when(mockGetCardService.getCard(CardTestUtil.OYSTER_NUMBER_1)).thenReturn(null);

        Boolean isCardDetailsAvailableFromCubic = service.isCardDetailsAvailableFromCubic(CardTestUtil.OYSTER_NUMBER_1);

        assertFalse(isCardDetailsAvailableFromCubic);
    }

    @Test
    public void getItemDTOSubclassInstanceForCartItemCmdImplShouldReturnPayAsYouGoItemDTOClass() {
        Class<? extends ItemDTO> itemDTOClass = service.getItemDTOSubclassInstanceForCartItemCmdImpl(CartItemTestUtil.getTestPayAsYouGo7());

        assertEquals(PayAsYouGoItemDTO.class, itemDTOClass);
    }

    @Test
    public void getItemDTOSubclassInstanceForCartItemCmdImplShouldReturnProductItemDTOClass() {
        Class<? extends ItemDTO> itemDTOClass = service.getItemDTOSubclassInstanceForCartItemCmdImpl(CartItemTestUtil.getTestTravelCard10());

        assertEquals(ProductItemDTO.class, itemDTOClass);
    }

    @Test
    public void isCustomerExistsWithCustomerIdShouldReturnTrue() {
        when(mockCustomerDataService.findById(CustomerTestUtil.CUSTOMER_ID_1)).thenReturn(CustomerTestUtil.getTestCustomerDTO1());

        Boolean isCustomerExistsWithCustomerId = service.isCustomerExistsWithCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        assertTrue(isCustomerExistsWithCustomerId);
    }

    @Test
    public void isCustomerExistsWithCustomerIdShouldReturnFalse() {
        when(mockCustomerDataService.findById(CustomerTestUtil.CUSTOMER_ID_1)).thenReturn(null);

        Boolean isCustomerExistsWithCustomerId = service.isCustomerExistsWithCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        assertFalse(isCustomerExistsWithCustomerId);
    }

    @Test
    public void isCardExistsWithCardIdShouldReturnTrue() {
        when(mockCardDataService.findById(CardTestUtil.CARD_ID_1)).thenReturn(CardTestUtil.getTestCardDTO1());

        Boolean isCardExistsWithCardId = service.isCardExistsWithCardId(CustomerTestUtil.CUSTOMER_ID_1);

        assertTrue(isCardExistsWithCardId);
    }

    @Test
    public void isCardExistsWithCardIdShouldReturnFalse() {
        when(mockCardDataService.findById(CardTestUtil.CARD_ID_1)).thenReturn(null);

        Boolean isCardExistsWithCardId = service.isCardExistsWithCardId(CustomerTestUtil.CUSTOMER_ID_1);

        assertFalse(isCardExistsWithCardId);
    }

    @Test
    public void getCartDTOUsingCartSessionDataDTOInSessionShouldReturnCartDTO() {
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());

        CartDTO cart = service.getCartDTOUsingCartSessionDataDTOInSession(mockSession);

        assertEquals(CartTestUtil.CART_ID_1, cart.getId());
    }
    
    @Test
    public void getCartDTOUsingCartSessionDataDTOInSessionShouldRecalcuateRefund() {
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());

        service.getCartDTOUsingCartSessionDataDTOInSession(mockSession);

        verify(mockCartService).findById(anyLong());
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class));
    }

    @Test
    public void storeCartIdInCartSessionDataDTOInSessionShouldCreateCartSessionData() {

        service.storeCartIdInCartSessionDataDTOInSession(mockSession, CartTestUtil.CART_ID_1);

        verify(mockSession).setAttribute(anyString(), any(CartSessionData.class));
    }

    @Test
    public void findRefundDetailsForIdAndCustomerShouldReturnRefundOrderItemDTO() {

        RefundOrderItemDTO refund = new RefundOrderItemDTO(4L, 3333l, "444444", new Date());
        when(mockOrderDataService.findRefundDetailForItemAndCustomer(anyLong(), anyLong())).thenReturn(refund);

        RefundOrderItemDTO result = service.findRefundDetailsForIdAndCustomer(RefundTestUtil.REFUND_ID_1, CustomerTestUtil.CUSTOMER_ID_1);

        verify(mockOrderDataService).findRefundDetailForItemAndCustomer(anyLong(), anyLong());
        assertEquals(result.getDateOfRefund(), refund.getDateOfRefund());
    }

    @Test
    public void getPayAsYouGoItemIdShouldReturnPayAsYouGoItemId() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());

        Long payAsYouGoItemId = service.getPayAsYouGoItemId(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());

        assertEquals(ItemTestUtil.ITEM_ID, payAsYouGoItemId);
    }

    @Test
    public void getPayAsYouGoItemIdShouldReturnNullIfNoPayAsYouGoInCart() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId());
        assertNull(service.getPayAsYouGoItemId(CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId()));
    }

    @Test
    public void getAdministrationFeeItemDTOShouldReturnNullIfNoAdministrationFeeInCart() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId());
        assertNull(service.getAdministrationFeeItemPriceId(CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId()));
    }

    @Test
    public void shouldNotDeleteCartDtoWhenCalledFromApprovals() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID_1);
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());
        service.getCartDTOForRefund(CardTestUtil.OYSTER_NUMBER_1, CartType.CANCEL_SURRENDER_REFUND.code(), Boolean.FALSE);

        verify(mockCartService, never()).deleteCartForCardId(CardTestUtil.CARD_ID_1);

    }

    @Test
    public void getCartDTOForRefundShouldNotUpdateDeposit() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID);
        doNothing().when(mockCartService).deleteCartForCardId(anyLong());
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());

        spyService.getCartDTOForRefund(CommonCardTestUtil.OYSTER_NUMBER_1, CartTestUtil.CART_TYPE_2, true);

        verify(spyService, never()).updateCartDTOWithDepositItem(any(CartDTO.class), anyString());
    }
    
    @Test
    public void getCartDTOForRefundShouldUpdateDepositIfFailedCartType() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID);
        doNothing().when(mockCartService).deleteCartForCardId(anyLong());
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());

        spyService.getCartDTOForRefund(CommonCardTestUtil.OYSTER_NUMBER_1, CartTestUtil.CART_TYPE_FAILED_CARD, true);

        verify(spyService).updateCartDTOWithDepositItem(any(CartDTO.class), anyString());
    }
    
    @Test
    public void getCartDTOForRefundShouldUpdateDepositIfDestroyedCartType() {
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID);
        doNothing().when(mockCartService).deleteCartForCardId(anyLong());
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());

        spyService.getCartDTOForRefund(CommonCardTestUtil.OYSTER_NUMBER_1, CartTestUtil.CART_TYPE_DESTROYED_CARD, true);

        verify(spyService).updateCartDTOWithDepositItem(any(CartDTO.class), anyString());
    }

    @Test
    public void shouldProcessRefundsForCompletedJourney() {
        CardUpdateResponseDTO cardUpdateResponseDTO = CardUpdateRequestTestUtil.getCardUpdateResponseDTO();
        JourneyCompletedRefundItemDTO mockDto = new JourneyCompletedRefundItemDTO();
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        completeJourneyCommandImpl.setPickUpStation(2l);
        NotifyAutoFillOfSSRStatusResponseDTO notifyAutoFillOfSSRStatusResponseDTO = new NotifyAutoFillOfSSRStatusResponseDTO();
        RefundOrchestrationResultDTO refundOrchestrationResult = new RefundOrchestrationResultDTO(AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED, 100);
        when(mockFareAggregationService.orchestrateServicesForRefundSubmission(anyLong(), any(Date.class), any(Integer.class), any(Integer.class), anyString(), any(Boolean.class))).thenReturn(refundOrchestrationResult);
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());

        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), anyLong(), any(Integer.class), any(Integer.class), anyString())).thenReturn(
                        cardUpdateResponseDTO.getRequestSequenceNumber());
        when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(OrderTestUtil.getTestOrderDTO1());
        doNothing().when(mockWebCreditService).applyWebCreditToOrder(anyLong(), any(Integer.class));
        when(mockJourneyCompletedRefundItemDataService.createOrUpdate(any(JourneyCompletedRefundItemDTO.class))).thenReturn(mockDto);
        when(mockIncompleteJourneyService.notifyAutofill(anyLong(), any(Date.class), any(Integer.class), any(AutoFillSSRNotificationStatus.class))).thenReturn(notifyAutoFillOfSSRStatusResponseDTO);

        CompletedJourneyProcessingResult CompletedJourneyProcessingResult = service.processRefundsForCompletedJourney(completeJourneyCommandImpl);

        assertEquals(refundOrchestrationResult.getRefundAmount(), CompletedJourneyProcessingResult.getAmount());
    }

    @Test
    public void processRefundsForCompletedJourneyShouldThrowExceptionAndCallIncompleteJourneyService() {
        CardUpdateResponseDTO cardUpdateResponseDTO = CardUpdateRequestTestUtil.getCardUpdateResponseDTO();
        JourneyCompletedRefundItemDTO mockDto = new JourneyCompletedRefundItemDTO();
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        completeJourneyCommandImpl.setPickUpStation(2l);
        NotifyAutoFillOfSSRStatusResponseDTO notifyAutoFillOfSSRStatusResponseDTO = new NotifyAutoFillOfSSRStatusResponseDTO();
        RefundOrchestrationResultDTO refundOrchestrationResult = new RefundOrchestrationResultDTO(AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED, 100);
        when(mockFareAggregationService.orchestrateServicesForRefundSubmission(anyLong(), any(Date.class), any(Integer.class), any(Integer.class), anyString(), any(Boolean.class))).thenReturn(refundOrchestrationResult);
        when(mockCardDataService.findById(anyLong())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());

        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), anyLong(), any(Integer.class), any(Integer.class), anyString())).thenReturn(
                        cardUpdateResponseDTO.getRequestSequenceNumber());
        when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(OrderTestUtil.getTestOrderDTO1());
        doNothing().when(mockWebCreditService).applyWebCreditToOrder(anyLong(), any(Integer.class));
        when(mockJourneyCompletedRefundItemDataService.createOrUpdate(any(JourneyCompletedRefundItemDTO.class))).thenReturn(mockDto);
        when(mockIncompleteJourneyService.notifyAutofill(anyLong(), any(Date.class), any(Integer.class), any(AutoFillSSRNotificationStatus.class))).thenReturn(notifyAutoFillOfSSRStatusResponseDTO);

        CompletedJourneyProcessingResult completedJourneyProcessingResult = service.processRefundsForCompletedJourney(completeJourneyCommandImpl);

        assertEquals(null, completedJourneyProcessingResult.getAmount());
    }

    @Test
    public void shouldUpdatePayAsYouGoValueInCartCmdImpl() {

        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd1();
        service.updatePayAsYouGoValueInCartCmdImpl(cartCmdImpl, cartDTO);
        assertEquals(cartCmdImpl.getPayAsYouGoValue(), cartDTO.getCartItems().get(0).getPrice());
    }

    @Test
    public void shouldUpdateAdministrationFeeValueInCartCmdImpl() {

        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd1();
        service.updateAdministrationFeeValueInCartCmdImpl(cartCmdImpl, cartDTO);
        assertEquals(cartCmdImpl.getAdministrationFeeValue(), cartDTO.getCartItems().get(0).getPrice());
    }

    @Test
    public void shouldUpdateDateOfRefundInCartCmdImpl() {

        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.setDateOfRefund(new Date());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd1();
        service.updateDateOfRefundInCartCmdImpl(cartCmdImpl, cartDTO);
        assertEquals(cartCmdImpl.getDateOfRefund(), cartDTO.getDateOfRefund());
    }

    @Test
    public void testTotalTicketPrice() {
        List<ItemDTO> cartItemsDto = CartTestUtil.getNewAnnualBusPassProductItemDTOList();
        assertEquals(new Integer(1240), service.getTotalTicketPrice(cartItemsDto));

    }

    private WorkflowItemDTO generateWorkflowItemForTest(){
    	WorkflowItemDTO workflowItem = generateWorkflowItem();
    	workflowItem.getRefundDetails().setPayeeAccountNumber(BANK_ACCOUNT);
    	workflowItem.getRefundDetails().setPayeeSortCode(SORT_CODE);
    	return workflowItem;
    }
    
    private WorkflowCmd generateWorkflowCmd(){
    	WorkflowCmd workflowCmd = new WorkflowCmd();
    	workflowCmd.setWorkflowItem(generateWorkflowItemForTest());
    	workflowCmd.setPaymentName(formatFullName());
    	return workflowCmd;
    }
    
    private String formatFullName(){
    	StringBuffer fullName = new StringBuffer();
        return fullName.append(FIRST_NAME_1).append(StringUtil.SPACE).append(INITIALS_1)
                        .append(StringUtil.SPACE)
                        .append(LAST_NAME_1).toString();
    }
    
    private CustomerDTO getCustomerInfo(){
		CustomerDTO customer = new CustomerDTO();
		customer.setTitle(TITLE_1);
		customer.setFirstName(FIRST_NAME_1);
		customer.setInitials(INITIALS_1);
		customer.setLastName(LAST_NAME_1);
		return customer;
	}
    
    @Test
    public void updatePaymentDetailsInCartCmdImplShouldInvokeServicesWithOutEditedPayeePayment(){
    	when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(workflowItemDTO);
    	when(mockWorkflowItemConverter.convert(any(WorkflowItemDTO.class))).thenReturn(generateWorkflowCmd());
    	when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(getPayeePaymentDTO1());
    	service.updatePaymentDetailsInCartCmdImpl(mockCartCmdImpl, mockCart);
    	verify(mockWorkflowService).getWorkflowItem(anyString());
    	verify(mockWorkflowItemConverter).convert(any(WorkflowItemDTO.class));
    	verify(mockEditRefundPaymentService, never()).populateEditedValuesInCartCmdImplFromPayeePayment(any(CartCmdImpl.class), any(PayeePaymentDTO.class));
    }
    
    @Test
    public void updatePaymentDetailsInCartCmdImplWithWebCreditPaymentMethodShouldInvokeServicesWithOutEditedPayeePayment1(){
        when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(workflowItemDTO);
        WorkflowCmd webCreditWorkFlowCmd  = generateWorkflowCmd();
        webCreditWorkFlowCmd.setPaymentMethod(RefundConstants.WEB_CREDIT);
        when(mockWorkflowItemConverter.convert(any(WorkflowItemDTO.class))).thenReturn(webCreditWorkFlowCmd);
        when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(getPayeePaymentDTO1());
        service.updatePaymentDetailsInCartCmdImpl(mockCartCmdImpl, mockCart);
        verify(mockWorkflowService).getWorkflowItem(anyString());
        verify(mockWorkflowItemConverter).convert(any(WorkflowItemDTO.class));
        verify(mockEditRefundPaymentService, never()).populateEditedValuesInCartCmdImplFromPayeePayment(any(CartCmdImpl.class), any(PayeePaymentDTO.class));
    }
    @Test
    public void updatePaymentDetailsInCartCmdImplWithAdhocLoadPaymentMethodShouldInvokeServicesWithOutEditedPayeePayment1(){
        when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(workflowItemDTO);
        WorkflowCmd adhocCreditWorkFlowCmd  = generateWorkflowCmd();
        adhocCreditWorkFlowCmd.setPaymentMethod(RefundConstants.ADHOC_LOAD);
        when(mockWorkflowItemConverter.convert(any(WorkflowItemDTO.class))).thenReturn(adhocCreditWorkFlowCmd);
        when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(getPayeePaymentDTO1());
        service.updatePaymentDetailsInCartCmdImpl(mockCartCmdImpl, mockCart);
        verify(mockWorkflowService).getWorkflowItem(anyString());
        verify(mockWorkflowItemConverter).convert(any(WorkflowItemDTO.class));
        verify(mockEditRefundPaymentService, never()).populateEditedValuesInCartCmdImplFromPayeePayment(any(CartCmdImpl.class), any(PayeePaymentDTO.class));
    }
    
    @Test
    public void updatePaymentDetailsInCartCmdImplWithPaymentCardPaymentMethodShouldInvokeServicesWithOutEditedPayeePayment1(){
        when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(workflowItemDTO);
        WorkflowCmd paymentCardWorkFlowCmd  = generateWorkflowCmd();
        paymentCardWorkFlowCmd.setPaymentMethod(RefundConstants.PAYMENT_CARD);
        when(mockWorkflowItemConverter.convert(any(WorkflowItemDTO.class))).thenReturn( paymentCardWorkFlowCmd);
        when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(getPayeePaymentDTO1());
        service.updatePaymentDetailsInCartCmdImpl(mockCartCmdImpl, mockCart);
        verify(mockWorkflowService).getWorkflowItem(anyString());
        verify(mockWorkflowItemConverter).convert(any(WorkflowItemDTO.class));
        verify(mockEditRefundPaymentService, never()).populateEditedValuesInCartCmdImplFromPayeePayment(any(CartCmdImpl.class), any(PayeePaymentDTO.class));
    }
    
    
    @Test
    public void updatePaymentDetailsInCartCmdImplShouldInvokeServicesWithEditedPayeePayment(){
    	PayeePaymentDTO payeePayment = getPayeePaymentDTO1();
    	payeePayment.setIsEdited(Boolean.TRUE);
    	when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(workflowItemDTO);
    	when(mockWorkflowItemConverter.convert(any(WorkflowItemDTO.class))).thenReturn(generateWorkflowCmd());
    	when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(payeePayment);
    	service.updatePaymentDetailsInCartCmdImpl(mockCartCmdImpl, mockCart);
    	verify(mockWorkflowService).getWorkflowItem(anyString());
    	verify(mockWorkflowItemConverter).convert(any(WorkflowItemDTO.class));
    	verify(mockEditRefundPaymentService).populateEditedValuesInCartCmdImplFromPayeePayment(any(CartCmdImpl.class), any(PayeePaymentDTO.class));
    }
    
    @Test
    public void updateCustomerNameAndAddressShouldInvokeServices(){
    	when(mockCustomerDataService.findByCardNumber(anyString())).thenReturn(getCustomerInfo());
    	when(mockAddressDataService.findById(any(Long.class))).thenReturn(new AddressDTO());
    	service.updateCustomerNameAndAddress(OYSTER_NUMBER_1, mockCartCmdImpl, mockCart);
    	
    	verify(mockCustomerDataService).findByCardNumber(anyString());
    	verify(mockAddressDataService).findById(any(Long.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testAttachPreviouslyExchangedTicketToTheExistingProductItem(){
        CartItemCmdImpl cartItemCmd = CartItemTestUtil.getPreviouslyExchnagedSevenDayTravelCard();
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        when(this.mockCartService.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class),any(Long.class))).thenReturn(ProductItemTestUtil.getTestTravelCardProductDTO1());
        when(mockTradedTravelCardServiceImpl.getTravelCardItemForTradedTicket(any(CartItemCmdImpl.class), any(ProductItemDTO.class))).thenReturn(ProductItemTestUtil.getTestTravelCardProductDTO1());
        when(mockCartService.updateCart(any(CartDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        service.attachPreviouslyExchangedTicketToTheExistingProductItem(cartItemCmd, cartDTO, new Long(2));
        
        verify(mockCartService,atLeastOnce()).updateCart(any(CartDTO.class));
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testAttachPreviouslyExchangedTicketToTheExistingProductItemForBusPass(){
        CartItemCmdImpl cartItemCmd = CartItemTestUtil.getPreviouslyExchnagedSevenDayTravelCard();
        cartItemCmd.setTradedTicket(CartItemTestUtil.getTestAnnualBusPass1());
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        when(this.mockCartService.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class),any(Long.class))).thenReturn(ProductItemTestUtil.getTestTravelCardProductDTO1());
        when(mockTradedTravelCardServiceImpl.getTravelCardItemForTradedTicket(any(CartItemCmdImpl.class), any(ProductItemDTO.class))).thenReturn(ProductItemTestUtil.getTestTravelCardProductDTO1());
        when(mockCartService.updateCart(any(CartDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        service.attachPreviouslyExchangedTicketToTheExistingProductItem(cartItemCmd, cartDTO, new Long(12));
        
        verify(mockCartService,never()).updateCart(any(CartDTO.class));
        assertFalse(cartItemCmd.getPreviouslyExchanged());
    }
    
    @Test
    public void testUpdateCartDTOWithDepositItem() {
        when(mockGetCardService.getCard(anyString())).thenReturn(getCardWitDeposit());
        CardRefundableDepositDataService mockCardRefundableDepositDataService = mock(CardRefundableDepositDataService.class);
        service.cardRefundableDepositDataService = mockCardRefundableDepositDataService;
        CardRefundableDepositDTO cardRefundableDepositDTO = new CardRefundableDepositDTO();
        CardInfoResponseV2DTO mockCardInfoResponseDTO = mock(CardInfoResponseV2DTO.class);
        when(mockCardInfoResponseDTO.getCardDeposit()).thenReturn(new Integer(5));
        when(mockCardRefundableDepositDataService.findByPrice(anyInt())).thenReturn(cardRefundableDepositDTO);
        CartDTO cartDTO = new CartDTO();
        service.updateCartDTOWithDepositItem(cartDTO, OYSTER_NUMBER_1);
        assertEquals(new Integer(5),cartDTO.getCardRefundableDepositItem().getPrice());
    }
    
    @Test
    public void shouldUpdateCardDepositForFailedCardRefund() {
        CartDTO cartDTO = new CartDTO();
        Date dateOfRefund = new Date();
        CardRefundableDepositDataService mockCardRefundableDepositDataService = mock(CardRefundableDepositDataService.class);
        service.cardRefundableDepositDataService = mockCardRefundableDepositDataService;
        CardRefundableDepositDTO cardRefundableDepositDTO = new CardRefundableDepositDTO();
        cartDTO.setDateOfRefund(dateOfRefund);
        cartDTO.addCartItem(CartTestUtil.getCardRefundableDepositItemDTO());
        when(mockGetCardService.getCard(anyString())).thenReturn(getCardWitDeposit());
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID);
        doNothing().when(mockCartService).deleteCartForCardId(anyLong());
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        CardInfoResponseV2DTO mockCardInfoResponseDTO = mock(CardInfoResponseV2DTO.class);
        when(mockCardInfoResponseDTO.getCardDeposit()).thenReturn(new Integer(5));
        when(mockCardRefundableDepositDataService.findByPrice(anyInt())).thenReturn(cardRefundableDepositDTO);
        cartDTO = service.getCartDTOForRefund(OYSTER_NUMBER_1, CartType.FAILED_CARD_REFUND.code(),false);
        Integer totalRefund = cartDTO.getCartRefundTotal()+cartDTO.getCardRefundableDepositAmount();
        assertEquals(new Integer(500),totalRefund);
    }
    
    @Test
    public void getPayAsYouGoValueFromCartDTOShouldReturnNull() {
        CartDTO cartNoPayAsYouGoItem = CartTestUtil.getNewCartDTOWithItem1();
        assertNull(service.getPayAsYouGoValueFromCartDTO(cartNoPayAsYouGoItem));
    }
    
    @Test
    public void getAdministrationFeeValueFromCartDTOShouldReturnNull() {
        CartDTO cartWithNoAdministrationFeeItem = CartTestUtil.getNewCartDTOWithCartRefundTotal();
        assertNull(service.getAdministrationFeeValueFromCartDTO(cartWithNoAdministrationFeeItem));
    }
    
    @Test
    public void doNotDeleteCartIfItsFromApprovalShouldCallDeleteCart() {
        service.doNotDeleteCartIfItsFromApproval(CartTestUtil.CART_ID_1 , true);
        verify(mockCartService,atLeastOnce()).deleteCartForCardId(anyLong());
    }
    
    @Test
    public void shouldGetDetailsFromCubic() {
        when(mockCubicCardDetailsToCartItemCmdImplConverter.convertCubicCardDetailsToCartItemCmdImpls(anyString(), anyString())).thenReturn(cartCmdItemImplSet);
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ItemDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotal());
        assertEquals(CartDTO.class, service.getDetailsFromCubic(CartTestUtil.getNewCartDTOWithCartRefundTotal(), CardTestUtil.OYSTER_NUMBER_1, CartType.FAILED_CARD_REFUND.code(), true).getClass());
        verify(mockGetCardService).getCard(anyString());
        verify(mockCubicCardDetailsToCartItemCmdImplConverter).convertCubicCardDetailsToCartItemCmdImpls(anyString(), anyString());
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class));
    }
    
}
