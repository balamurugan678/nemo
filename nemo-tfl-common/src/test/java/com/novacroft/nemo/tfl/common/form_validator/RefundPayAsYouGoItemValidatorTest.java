package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestPayAsYouGoList1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

@RunWith(Parameterized.class)
public class RefundPayAsYouGoItemValidatorTest {

    private RefundPayAsYouGoItemValidator validator;
    private CartCmdImpl cmd;
    private Errors errors;
    private SystemParameterService systemParameterService;

    private boolean hasErrors;
    private Integer limit;
    private Integer payAsYouGoValue;
    private List<CartItemCmdImpl> cartItems;

    @Before
    public void setUp() throws Exception {
        validator = new RefundPayAsYouGoItemValidator();
        cmd = new CartCmdImpl();
        systemParameterService = mock(SystemParameterService.class);
        errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.systemParameterService = systemParameterService;

    }

    @Parameterized.Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { { "shouldValidate", 123, getTestPayAsYouGoList1(), 1234, false },
                        { "shouldValidateIfCartItemListIsEmpty", 123, null, 1234, false },
                        { "shouldValidateIfPayAsYouGoBalanceIsGreaterThanLimitInSystem", 123, getTestPayAsYouGoList1(), 14, true },
                        { "shouldValidateIfPayAsYouGoCharacterLengthIsGreaterThanLimit", 11234523, getTestPayAsYouGoList1(), 11234525, true }});
    }

    public RefundPayAsYouGoItemValidatorTest(String testName, Integer payAsYouGoValue, List<CartItemCmdImpl> cartItems, Integer limit,
                    boolean hasErrors) {
        this.payAsYouGoValue = payAsYouGoValue;
        this.cartItems = cartItems;
        this.limit = limit;
        this.hasErrors = hasErrors;
    }

    @Test
    public void validate() {
        cmd.setPayAsYouGoValue(this.payAsYouGoValue);
        cmd.setCartItemList(this.cartItems);
        when(systemParameterService.getIntegerParameterValue(anyString())).thenReturn(this.limit);
        validator.validate(cmd, errors);
        assertEquals(this.hasErrors, errors.hasErrors());
    }
    
    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(Long.class));
    }
}
