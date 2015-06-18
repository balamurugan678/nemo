package com.novacroft.nemo.tfl.services.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.test_support.WebServiceResultTestUtil;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public class WebServiceResultUtilTest {

    @Test
    public void generateResult() {
        assertNotNull(WebServiceResultUtil.generateResult(WebServiceResultTestUtil.ID_1, WebServiceResultTestUtil.ORIGINAL_ID_1,
                        WebServiceResultAttribute.SUCCESS.name(), WebServiceResultAttribute.SUCCESS.contentCode()));
    }

    @Test
    public void generateSuccessResult() {
        WebServiceResult result = WebServiceResultUtil.generateSuccessResult(WebServiceResultTestUtil.ID_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.SUCCESS.name(), result.getResult());
    }

}
