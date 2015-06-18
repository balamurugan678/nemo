package com.novacroft.nemo.common.support;

import static com.novacroft.nemo.common.support.QuerySqlBuilder.AND;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.END_PARENTHESIS;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.FROM;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.OR;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.PARAMETER_EQUALS;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.PARAMETER_LIKE;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.SELECT;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.SEPARATOR;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.START_PARENTHESIS;
import static com.novacroft.nemo.common.support.QuerySqlBuilder.WHERE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuerySqlBuilderTest {

    protected final static String SELECT_TEST_CLAUSE_1 = "column-1";
    protected final static String SELECT_TEST_CLAUSE_2 = "column-2";
    protected final static String TABLE_TEST_CLAUSE_1 = "table-1";
    protected final static String TABLE_TEST_CLAUSE_2 = "table-2";
    protected final static String WHERE_TEST_CLAUSE_1 = "column-3 = column-4";
    protected final static String WHERE_TEST_CLAUSE_2 = "column-5 = column-6";
    protected final static String FILTER_TEST_CLAUSE_1 = "column-7";
    protected final static String PARAMETER_TEST_NAME_1 = "parameter-1";

    @Test
    public void shouldAppendFirstSelect() {
        String expectedResult = String.format("%s%s", SELECT, SELECT_TEST_CLAUSE_1);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendFirstSelect(SELECT_TEST_CLAUSE_1);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void shouldAppendSelect() {
        String expectedResult = String.format("%s%s", SEPARATOR, SELECT_TEST_CLAUSE_2);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendSelect(SELECT_TEST_CLAUSE_2);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void shouldAppendFirstTable() {
        String expectedResult = String.format("%s%s", FROM, TABLE_TEST_CLAUSE_1);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendFirstTable(TABLE_TEST_CLAUSE_1);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void shouldAppendTable() {
        String expectedResult = String.format("%s%s", SEPARATOR, TABLE_TEST_CLAUSE_2);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendTable(TABLE_TEST_CLAUSE_2);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendTableShouldAppend() {
        String expectedResult = String.format("%s%s", SEPARATOR, TABLE_TEST_CLAUSE_2);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendTable(true, TABLE_TEST_CLAUSE_2);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendTableShouldNotAppend() {
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendTable(false, TABLE_TEST_CLAUSE_2);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }

    @Test
    public void shouldAppendFirstWhereJoin() {
        String expectedResult = String.format("%s%s", WHERE, WHERE_TEST_CLAUSE_1);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendFirstWhereJoin(WHERE_TEST_CLAUSE_1);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void shouldAppendAndJoin() {
        String expectedResult = String.format("%s%s", AND, WHERE_TEST_CLAUSE_2);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendAndJoin(WHERE_TEST_CLAUSE_2);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndJoinShouldAppend() {
        String expectedResult = String.format("%s%s", AND, WHERE_TEST_CLAUSE_2, PARAMETER_EQUALS);
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendAndJoin(true, WHERE_TEST_CLAUSE_2);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndJoinShouldNotAppend() {
        QuerySqlBuilder querySqlBuilder = new QuerySqlBuilder().appendAndJoin(false, WHERE_TEST_CLAUSE_2);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }

    @Test
    public void appendOrFilterShouldAppendEquals() {
        String expectedResult = String.format("%s%s%s%s", OR, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendOrFilter(FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, false);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendOrFilterShouldAppendLike() {
        String expectedResult = String.format("%s%s%s%s", OR, FILTER_TEST_CLAUSE_1, PARAMETER_LIKE, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendOrFilter(FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendOrFilterShouldConditionallyAppendEquals() {
        String expectedResult = String.format("%s%s%s%s", OR, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendOrFilter(true, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, false);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendOrFilterShouldConditionallyAppendLike() {
        String expectedResult = String.format("%s%s%s%s", OR, FILTER_TEST_CLAUSE_1, PARAMETER_LIKE, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendOrFilter(true, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendOrFilterShouldConditionallyNotAppend() {
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendOrFilter(false, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldAppendEquals() {
        String expectedResult = String.format("%s%s%s%s", AND, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, false);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldAppendLike() {
        String expectedResult = String.format("%s%s%s%s", AND, FILTER_TEST_CLAUSE_1, PARAMETER_LIKE, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldConditionallyAppendEquals() {
        String expectedResult = String.format("%s%s%s%s", AND, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(true, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, false);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldConditionallyAppendLike() {
        String expectedResult = String.format("%s%s%s%s", AND, FILTER_TEST_CLAUSE_1, PARAMETER_LIKE, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(true, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldConditionallyNotAppend() {
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(false, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldConditionallyAppendWithoutLike() {
        String expectedResult = String.format("%s%s%s%s", AND, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(true, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterShouldConditionallyNotAppendWithoutLike() {
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilter(false, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }
    
    @Test
    public void appendAndFilterWithStartParanthasisShouldAppendEquals() {
        String expectedResult = String.format("%s%s%s%s", AND + START_PARENTHESIS, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilterWithStartParanthasis(FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, false);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterWithStartParanthasisShouldAppendLike() {
        String expectedResult = String.format("%s%s%s%s", AND + START_PARENTHESIS, FILTER_TEST_CLAUSE_1, PARAMETER_LIKE, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilterWithStartParanthasis(FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1, true);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }

    @Test
    public void appendAndFilterWithStartParanthasisShouldConditionallyAppendEquals() {
        String expectedResult = String.format("%s%s%s%s", AND + START_PARENTHESIS, FILTER_TEST_CLAUSE_1, PARAMETER_EQUALS, PARAMETER_TEST_NAME_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilterWithStartParanthasis(true, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }
   
    @Test
    public void appendAndFilterWithStartParanthasisShouldConditionallyNotAppend() {
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendAndFilterWithStartParanthasis(false, FILTER_TEST_CLAUSE_1, PARAMETER_TEST_NAME_1);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }
    
    @Test
    public void appendEndParanthasisShouldAppend() {
        String expectedResult = String.format("%s%s", END_PARENTHESIS, FILTER_TEST_CLAUSE_1);
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendEndParenthesis(true, FILTER_TEST_CLAUSE_1);
        assertEquals(expectedResult, querySqlBuilder.toString());
    }
    
    @Test
    public void appendEndParanthasisShouldNotAppend() {
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendEndParenthesis(false, FILTER_TEST_CLAUSE_1);
        assertEquals(EMPTY, querySqlBuilder.toString());
    }

}