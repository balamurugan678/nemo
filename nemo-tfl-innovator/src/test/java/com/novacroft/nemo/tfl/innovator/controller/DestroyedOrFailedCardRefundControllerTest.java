package com.novacroft.nemo.tfl.innovator.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.beans.PropertyEditor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;

import com.novacroft.nemo.tfl.common.form_validator.AddUnlistedProductValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartValidator;

public class DestroyedOrFailedCardRefundControllerTest {
    private DestroyedOrFailedCardRefundController mockController;
    private RefundCartValidator mockRefundCartValidator;
    private AddUnlistedProductValidator mockAddUnlistedProductValidator;
    
    @Before
    public void setUp() {
        mockController = mock(DestroyedOrFailedCardRefundController.class, Mockito.CALLS_REAL_METHODS);
        
        mockRefundCartValidator = mock(RefundCartValidator.class);
        mockAddUnlistedProductValidator = mock(AddUnlistedProductValidator.class);
        
        mockController.refundCartValidator = mockRefundCartValidator;
        mockController.addUnlistedProductValidator = mockAddUnlistedProductValidator;
    }
    
    @Test
    public void initBinderShouldRegisterCustomerEditor() {
        ServletRequestDataBinder mockBinder = mock(ServletRequestDataBinder.class);
        doNothing().when(mockBinder).registerCustomEditor(any(Class.class), any(PropertyEditor.class));
        
        mockController.initBinder(mockBinder);
        
        verify(mockBinder).registerCustomEditor(any(Class.class), any(PropertyEditor.class));
    }
    
    @Test
    public void shouldInitAjaxCartCmdImplBinder() {
        WebDataBinder mockWebDataBinder = mock(WebDataBinder.class);
        doNothing().when(mockWebDataBinder).setValidator(any(Validator.class));
        
        mockController.initAjaxCartCmdImplBinder(mockWebDataBinder);
        
        verify(mockWebDataBinder).setValidator(mockAddUnlistedProductValidator);
    }
    
    @Test
    public void getValidatorShouldReturnRefundCartValidator() {
        assertEquals(mockRefundCartValidator, mockController.getValidator());
    }
}
