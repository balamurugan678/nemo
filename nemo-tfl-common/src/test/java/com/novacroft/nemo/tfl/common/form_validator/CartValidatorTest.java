package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartTestUtil.*;
import static com.novacroft.nemo.tfl.common.constant.TicketType.BUS_PASS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyString;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * Cart validator unit tests
 */
public class CartValidatorTest {
    private CartValidator validator;
    private CartItemCmdImpl cartItemCmd;
    private SystemParameterService mockSystemParameterService;
    private CartService mockCartService;
    private TopUpTicketService mockTopUpTicketService;
    

    @Before
    public void setUp() {
        validator = new CartValidator();
        cartItemCmd = new CartItemCmdImpl();
        mockSystemParameterService = mock(SystemParameterService.class);
        mockCartService = mock(CartService.class);
        mockTopUpTicketService = mock(TopUpTicketService.class);

        validator.cartService = mockCartService;
        validator.systemParameterService = mockSystemParameterService;
        validator.topUpTicketService = mockTopUpTicketService;

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
    public void shouldValidateWithAnnualBusPass() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        cartItemCmd.setTicketType(BUS_PASS.code());
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cmd");
        validator.validate(cartItemCmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMultipleAnnualBusPasses() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithAnnualBusPassProductItem());
        cartItemCmd.setTicketType(BUS_PASS.code());
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cmd");
        validator.validate(cartItemCmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateCartItemCmdNull(){
        Errors errors = mock(Errors.class);
        validator.validate(null, errors);
        verify(errors, never()).reject(anyString());
        verify(errors, never()).hasErrors();
    }
    
    @Test
    public void sholdNotValidateIfProductItemDTOsInCartDTOExceedMaximumAllowedTravelCardsLimit(){
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithProductItem());
        when (mockTopUpTicketService.isOysterCardIncludesPendingOrExistingTravelCards(anyLong())).thenReturn(true);
        cartItemCmd.setTicketType("Test");
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cmd");
        validator.validate(cartItemCmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfZonesInCartItemCmdImplOverlapWithAlreadyAddedZonesInProductItemDTOsOfCartDTO(){
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when (mockTopUpTicketService.isOysterCardIncludesPendingOrExistingTravelCards(anyLong())).thenReturn(true);
        cartItemCmd.setTicketType("Test");
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cmd");
        validator.validate(cartItemCmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithAnnualBusPassNameNull() {
        CartDTO cartDto = getNewCartDTOWithAnnualBusPassProductItem();
        cartDto.getCartItems().get(0).setName(null);
        when(mockCartService.findById(anyLong())).thenReturn(cartDto);
        cartItemCmd.setTicketType(BUS_PASS.code());
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cmd");
        validator.validate(cartItemCmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithAnnualBusPassNameDoesNotEqualAnnualBusBass() {
        CartDTO cartDto = getNewCartDTOWithAnnualBusPassProductItem();
        cartDto.getCartItems().get(0).setName("Annual");
        when(mockCartService.findById(anyLong())).thenReturn(cartDto);
        cartItemCmd.setTicketType(BUS_PASS.code());
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cmd");
        validator.validate(cartItemCmd, errors);
        assertFalse(errors.hasErrors());
    }

}
