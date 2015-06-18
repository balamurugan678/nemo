package com.novacroft.nemo.tfl.online.tag;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

import com.novacroft.nemo.common.transfer.CountryDTO;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import java.io.UnsupportedEncodingException;

import static com.novacroft.nemo.test_support.AddressTestUtil.HOUSE_NAME_NUMBER_2;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_2;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_2;
import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_2;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CountryTestUtil.TEST_COUNTRY_UK_NAME;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for address tag
 */
public class AddressTagTest {
    private MockServletContext mockServletContext;
    private MockPageContext mockPageContext;
    private CountryDTO testCountryDTO;
    
    @Before
    public void setUp() {
        mockServletContext = new MockServletContext();
        mockPageContext = new MockPageContext(mockServletContext);
        testCountryDTO = getTestCountryDTO1();
    }

    @Test
    public void shouldOutputAddressWithHouseNumber() throws JspException, UnsupportedEncodingException {
        AddressTag tag = createTestAddressTag(mockPageContext, HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, POSTCODE_2, testCountryDTO);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        String expectedResult =
                String.format("%s %s<br/>%s, %s<br/>%s", HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, POSTCODE_2, TEST_COUNTRY_UK_NAME);
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldOutputAddressWithoutHouseNameNumber() throws JspException, UnsupportedEncodingException {
        AddressTag tag = createTestAddressTag(mockPageContext, null, STREET_2, TOWN_2, POSTCODE_2, testCountryDTO);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        String expectedResult = String.format("%s<br/>%s, %s<br/>%s", STREET_2, TOWN_2, POSTCODE_2, TEST_COUNTRY_UK_NAME);
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldOutputAddressWithoutStreet() throws JspException, UnsupportedEncodingException {
        AddressTag tag = createTestAddressTag(mockPageContext, HOUSE_NAME_NUMBER_2, null, TOWN_2, POSTCODE_2, testCountryDTO);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        String expectedResult = String.format("%s<br/>%s, %s<br/>%s", HOUSE_NAME_NUMBER_2, TOWN_2, POSTCODE_2, TEST_COUNTRY_UK_NAME);
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldOutputAddressWithoutPostcode() throws JspException, UnsupportedEncodingException {
        AddressTag tag = createTestAddressTag(mockPageContext, HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, null, testCountryDTO);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        String expectedResult = String.format("%s %s<br/>%s<br/>%s", HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, TEST_COUNTRY_UK_NAME);
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldOutputAddressWithoutTown() throws JspException, UnsupportedEncodingException {
        AddressTag tag = createTestAddressTag(mockPageContext, HOUSE_NAME_NUMBER_2, STREET_2, null, POSTCODE_2, testCountryDTO);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        String expectedResult = String.format("%s %s<br/>%s<br/>%s", HOUSE_NAME_NUMBER_2, STREET_2, POSTCODE_2, TEST_COUNTRY_UK_NAME);
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldOutputAddressWithoutCountry() throws JspException, UnsupportedEncodingException {
        AddressTag tag = createTestAddressTag(mockPageContext, HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, POSTCODE_2, null);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        String expectedResult = String.format("%s %s<br/>%s, %s", HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, POSTCODE_2);
        assertEquals(expectedResult.trim(), result.trim());
    }
    
    private AddressTag createTestAddressTag(PageContext pageContext, String houseNameNumber, String street,
                    String town, String postcode, CountryDTO countryDTO) {
        AddressTag tag = new AddressTag();
        tag.setPageContext(pageContext);
        tag.setHouseNameNumber(houseNameNumber);
        tag.setStreet(street);
        tag.setTown(town);
        tag.setPostcode(postcode);
        tag.setCountry(countryDTO);
        return tag;
    }
}
