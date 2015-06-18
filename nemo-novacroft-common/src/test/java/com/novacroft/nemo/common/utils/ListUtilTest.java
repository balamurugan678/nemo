package com.novacroft.nemo.common.utils;

/**
 * ListUtil unit tests
 */

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListUtilTest {

    private List<String> testList = new ArrayList<String>(2);
    private String testItem1 = "item-1";
    private String testItem2 = "item-2";
    private String testString = String.format("%s,%s", testItem1, testItem2);

    @Before
    public void setUp() {
        this.testList.add(testItem1);
        this.testList.add(testItem2);
    }

    @Test
    public void getCommaDelimitedStringAsListShouldReturnEmptyList() {
        assertTrue(ListUtil.getCommaDelimitedStringAsList(null).isEmpty());
    }

    @Test
    public void getCommaDelimitedStringAsListShouldReturnList() {
        List<String> result = ListUtil.getCommaDelimitedStringAsList(this.testString);
        assertEquals(2, result.size());
        assertTrue(result.contains(this.testItem1));
        assertTrue(result.contains(this.testItem2));
    }

    @Test
    public void getListAsCommaDelimitedStringShouldReturnEmptyString() {
        assertEquals("", ListUtil.getListAsCommaDelimitedString(null));
    }

    @Test
    public void getListAsCommaDelimitedStringShouldReturnString() {
        assertEquals(this.testString, ListUtil.getListAsCommaDelimitedString(this.testList));
    }
}
