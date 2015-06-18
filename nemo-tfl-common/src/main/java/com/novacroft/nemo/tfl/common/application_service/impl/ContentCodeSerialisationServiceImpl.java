package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.common.application_service.ContentCodeSerialisationService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.ContentCodeSuffix;

/**
 * JSON serialiser for content code constants
 */
@Service(value = "contentCodeSerialisationService")
public class ContentCodeSerialisationServiceImpl implements ContentCodeSerialisationService {
    protected static final ContentCodeSuffix[][] CONTENT_CODE_SUFFIXES_FOR_CLIENT_SIDE = { { ContentCodeSuffix.ERROR }, { ContentCodeSuffix.LABEL }, { ContentCodeSuffix.TEXT },
            { ContentCodeSuffix.POPUP, ContentCodeSuffix.TEXT } };
    protected static final String NAME_SEPARATOR = "_";
    protected static final String VALUE_SEPARATOR = ".";

    @Override
    public String serialiseContentCodes() {
        return new Gson().toJson(getContentCodeMap());
    }

    protected Map<String, String> getContentCodeMap() {
        Map<String, String> contentCodeMap = new HashMap<String, String>();
        for (ContentCode contentCode : ContentCode.values()) {
            addContentCodeToMap(contentCode, contentCodeMap);
        }
        return contentCodeMap;
    }

    protected void addContentCodeToMap(ContentCode contentCode, Map<String, String> contentCodeMap) {
        for (ContentCodeSuffix[] contentCodeSuffixes : CONTENT_CODE_SUFFIXES_FOR_CLIENT_SIDE) {
            addContentCodeSuffixToMap(getCode(contentCode, contentCodeSuffixes), getValue(contentCode, contentCodeSuffixes), contentCodeMap);
        }
    }

    protected void addContentCodeSuffixToMap(String code, String value, Map<String, String> contentCodeMap) {
        contentCodeMap.put(code, value);
    }

    protected String getCode(ContentCode contentCode, ContentCodeSuffix[] contentCodeSuffixes) {
        return contentCode.name() + getCodeSuffix(contentCodeSuffixes);
    }

    protected String getCodeSuffix(ContentCodeSuffix[] contentCodeSuffixes) {
        String codeSuffix = "";
        for (ContentCodeSuffix contentCodeSuffix : contentCodeSuffixes) {
            codeSuffix += NAME_SEPARATOR + contentCodeSuffix.name();
        }
        return codeSuffix;
    }

    protected String getValue(ContentCode contentCode, ContentCodeSuffix[] contentCodeSuffixes) {

        return contentCode.codeStem() + getValueSuffix(contentCodeSuffixes);
    }

    protected String getValueSuffix(ContentCodeSuffix[] contentCodeSuffixes) {
        String valueSuffix = "";
        for (ContentCodeSuffix contentCodeSuffix : contentCodeSuffixes) {
            valueSuffix += VALUE_SEPARATOR + contentCodeSuffix.code();
        }
        return valueSuffix;
    }
}
