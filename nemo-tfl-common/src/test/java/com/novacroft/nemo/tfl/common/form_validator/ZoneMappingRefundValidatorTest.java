package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.AlternativeZoneMappingTestUtil.getTestAlternativeZoneMappingDTO1;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.impl.AddUnlistedProductServiceImpl;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.data_service.AlternativeZoneMappingDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;

/**
 * ZoneMapping Refund Validator unit tests
 */
public class ZoneMappingRefundValidatorTest {
    static final Logger logger = LoggerFactory.getLogger(ZoneMappingRefundValidatorTest.class);

    private ZoneMappingRefundValidator validator;
    private CartCmdImpl cmd;
    private AlternativeZoneMappingDataService mockAlternativeZoneMappingDataService;
    private SystemParameterService mockSystemParameterService;
    private ProductDataService mockProductDataService;
    private AddUnlistedProductService mockAddUnlistedProductService;

    private static final String TRAVEL_CARD_TYPE = "3 Month";
    private static final String OTHER_TRAVEL_CARD_TYPE = "Unknown Travelcard";
    private static final Integer ZONE_7 = 7;
    private static final Integer ZONE_1 = 1;
    private static final Integer ZONE_6 = 6;
    private static final Integer ZONE_2 = 2;
    private static final Integer ZONE_9 = 9;
    private static final Integer ZONE_5 = 5;

    @Before
    public void setUp() {
        validator = new ZoneMappingRefundValidator();
        cmd = new CartCmdImpl();
        cmd.setCartItemCmd(new CartItemCmdImpl());
        cmd.getCartItemCmd().setTradedTicket(new CartItemCmdImpl());
        cmd.getCartItemCmd().setTravelCardType(TRAVEL_CARD_TYPE);
        mockAlternativeZoneMappingDataService = mock(AlternativeZoneMappingDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockProductDataService = mock(ProductDataService.class);
        mockAddUnlistedProductService = mock(AddUnlistedProductServiceImpl.class);

        validator.alternativeZoneMappingDataService = mockAlternativeZoneMappingDataService;
        validator.systemParameterService = mockSystemParameterService;
        validator.productDataService = mockProductDataService;
        validator.addUnlistedProductService = mockAddUnlistedProductService;

        doCallRealMethod().when(mockAddUnlistedProductService).removeTravelCardSuffixFromTravelCardType(anyString());
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(Durations.SEVEN_DAYS.getDurationType());
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(BusPassCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.getCartItemCmd().setStartZone(ZONE_1);
        cmd.getCartItemCmd().setEndZone(ZONE_7);

        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType( anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithEndZoneGreaterThanStartZone() {
        cmd.getCartItemCmd().setStartZone(ZONE_7);
        cmd.getCartItemCmd().setEndZone(ZONE_1);

        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithOtherTravelCardType() {
        cmd.getCartItemCmd().setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.getCartItemCmd().setStartZone(ZONE_7);
        cmd.getCartItemCmd().setEndZone(ZONE_5);
        cmd.getCartItemCmd().getTradedTicket().setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.getCartItemCmd().getTradedTicket().setStartZone(ZONE_7);
        cmd.getCartItemCmd().getTradedTicket().setEndZone(ZONE_5);

        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullOtherTradedTicketTravelCardType() {
        cmd.getCartItemCmd().setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.getCartItemCmd().setStartZone(ZONE_7);
        cmd.getCartItemCmd().setEndZone(ZONE_5);
        cmd.getCartItemCmd().setTradedTicket(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        ZoneMappingRefundValidator spyZoneMappingRefundValidator = spy(validator);
        spyZoneMappingRefundValidator.validate(cmd, errors);
        verify(spyZoneMappingRefundValidator, never()).updatedOtherTradedTicketTravelCardTypeZoneMapping(anyString(), any(CartCmdImpl.class),
                        any(Errors.class));
    }

    @Test
    public void shouldNotValidateWithNotExistedZoneMapping() {
        cmd.getCartItemCmd().setStartZone(ZONE_5);
        cmd.getCartItemCmd().setEndZone(ZONE_7);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockAlternativeZoneMappingDataService.findByStartZoneAndEndZone(anyInt(), anyInt())).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockAlternativeZoneMappingDataService, atLeastOnce()).findByStartZoneAndEndZone(anyInt(), anyInt());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNotExistedZoneMappingSuggestAlternativeProduct() {
        cmd.getCartItemCmd().setStartZone(ZONE_6);
        cmd.getCartItemCmd().setEndZone(ZONE_9);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockAlternativeZoneMappingDataService.findByStartZoneAndEndZone(anyInt(), anyInt())).thenReturn(getTestAlternativeZoneMappingDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        logger.info("Alternative Startzone: " + cmd.getCartItemCmd().getStartZone());
        logger.info("Alternative Endzone: " + cmd.getCartItemCmd().getEndZone());

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockAlternativeZoneMappingDataService, atLeastOnce()).findByStartZoneAndEndZone(anyInt(), anyInt());
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithTradedNotExistedZoneMappingSuggestAlternativeProduct() {
        cmd.getCartItemCmd().setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.getCartItemCmd().setStartZone(ZONE_6);
        cmd.getCartItemCmd().setEndZone(ZONE_9);
        cmd.getCartItemCmd().getTradedTicket().setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.getCartItemCmd().getTradedTicket().setStartZone(ZONE_6);
        cmd.getCartItemCmd().getTradedTicket().setEndZone(ZONE_9);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockAlternativeZoneMappingDataService.findByStartZoneAndEndZone(anyInt(), anyInt())).thenReturn(getTestAlternativeZoneMappingDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        logger.info("Alternative Startzone: " + cmd.getCartItemCmd().getStartZone());
        logger.info("Alternative Endzone: " + cmd.getCartItemCmd().getEndZone());

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockAlternativeZoneMappingDataService, atLeastOnce()).findByStartZoneAndEndZone(anyInt(), anyInt());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullStartDate() {
        cmd.getCartItemCmd().setStartZone(null);
        cmd.getCartItemCmd().setEndZone(ZONE_7);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, never()).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullEndDate() {
        cmd.getCartItemCmd().setStartZone(ZONE_1);
        cmd.getCartItemCmd().setEndZone(null);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, never()).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithSingleZoneSelection() {
        cmd.getCartItemCmd().setStartZone(ZONE_7);
        cmd.getCartItemCmd().setEndZone(ZONE_7);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, never()).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateOtherTradedTicket() {
        CartItemCmdImpl cmdItem = new CartItemCmdImpl();
        cmdItem.setTravelCardType(CartAttribute.OTHER_TRAVEL_CARD);
        cmdItem.setStartZone(ZONE_1);
        cmdItem.setEndZone(ZONE_7);
        cmd.getCartItemCmd().setStartZone(ZONE_1);
        cmd.getCartItemCmd().setEndZone(ZONE_7);
        cmd.getCartItemCmd().setTradedTicket(cmdItem);
        cmd.getCartItemCmd().setTravelCardType(CartAttribute.OTHER);

        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateNoMappingAvailableForTradedTicket() {
        CartItemCmdImpl cmdItem = new CartItemCmdImpl();
        cmdItem.setTravelCardType(CartAttribute.OTHER_TRAVEL_CARD);
        cmdItem.setStartZone(ZONE_1);
        cmdItem.setEndZone(ZONE_7);
        cmd.getCartItemCmd().setStartZone(ZONE_1);
        cmd.getCartItemCmd().setEndZone(ZONE_7);
        cmd.getCartItemCmd().setTradedTicket(cmdItem);
        cmd.getCartItemCmd().setTravelCardType(CartAttribute.OTHER);


        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestAllNull() {
        validator.isZoneMappingAvailable(null, null, null, null, null, null, null);
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestNullTravelCardType() {
        validator.isZoneMappingAvailable(null, ZONE_7, ZONE_2, null, null, null, null);
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestNulltravelCardTypeAndStartZone() {
        validator.isZoneMappingAvailable(null, null, ZONE_2, null, null, null, null);
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestNullTravelCardTypeAndEndZone() {
        validator.isZoneMappingAvailable(null, ZONE_7, null, null, null, null, null);
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestNullStartZoneAndEndZone() {
        validator.isZoneMappingAvailable(Durations.OTHER.getDurationType(), null, null, null, null, null, null);
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestNullEndZone() {
        validator.isZoneMappingAvailable(Durations.OTHER.getDurationType(), ZONE_7, null, null, null, null, null);
    }

    @Test(expected = AssertionError.class)
    public void isZoneMappingAvailableTestNullStartZone() {
        validator.isZoneMappingAvailable(Durations.OTHER.getDurationType(), null, ZONE_2, null, null, null, null);
    }
}
