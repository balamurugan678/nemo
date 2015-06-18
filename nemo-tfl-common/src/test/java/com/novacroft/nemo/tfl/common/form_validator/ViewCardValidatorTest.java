package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.command.AddPaymentCardActionCmd;
import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;

public class ViewCardValidatorTest {

    private static ViewCardValidator viewCardValidator;
    private static Errors mockErrors;
    private static CardDataService cardDataService;

    @BeforeClass
    public static void setup() {
        viewCardValidator = mock(ViewCardValidator.class);
        mockErrors = mock(Errors.class);
        cardDataService = mock(CardDataService.class);

        viewCardValidator.cardDataService = cardDataService;
    }

    @Test
    public void supportClass() {
        when(viewCardValidator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(viewCardValidator.supports(SelectCardCmd.class));
    }

    @Test
    public void unsupportClass() {
        when(viewCardValidator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(viewCardValidator.supports(AddPaymentCardActionCmd.class));
    }

    @Test
    public void shouldValidate() {
        when(cardDataService.findById(CardTestUtil.CARD_ID)).thenReturn(CardTestUtil.getTestCardDTO1());
        doCallRealMethod().when(viewCardValidator).validate(anyObject(), any(Errors.class));
        viewCardValidator.validate(CardTestUtil.CARD_ID, mockErrors);
    }

    @Test
    public void shouldNotValidate() {
        when(cardDataService.findById(CardTestUtil.CARD_ID)).thenReturn(null);
        doCallRealMethod().when(viewCardValidator).validate(any(), any(Errors.class));
        viewCardValidator.validate(CardTestUtil.CARD_ID, mockErrors);
        verify(mockErrors, atLeastOnce()).rejectValue(anyString(), anyString(), (Object[]) any(), anyString());
    }

    @Test
    public void classInstantiation() {
        viewCardValidator = new ViewCardValidator();
        assertNotNull(viewCardValidator);
        
        ViewCardValidatorTest.setup();
    }

}
