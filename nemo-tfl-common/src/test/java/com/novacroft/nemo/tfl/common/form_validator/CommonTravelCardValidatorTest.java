package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getDate20032014;
import static com.novacroft.nemo.test_support.DateTestUtil.getDate21122013;
import static com.novacroft.nemo.test_support.DateTestUtil.getDate21122014;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@RunWith(Parameterized.class)
public class CommonTravelCardValidatorTest {

    private CommonTravelCardValidator validator;
    private ProductItemDTO productItemDTO;
    private SystemParameterService mockSystemParameterService;
    private Errors errors;
    private final String duration;
    private final Date startDateValue;
    private final Date endDateValue;
    private final boolean expectedErrorValue;

    private static final String TRAVEL_CARD_TYPE = "3 Month";
    private static final String OTHER_TRAVEL_CARD_TYPE = "Other";
    private static final String TEST_FIELD_NAME = "test-field";

    @Before
    public void setUp() throws Exception {
        validator = new CommonTravelCardValidator();
        productItemDTO = new ProductItemDTO();
        mockSystemParameterService = mock(SystemParameterService.class);
        validator.systemParameterService = mockSystemParameterService;
        errors = new BeanPropertyBindingResult(productItemDTO, "productItemDTO");
    }

    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] {
                        { "ShouldValidate", TRAVEL_CARD_TYPE, getDate21122013(), getDate20032014(), false },
                        { "ShouldValidateForOtherTravelCardType", OTHER_TRAVEL_CARD_TYPE, getDate21122013(), getDate20032014(), false },
                        { "ShouldNotValidateWithEmptyTravelCardType", " ", getDate21122013(), null, true },
                        { "ShouldNotValidateWithNullTravelCardType", null, getDate21122013(), null, true },
                        { "ShouldNotValidateWithNullStartDate", TRAVEL_CARD_TYPE, null, null, true },
                        { "ShouldNotValidateWithNullEndDateForOtherTravelCardType", OTHER_TRAVEL_CARD_TYPE, getDate21122013(), null, true },
                        { "ShouldNotValidateWithEndDateGreaterThanStartDateForOtherTravelCardType", OTHER_TRAVEL_CARD_TYPE, getDate21122013(),
                                        getAug19(), true },
                        { "ShouldNotValidateWithLessThanOrEqualToOneMonthTravelCardDurationForOtherTravelCardType", OTHER_TRAVEL_CARD_TYPE,
                                        getDate21122013(), getDate21122013(), true },
                        { "ShouldNotValidateWithGreaterThanOrEqualToAnnualTravelCardDurationForOtherTravelCardType", OTHER_TRAVEL_CARD_TYPE,
                                        getDate21122013(), getDate21122014(), true },
                        { "ShouldNotValidateWithInvalidDate", OTHER_TRAVEL_CARD_TYPE, getDate21122013(), DateTestUtil.getInvalidDateFeb30(), false },
                        { "ShouldValidateWithValidDate", OTHER_TRAVEL_CARD_TYPE, getDate21122013(), getDate21122014(), true }, });
    }

    public CommonTravelCardValidatorTest(String testName, String duration, Date startDateValue, Date endDateValue, boolean expectedErrorValue) {
        this.duration = duration;
        this.startDateValue = startDateValue;
        this.endDateValue = endDateValue;
        this.expectedErrorValue = expectedErrorValue;
    }

    @Test
    public void test() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(
                        OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS)).thenReturn(
                        OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS)).thenReturn(
                        OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS);
        productItemDTO.setDuration(this.duration);
        productItemDTO.setStartDate(this.startDateValue);
        productItemDTO.setEndDate(this.endDateValue);

        validator.validate(productItemDTO, errors);
        assertEquals(this.expectedErrorValue, errors.hasErrors());
    }

}
