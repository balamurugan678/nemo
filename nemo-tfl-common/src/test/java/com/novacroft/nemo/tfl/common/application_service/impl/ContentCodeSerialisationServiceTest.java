package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.ContentCodeSerialisationService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.ContentCodeSuffix;

/**
 * ContentCodeSerialisationService unit tests
 */
public class ContentCodeSerialisationServiceTest {

    protected static final String TEST_CODE = ContentCode.MANDATORY_FIELD_EMPTY.name() + ContentCodeSerialisationServiceImpl.NAME_SEPARATOR + ContentCodeSuffix.ERROR.name();
    protected static final String TEST_VALUE = ContentCode.MANDATORY_FIELD_EMPTY.codeStem() + ContentCodeSerialisationServiceImpl.VALUE_SEPARATOR + ContentCodeSuffix.ERROR.code();
    protected static final String TEST_CODE_POPUP = ContentCode.MANDATORY_FIELD_EMPTY.name() + ContentCodeSerialisationServiceImpl.NAME_SEPARATOR + ContentCodeSuffix.POPUP.name()
            + ContentCodeSerialisationServiceImpl.NAME_SEPARATOR + ContentCodeSuffix.TEXT.name();
    protected static final String TEST_VALUE_POPUP = ContentCode.MANDATORY_FIELD_EMPTY.codeStem() + ContentCodeSerialisationServiceImpl.VALUE_SEPARATOR + ContentCodeSuffix.POPUP.code()
            + ContentCodeSerialisationServiceImpl.VALUE_SEPARATOR + ContentCodeSuffix.TEXT.code();

    @Test
    public void shouldGetCodes() {
        ContentCodeSerialisationServiceImpl contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        String result = contentCodeSerialisationService.getCode(ContentCode.MANDATORY_FIELD_EMPTY, new ContentCodeSuffix[] { ContentCodeSuffix.ERROR });
        assertEquals(TEST_CODE, result);
    }

    @Test
    public void shouldGetValue() {
        ContentCodeSerialisationServiceImpl contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        String result = contentCodeSerialisationService.getValue(ContentCode.MANDATORY_FIELD_EMPTY, new ContentCodeSuffix[] { ContentCodeSuffix.ERROR });
        assertEquals(TEST_VALUE, result);
    }

    @Test
    public void shouldAddContentCodeSuffixToMap() {
        ContentCodeSerialisationServiceImpl contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        Map<String, String> contentCodeMap = new HashMap<String, String>();
        contentCodeSerialisationService.addContentCodeSuffixToMap(contentCodeSerialisationService.getCode(ContentCode.MANDATORY_FIELD_EMPTY, new ContentCodeSuffix[] { ContentCodeSuffix.ERROR }),
                contentCodeSerialisationService.getValue(ContentCode.MANDATORY_FIELD_EMPTY, new ContentCodeSuffix[] { ContentCodeSuffix.ERROR }), contentCodeMap);
        assertTrue(contentCodeMap.containsKey(TEST_CODE));
        assertEquals(TEST_VALUE, contentCodeMap.get(TEST_CODE));
    }

    @Test
    public void shouldAddContentCodeToMap() {
        ContentCodeSerialisationServiceImpl contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        Map<String, String> contentCodeMap = new HashMap<String, String>();
        contentCodeSerialisationService.addContentCodeToMap(ContentCode.MANDATORY_FIELD_EMPTY, contentCodeMap);
        assertTrue(contentCodeMap.containsKey(TEST_CODE));
        assertEquals(TEST_VALUE, contentCodeMap.get(TEST_CODE));
    }

    @Test
    public void shouldAddContentCodeForPopupToMap() {
        ContentCodeSerialisationServiceImpl contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        Map<String, String> contentCodeMap = new HashMap<String, String>();
        contentCodeSerialisationService.addContentCodeToMap(ContentCode.MANDATORY_FIELD_EMPTY, contentCodeMap);
        assertTrue(contentCodeMap.containsKey(TEST_CODE_POPUP));
        assertEquals(TEST_VALUE_POPUP, contentCodeMap.get(TEST_CODE_POPUP));
    }

    @Test
    public void shouldGetContentCodeMap() {
        ContentCodeSerialisationServiceImpl contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        Map<String, String> result = contentCodeSerialisationService.getContentCodeMap();
        assertTrue(result.containsKey(TEST_CODE));
        assertEquals(TEST_VALUE, result.get(TEST_CODE));
    }

    @Test
    public void shouldSerialiseContentCodes() {
        ContentCodeSerialisationService contentCodeSerialisationService = new ContentCodeSerialisationServiceImpl();
        String result = contentCodeSerialisationService.serialiseContentCodes();
        assertTrue(result.contains(TEST_CODE));
        assertTrue(result.contains(TEST_VALUE));
    }
}
