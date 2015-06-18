package com.novacroft.nemo.tfl.common.controller;

import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.constant.ContentCode;

public class FindAddressControllerTest {
    private FindAddressController controller;
    private PAFService mockPafService;
    private PostcodeValidator mockValidator;
    
    private MockHttpServletRequest mockRequest;
    
    @Before
    public void setUp() {
        controller = new FindAddressController();
        mockPafService = mock(PAFService.class);
        mockValidator = mock(PostcodeValidator.class);
        
        controller.pafService = mockPafService;
        controller.postcodeValidator = mockValidator;
        
        mockRequest = new MockHttpServletRequest();
        mockRequest.setParameter("postcode", POSTCODE_1);
    }
    
    @Test
    public void findAddressShouldReturnError() {
        when(mockValidator.validate(anyString())).thenReturn(false);
        
        String actualResult = null;
        String exceptionError = null;
        try {
            actualResult = controller.findAddress(mockRequest);
        } catch (Exception e) {
            exceptionError = e.toString();
        }
        verify(mockValidator).validate(POSTCODE_1);
        assertEquals(ContentCode.INVALID_POSTCODE.codeStem(), actualResult);
        assertNull(exceptionError);
    }
    
    @Test
    public void findAddressShouldReturnPostCode() {
        String[] addressArray = new String[]{STREET_1};
        when(mockValidator.validate(anyString())).thenReturn(true);
        when(mockPafService.getAddressesForPostcode(anyString())).thenReturn(addressArray);
        
        String actualResult = null;
        String exceptionError = null;
        try {
            actualResult = controller.findAddress(mockRequest);
        } catch (Exception e) {
            exceptionError = e.toString();
        }
        
        assertEquals(new Gson().toJson(addressArray), actualResult);
        assertNull(exceptionError);
    }
    
    @Test(expected=Exception.class)
    public void findAddressShouldThrowException() throws Exception {
        controller.findAddress(null);
    }
}
