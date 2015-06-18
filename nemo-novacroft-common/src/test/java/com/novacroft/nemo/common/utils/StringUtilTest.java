package com.novacroft.nemo.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * StringUtil unit tests
 */
public class StringUtilTest {

    private static final char TEST_CHAR_X = 'x';
    
    private static final String TEST_WORD_1 = "word-1";
    private static final String TEST_WORD_2 = "word-2";
    private static final String TEST_WORD_3 = "word-3";
    private static final String NULL_TEST_STRING = null;
    private static final String SPACE_TEST_STRING = " ";
    private static final String EMPTY_TEST_STRING = "";
    private static final String ONE_WORD_TEST_STRING = TEST_WORD_1;
    private static final String TWO_WORD_TEST_STRING = String.format("%s %s", TEST_WORD_1, TEST_WORD_2);
    private static final String THREE_WORD_TEST_STRING = String.format("%s %s %s", TEST_WORD_1, TEST_WORD_2, TEST_WORD_3);
    private static final String SECOND_AND_THIRD_WORD_TEST_STRING = String.format("%s %s", TEST_WORD_2, TEST_WORD_3);
    
    private static final Integer TEST_PENCE = Integer.valueOf(123456789);

    @Test
    public void shouldInitCap() {
        assertEquals("Abc", StringUtil.initCap("abc"));
    }

    @Test
    public void shouldArrayToString() {
        assertEquals("ab, cd, ef", StringUtil.arrayToString(new String[]{"ab", "cd", "ef"}));
    }

    @Test
    public void shouldCountOccurrences() {
        assertEquals(3, StringUtil.countOccurrences("abacad", 'a'));
    }

    @Test
    public void extractFirstWordShouldReturnEmptyStringForNullString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.extractFirstWord(NULL_TEST_STRING));
    }

    @Test
    public void extractFirstWordShouldReturnEmptyStringForEmptyString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.extractFirstWord(SPACE_TEST_STRING));
    }

    @Test
    public void extractFirstWordShouldReturnFirstWordOfOneWordString() {
        assertEquals(TEST_WORD_1, StringUtil.extractFirstWord(ONE_WORD_TEST_STRING));
    }

    @Test
    public void extractFirstWordShouldReturnFirstWordOfTwoWordString() {
        assertEquals(TEST_WORD_1, StringUtil.extractFirstWord(TWO_WORD_TEST_STRING));
    }

    @Test
    public void extractFirstWordShouldReturnFirstWordOfThreeWordString() {
        assertEquals(TEST_WORD_1, StringUtil.extractFirstWord(THREE_WORD_TEST_STRING));
    }

    @Test
    public void extractAfterFirstWordShouldReturnEmptyStringForNullString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.extractAfterFirstWord(NULL_TEST_STRING));
    }

    @Test
    public void extractAfterFirstWordShouldReturnEmptyStringForEmptyString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.extractAfterFirstWord(SPACE_TEST_STRING));
    }

    @Test
    public void extractAfterFirstWordShouldReturnEmptyStringOfOneWordString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.extractAfterFirstWord(ONE_WORD_TEST_STRING));
    }

    @Test
    public void extractAfterFirstWordShouldReturnSecondWordOfTwoWordString() {
        assertEquals(TEST_WORD_2, StringUtil.extractAfterFirstWord(TWO_WORD_TEST_STRING));
    }

    @Test
    public void extractAfterFirstWordShouldReturnSecondAndThirdWordsOfThreeWordString() {
        assertEquals(SECOND_AND_THIRD_WORD_TEST_STRING, StringUtil.extractAfterFirstWord(THREE_WORD_TEST_STRING));
    }

    @Test
    public void testConvertNulltoEmptyStringIfNull() {
        assertEquals(SPACE_TEST_STRING, StringUtil.convertNulltoEmptyString(NULL_TEST_STRING));
    }
    
    @Test
    public void testConvertNulltoEmptyStringIfNotNull() {
        assertEquals(TEST_WORD_1, StringUtil.convertNulltoEmptyString(TEST_WORD_1));
    }
    
    @Test
    public void testReplicateString() {
        final String expectedResult = TEST_WORD_1 + TEST_WORD_1 + TEST_WORD_1;
        assertEquals(expectedResult, StringUtil.replicate(TEST_WORD_1, 3));
    }
    
    @Test
    public void testReplicateChar() {
        final String expectedResult = "" + TEST_CHAR_X + TEST_CHAR_X + TEST_CHAR_X;
        assertEquals(expectedResult, StringUtil.replicate(TEST_CHAR_X, 3));
    }
    
    @Test
    public void midShouldReturnEmptyString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.mid(TEST_WORD_1, TEST_WORD_1.length() + 1, 1));
    }
    
    @Test
    public void midShouldReturnNonEmptyString() {
        String actualResult = StringUtil.mid(TEST_WORD_1, 1, 2);
        assertFalse(actualResult.isEmpty());
    }
    
    @Test
    public void midShouldReturnTheRestString() {
        assertEquals("ord-1", StringUtil.mid(TEST_WORD_1, 1, TEST_WORD_1.length()));
    }
    
    @Test
    public void midShouldReturnStringIfMinusLength() {
        assertEquals("ord-", StringUtil.mid(TEST_WORD_1, 1, -2));
    }
    
    @Test
    public void rightShouldReturnWholeWord() {
        assertEquals(TEST_WORD_1, StringUtil.right(TEST_WORD_1, TEST_WORD_1.length() + 1));
    }
    
    @Test
    public void rightShouldReturnStringIfMinusLength() {
        assertEquals("-1", StringUtil.right(TEST_WORD_1, -4));
    }
    
    @Test
    public void rightShouldReturnRightSubString() {
        assertEquals("rd-1", StringUtil.right(TEST_WORD_1, 4));
    }
    
    @Test
    public void leftShouldReturnWholeWord() {
        assertEquals(TEST_WORD_1, StringUtil.left(TEST_WORD_1, TEST_WORD_1.length() + 1));
    }
    
    @Test
    public void leftShouldReturnStringIfMinusLength() {
        assertEquals("wo", StringUtil.left(TEST_WORD_1, -4));
    }
    
    @Test
    public void leftShouldReturnLeftRightString() {
        assertEquals("word", StringUtil.left(TEST_WORD_1, 4));
    }
    
    @Test
    public void testFormatPenceToCurrency() {
        assertEquals("£1,234,567.89", StringUtil.formatPenceToCurrency(TEST_PENCE));
    }
    
    @Test
    public void shouldReturnDefaultIfEmptyString() {
        assertEquals(TEST_WORD_1, StringUtil.isEmptyReturnDefault(EMPTY_TEST_STRING, TEST_WORD_1));
    }
    
    @Test
    public void shouldIgnoreDefaultIfNotEmptyString() {
        final String defaultString = "IgnoreMe";
        assertEquals(TEST_WORD_2, StringUtil.isEmptyReturnDefault(TEST_WORD_2, defaultString));
    }
    
    @Test
    public void trimShouldReturnNull() {
        assertNull(StringUtil.trim(null));
    }
    
    @Test
    public void shouldReturnTrimmedString() {
        String testString = EMPTY_TEST_STRING + TEST_WORD_1 + EMPTY_TEST_STRING;
        assertEquals(TEST_WORD_1, StringUtil.trim(testString));
    }
    
    @Test
    public void isAlphabeticShouldReturnTrue() {
        assertTrue(StringUtil.isAlphabetic("abc"));
    }
    
    @Test
    public void isAlphabeticShouldReturnFalse() {
        assertFalse(StringUtil.isAlphabetic(TEST_WORD_1));
    }
    
    @Test
    public void testEmptyCheckForEmptyString() {
        assertTrue(StringUtil.isEmpty(EMPTY_TEST_STRING));
        assertFalse(StringUtil.isNotEmpty(EMPTY_TEST_STRING));
    }
    
    @Test
    public void testEmptyCheckForNonEmptyString() {
        assertFalse(StringUtil.isEmpty(TEST_WORD_1));
        assertTrue(StringUtil.isNotEmpty(TEST_WORD_1));
    }
    
    @Test
    public void isIntegerShouldReturnTrue() {
        assertTrue(StringUtil.isInteger("12345"));
    }
    
    @Test
    public void isIntegerShouldReturnFalse() {
        assertFalse(StringUtil.isInteger("123.45"));
    }
    
    @Test
    public void shouldConvertToMixCase() {
        final String expectedResult = "Word-2 Word-3";
        assertEquals(expectedResult, StringUtil.mixCase(SECOND_AND_THIRD_WORD_TEST_STRING));
    }
    
    @Test
    public void indexShouldReturnMinusOneIfNotFound() {
        assertEquals(-1, StringUtil.index(SECOND_AND_THIRD_WORD_TEST_STRING, 'x', 1));
    }
    
    @Test
    public void indexShouldReturnCorrectIndex() {
        assertEquals(7, StringUtil.index(SECOND_AND_THIRD_WORD_TEST_STRING, 'w', 2));
    }
    
    @Test
    public void fieldShouldReturnEmptyString() {
        assertEquals(EMPTY_TEST_STRING, StringUtil.field(SECOND_AND_THIRD_WORD_TEST_STRING, 'w', 1));
    }
    
    @Test
    public void fieldShouldReturnFoundField() {
        assertEquals("word-3", StringUtil.field(SECOND_AND_THIRD_WORD_TEST_STRING, ' ', 2));
    }
}
