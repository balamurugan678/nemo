package com.novacroft.nemo.tfl.common.util;

import org.junit.Test;

import java.util.List;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.*;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.SPACE;
import static org.junit.Assert.*;

/**
 * Unit tests for AddressFormatUtil
 */
public class AddressFormatUtilTest {
    private static final int EXPECTED_ADDRESS_NAME_LINE_COUNT_4 = 4;
    private static final int EXPECTED_ADDRESS_NAME_LINE_COUNT_5 = 5;

    @Test
    public void shouldFormatNameFromCustomerDTO() {
        assertEquals(FORMATED_NAME_1, AddressFormatUtil.formatName(getTestCustomerDTO1()));
    }

    @Test
    public void isHouseNumberShouldReturnTrueForNumber() {
        assertTrue(AddressFormatUtil.isHouseNumber(TEST_HOUSE_NUMBER_1));
    }

    @Test
    public void isHouseNumberShouldReturnTrueForNumberRange() {
        assertTrue(AddressFormatUtil.isHouseNumber(TEST_HOUSE_NUMBER_RANGE_1));
    }

    @Test
    public void isHouseNumberShouldReturnTrueForNumberAndLetter() {
        assertTrue(AddressFormatUtil.isHouseNumber(TEST_HOUSE_NUMBER_WITH_LETTER_1));
    }

    @Test
    public void isHouseNumberShouldReturnFalseForName() {
        assertFalse(AddressFormatUtil.isHouseNumber(TEST_HOUSE_NAME_1));
    }

    @Test
    public void shouldFormatLine1WithNumberAndStreet() {
        assertEquals(String.format("%s %s", TEST_HOUSE_NUMBER_1, STREET_1),
                AddressFormatUtil.formatLine1(TEST_HOUSE_NUMBER_1, STREET_1));
    }

    @Test
    public void shouldFormatLine1WithName() {
        assertEquals(TEST_HOUSE_NAME_1, AddressFormatUtil.formatLine1(TEST_HOUSE_NAME_1, STREET_1));
    }

    @Test
    public void shouldFormatLine1WithStreet() {
        assertEquals(STREET_1, AddressFormatUtil.formatLine1(EMPTY_STRING, STREET_1));
    }

    @Test
    public void shouldFormatLine1WithoutNumberNameOrStreet() {
        assertEquals(EMPTY_STRING, AddressFormatUtil.formatLine1(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    public void shouldFormatLine2WithNumberAndStreet() {
        assertEquals(EMPTY_STRING, AddressFormatUtil.formatLine2(TEST_HOUSE_NUMBER_1, STREET_1));
    }

    @Test
    public void shouldFormatLine2WithName() {
        assertEquals(STREET_1, AddressFormatUtil.formatLine2(TEST_HOUSE_NAME_1, STREET_1));
    }

    @Test
    public void shouldFormatLine2WithStreet() {
        assertEquals(EMPTY_STRING, AddressFormatUtil.formatLine2(EMPTY_STRING, STREET_1));
    }

    @Test
    public void shouldFormatLine2WithoutNumberNameOrStreet() {
        assertEquals(EMPTY_STRING, AddressFormatUtil.formatLine2(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    public void shouldFormatName() {
        assertEquals(FORMATED_NAME_1, AddressFormatUtil.formatName(TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1));
    }

    @Test
    public void shouldFormatNameIfNoTitle() {
        assertEquals(FIRST_NAME_1 + SPACE + INITIALS_1 + SPACE + LAST_NAME_1,
                AddressFormatUtil.formatName(FIRST_NAME_1, INITIALS_1, LAST_NAME_1));
    }

    @Test
    public void shouldFormatNameIfNoTitleAndInitial() {
        assertEquals(FIRST_NAME_1 + SPACE + LAST_NAME_1, AddressFormatUtil.formatName(FIRST_NAME_1, LAST_NAME_1));
    }

    @Test
    public void shouldFormatNameAndAddressWhenHouseNumberIsNumber() {
        List<String> actualResult = AddressFormatUtil
                .formatNameAndAddress(TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, TEST_HOUSE_NUMBER_1, STREET_1, TOWN_1,
                        POSTCODE_1, getTestCountryDTO1());
        assertEquals(EXPECTED_ADDRESS_NAME_LINE_COUNT_4, actualResult.size());
    }

    @Test
    public void shouldFormatNameAndAddressWhenHouseNumberIsName() {
        List<String> actualResult = AddressFormatUtil
                .formatNameAndAddress(TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, TEST_HOUSE_NAME_1, STREET_1, TOWN_1,
                        POSTCODE_1, getTestCountryDTO1());
        assertEquals(EXPECTED_ADDRESS_NAME_LINE_COUNT_5, actualResult.size());
    }

    @Test
    public void formatHouseNumberShouldReturnEmpty() {
        assertEquals(EMPTY_STRING, AddressFormatUtil.formatHouseNumber(TEST_HOUSE_NAME_1));
    }

    @Test
    public void formatHouseNumberShouldReturnHouseNumber() {
        assertEquals(TEST_HOUSE_NUMBER_1, AddressFormatUtil.formatHouseNumber(TEST_HOUSE_NUMBER_1));
    }

    @Test
    public void formatLine1WithSeparateHouseNumberWithHouseName() {
        assertEquals(TEST_HOUSE_NAME_1, AddressFormatUtil.formatLine1WithSeparateHouseNumber(TEST_HOUSE_NAME_1, STREET_1));
    }

    @Test
    public void formatLine1WithSeparateHouseNumberWithHouseNumber() {
        assertEquals(STREET_1, AddressFormatUtil.formatLine1WithSeparateHouseNumber(TEST_HOUSE_NUMBER_1, STREET_1));
    }

    @Test
    public void formatLine2WithSeparateHouseNumberWithHouseName() {
        assertEquals(STREET_1, AddressFormatUtil.formatLine2WithSeparateHouseNumber(TEST_HOUSE_NAME_1, STREET_1));
    }

    @Test
    public void formatLine2WithSeparateHouseNumberWithHouseNumber() {
        assertEquals(EMPTY_STRING, AddressFormatUtil.formatLine2WithSeparateHouseNumber(TEST_HOUSE_NUMBER_1, STREET_1));
    }

    @Test
    public void formatHouseNameNumberShouldReturnNull() {
        assertNull(AddressFormatUtil.formatHouseNameNumber(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    public void formatHouseNameNumberShouldReturnNumberName() {
        assertEquals(TEST_HOUSE_NUMBER_1 + SPACE + TEST_HOUSE_NAME_1,
                AddressFormatUtil.formatHouseNameNumber(TEST_HOUSE_NUMBER_1, TEST_HOUSE_NAME_1));
    }

    @Test
    public void formatHouseNameNumberShouldReturnNumberOnly() {
        assertEquals(TEST_HOUSE_NUMBER_1, AddressFormatUtil.formatHouseNameNumber(TEST_HOUSE_NUMBER_1, null));
    }

    @Test
    public void formatHouseNameNumberShouldReturnNameOnly() {
        assertEquals(TEST_HOUSE_NAME_1, AddressFormatUtil.formatHouseNameNumber(null, TEST_HOUSE_NAME_1));
    }

    @Test
    public void extractHouseNameNumberIfNumberOnly() {
        String[] actualResult = AddressFormatUtil.extractHouseNameNumber(TEST_HOUSE_NUMBER_WITH_LETTER_1);
        assertEquals(TEST_HOUSE_NUMBER_WITH_LETTER_1, actualResult[0]);
        assertEquals(EMPTY_STRING, actualResult[1]);
    }

    @Test
    public void extractHouseNameNumberIfNameOnly() {
        String[] actualResult = AddressFormatUtil.extractHouseNameNumber(TEST_HOUSE_NAME_1);
        assertEquals(EMPTY_STRING, actualResult[0]);
        assertEquals(TEST_HOUSE_NAME_1, actualResult[1]);
    }

    @Test
    public void extractHouseNameNumberIfNumberAndName() {
        String testData = TEST_HOUSE_NUMBER_1 + SPACE + TEST_HOUSE_NAME_1;
        String[] actualResult = AddressFormatUtil.extractHouseNameNumber(testData);
        assertEquals(TEST_HOUSE_NUMBER_1, actualResult[0]);
        assertEquals(TEST_HOUSE_NAME_1, actualResult[1]);
    }

    @Test
    public void shouldFormatNameWithNullInitials() {
        assertEquals(FIRST_NAME_1 + SPACE + LAST_NAME_1, AddressFormatUtil.formatName(FIRST_NAME_1, null, LAST_NAME_1));
    }

    @Test
    public void shouldFormatNameWithEmptyInitials() {
        assertEquals(FIRST_NAME_1 + SPACE + LAST_NAME_1, AddressFormatUtil.formatName(FIRST_NAME_1, EMPTY_STRING, LAST_NAME_1));
    }
}
