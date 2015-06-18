package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.AlternativeZoneMappingTestUtil.getTestAlternativeZoneMappingDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PRE_PAID_TICKET_ID;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicketDTO;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_END_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_ZONE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_TRAVEL_CARD_TYPE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.AlternativeZoneMappingDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

/**
 * ZoneMappingValidator unit tests
 */
public class ZoneMappingValidatorTest {
    static final Logger logger = LoggerFactory.getLogger(ZoneMappingValidatorTest.class);

    private ZoneMappingValidator validator;
    private ZoneMappingValidator mockValidator;
    private CartItemCmdImpl cmd;
    private AlternativeZoneMappingDataService mockAlternativeZoneMappingDataService;
    private SystemParameterService mockSystemParameterService;
    private ProductDataService mockProductDataService;
    private PrePaidTicketDataService mockPrePaidTicketDataService;
    private TravelCardCmd mockTravelCardCmd;

    private static final String TRAVEL_CARD_TYPE = "3 Month";
    private static final String OTHER_TRAVEL_CARD_TYPE = "Unknown";

    @Before
    public void setUp() {
        validator = new ZoneMappingValidator();
        mockValidator = mock(ZoneMappingValidator.class);
        cmd = new CartItemCmdImpl();
        cmd.setTravelCardType(TRAVEL_CARD_TYPE);
        mockAlternativeZoneMappingDataService = mock(AlternativeZoneMappingDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockProductDataService = mock(ProductDataService.class);
        mockPrePaidTicketDataService = mock(PrePaidTicketDataService.class);

        validator.alternativeZoneMappingDataService = mockAlternativeZoneMappingDataService;
        validator.systemParameterService = mockSystemParameterService;
        validator.productDataService = mockProductDataService;
        validator.prePaidTicketDataService = mockPrePaidTicketDataService;
        
        mockValidator.alternativeZoneMappingDataService = mockAlternativeZoneMappingDataService;
        mockValidator.systemParameterService = mockSystemParameterService;
        mockValidator.productDataService = mockProductDataService;
        mockValidator.prePaidTicketDataService = mockPrePaidTicketDataService;
        
        mockTravelCardCmd = mock(TravelCardCmd.class);
        
        cmd.setStartDate("20/10/2014");
        cmd.setEndDate("26/10/2014");
       
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(Durations.SEVEN_DAYS.getDurationType());
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(TravelCardCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(BusPassCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setStartZone(1);
        cmd.setEndZone(7);
       
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithEndZoneGreaterThanStartZone() {
    	cmd.setStartDate("20/10/2014");
        cmd.setEndDate("26/11/2014");
        cmd.setStartZone(7);
        cmd.setEndZone(1);

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithOtherTravelCardType() {
        cmd.setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.setStartZone(1);
        cmd.setEndZone(5);

        cmd.setStartDate("20/10/2014");
        cmd.setEndDate("26/11/2014");
       
        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(),  anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestProductDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, atLeastOnce()).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(),  anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNotExistedZoneMapping() {
        cmd.setStartZone(5);
        cmd.setEndZone(7);

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
        cmd.setStartZone(6);
        cmd.setEndZone(9);

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockAlternativeZoneMappingDataService.findByStartZoneAndEndZone(anyInt(), anyInt()))
                .thenReturn(getTestAlternativeZoneMappingDTO1());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        logger.info("Alternative Startzone: " + cmd.getStartZone());
        logger.info("Alternative Endzone: " + cmd.getEndZone());

        verify(mockProductDataService, atLeastOnce()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockAlternativeZoneMappingDataService, atLeastOnce()).findByStartZoneAndEndZone(anyInt(), anyInt());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullStartZone() {
        cmd.setStartZone(null);
        cmd.setEndZone(7);

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, never()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                        any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullEndDate() {
        cmd.setStartZone(1);
        cmd.setEndZone(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, never()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                        any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithSingleZoneSelection() {
        cmd.setStartZone(2);
        cmd.setEndZone(2);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockProductDataService, never()).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                        any(Date.class), anyString(), anyString(), anyString());
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateContainsErrors() {
        cmd.setStartZone(2);
        cmd.setEndZone(2);

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = mock(Errors.class);
        when(errors.hasFieldErrors(FIELD_START_ZONE)).thenReturn(true);
        when(errors.hasFieldErrors(FIELD_TRAVEL_CARD_TYPE)).thenReturn(false);
        when(errors.hasErrors()).thenReturn(true);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateContainsErrorsTravelCardType() {
        cmd.setStartZone(2);
        cmd.setEndZone(2);

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);

        Errors errors = mock(Errors.class);
        when(errors.hasFieldErrors(FIELD_START_ZONE)).thenReturn(false);
        when(errors.hasFieldErrors(FIELD_TRAVEL_CARD_TYPE)).thenReturn(true);
        when(errors.hasErrors()).thenReturn(true);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateContainsErrorsEndDate() {
        cmd.setStartZone(2);
        cmd.setEndZone(2);

        Errors errors = mock(Errors.class);
        when(errors.hasFieldErrors(FIELD_START_ZONE)).thenReturn(false);
        when(errors.hasFieldErrors(FIELD_TRAVEL_CARD_TYPE)).thenReturn(false);
        when(errors.hasFieldErrors(FIELD_END_DATE)).thenReturn(true);
        when(errors.hasErrors()).thenReturn(true);
        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNovalidateZoneMappingNotAvailableForSubstitutionTravelCard(){
        cmd.setTravelCardType(OTHER_TRAVEL_CARD_TYPE);
        cmd.setStartZone(2);
        cmd.setEndZone(3);
        cmd.setStartDate("20/10/2014");
        cmd.setEndDate("26/11/2014");
       
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockSystemParameterService.getParameterValue(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE)).thenReturn(Durations.SEVEN_DAYS.getDurationType());

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
    }
  
    @Test
    public void shouldGetPrePaidTickectDTOById() {
        when(mockValidator.getPrePaidTickectDTOById((TravelCardCmd)any())).thenCallRealMethod();
        when(mockTravelCardCmd.getPrePaidTicketId()).thenReturn(PRE_PAID_TICKET_ID);
        when(mockPrePaidTicketDataService.findById((Long)any())).thenReturn(getTestPrePaidTicketDTO());
        PrePaidTicketDTO result = mockValidator.getPrePaidTickectDTOById(mockTravelCardCmd);
        assertNotNull(result);
        verify(mockTravelCardCmd, atLeastOnce()).getPrePaidTicketId();
        verify(mockPrePaidTicketDataService, atLeastOnce()).findById((Long)any());
    }

    @Test
    public void shouldNotGetPrePaidTickectDTOById() {
        when(mockValidator.getPrePaidTickectDTOById((TravelCardCmd)any())).thenCallRealMethod();
        when(mockTravelCardCmd.getPrePaidTicketId()).thenReturn(null);
        when(mockPrePaidTicketDataService.findById((Long)any())).thenReturn(getTestPrePaidTicketDTO());
        PrePaidTicketDTO result = mockValidator.getPrePaidTickectDTOById(mockTravelCardCmd);
        assertNull(result);
        verify(mockTravelCardCmd, atLeastOnce()).getPrePaidTicketId();
        verify(mockPrePaidTicketDataService, never()).findById((Long)any());
    }
}
