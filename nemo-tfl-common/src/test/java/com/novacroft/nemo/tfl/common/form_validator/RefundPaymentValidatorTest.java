package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PaymentType.WEB_ACCOUNT_CREDIT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public class RefundPaymentValidatorTest {
    private RefundPaymentValidator validator;
    private CartCmdImpl cmd;
    private CartDTO mockCartDTO;
    private Errors mockErrors;

    @Before
    public void setUp() {
        validator = new RefundPaymentValidator();
        cmd = new CartCmdImpl();
        mockCartDTO = mock(CartDTO.class);
        mockErrors = mock(Errors.class);
        doNothing().when(mockErrors).rejectValue(anyString(), anyString());
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartDTO.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(BusPassCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setPaymentType(WEB_ACCOUNT_CREDIT.code());
        cmd.setCartDTO(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        cartDTO.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfPaymentTypeFieldEmpty() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateZeroTotalAmount() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfPickUpLocationIsDifferentFromPPVLocation() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        cartDTO.setPpvPickupLocationAddFlag(true);
        cartDTO.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateTotalAmountIsNotZeroWithDepositForFailedCardRefund() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithCardRefundableDepositItemDTO();
        CartCmdImpl cmd= new CartCmdImpl();
        cmd.setPaymentType(WEB_ACCOUNT_CREDIT.code());
        cartDTO.setCartType(CartType.FAILED_CARD_REFUND.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertFalse(errors.hasErrors());      
    }
    
    @Test
    public void shouldValidateTotalAmountIsZeroWithDepositForFailedCardRefund() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        CartCmdImpl cmd= new CartCmdImpl();
        cmd.setPaymentType(WEB_ACCOUNT_CREDIT.code());
        cartDTO.setCartType(CartType.FAILED_CARD_REFUND.code());
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());      
    }
    
    @Test
    public void shouldRejectIfPickUpStationIsDifferentFromPendingItemLocation() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        cartDTO.setPpvPickupLocationAddFlag(Boolean.TRUE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.rejectIfPickUpStationIsDifferentFromPendingItemLocation(cartDTO,errors);
        assertTrue(errors.hasErrors());      
    }
    
    @Test
    public void shouldRejectIfPickUpStationIsDifferentFromPendingItemLocation1() {
        CartDTO cartDTO = new CartDTO();
        CartCmdImpl cmd= new CartCmdImpl();
        cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        cmd.setPaymentType(WEB_ACCOUNT_CREDIT.code());
        cartDTO.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        cartDTO.setPpvPickupLocationAddFlag(Boolean.TRUE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO,errors);
        assertTrue(errors.hasErrors());      
    }
    
    @Test
    public void shouldNotRejectIfPickUpStationIsSameAsPendingItemLocation() {
        CartDTO cartDTO = new CartDTO();
        CartCmdImpl cmd= new CartCmdImpl();
        cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        cmd.setPaymentType(WEB_ACCOUNT_CREDIT.code());
        cartDTO.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        cartDTO.setPpvPickupLocationAddFlag(Boolean.FALSE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO,errors);
        assertFalse(errors.hasErrors());      
    }
    
    @Test
    public void shouldNotValidateTotalAmountForFaildedCard() {
        when(mockCartDTO.getCartType()).thenReturn(CartType.FAILED_CARD_REFUND.code());
        when(mockCartDTO.getCartRefundTotal()).thenReturn(Integer.valueOf(0));
        when(mockCartDTO.getCardRefundableDepositAmount()).thenReturn(Integer.valueOf(0));
        
        validator.rejectIfTotalAmountIsZeroOrLess(mockCartDTO, mockErrors);
        
        verify(mockErrors).rejectValue(anyString(), anyString());
    }
    
    @Test
    public void shouldNotValidateTotalAmountForDestroyedCard() {
        when(mockCartDTO.getCartType()).thenReturn(CartType.DESTROYED_CARD_REFUND.code());
        when(mockCartDTO.getCartRefundTotal()).thenReturn(Integer.valueOf(0));
        when(mockCartDTO.getCardRefundableDepositAmount()).thenReturn(Integer.valueOf(0));
        
        validator.rejectIfTotalAmountIsZeroOrLess(mockCartDTO, mockErrors);
        
        verify(mockErrors).rejectValue(anyString(), anyString());
    }
}
