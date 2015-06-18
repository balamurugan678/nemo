package com.novacroft.nemo.tfl.common.form_validator;

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

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;

@RunWith(Parameterized.class)
public class TravelCardRefundValidatorTestTicketShorterThanOneMonthOneDayTest {
    
    private TravelCardRefundValidator validator;
    private SystemParameterService mockSystemParameterService;
    private int diffMonthsValue;
    private int diffDaysValue;
    private boolean expectedResult;

    @Before
    public void setUp() throws Exception {
        
        validator = new TravelCardRefundValidator();
        mockSystemParameterService = mock(SystemParameterService.class);
        validator.systemParameterService = mockSystemParameterService;
    }
    
    public TravelCardRefundValidatorTestTicketShorterThanOneMonthOneDayTest(int diffMonthsValue, int diffDaysValue, boolean expectedValue){
        this.diffMonthsValue = diffMonthsValue;
        this.diffDaysValue = diffDaysValue;
        this.expectedResult = expectedValue;
    }

    @Parameters()
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {0,0, true},
                        {3,45,false},
                        {1,0,true},
                        {2,0,false},
                        {1,1,false}
        });
    }
    
    @Test
    public void isTicketShorterThanOneMonthOneDay() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS)).thenReturn(
                        OTHER_TRAVELCARD_MINIMUM_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS)).thenReturn(
                        OTHER_TRAVELCARD_MINIMUM_ALLOWED_MONTHS);
        assertEquals(this.expectedResult, validator.isTicketShorterThanOneMonthOneDay(this.diffMonthsValue, this.diffDaysValue));
    }

}
