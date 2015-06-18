package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.getTestNotExpiredPaymentCardDTO;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * ManagePaymentCardDetailsValidator unit tests
 */
public class PaymentCardEditValidatorTest {
    private PaymentCardEditValidator validator;
    private PostcodeValidator postcodeValidator;
    private Errors mockErrors;
    private PaymentCardCmdImpl mockPaymentCardCmd;
    private PaymentCardDTO mockPaymentCardDTO;
    private AddressDTO addressDTO;
    private PaymentCardDataService mockPaymentCardDataService;

    @Before
    public void setUp() {
        this.validator = mock(PaymentCardEditValidator.class);
        doCallRealMethod().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());

        this.postcodeValidator = mock(PostcodeValidator.class);
        this.validator.postcodeValidator = this.postcodeValidator;

        this.mockPaymentCardDataService = mock(PaymentCardDataService.class);
        this.validator.paymentCardDataService = this.mockPaymentCardDataService;

        this.mockPaymentCardCmd = new PaymentCardCmdImpl();

        this.addressDTO = new AddressDTO();
        this.addressDTO.setPostcode(POSTCODE_1);
        this.addressDTO.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        this.addressDTO.setStreet(STREET_1);
        this.addressDTO.setTown(TOWN_1);

        this.mockPaymentCardDTO = getTestNotExpiredPaymentCardDTO();
        this.mockPaymentCardDTO.setAddressDTO(this.addressDTO);
        this.mockPaymentCardCmd.setPaymentCardDTO(this.mockPaymentCardDTO);

        this.mockErrors = new BeanPropertyBindingResult(mockPaymentCardCmd, "cmd");
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(PaymentCardCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldValidate() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        doNothing().when(this.validator).rejectIfInvalidPostcode(any(Errors.class), anyString());
        when(this.postcodeValidator.validate(anyString())).thenReturn(Boolean.TRUE);

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);
        assertFalse(this.mockErrors.hasErrors());
    }

    @Test
    public void shouldNotValidateEmptyPostCode() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        doNothing().when(this.validator).rejectIfInvalidPostcode(any(Errors.class), anyString());
        when(this.postcodeValidator.validate(anyString())).thenReturn(Boolean.TRUE);

        this.addressDTO.setPostcode(" ");

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        assertTrue(this.mockErrors.hasErrors());
    }

    @Test
    public void shouldNotValidateEmptyHouseName() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        doNothing().when(this.validator).rejectIfInvalidPostcode(any(Errors.class), anyString());
        when(this.postcodeValidator.validate(anyString())).thenReturn(Boolean.TRUE);

        this.addressDTO.setHouseNameNumber(" ");

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        assertTrue(this.mockErrors.hasErrors());
    }

    @Test
    public void shouldNotValidateEmptyStreet() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        doNothing().when(this.validator).rejectIfInvalidPostcode(any(Errors.class), anyString());
        when(this.postcodeValidator.validate(anyString())).thenReturn(Boolean.TRUE);

        this.addressDTO.setStreet(" ");

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        assertTrue(this.mockErrors.hasErrors());
    }

    @Test
    public void shouldNotValidateEmptyTown() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        doNothing().when(this.validator).rejectIfInvalidPostcode(any(Errors.class), anyString());
        when(this.postcodeValidator.validate(anyString())).thenReturn(Boolean.TRUE);

        this.addressDTO.setTown(" ");

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        assertTrue(this.mockErrors.hasErrors());
    }

    @Test
    public void shouldNotValidateTestForInvalidPostCode() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        doCallRealMethod().when(this.validator).rejectIfInvalidPostcode(any(Errors.class), anyString());
        when(this.postcodeValidator.validate(anyString())).thenReturn(Boolean.FALSE);

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        assertTrue(this.mockErrors.hasErrors());
    }

    @Test
    public void isNickNameUniqueShouldReturnTrue() {
        when(this.mockPaymentCardDataService.findByCustomerIdIfNickNameUsedByAnotherCard(anyLong(), anyLong(), anyString()))
                .thenReturn(null);
        doCallRealMethod().when(this.validator).isNickNameUnique(any(PaymentCardCmdImpl.class));

        assertTrue(this.validator.isNickNameUnique(this.mockPaymentCardCmd));
        verify(this.mockPaymentCardDataService).findByCustomerIdIfNickNameUsedByAnotherCard(anyLong(), anyLong(), anyString());
    }

    @Test
    public void isNickNameUniqueShouldReturnFalse() {
        when(this.mockPaymentCardDataService.findByCustomerIdIfNickNameUsedByAnotherCard(anyLong(), anyLong(), anyString()))
                .thenReturn(new PaymentCardDTO());
        doCallRealMethod().when(this.validator).isNickNameUnique(any(PaymentCardCmdImpl.class));

        assertFalse(this.validator.isNickNameUnique(this.mockPaymentCardCmd));
        verify(this.mockPaymentCardDataService).findByCustomerIdIfNickNameUsedByAnotherCard(anyLong(), anyLong(), anyString());
    }

    @Test
    public void isNotNickNameUniqueShouldReturnTrue() {
        when(this.validator.isNickNameUnique(any(PaymentCardCmdImpl.class))).thenReturn(Boolean.FALSE);
        doCallRealMethod().when(this.validator).isNotNickNameUnique(any(PaymentCardCmdImpl.class));

        assertTrue(this.validator.isNotNickNameUnique(this.mockPaymentCardCmd));
        verify(this.validator).isNickNameUnique(any(PaymentCardCmdImpl.class));
    }

    @Test
    public void isNotNickNameUniqueShouldReturnFalse() {
        when(this.validator.isNickNameUnique(any(PaymentCardCmdImpl.class))).thenReturn(Boolean.TRUE);
        doCallRealMethod().when(this.validator).isNotNickNameUnique(any(PaymentCardCmdImpl.class));

        assertFalse(this.validator.isNotNickNameUnique(this.mockPaymentCardCmd));
        verify(this.validator).isNickNameUnique(any(PaymentCardCmdImpl.class));
    }

    @Test
    public void rejectIfNickNameNotUniqueShouldNotRejectBlank() {
        doCallRealMethod().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        when(this.validator.isNotNickNameUnique(any(PaymentCardCmdImpl.class))).thenReturn(Boolean.FALSE);

        this.mockPaymentCardDTO.setNickName(StringUtils.EMPTY);

        this.validator.rejectIfNickNameNotUnique(this.mockErrors, this.mockPaymentCardCmd);

        assertFalse(this.mockErrors.hasErrors());
        verify(this.validator, never()).isNotNickNameUnique(any(PaymentCardCmdImpl.class));
    }

    @Test
    public void rejectIfNickNameNotUniqueShouldNotRejectUniqueNick() {
        doCallRealMethod().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        when(this.validator.isNotNickNameUnique(any(PaymentCardCmdImpl.class))).thenReturn(Boolean.FALSE);

        this.validator.rejectIfNickNameNotUnique(this.mockErrors, this.mockPaymentCardCmd);

        assertFalse(this.mockErrors.hasErrors());
        verify(this.validator).isNotNickNameUnique(any(PaymentCardCmdImpl.class));
    }

    @Test
    public void rejectIfNickNameNotUniqueShouldRejectNonUniqueNick() {
        doCallRealMethod().when(this.validator).rejectIfNickNameNotUnique(any(Errors.class), any(PaymentCardCmdImpl.class));
        when(this.validator.isNotNickNameUnique(any(PaymentCardCmdImpl.class))).thenReturn(Boolean.TRUE);

        this.validator.rejectIfNickNameNotUnique(this.mockErrors, this.mockPaymentCardCmd);

        assertTrue(this.mockErrors.hasErrors());
        verify(this.validator).isNotNickNameUnique(any(PaymentCardCmdImpl.class));
    }
}
