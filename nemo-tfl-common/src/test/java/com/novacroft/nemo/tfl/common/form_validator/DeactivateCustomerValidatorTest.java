package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCard1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards1And2;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_2;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.GetCardTestUtil.getTestSuccessCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.GetCardTestUtil.getTestSuccessCardInfoResponseV2DTO2;
import static com.novacroft.nemo.test_support.GetCardTestUtil.getTestSuccessCardInfoResponseV2DTO3;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.CUSTOMER_DEACTIVATION_REASON_OTHER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.any;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Cart validator unit tests
 */
public class DeactivateCustomerValidatorTest {
    private DeactivateCustomerValidator validator;
    private PersonalDetailsCmdImpl cmd;
    private CardDataService mockCardDataService;
    private CustomerDataService mockCustomerDataService;
    private GetCardService mockGetCardService;
    private CustomerDTO mockTestCustomer;

    private static final String DEACTIVATION_REASON = "Unit Testing";

    @Before
    public void setUp() {
        validator = new DeactivateCustomerValidator();
        cmd = new PersonalDetailsCmdImpl();
        mockCardDataService = mock(CardDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockTestCustomer = mock(CustomerDTO.class);

        validator.cardDataService = mockCardDataService;
        validator.customerDataService = mockCustomerDataService;
        validator.getCardService = mockGetCardService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(PersonalDetailsCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotValidateIfOneOfTheCardsHaveAutoTopUpEnabled() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);
        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCards1And2());
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestSuccessCardInfoResponseV2DTO1());
        when(mockGetCardService.getCard(OYSTER_NUMBER_2)).thenReturn(getTestSuccessCardInfoResponseV2DTO2());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfNoCardsHaveAutoTopUpEnabled() {
        cmd.setCustomerId(CUSTOMER_ID_1);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCard1());
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestSuccessCardInfoResponseV2DTO3());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shoudNotValidateIFCustomerDeactivationReasonIsEmpty() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCard1());
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestSuccessCardInfoResponseV2DTO3());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void ShouldNotValidateIfCustomerDeactivationReasonIsNull() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);
        cmd.setCustomerDeactivationReason(CUSTOMER_DEACTIVATION_REASON_OTHER);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCard1());
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestSuccessCardInfoResponseV2DTO3());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());

    }

    @Test
    public void ShouldNotValidateIfCustomerDeactivationReasonIsNotNull() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);
        cmd.setCustomerDeactivationReason(DEACTIVATION_REASON);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCard1());
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestSuccessCardInfoResponseV2DTO3());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());

    }

    @Test(expected = AssertionError.class)
    public void shouldNotValidateIfCardInfoResposnseIsNull() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);
        cmd.setCustomerDeactivationReason(DEACTIVATION_REASON);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCard1());
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);
    }

    @Test
    public void shouldNotValidateIfCardNumberIsNull() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);
        cmd.setCustomerDeactivationReason(DEACTIVATION_REASON);

        List<CardDTO> cardDTOList = getTestCard1();
        CardDTO cardDTO = cardDTOList.get(0);
        cardDTO.setCardNumber(null);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(cardDTOList);
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(getTestSuccessCardInfoResponseV2DTO3());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());

    }

    @Test
    public void shouldValidateIfAutoLoadStateIsNull() {
        cmd.setCustomerId(CUSTOMER_ID_1);
        cmd.setCustomerDeactivated(true);
        cmd.setCustomerDeactivationReason(DEACTIVATION_REASON);

        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCard1());
        CardInfoResponseV2DTO cardInfoRespsone = getTestSuccessCardInfoResponseV2DTO3();
        cardInfoRespsone.setAutoLoadState(null);
        when(mockGetCardService.getCard(OYSTER_NUMBER_1)).thenReturn(cardInfoRespsone);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.validate(cmd, errors);
    }
    
    @Test
    public void shouldRejectIfAutoTopUpConfiguredForOneOfTheCardsWithDeactivatedAccount() {
    	Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        cmd.setCustomerId(CUSTOMER_ID_1);
        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(mockTestCustomer);
        when(mockCardDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCards1And2());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestSuccessCardInfoResponseV2DTO2());
        cmd.setCustomerDeactivated(true);
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
}
