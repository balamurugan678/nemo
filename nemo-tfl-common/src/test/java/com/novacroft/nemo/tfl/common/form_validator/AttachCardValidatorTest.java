package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttachCardValidatorTest {

    protected AttachCardValidator validator;
    private Errors errors;
    private AddUnattachedCardCmdImpl cmd;
    private CardDataService mockCardOysterOnlineService;

    @Before
    public void setUp() {
        validator = new AttachCardValidator();
        cmd = new AddUnattachedCardCmdImpl();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        mockCardOysterOnlineService = mock(CardDataService.class);
        validator.cardOysterOnlineService = mockCardOysterOnlineService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CustomerSearchCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setCardNumber(OYSTER_NUMBER_1);
        when(mockCardOysterOnlineService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfCardNumberIsNull() {
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfCardDataOysterOnlineIsNull() {
        cmd.setCardNumber(OYSTER_NUMBER_1);
        when(mockCardOysterOnlineService.findByCardNumber(anyString())).thenReturn(null);
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfCardDataOysterOnlineCustomerIsNull() {
        cmd.setCardNumber(OYSTER_NUMBER_1);
        CardDTO cardDTO = getTestCardDTO1();
        cardDTO.setWebaccountId(null);
        when(mockCardOysterOnlineService.findByCardNumber(anyString())).thenReturn(cardDTO);
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shoudNotValidateIfCardIsAlreadyAssociated() {
        cmd.setCardNumber(OYSTER_NUMBER_1);
        CardDTO cardDTO = getTestCardDTO1();
        cardDTO.setWebaccountId(CUSTOMER_ID_1);
        when(mockCardOysterOnlineService.findByCardNumber(anyString())).thenReturn(cardDTO);
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
}
