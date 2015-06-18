package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithHotlistedCard;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONonHotlistedCard;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;

import org.springframework.validation.Errors;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;

/**
 * OysterCardValidator unit tests
 */
public class OysterCardValidatorTest {
    private OysterCardValidator validator;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private HotlistService mockHotlistService;
    private SystemParameterService mockSystemParameterService;
    
    @Before
    public void setUp() {
        validator = new OysterCardValidator();
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService= mock(GetCardService.class);
        mockHotlistService = mock(HotlistService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        
        validator.cardDataService = mockCardDataService;
        validator.getCardService = mockGetCardService;
        validator.hotlistService = mockHotlistService;
        validator.systemParameterService = mockSystemParameterService;
    }


    @SuppressWarnings("unchecked")
    @Test
    public void checkCardAvailableOnCubic() {
        when(mockCardDataService.findByCardNumber(anyString())).thenThrow(Exception.class);
        assertFalse(validator.checkCardAvailableOnCubic("123456A7891"));
    }
    
    @Test
    public void checkCardHotlistedOnCubic() {
    	when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
    	when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithHotlistedCard());
    	when(mockHotlistService.isCardHotListedInCubic(anyString())).thenReturn(true);
        assertTrue(validator.isCardHotListedInCubic("123456A7891"));
    }
    
    @Test
    public void checkCardNotHotlistedOnCubic() {
    	when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
    	when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTONonHotlistedCard());
    	when(mockHotlistService.isCardHotListedInCubic(anyString())).thenReturn(false);
    	assertFalse(validator.isCardHotListedInCubic("123456A7891"));
    }
    
    @Test
    public void checkHotlistedCardOnCubicShouldRejectValue() {
    	OysterCardValidator mockValidator = mock(OysterCardValidator.class);
    	mockValidator.cardDataService = mockCardDataService;
    	mockValidator.getCardService = mockGetCardService;
    	when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
    	when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithHotlistedCard());
    	when(mockValidator.verifyCardCheckDigit(anyString())).thenReturn(true);
    	when(mockValidator.checkCardAvailableOnCubic(anyString())).thenReturn(true);
    	when(mockValidator.hasCardNumberNoFieldError(any(Errors.class))).thenReturn(true);
    	when(mockValidator.isCardHotListedInCubic(anyString())).thenReturn(true);
    	doCallRealMethod().when(mockValidator).checkCardCheckDigitAndAvailableOnCubicAndAlreadyAssociated(anyString(), any(Errors.class));
    	doNothing().when(mockValidator).checkCardNotAlreadyAssociated(any(Errors.class), anyString());
    	Errors mockErrors = mock(Errors.class);
    	mockValidator.checkCardCheckDigitAndAvailableOnCubicAndAlreadyAssociated("123456A7891", mockErrors);
    	verify(mockErrors).rejectValue(anyString(), anyString());
    }
    
    @Test
    public void shouldInitialize() {
    	validator.initialize();
    	verify(mockSystemParameterService, atLeast(2)).getIntegerParameterValue(anyString());
	}
    
}

