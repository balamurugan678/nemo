package com.novacroft.nemo.common.application_service.impl;

import static com.novacroft.nemo.test_support.CommonAddressTestUtil.HOUSE_NAME_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.POSTCODE_WITHOUT_SPACE;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.getTestCommonAddressDTO1;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.data_service.PAFDataService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

public class PAFServiceImplTest {
    private PAFServiceImpl service;
    private PAFDataService mockPafDataService;
    
    @Before
    public void setUp() {
        service = new PAFServiceImpl();
        mockPafDataService = mock(PAFDataService.class);
        service.pafDataService = mockPafDataService;
        
        when(mockPafDataService.findAddressesForPostcode(anyString()))
                .thenReturn(Arrays.asList(getTestCommonAddressDTO1()));
    }
    
    @Test
    public void shouldGetAddressesForPostcode() {
        String[] expectedResult = new String[] {HOUSE_NAME_NUMBER_1 + ", " + STREET_1 + ", " + TOWN_1};
        String[] actualResult = service.getAddressesForPostcode(POSTCODE_1);
        
        verify(mockPafDataService).findAddressesForPostcode(POSTCODE_1);
        assertArrayEquals(expectedResult, actualResult);
    }
    
    @Test
    public void shouldGetAddressesForPostcodeSelectList() {
        SelectListDTO actualResult = service.getAddressesForPostcodeSelectList(POSTCODE_1);
        
        verify(mockPafDataService).findAddressesForPostcode(POSTCODE_1);
        assertEquals(1, actualResult.getOptions().size());
        
        SelectListOptionDTO option = actualResult.getOptions().get(0);
        assertNotNull(option.getValue());
    }
    
    @Test
    public void shouldCheckAndAddDelimiter() {
        assertEquals(POSTCODE_1, service.checkAndAddDelimiter(POSTCODE_WITHOUT_SPACE));
    }
    
    @Test
    public void populateAddressFromJson() {
        CommonOrderCardCmd cmd = new CommonOrderCardCmd();
        String addressForPostcode = "{\"houseNameNumber\":\"test-housenumber\",\"street\":\"test-street\",\"town\":\"test-town\"," +
                        "\"country\":{\"code\":\"test-country-code\",\"name\":\"test-country\"}}";
        cmd = service.populateAddressFromJson(cmd, addressForPostcode);
        assertEquals("test-town", cmd.getTown());
        assertEquals("test-street", cmd.getStreet());
    }
}
