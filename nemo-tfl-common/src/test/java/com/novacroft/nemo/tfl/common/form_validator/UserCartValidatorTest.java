package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.Durations.SEVEN_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.MAXIMUM_ALLOWED_PENDING_ITEMS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_DATE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_ZONE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_ZONE_3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_DATE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_DATE_3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_ZONE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_ZONE_3;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithExpiredProductItems;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithGoodwillItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithProductItem;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.decorator.CartItemCmdImplDecorator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * UserCartValidator unit tests
 */
public class UserCartValidatorTest {
    private UserCartValidator validator;
    private CartItemCmdImplDecorator cmd;
    private SystemParameterService mockSystemParameterService;
    private TopUpTicketService mockTopUpTicketService;
    private CartDataService mockCartDataService;
    private ZoneService mockZoneService;
    private CartService mockCartService;
    private CartDTO cartDTO;
    private CardDataService cardDataService;
    private GetCardService getCardService;

    public static final String SEVEN_DAY_TRAVEL_CARD = "7 day Travelcard";

    @Before
    public void setUp() {
        validator = new UserCartValidator();
        cmd = new CartItemCmdImplDecorator();
        cmd.setItemsInThisCart(1);
        cartDTO = new CartDTO();
        ProductItemDTO productItemDTO = ProductItemTestUtil.getTestBussPassProductDTO1();
        productItemDTO.setName(SEVEN_DAY_TRAVEL_CARD);
        cartDTO.getCartItems().add(productItemDTO);

        mockSystemParameterService = mock(SystemParameterService.class);
        mockTopUpTicketService = mock(TopUpTicketService.class);
        mockCartDataService = mock(CartDataService.class);
        mockZoneService = mock(ZoneService.class);
        mockCartService = mock(CartService.class);
        cardDataService = mock(CardDataService.class);
        getCardService = mock(GetCardService.class);

        validator.cartService = mockCartService;
        validator.systemParameterService = mockSystemParameterService;
        validator.topUpTicketService = mockTopUpTicketService;
        validator.cartDataService = mockCartDataService;
        validator.zoneService = mockZoneService;
        validator.cardDataService = cardDataService;
        validator.getCardService = getCardService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartItemCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldValidateWithTravelCard() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS))
                        .thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
                        MAXIMUM_ALLOWED_PENDING_ITEMS);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockTopUpTicketService.updateCartDTOWithProductsFromCubic(any(CartDTO.class))).thenReturn(cartDTO);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO5());
        cmd.setStartDate(TRAVEL_START_DATE_3);
        cmd.setEndDate(DateUtil.formatDate(new Date()));
        cmd.setTravelCardType(SEVEN_DAYS.getDurationType());
        cmd.setStartZone(TRAVEL_START_ZONE_3);
        cmd.setEndZone(TRAVEL_END_ZONE_3);
        cmd.setTicketType(ProductItemType.TRAVEL_CARD.databaseCode());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithOverlapTravelCardZones() {
        when(mockCartDataService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        when(
                        mockZoneService.isZonesOverlapWithProductItemDTOZones(anyInt(), anyInt(), (ProductItemDTO) anyObject(), (Date) anyObject(),
                                        (Date) anyObject())).thenReturn(true);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockTopUpTicketService.updateCartDTOWithProductsFromCubic(any(CartDTO.class))).thenReturn(cartDTO);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        cmd.setStartDate(TRAVEL_START_DATE_2);
        cmd.setEndDate(TRAVEL_END_DATE_2);
        cmd.setTravelCardType(SEVEN_DAYS.getDurationType());
        cmd.setStartZone(TRAVEL_START_ZONE_2);
        cmd.setEndZone(TRAVEL_END_ZONE_2);
        cmd.setTicketType(ProductItemType.TRAVEL_CARD.databaseCode());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        verify(mockZoneService, atLeastOnce()).isZonesOverlapWithProductItemDTOZones(anyInt(), anyInt(), (ProductItemDTO) anyObject(),
                        (Date) anyObject(), (Date) anyObject());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithOverlapTravelCardDates() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        when(mockZoneService.isZonesOverlapWithProductItemDTOZones(anyInt(), anyInt(), (ProductItemDTO) anyObject(), (Date) anyObject(),
                                        (Date) anyObject())).thenReturn(true);
        when(mockTopUpTicketService.updateCartDTOWithProductsFromCubic(any(CartDTO.class))).thenReturn(cartDTO);
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        cmd.setStartDate(TRAVEL_START_DATE_2);
        cmd.setEndDate(TRAVEL_END_DATE_2);
        cmd.setTravelCardType(SEVEN_DAYS.getDurationType());
        cmd.setStartZone(TRAVEL_START_ZONE_2);
        cmd.setEndZone(TRAVEL_END_ZONE_2);
        cmd.setTicketType(ProductItemType.TRAVEL_CARD.databaseCode());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMaximumTravelCards() {
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockTopUpTicketService.updateCartDTOWithProductsFromCubic(any(CartDTO.class))).thenReturn(cartDTO);
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getCardResponseWithMaxPendingItems());

        cmd.setStartDate(TRAVEL_START_DATE_2);
        cmd.setEndDate(TRAVEL_END_DATE_2);
        cmd.setTravelCardType(SEVEN_DAYS.getDurationType());
        cmd.setStartZone(TRAVEL_START_ZONE_2);
        cmd.setEndZone(TRAVEL_END_ZONE_2);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void isEndZoneExistsInCartItemCmdImplFalseResult() {
        cmd.setEndZone(null);
        assertFalse(validator.isEndZoneExistsInCartItemCmdImpl(cmd));
    }

    @Test
    public void isStartZoneExistsInCartItemCmdImplFalseResult() {
        cmd.setStartZone(null);
        assertFalse(validator.isStartZoneExistsInCartItemCmdImpl(cmd));
    }

    @Test
    public void isStartDateExistsInCartItemCmdImplFalseResult() {
        cmd.setStartDate(null);
        assertFalse(validator.isStartDateExistsInCartItemCmdImpl(cmd));
    }

    @Test
    public void registerMaximumTravelCardsErrorinErrorsObjectForCardIdExceedsMaxiTravelCards() {
        when(mockTopUpTicketService.isOysterCardIncludesPendingOrExistingTravelCards(anyLong())).thenReturn(true);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.registerMaximumTravelCardsErrorinErrorsObjectForCardId(errors, CartTestUtil.CARD_ID_1);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void isTravelCardNotExpiredTest1() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfProductItemDTOsInCartDTOExceedMaximumAllowedTravelCardsLimit(getNewCartDTOWithGoodwillItem(), null, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void isTravelCardNotExpiredTest2() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfProductItemDTOsInCartDTOExceedMaximumAllowedTravelCardsLimit(getNewCartDTOWithExpiredProductItems(),
                        CartTestUtil.CARD_ID_1, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfDoesNototExceedMaxPendingItems() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        cmd.setStartDate(DateUtil.formatDate(new Date()));
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
                        MAXIMUM_ALLOWED_PENDING_ITEMS);
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6());

        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfDoesNototExceedMaximumPendingItems() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
                        MAXIMUM_ALLOWED_PENDING_ITEMS);
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6());

        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfExceedMaxPendingItems() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
                        MAXIMUM_ALLOWED_PENDING_ITEMS);
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getCardResponseWithMaxPendingItems());

        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfExceedMaxPendingItemsCombinedWithCartTotal() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
                        MAXIMUM_ALLOWED_PENDING_ITEMS);
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getCardResponseWithMaxPendingItems());

        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    @Test
    public void shouldValidateIfExceedMaxPendingItemsCombinedWithCartTotalIsNotRequired() {
    	CartItemCmdImpl cmd = new CartItemCmdImpl();
    	Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
    	when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
    	when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
    			MAXIMUM_ALLOWED_PENDING_ITEMS);
    	when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getCardResponseWithMaxPendingItems());
    	
    	validator.validate(cmd, errors);
    	assertFalse(errors.hasErrors());
    }

    @Test
    public void shoudValidateIfThereAreNoPendingItems() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        cmd.setStartDate(TRAVEL_START_DATE_2);
        cmd.setEndDate(TRAVEL_END_DATE_2);
        cmd.setTravelCardType(SEVEN_DAYS.getDurationType());
        cmd.setStartZone(TRAVEL_START_ZONE_2);
        cmd.setEndZone(TRAVEL_END_ZONE_2);
        cmd.setTicketType(ProductItemType.BUS_PASS.databaseCode());
        when(cardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_PENDING_ITEMS)).thenReturn(
                        MAXIMUM_ALLOWED_PENDING_ITEMS);
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTravelCardWithDiscount());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS))
                        .thenReturn(MAXIMUM_ALLOWED_TRAVEL_CARDS);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when(mockTopUpTicketService.updateCartDTOWithProductsFromCubic(any(CartDTO.class))).thenReturn(cartDTO);

        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void isCartItemABusPass() {
        cmd.setTicketType(ProductItemType.BUS_PASS.databaseCode());
        assertTrue(validator.isCartItemABusPass(cmd));
    }
    
    @Test 
    public void isNotProductItemDTOATravelCard() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("");
        assertFalse(validator.isProductItemDTOATravelCard(itemDTO));
    }
    
    @Test 
    public void isProductItemDTOATravelCard() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("travelcard");
        assertTrue(validator.isProductItemDTOATravelCard(itemDTO));
    }
    
    @Test
    public void isProductItemDTOABusPass() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("bus");
        assertTrue(validator.isProductItemDTOABusPass(itemDTO));
    }
    
    @Test
    public void isProductItemDTOABusPassContainsBusPass() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(ProductItemType.BUS_PASS.databaseCode());
        assertTrue(validator.isProductItemDTOABusPass(itemDTO));
    }
    
    @Test
    public void isNotProductItemDTOABusPass() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("");
        assertFalse(validator.isProductItemDTOABusPass(itemDTO));
    }
}
