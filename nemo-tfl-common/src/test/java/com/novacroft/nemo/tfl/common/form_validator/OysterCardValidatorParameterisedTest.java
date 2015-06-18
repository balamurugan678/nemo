package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CommonCardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@RunWith(Parameterized.class)
public class OysterCardValidatorParameterisedTest {

    private OysterCardValidator validator;
    private CartCmdImpl cmd;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private SystemParameterService mockSystemParameterService;
    private HotlistService mockHotlistService;
    private Errors errors;

    private String cardNumberValue;
    private CardInfoResponseV2DTO cardInforResponseValue;
    private CardDTO cardDtoValue;
    private boolean expectedValue;

    public static final String OYSTER_CARD_CHECKSUM_LENGTH = "oysterCardChecksumLength";
    public static final String OYSTER_CARD_FULL_LENGTH = "oysterCardFullLength";

    @Before
    public void setUp() throws Exception {
        validator = new OysterCardValidator();
        cmd = new CartCmdImpl();
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockHotlistService = mock(HotlistService.class);
        errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.cardDataService = mockCardDataService;
        validator.hotlistService = mockHotlistService;
        validator.getCardService = mockGetCardService;
        validator.systemParameterService = mockSystemParameterService;
    }

    public OysterCardValidatorParameterisedTest(String testName, String cardNumberValue, CardInfoResponseV2DTO cardInforResponseValue,
                    CardDTO cardDtoValue, boolean expectedValue) {
        this.cardNumberValue = cardNumberValue;
        this.cardInforResponseValue = cardInforResponseValue;
        this.cardDtoValue = cardDtoValue;
        this.expectedValue = expectedValue;
    }

    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { { "shouldValidate", "072767044410", getTestCardInfoResponseV2DTO1(), null, false },
                        { "shouldNotValidateWithAlreadyUsedCardNumber", "072767044410", getTestCardInfoResponseV2DTO1(), getTestCardDTO1(), true },
                        { "shouldNotValidateWithCardNumberNotAvailableOnCubic", "072767044410", null, null, true },
                        { "shouldNotValidateWithInvalidCheckSum", "072767044420", getTestCardInfoResponseV2DTO1(), null, true },
                        { "shouldNotValidateWithInvalidCardNumber", "1234567891", null, getTestCardDTO1(), true },
                        { "shouldNotValidateWithEmptyCardNumber", " ", null, getTestCardDTO1(), true },
                        { "shouldNotValidateWithNullCardNumber", null, null, getTestCardDTO1(), true },
                        { "shouldNotValidateWithSpecialCharacters", "123456£7891$", null, getTestCardDTO1(), true },
                        { "shouldNotValidateWithAlphabets", "123456A7891B", null, getTestCardDTO1(), true } });
    }

    @Test
    public void validate() {
    	
    	validator.systemParameterOysterCardChecksumLength = CardTestUtil.CHECKSUM_LENGTH;
    	validator.systemParameterOysterCardFullLength = CardTestUtil.OYSTER_CARD_NUMBER_LENGTH;
        cmd.setCardNumber(this.cardNumberValue);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(this.cardDtoValue);
        when(mockGetCardService.getCard(anyString())).thenReturn(this.cardInforResponseValue);
        when(mockHotlistService.isCardHotListedInCubic(anyString())).thenReturn(false);
        when(mockSystemParameterService.getIntegerParameterValue(OYSTER_CARD_CHECKSUM_LENGTH)).thenReturn(
                        CommonCardTestUtil.OYSTER_CARD_CHECKSUM_LENGTH);
        when(mockSystemParameterService.getIntegerParameterValue(OYSTER_CARD_FULL_LENGTH)).thenReturn(CommonCardTestUtil.OYSTER_CARD_FULL_LENGTH);
        validator.validate(cmd, errors);

        assertEquals(this.expectedValue, errors.hasErrors());
    }

}
