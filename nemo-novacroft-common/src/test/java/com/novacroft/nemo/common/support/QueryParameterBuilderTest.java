package com.novacroft.nemo.common.support;

import static com.novacroft.nemo.common.support.QueryParameterBuilder.LIKE_WILDCARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.novacroft.nemo.test_support.DateTestUtil;

public class QueryParameterBuilderTest {
    private static final String TEST_PARAMETER_1 = "parameter-1";
    private static final Date TEST_VALUE_1 = DateTestUtil.getAug19();
    private static final String TEST_VALUE_2 = "value-1";

    @Test
    public void shouldAddParameter() {
        QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder().addParameter(TEST_PARAMETER_1, TEST_VALUE_1);
        assertTrue(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
        assertEquals(TEST_VALUE_1, queryParameterBuilder.toMap().get(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldConditionallyAddParameter() {
        QueryParameterBuilder queryParameterBuilder =
                new QueryParameterBuilder().addParameter(true, TEST_PARAMETER_1, TEST_VALUE_1);
        assertTrue(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
        assertEquals(TEST_VALUE_1, queryParameterBuilder.toMap().get(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldConditionallyNotAddParameter() {
        QueryParameterBuilder queryParameterBuilder =
                new QueryParameterBuilder().addParameter(false, TEST_PARAMETER_1, TEST_VALUE_1);
        assertFalse(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldConditionallyAddParameterWithoutWildcard() {
        QueryParameterBuilder queryParameterBuilder =
                new QueryParameterBuilder().addParameter(true, TEST_PARAMETER_1, TEST_VALUE_2, false);
        assertTrue(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
        assertEquals(TEST_VALUE_2, queryParameterBuilder.toMap().get(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldConditionallyAddParameterWithWildcard() {
        QueryParameterBuilder queryParameterBuilder =
                new QueryParameterBuilder().addParameter(true, TEST_PARAMETER_1, TEST_VALUE_2, true);
        assertTrue(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
        assertEquals(TEST_VALUE_2 + LIKE_WILDCARD, queryParameterBuilder.toMap().get(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldConditionallyNotAddParameterWithWildcardOption() {
        QueryParameterBuilder queryParameterBuilder =
                new QueryParameterBuilder().addParameter(false, TEST_PARAMETER_1, TEST_VALUE_2, false);
        assertFalse(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
    }

    @Test
    public void shouldToString() {
        String expectedResult = String.format("%s = %s; ", TEST_PARAMETER_1, TEST_VALUE_2);
        QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder().addParameter(TEST_PARAMETER_1, TEST_VALUE_2);
        assertEquals(expectedResult, queryParameterBuilder.toString());
    }

    @Test
    public void addParameterShouldConditionallyAddParameterWithWildcardSurround() {
        QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder().addParameterSurroundedByWildcard(true, TEST_PARAMETER_1, TEST_VALUE_2, true);
        assertTrue(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
        assertEquals(LIKE_WILDCARD + TEST_VALUE_2 + LIKE_WILDCARD, queryParameterBuilder.toMap().get(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldConditionallyNotAddParameterWithWildcardSurround() {
        QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder().addParameterSurroundedByWildcard(true, TEST_PARAMETER_1, TEST_VALUE_2, false);
        assertTrue(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
        assertEquals(TEST_VALUE_2, queryParameterBuilder.toMap().get(TEST_PARAMETER_1));
    }

    @Test
    public void addParameterShouldNotConditionallyAddParameterWithWildcardSurround() {
        QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder().addParameterSurroundedByWildcard(false, TEST_PARAMETER_1, TEST_VALUE_2, false);
        assertFalse(queryParameterBuilder.toMap().containsKey(TEST_PARAMETER_1));
    }

}