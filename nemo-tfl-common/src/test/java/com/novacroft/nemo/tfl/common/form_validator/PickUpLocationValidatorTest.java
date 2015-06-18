package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.STATION_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_STATION_ID;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.PickUpLocationCmd;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;

/**
 * Pick up location validator unit tests
 */
public class PickUpLocationValidatorTest {
    private PickUpLocationValidator validator;
    private CartCmdImpl cmd;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private SelectStationValidator mockSelectStationValidator;
    private LocationDataService mockLocationDataService;
    
    @Before
    public void setUp() {
        validator = new PickUpLocationValidator();
        cmd = new CartCmdImpl();
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockLocationDataService = mock(LocationDataService.class);

        validator.cardDataService = mockCardDataService;
        validator.getCardService = mockGetCardService;
        validator.locationDataService = mockLocationDataService;
        validator.selectStationValidator = mockSelectStationValidator;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(PickUpLocationCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(TravelCardCmd.class));
    }
    
    @Test
    public void shouldNotValidateWithNullCardInfoResponseV2DTO() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(null);
        
        cmd.setStationId(STATION_ID);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
    }
    
    @Test
    public void shouldNotValidateErrors(){
        Errors errors = mock(Errors.class);
        cmd.setStationId(STATION_ID);
        when(errors.hasFieldErrors(FIELD_STATION_ID)).thenReturn(true);
        validator.validate(cmd, errors);
        
        verify(mockSelectStationValidator, atLeastOnce()).validate(cmd, errors);
        verify(mockGetCardService, never()).getCard(anyString());
    }
}
