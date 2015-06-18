package com.novacroft.nemo.tfl.services.converter.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.novacroft.nemo.test_support.CancelOrderTestUtil;
import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

import org.junit.Before;
import org.junit.Test;

public class WebsServiceResultConverterImplTest {

    private WebsServiceResultConverterImpl converter;

    @Before
    public void setUp() throws Exception {
        converter = new WebsServiceResultConverterImpl();
    }

    @Test
    public void convertCancelOrderResultDTOToWebServiceResult() {
        CancelOrderResultDTO cancelOrderResultDTO1 = CancelOrderTestUtil.getCancelOrderResultDTO1();
        WebServiceResult result = converter.convertCancelOrderResultDTOToWebServiceResult(cancelOrderResultDTO1);

        assertNotNull(result);
        assertEquals(cancelOrderResultDTO1.getId(), result.getId());
        assertEquals(cancelOrderResultDTO1.getResult(), result.getResult());
    }

}
