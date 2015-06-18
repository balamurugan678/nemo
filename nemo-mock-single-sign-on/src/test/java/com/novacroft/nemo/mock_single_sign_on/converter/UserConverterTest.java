package com.novacroft.nemo.mock_single_sign_on.converter;

import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.UserTestUtil.CUSTOMER_ID;
import static com.novacroft.nemo.test_support.UserTestUtil.getUser;
import static com.novacroft.nemo.test_support.UserTestUtil.getUserDetailsCmdWithStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.mock_single_sign_on.command.UserDetailsCmd;
import com.novacroft.nemo.mock_single_sign_on.domain.User;

public class UserConverterTest {
    private UserConverter converter;
    private CountryDataService mockCountryDataService;

    @Before
    public void setUp() {
        converter = new UserConverter();
        mockCountryDataService = mock(CountryDataService.class);
        converter.countryDataService = mockCountryDataService;
        when(mockCountryDataService.findCountryByName(anyString())).thenReturn(getTestCountryDTO1());
        when(mockCountryDataService.findCountryByCode(anyString())).thenReturn(getTestCountryDTO1());
    }

    @Test
    public void shouldConvertToUser() {
        User result = converter.convert(getUserDetailsCmdWithStatus());
        assertNotNull(result);
        assertEquals(CUSTOMER_ID, result.getCustomer().getCustomerId());
        assertEquals(USERNAME_1, result.getUserAccount().getUserName());
        verify(mockCountryDataService).findCountryByCode(anyString());
    }
    
    @Test
    public void shouldConvertToUserDetailsCmd() {
        UserDetailsCmd result = converter.convert(getUser());
        assertNotNull(result);
        assertEquals(CUSTOMER_ID, result.getCustomerId());
        assertEquals(USERNAME_1, result.getUsername());
        verify(mockCountryDataService).findCountryByName(anyString());
    }
}
