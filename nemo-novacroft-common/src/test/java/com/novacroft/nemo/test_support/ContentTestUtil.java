package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.Content;
import com.novacroft.nemo.common.transfer.ContentDTO;

import java.util.Locale;

/**
 * Utilities for content tests
 */
public final class ContentTestUtil {
    public static final Locale UK_LOCALE = new Locale("en", "GB");
    public static final Locale FRENCH_LOCALE = new Locale("fr", "FR");
    public static final Locale US_LOCALE = new Locale("en", "US");

    public static final Long TEST_ID_1 = 4L;
    public static final String TEST_CODE_1 = "test-code-1";
    public static final String TEST_CODE_1_PARENT = "test-page.test-code-1";
    public static final String TEST_LOCALE_1 = "aa_AA";
    public static final String TEST_CONTENT_1 = "test-content-1";

    public static final Long TEST_ID_2 = 8L;
    public static final String TEST_CODE_2 = "test-code-2";
    public static final String TEST_LOCALE_2 = "bb_BB";
    public static final String TEST_CONTENT_2 = "test-content-2";

    public static final Long TEST_ID_3 = 8L;
    public static final String TEST_CODE_3 = "test-code-3";
    public static final String TEST_LOCALE_3 = "cc_CC";
    public static final String TEST_CONTENT_3 = "test-content-3";

    public static Content getTestContent1() {
        return getTestContent(TEST_ID_1, TEST_CODE_1, TEST_LOCALE_1, TEST_CONTENT_1);
    }

    public static Content getTestContent2() {
        return getTestContent(TEST_ID_2, TEST_CODE_2, TEST_LOCALE_2, TEST_CONTENT_2);
    }

    public static Content getTestContent(Long testId, String testCode, String testLocale, String testContent) {
        Content content = new Content();
        content.setId(testId);
        content.setCode(testCode);
        content.setLocale(testLocale);
        content.setContent(testContent);
        return content;
    }

    public static ContentDTO getTestContentDTO1() {
        return getTestContentDTO(TEST_ID_1, TEST_CODE_1, TEST_LOCALE_1, TEST_CONTENT_1);
    }

    public static ContentDTO getTestContentDTO2() {
        return getTestContentDTO(TEST_ID_2, TEST_CODE_2, TEST_LOCALE_2, TEST_CONTENT_2);
    }

    public static ContentDTO getTestContentDTOuk1() {
        return getTestContentDTO(TEST_ID_1, TEST_CODE_1, UK_LOCALE.toString(), TEST_CONTENT_1);
    }

    public static ContentDTO getTestContentDTOuk2() {
        return getTestContentDTO(TEST_ID_2, TEST_CODE_2, UK_LOCALE.toString(), TEST_CONTENT_2);
    }

    public static ContentDTO getTestContentDTOfr3() {
        return getTestContentDTO(TEST_ID_3, TEST_CODE_3, FRENCH_LOCALE.toString(), TEST_CONTENT_3);
    }

    public static ContentDTO getTestContentDTO(Long testId, String testCode, String testLocale, String testContentDTO) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setId(testId);
        contentDTO.setCode(testCode);
        contentDTO.setLocale(testLocale);
        contentDTO.setContent(testContentDTO);
        return contentDTO;
    }
}
