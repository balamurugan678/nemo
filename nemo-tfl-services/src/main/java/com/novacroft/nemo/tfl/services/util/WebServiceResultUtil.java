package com.novacroft.nemo.tfl.services.util;

import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public final class WebServiceResultUtil {
    public static WebServiceResult generateResult(Long id, Long originalId, String result, String message) {
        WebServiceResult webServiceResult = new WebServiceResult();
        webServiceResult.setId(id);
        webServiceResult.setOriginalId(originalId);
        webServiceResult.setResult(result);
        webServiceResult.setMessage(message);
        return webServiceResult;
    }

    public static WebServiceResult generateSuccessResult(Long id) {
        return generateResult(id, null, WebServiceResultAttribute.SUCCESS.name(), null);
    }

    private WebServiceResultUtil() {

    }
}
