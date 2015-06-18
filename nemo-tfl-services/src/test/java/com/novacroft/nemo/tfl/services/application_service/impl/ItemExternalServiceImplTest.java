package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_ADDED;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_START_DATE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.PrePaidTicketDataServiceImpl;
import com.novacroft.nemo.tfl.common.form_validator.BusPassValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.UserCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.Item;

public class ItemExternalServiceImplTest {

    private ItemExternalServiceImpl service;
    private CartService mockCartService;
    private CardDataService mockCardDataService;
    private CartDataService mockCartDataService;
    private TravelCardService mockTravelCardService;
    private BusPassService mockBusPassService;
    private PayAsYouGoService mockPayAsYouGoService;
    private UserCartValidator mockUserCartValidator;
    private TravelCardValidator mockTravelCardValidator;
    private ZoneMappingValidator mockZoneMappingValidator;
    private PayAsYouGoValidator mockPayAsYouGoValidator;
    private BusPassValidator mockBusPassValidator;
    private CardService mockCardService;
    private PrePaidTicketDataServiceImpl mockPrePaidTicketDataServiceImpl;

    @Before
    public void setUp() throws Exception {
        service = mock(ItemExternalServiceImpl.class);
        mockBusPassService = mock(BusPassService.class);
        mockBusPassValidator = mock(BusPassValidator.class);
        mockCardDataService = mock(CardDataService.class);
        mockCartService = mock(CartService.class);
        mockCartDataService = mock(CartDataService.class);
        mockTravelCardService = mock(TravelCardService.class);
        mockPayAsYouGoService = mock(PayAsYouGoService.class);
        mockUserCartValidator = mock(UserCartValidator.class);
        mockTravelCardValidator = mock(TravelCardValidator.class);
        mockZoneMappingValidator = mock(ZoneMappingValidator.class);
        mockPayAsYouGoValidator = mock(PayAsYouGoValidator.class);
        mockCardService = mock(CardService.class);
        mockPrePaidTicketDataServiceImpl = mock(PrePaidTicketDataServiceImpl.class);
        service.busPassService = mockBusPassService;
        service.busPassValidator = mockBusPassValidator;
        service.cardDataService = mockCardDataService;
        service.cartService = mockCartService;
        service.cartDataService = mockCartDataService;
        service.payAsYouGoService = mockPayAsYouGoService;
        service.payAsYouGoValidator = mockPayAsYouGoValidator;
        service.travelCardService = mockTravelCardService;
        service.travelCardValidator = mockTravelCardValidator;
        service.userCartValidator = mockUserCartValidator;
        service.zoneMappingValidator = mockZoneMappingValidator;
        service.cardService = mockCardService;
        service.prePaidTicketDataService = mockPrePaidTicketDataServiceImpl;
        
    }

    @Test
    public void addBusPassItemToCartCompletesSuccessFully() {
        CartDTO cartDTO = new CartDTO();
        doNothing().when(mockBusPassValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockBusPassService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(service.addBusPassItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenCallRealMethod();
        Errors errors = service.addBusPassItemToCart(cartDTO, ItemTestUtil.getTestBusPassItem(), new Cart(),0);
        verify(mockBusPassService).addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void addBusPassItemToCartHasErrors() {
        CartDTO cartDTO = new CartDTO();
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Errors errors = (Errors) args[1];
                errors.reject(ALREADY_ADDED.errorCode());
                return null;
            }
        }).when(mockBusPassValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockBusPassService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(service.addBusPassItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenCallRealMethod();
        Errors errors = service.addBusPassItemToCart(cartDTO, ItemTestUtil.getTestBusPassItem(), new Cart(),0);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void addPayAsYouGoItemToCartCompletesSuccessFully() {
        CartDTO cartDTO = new CartDTO();
        doNothing().when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockPayAsYouGoService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());
        when(service.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenCallRealMethod();
        Errors errors = service.addPayAsYouGoItemToCart(cartDTO, ItemTestUtil.getTestPayAsYouGoItem(),0);
        verify(mockPayAsYouGoService).addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void addPayAsYouGoItemHasErrors() {
        CartDTO cartDTO = new CartDTO();
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Errors errors = (Errors) args[1];
                errors.rejectValue(PageCommandAttribute.FIELD_CREDIT_BALANCE, INVALID_AMOUNT.errorCode());
                return null;
            }
        }).when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockPayAsYouGoService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());
        when(service.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenCallRealMethod();
        Errors errors = service.addPayAsYouGoItemToCart(cartDTO, ItemTestUtil.getTestPayAsYouGoItem(),0);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void addTravelCardItemToCartCompletesSuccessFully() {
        CartDTO cartDTO = new CartDTO();
        Cart cart = new Cart();
        cart.setPassengerType(CubicConstant.PASSENGER_TYPE_ADULT_CODE);
        cart.setDiscountType(CubicConstant.NO_DISCOUNT_TYPE_CODE);
        doNothing().when(mockTravelCardValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockZoneMappingValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        doCallRealMethod().when(service).setPassengerTypeAndDiscountType(any(Cart.class), any(CartItemCmdImpl.class));
        when(service.formatEmailReminder(anyString())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn("");
        when(service.addTravelCardItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenCallRealMethod();
        Errors errors = service.addTravelCardItemToCart(cartDTO, ItemTestUtil.getTestTravelCardItem1(), cart,0);
        verify(mockTravelCardService).addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void addTravelCardItemToCartHasErrors() {
        CartDTO cartDTO = new CartDTO();
        Cart cart = new Cart();
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Errors errors = (Errors) args[1];
                errors.rejectValue(FIELD_END_DATE, GREATER_THAN_START_DATE.errorCode());
                return null;
            }
        }).when(mockTravelCardValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        doNothing().when(mockZoneMappingValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        doCallRealMethod().when(service).setPassengerTypeAndDiscountType(any(Cart.class), any(CartItemCmdImpl.class));
        when(service.getContent(anyString())).thenReturn("");
        when(service.addTravelCardItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn("");
        Errors errors = service.addTravelCardItemToCart(cartDTO, ItemTestUtil.getTestTravelCardItem1(), cart, 0);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void testSetUpPrePaidTicketId() {
    	when(mockPrePaidTicketDataServiceImpl.getInternalIdFromExternalId(anyLong())).thenReturn(123l);
        Item item = ItemTestUtil.getTestTravelCardItem1();
        CartItemCmdImpl cartItemCmd = CartItemTestUtil.getTestTravelCard1();
        assertNull(cartItemCmd.getPrePaidTicketId());
        doCallRealMethod().when(service).setUpPrePaidTicketId(any(Item.class), any(CartItemCmdImpl.class), any(Errors.class));
        service.setUpPrePaidTicketId(item, cartItemCmd, null);
        assertNotNull(cartItemCmd.getPrePaidTicketId());
    }
    
    @Test
    public void testInitialisation(){
        assertNotNull(new ItemExternalServiceImpl());
    }

}
