package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardRefundableDepositItemTestUtil;
import com.novacroft.nemo.test_support.CardRefundableDepositTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.HotlistReasonTestUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.ConvertCubicCardDetailsToCartDTOService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.AutoTopUpConfirmationEmailService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

public class TransferProductServiceImplTest {

    private TransferProductServiceImpl service;
    private CartService mockCartService;
    private CartDataService mockCartDataService;
    private OrderDataService mockOrderDataService;
    private AutoTopUpConfigurationService mockAutoTopUpConfigurationService;
    private CardUpdateService mockCardUpdateService;
    private CardDataService mockCardDataService;
    private HotlistCardService mockHotlistCardService;
    private ConvertCubicCardDetailsToCartDTOService mockConvertCubicCardDetailsToCartDTOService;
    private PayAsYouGoService mockPayAsYouGoService;
    private TravelCardService mockTravelCardService;
    private BaseEmailPreparationService mockBaseEmailPreparationService;
    private AutoTopUpConfirmationEmailService mockTransferProductsConfirmationEmailService;
    private LocationDataService mockLocationDataService;
    private CustomerDataService mockCustomerDataService;
    private PayAsYouGoDataService mockPayAsYouGoDataService;
    private CardRefundableDepositDataService mockCardRefundableDepositDataService;
    private HttpSession mockHttpSession;
    private HotlistReasonDataService mockHotlistReasonDataService;
    private CartDTO cartDTO;
    
    private final Long sourceCardId = new Long(5012);
    private final Long targetCardId = new Long(5013);
    private final Long stationId = new Long(582);
    private final Long customerId = new Long(5001);
    private final Integer totalAmount = 1000;

    @Before
    public void setUp() throws Exception {
        service = new TransferProductServiceImpl();
        mockCartService = mock(CartService.class);
        mockCartDataService = mock(CartDataService.class);
        mockOrderDataService = mock(OrderDataService.class);
        mockAutoTopUpConfigurationService = mock(AutoTopUpConfigurationService.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockCardDataService = mock(CardDataService.class);
        mockHotlistCardService = mock(HotlistCardService.class);
        mockConvertCubicCardDetailsToCartDTOService = mock(ConvertCubicCardDetailsToCartDTOService.class);
        mockPayAsYouGoService = mock(PayAsYouGoService.class);
        mockTravelCardService = mock(TravelCardService.class);
        mockBaseEmailPreparationService = mock(BaseEmailPreparationService.class);
        mockTransferProductsConfirmationEmailService = mock(AutoTopUpConfirmationEmailService.class);
        mockLocationDataService = mock(LocationDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockPayAsYouGoDataService = mock(PayAsYouGoDataService.class);
        mockCardRefundableDepositDataService = mock(CardRefundableDepositDataService.class);
        mockHttpSession = mock(HttpSession.class);
        mockHotlistReasonDataService = mock(HotlistReasonDataService.class);
        cartDTO = CartTestUtil.getTestCartDTO1();
        
        service.cartService = mockCartService;
        service.cartDataService = mockCartDataService;
        service.orderDataService = mockOrderDataService;
        service.autoTopUpConfigurationService = mockAutoTopUpConfigurationService;
        service.cardUpdateService = mockCardUpdateService;
        service.cardDataService = mockCardDataService;
        service.hotlistCardService = mockHotlistCardService;
        service.convertCubicCardDetailsToCartDTOService = mockConvertCubicCardDetailsToCartDTOService;
        service.payAsYouGoService = mockPayAsYouGoService;
        service.travelCardService = mockTravelCardService;
        service.baseEmailPreparationService = mockBaseEmailPreparationService;
        service.autoTopUpConfigurationService= mockAutoTopUpConfigurationService;
        service.locationDataService = mockLocationDataService;
        service.customerDataService  = mockCustomerDataService;
        service.transferProductsConfirmationEmailService = mockTransferProductsConfirmationEmailService;
        service.payAsYouGoDataService = mockPayAsYouGoDataService;
        service.cardRefundableDepositDataService  = mockCardRefundableDepositDataService;
        service.cartService =mockCartService;
        service.hotlistReasonDataService = mockHotlistReasonDataService;
        
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
    }

    @Test
    public void shouldValidateCreateAndPersistSourceCardItemsToSourceCart() {
    	when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockConvertCubicCardDetailsToCartDTOService.populateCartItemsToCartDTOFromCubic(any(CartDTO.class), anyLong())).thenReturn(
                        CartTestUtil.getTestCartDTO1());
        service.createAndPersistSourceCardItemsToSourceCart(mockHttpSession, sourceCardId);
        verify(mockCartService).updateCartWithoutRefundCalculationInPostProcess(any(CartDTO.class));

    }

    @Test
    public void shouldValidateCreateAndPersistTargetCardItemsToTargetCart() {
        when(mockConvertCubicCardDetailsToCartDTOService.populateCartItemsToCartDTOFromCubic(any(CartDTO.class), anyLong())).thenReturn(
                        CartTestUtil.getTestCartDTO1());
        service.createAndPersistTargetCardItemsToTargetCart(CartTestUtil.getTestCartDTO1(), sourceCardId);
        verify(mockCartService,never()).updateCartWithoutRefundCalculationInPostProcess(any(CartDTO.class));

    }


    @Test
    public void shouldUpdateOrderStatusAndItems() {
        OrderDTO orderDTO = OrderTestUtil.getOrderDTOWithItems();
        orderDTO.setStatus(OrderStatus.PAID.code());
        orderDTO = service.updateOrderStatusAndItems(CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems(), orderDTO);
        verify(mockOrderDataService).create(any(OrderDTO.class));
    }

    @Test
    public void shouldHandleAutoLoadConfigurationFromSourcecardToTargetCard() {
        CartDTO mockSourceCartDTO = mock(CartDTO.class);
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
        autoTopUpItemDTO.setAutoTopUpId(new Long(2));
        when(mockSourceCartDTO.getAutoTopUpItem()).thenReturn(autoTopUpItemDTO);
        when(mockSourceCartDTO.isAutoTopUpPresent()).thenReturn(true);
        when(mockConvertCubicCardDetailsToCartDTOService.getCardDetailsFromCubic(anyLong())).thenReturn(
                        CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2());
        CartDTO targetCartDTO = new CartDTO();
        doNothing().when(mockAutoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        CardDTO targetCardDTO = CardTestUtil.getTestCardDTO1();
        targetCardDTO.setPaymentCardId(new Long(1234));
        when(mockCardDataService.findById(anyLong())).thenReturn(targetCardDTO);
        targetCartDTO.setOrder(OrderTestUtil.getTestOrderDTO1());
        service.handleAutoLoadConfigurationFromSourceCardToTargetCard(mockSourceCartDTO, targetCartDTO,stationId);
        verify(mockCardDataService).createOrUpdate(any(CardDTO.class));
    }

    @Test
    public void shouldTransferAndPersistProductsFromSourceCardToTargetCard() {
        
        List<ItemDTO> cartItems = new ArrayList<ItemDTO>();
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
        autoTopUpItemDTO.setAutoTopUpAmount(2000);
        autoTopUpItemDTO.setAutoTopUpId(new Long(2));
        cartItems.add(autoTopUpItemDTO);
        cartItems.add(CartTestUtil.getPayAsYouGoItemDTO());
        cartItems.add(CartTestUtil.getNewProductItemDTO());
        CartDTO sourceCartDTO = CartTestUtil.getTestCartDTO1();
        sourceCartDTO.setCartItems(cartItems);
        Boolean isCardLostOrStolen = Boolean.FALSE;
        when(mockConvertCubicCardDetailsToCartDTOService.populateCartItemsToCartDTOFromCubic(any(CartDTO.class), anyLong()))
                        .thenReturn(sourceCartDTO);
        when(mockCartService.updateCartWithoutRefundCalculationInPostProcess(any(CartDTO.class))).thenReturn(sourceCartDTO);
        OrderDTO orderDTO = OrderTestUtil.getTestOrderDTO1();
        orderDTO.setStatus(OrderStatus.PAID.code());
        when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(orderDTO);
        when(mockConvertCubicCardDetailsToCartDTOService.getCardDetailsFromCubic(anyLong())).thenReturn(
                        CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2());
        doNothing().when(mockAutoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        CardDTO targetCardDTO = CardTestUtil.getTestCardDTO1();
        targetCardDTO.setPaymentCardId(new Long(1234));
        when(mockCardDataService.findById(anyLong())).thenReturn(targetCardDTO);
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(targetCardDTO);
        when(mockLocationDataService.findById(anyLong())).thenReturn(LocationTestUtil.getTestLocationDTO1());
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockBaseEmailPreparationService.getSalutation(any(CustomerDTO.class))).thenReturn("Mr");
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockPayAsYouGoService.updatePrePayValueToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(mockTravelCardService.addPrePayTicketToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(mockHotlistReasonDataService.findByDescription(anyString())).thenReturn(HotlistReasonTestUtil.getHotlistReasonDTO3());
        Map<String,Object> data = service.transferProductFromSourceCardToTargetCard(mockHttpSession, sourceCardId, targetCardId, stationId,isCardLostOrStolen);
        doNothing().when(mockTransferProductsConfirmationEmailService).sendConfirmationMessage(any(EmailArgumentsDTO.class));
        assertTrue((Boolean)data.get(TransferConstants.IS_TRANSFER_SUCCESSFUL));
        
    }
    
    @Test(expected = ApplicationServiceException.class)
    public void shouldNotTransferAndPersistProductsFromSourceCardDueToPrePayValueCubicFailure() {

        List<ItemDTO> cartItems = new ArrayList<ItemDTO>();
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
        autoTopUpItemDTO.setAutoTopUpAmount(2000);
        autoTopUpItemDTO.setAutoTopUpId(new Long(2));
        cartItems.add(autoTopUpItemDTO);
        cartItems.add(CartTestUtil.getPayAsYouGoItemDTO());
        cartItems.add(CartTestUtil.getNewProductItemDTO());
        CartDTO sourceCartDTO = CartTestUtil.getTestCartDTO1();
        sourceCartDTO.setCartItems(cartItems);
        Boolean isCardLostOrStolen = Boolean.FALSE;
        when(mockConvertCubicCardDetailsToCartDTOService.populateCartItemsToCartDTOFromCubic(any(CartDTO.class), anyLong())).thenReturn(sourceCartDTO);
        when(mockCartService.updateCartWithoutRefundCalculationInPostProcess(any(CartDTO.class))).thenReturn(sourceCartDTO);
        OrderDTO orderDTO = OrderTestUtil.getTestOrderDTO1();
        orderDTO.setStatus(OrderStatus.PAID.code());
        when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(orderDTO);
        when(mockConvertCubicCardDetailsToCartDTOService.getCardDetailsFromCubic(anyLong())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2());
        doNothing().when(mockAutoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        CardDTO targetCardDTO = CardTestUtil.getTestCardDTO1();
        targetCardDTO.setPaymentCardId(new Long(1234));
        when(mockCardDataService.findById(anyLong())).thenReturn(targetCardDTO);
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(targetCardDTO);
        when(mockLocationDataService.findById(anyLong())).thenReturn(LocationTestUtil.getTestLocationDTO1());
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockBaseEmailPreparationService.getSalutation(any(CustomerDTO.class))).thenReturn("Mr");
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockPayAsYouGoService.updatePrePayValueToCubic(any(CartCmdImpl.class))).thenReturn(true);
        when(mockTravelCardService.addPrePayTicketToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(mockHotlistReasonDataService.findByDescription(anyString())).thenReturn(HotlistReasonTestUtil.getHotlistReasonDTO3());
        doNothing().when(mockTransferProductsConfirmationEmailService).sendConfirmationMessage(any(EmailArgumentsDTO.class));

        Map<String, Object> data = service.transferProductFromSourceCardToTargetCard(mockHttpSession, sourceCardId, targetCardId, stationId, isCardLostOrStolen);

        verify(mockPayAsYouGoService, times(1)).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(mockTravelCardService, never()).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }
    
    @Test(expected = ApplicationServiceException.class)
    public void shouldNotTransferAndPersistProductsFromSourceCardDueToPrePayTicketCubicFailure() {

        List<ItemDTO> cartItems = new ArrayList<ItemDTO>();
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = new AutoTopUpConfigurationItemDTO();
        autoTopUpItemDTO.setAutoTopUpAmount(2000);
        autoTopUpItemDTO.setAutoTopUpId(new Long(2));
        cartItems.add(autoTopUpItemDTO);
        cartItems.add(CartTestUtil.getPayAsYouGoItemDTO());
        cartItems.add(CartTestUtil.getNewProductItemDTO());
        CartDTO sourceCartDTO = CartTestUtil.getTestCartDTO1();
        sourceCartDTO.setCartItems(cartItems);
        Boolean isCardLostOrStolen = Boolean.FALSE;
        when(mockConvertCubicCardDetailsToCartDTOService.populateCartItemsToCartDTOFromCubic(any(CartDTO.class), anyLong())).thenReturn(sourceCartDTO);
        when(mockCartService.updateCartWithoutRefundCalculationInPostProcess(any(CartDTO.class))).thenReturn(sourceCartDTO);
        OrderDTO orderDTO = OrderTestUtil.getTestOrderDTO1();
        orderDTO.setStatus(OrderStatus.PAID.code());
        when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(orderDTO);
        when(mockConvertCubicCardDetailsToCartDTOService.getCardDetailsFromCubic(anyLong())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2());
        doNothing().when(mockAutoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        CardDTO targetCardDTO = CardTestUtil.getTestCardDTO1();
        targetCardDTO.setPaymentCardId(new Long(1234));
        when(mockCardDataService.findById(anyLong())).thenReturn(targetCardDTO);
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(targetCardDTO);
        when(mockLocationDataService.findById(anyLong())).thenReturn(LocationTestUtil.getTestLocationDTO1());
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockBaseEmailPreparationService.getSalutation(any(CustomerDTO.class))).thenReturn("Mr");
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockPayAsYouGoService.updatePrePayValueToCubic(any(CartCmdImpl.class))).thenReturn(false);
        when(mockTravelCardService.addPrePayTicketToCubic(any(CartCmdImpl.class))).thenReturn(true);
        when(mockHotlistReasonDataService.findByDescription(anyString())).thenReturn(HotlistReasonTestUtil.getHotlistReasonDTO3());
        doNothing().when(mockTransferProductsConfirmationEmailService).sendConfirmationMessage(any(EmailArgumentsDTO.class));

        Map<String, Object> data = service.transferProductFromSourceCardToTargetCard(mockHttpSession, sourceCardId, targetCardId, stationId, isCardLostOrStolen);

        verify(mockPayAsYouGoService, times(1)).updatePrePayValueToCubic(any(CartCmdImpl.class));
        verify(mockTravelCardService, times(1)).addPrePayTicketToCubic(any(CartCmdImpl.class));

    }

    @Test
    public void shouldAddSourceCardRefundableDepositAmountToTargetCardPayGAmount() {
        List<ItemDTO> sourceCartItems = new ArrayList<ItemDTO>();
        CardRefundableDepositItemDTO cardRefundableDepositItemDTO = CardRefundableDepositItemTestUtil.getTestCardRefundableDepositItemDTO1();
        sourceCartItems.add(cardRefundableDepositItemDTO);
        CartDTO sourceCartDTO = CartTestUtil.getTestCartDTO1();
        sourceCartDTO.setCartItems(sourceCartItems);
        
        List<ItemDTO> targetCartItems = new ArrayList<ItemDTO>();
        PayAsYouGoItemDTO payAsYouGoItemDTO = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1();
        targetCartItems.add(payAsYouGoItemDTO);
        CartDTO targetCartDTO = CartTestUtil.getTestCartDTO1();
        targetCartDTO.setCardId(new Long(1234));
        targetCartDTO.setCartItems(targetCartItems);
        
        when(mockPayAsYouGoDataService.findById(anyLong())).thenReturn(PayAsYouGoTestUtil.getTestPayAsYouGoDTO1());
        when(mockCardRefundableDepositDataService.findById(anyLong())).thenReturn(CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1());
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(PayAsYouGoTestUtil.getTestPayAsYouGoDTO1());
        
        service.addSourceCardRefundableDepositAmountToTargetCardPayGAmount(sourceCartDTO, targetCartDTO,Boolean.FALSE);
        assertEquals(new Integer(1500), targetCartDTO.getPayAsYouGoItem().getPrice());
    }
    
    @Test
    public void shouldNotAddSourceCardRefundableDepositAmountToTargetCardPayGAmountForLostOrStolen() {
        List<ItemDTO> sourceCartItems = new ArrayList<ItemDTO>();
        CardRefundableDepositItemDTO cardRefundableDepositItemDTO = CardRefundableDepositItemTestUtil.getTestCardRefundableDepositItemDTO1();
        Boolean isLostOrStolen = Boolean.TRUE;
        sourceCartItems.add(cardRefundableDepositItemDTO);
        CartDTO sourceCartDTO = CartTestUtil.getTestCartDTO1();
        sourceCartDTO.setCartItems(sourceCartItems);
        
        List<ItemDTO> targetCartItems = new ArrayList<ItemDTO>();
        PayAsYouGoItemDTO payAsYouGoItemDTO = PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1();
        targetCartItems.add(payAsYouGoItemDTO);
        CartDTO targetCartDTO = CartTestUtil.getTestCartDTO1();
        targetCartDTO.setCardId(new Long(1234));
        targetCartDTO.setCartItems(targetCartItems);
        
        when(mockPayAsYouGoDataService.findById(anyLong())).thenReturn(PayAsYouGoTestUtil.getTestPayAsYouGoDTO1());
        when(mockCardRefundableDepositDataService.findById(anyLong())).thenReturn(CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1());
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(PayAsYouGoTestUtil.getTestPayAsYouGoDTO1());
        
        service.addSourceCardRefundableDepositAmountToTargetCardPayGAmount(sourceCartDTO, targetCartDTO,isLostOrStolen);
        assertEquals(new Integer(1000), targetCartDTO.getPayAsYouGoItem().getPrice());
    }
}
