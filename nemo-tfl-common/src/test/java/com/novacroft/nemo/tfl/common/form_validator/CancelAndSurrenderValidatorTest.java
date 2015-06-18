package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd15;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd4;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVEL_CARD_TYPE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getOtherTradedTicket;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getPreviouslyExchnagedOtherTravelCard;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getPreviouslyExchnagedSevenDayTravelCard;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestAnnualBusPass1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestAnnualBusPass2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestGoodwillPayment1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard3;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithProductItemWithRefund;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_ALLOWED_TRAVEL_CARDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.TicketType;

public class CancelAndSurrenderValidatorTest {

    private CancelAndSurrenderValidator validator;
    private Errors errors;
    private CartCmdImpl cmd;
    private SystemParameterService mockSystemParameterService;
    private AddUnlistedProductService addUnlistedProductService;
    private ZoneMappingRefundValidator zoneMappingRefundValidator;
    private TravelCardRefundValidator travelCardRefundValidator;

    @Before
    public void setUp() {
        this.validator = new CancelAndSurrenderValidator();
        this.cmd = new CartCmdImpl();
        this.errors = new BeanPropertyBindingResult(cmd, "cmd");
        this.mockSystemParameterService = mock(SystemParameterService.class);
        zoneMappingRefundValidator = mock(ZoneMappingRefundValidator.class);
        travelCardRefundValidator = mock(TravelCardRefundValidator.class);
        addUnlistedProductService = mock(AddUnlistedProductService.class);
        validator.zoneMappingRefundValidator = zoneMappingRefundValidator;
        validator.travelCardRefundValidator = travelCardRefundValidator;
        validator.systemParameterService = mockSystemParameterService;
        validator.addUnlistedProductService = addUnlistedProductService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CancelAndSurrenderCmd.class));
    }

    @Test
    public void shouldValidateBussPass() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestAnnualBusPass1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cmd.setCartItemCmd(cartItem);
        cmd.getCartItemList().add(getTestAnnualBusPass2());
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateTravelCard() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cmd.setCartItemCmd(cartItem);
        cmd.getCartItemList().add(getTestTravelCard3());
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateNoBussPassORTravelCard() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cmd.setCartItemCmd(cartItem);
        cmd.getCartItemList().add(getTestGoodwillPayment1());
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfMagenticTicketIsInTheCorrectFormat() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setMagneticTicketNumber("A123456");
        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateTravelCardWithStartAndEndDate() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setTicketType(TicketType.TRAVEL_CARD.code());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfTravelCardIsOther() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVEL_CARD_TYPE_1);
        cartItem.setRate("123");
        cartItem.setTicketType(TicketType.TRAVEL_CARD.code());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfTravelCardTypeEndsWithBusPass() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE + " Bus Pass");
        cartItem.setRate("123");
        cartItem.setTicketType(TicketType.BUS_PASS.code());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfBackDated() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);
        cartItem.setBackdatedRefundReasonId(3L);
        cartItem.setDateOfCanceAndSurrender(new Date());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfNotBackDated() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(false);


        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfBackDatedOtherReasonEmpty() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);
        cartItem.setBackdatedRefundReasonId(2L);
        cartItem.setDateOfCanceAndSurrender(new Date());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIfBackDatedOtherReasonIsPresent() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);
        cartItem.setBackdatedRefundReasonId(2L);
        cartItem.setDateOfCanceAndSurrender(new Date());
        cartItem.setBackdatedOtherReason("Broken Ticket");

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfIfNotPreviouslyExchnaged() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setPreviouslyExchanged(false);

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfIfPreviouslyExchangedOtherTravelType() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getPreviouslyExchnagedOtherTravelCard();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setPreviouslyExchanged(true);

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfIfPreviouslyExchnaged() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getPreviouslyExchnagedSevenDayTravelCard();

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfBackDatedRefundReasonIdIsNull() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfTravelCardTypeIsBlank() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(" ");
        cartItem.setRate("123");
        cartItem.setTicketType(TicketType.BUS_PASS.code());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfTravelCardBussPassCountIsGreaterThanMaximumTravelCards() {
        cmd = getTestCartCmd4();
        cmd.setCartDTO(getNewCartDTOWithProductItemWithRefund());
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(1);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotValidateIfDuplicatesAreFound() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cmd.setCartItemCmd(cartItem);
        cmd.getCartDTO().getCartItems().add(ProductItemTestUtil.getTestTravelCardProductDTO1());
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(2);
        when(addUnlistedProductService.isCartItemDuplicate(any(List.class), any(CartItemCmdImpl.class))).thenReturn(Boolean.TRUE);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfMageneticTicketNumberISInTheIncorrectFormat() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setMagneticTicketNumber("159632587322566558");
        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(2);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shoudNotValidateIfTradedTicketIsIdenticalToCurrent() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getOtherTradedTicket();
        cartItem.setRate("123");
        cartItem.setPreviouslyExchanged(true);
        cartItem.setTradedTicket(getOtherTradedTicket());

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateStartAndEndZonesForTradedTicketTravelCardTypeEndingWithBusPass() {
        cmd = getTestCartCmd15();
        cmd.getCartItemCmd().setPreviouslyExchanged(true);
        cmd.getCartItemCmd().setTicketType(TicketType.TRAVEL_CARD.code());
        CartItemCmdImpl tradedTicket = getTestAnnualBusPass1();
        tradedTicket.setTravelCardType("Weekly Bus Pass");
        tradedTicket.setStartZone(1);
        tradedTicket.setExchangedDate(new DateTime());
        cmd.getCartItemCmd().setTradedTicket(tradedTicket);;
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(2);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfDateOfLastUsageIsEmpty() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setBackdated(true);

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldValidateIfNotDeceasedCustomer() {
        cmd = getTestCartCmd4();
        CartItemCmdImpl cartItem = getTestTravelCard1();
        cartItem.setTravelCardType(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
        cartItem.setRate("123");
        cartItem.setDeceasedCustomer(false);

        cmd.setCartItemCmd(cartItem);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_ALLOWED_TRAVEL_CARDS)).thenReturn(3);
        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
    }
}
