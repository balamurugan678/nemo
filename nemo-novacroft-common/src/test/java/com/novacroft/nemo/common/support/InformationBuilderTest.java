package com.novacroft.nemo.common.support;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * InformationBuilder unit tests
 */
public class InformationBuilderTest {
    @Test
    public void shouldAppendString() {
        String testString1 = "A";
        String testString2 = "B";
        String expectedResult = testString1 + testString2;
        assertEquals(expectedResult, new InformationBuilder().append(testString1).append(testString2).toString());
    }

    @Test
    public void shouldAppendFormattedString() {
        String testFormat1 = "%s%s";
        String testFormat1Argument1 = "A";
        String testFormat1Argument2 = "B";
        String testFormat2 = "%s%s";
        String testFormat2Argument1 = "C";
        String testFormat2Argument2 = "D";
        String expectedResult = testFormat1Argument1 + testFormat1Argument2 + testFormat2Argument1 + testFormat2Argument2;
        assertEquals(expectedResult, new InformationBuilder().append(testFormat1, testFormat1Argument1, testFormat1Argument2)
                .append(testFormat2, testFormat2Argument1, testFormat2Argument2).toString());
    }
}
