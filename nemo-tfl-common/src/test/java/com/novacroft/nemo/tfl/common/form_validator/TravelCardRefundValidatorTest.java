package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;

@RunWith(Parameterized.class)
public class TravelCardRefundValidatorTest {

    private TravelCardRefundValidator validator;
    private CartCmdImpl cmd;
    private SystemParameterService mockSystemParameterService;
    private final String travelcardTypeValue;
    private final String startDateValue;
    private final String endDateValue;
    private final boolean expectedErrorValue;
    
    private static final String TRAVEL_CARD_TYPE = "3 Month";
    private static final String START_DATE = "21/12/2013";
    private static final String END_DATE = "20/03/2014";

    @Before
    public void setUp() throws Exception {
        validator = new TravelCardRefundValidator();
        cmd = new CartCmdImpl();
        cmd.setCartItemCmd(new CartItemCmdImpl());
        mockSystemParameterService = mock(SystemParameterService.class);
        validator.systemParameterService = mockSystemParameterService;
    }

    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { 
                        {"ShouldValidate",TRAVEL_CARD_TYPE, START_DATE, null, false},
                        {"ShouldValidateForOtherTravelCardType",Durations.OTHER.getDurationType(),START_DATE,END_DATE, false},
                        {"ShouldNotValidateWithEmptyTravelCardType"," ", START_DATE, null, true},
                        {"ShouldNotValidateWithNullTravelCardType", null, START_DATE, null, true},
                        {"ShouldNotValidateWithEmptyStartDate", TRAVEL_CARD_TYPE, " ", null, true},
                        {"ShouldNotValidateWithNullStartDate", TRAVEL_CARD_TYPE, null, null, true},
                        {"ShouldNotValidateWithInvalidStartDate", TRAVEL_CARD_TYPE, "21122013", null, true},
                        {"ShouldNotValidateWithEmptyEndDateForOtherTravelCardType", Durations.OTHER.getDurationType(), START_DATE, " ", true},
                        {"ShouldNotValidateWithNullEndDateForOtherTravelCardType", Durations.OTHER.getDurationType(), START_DATE, null, true},
                        {"ShouldNotValidateWithInvalidEndDateForOtherTravelCardType", Durations.OTHER.getDurationType(), START_DATE, "25122014", true},
                        {"ShouldNotValidateWithEndDateGreaterThanStartDateForOtherTravelCardType",Durations.OTHER.getDurationType(),START_DATE, "21/11/2013", true},
                        {"ShouldNotValidateWithLessThanOrEqualToOneMonthTravelCardDurationForOtherTravelCardType",Durations.OTHER.getDurationType(), START_DATE, "31/12/2013", true},
                        {"ShouldNotValidateWithGreaterThanOrEqualToAnnualTravelCardDurationForOtherTravelCardType",Durations.OTHER.getDurationType(), START_DATE, "31/12/2014", true},
                        {"ShouldNotValidateWithInvalidDate", Durations.OTHER.getDurationType(), DateTestUtil.INVALID_DATE_1, DateTestUtil.INVALID_DATE_2, true}
                       });
    }

    public TravelCardRefundValidatorTest(String testName, String travelcardTypeValue, String startDateValue, String endDateValue, boolean expectedErrorValue) {
        this.travelcardTypeValue = travelcardTypeValue;
        this.startDateValue = startDateValue;
        this.endDateValue = endDateValue;
        this.expectedErrorValue = expectedErrorValue;
    }

    @Test
    public void validate() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS)).thenReturn(OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS)).thenReturn(OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS);
        cmd.getCartItemCmd().setTravelCardType(this.travelcardTypeValue);
        cmd.getCartItemCmd().setStartDate(this.startDateValue);
        cmd.getCartItemCmd().setEndDate(this.endDateValue);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertEquals(this.expectedErrorValue, errors.hasErrors());
    }

}
