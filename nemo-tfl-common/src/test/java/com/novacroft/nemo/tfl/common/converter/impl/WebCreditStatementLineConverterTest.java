package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.converter.WebCreditStatementLineConverter;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;
import org.junit.Test;

import java.util.List;

import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * WebCreditStatementLineConverter unit tests
 */
public class WebCreditStatementLineConverterTest {

    @Test
    public void shouldConvertEntityToDto() {
        WebCreditStatementLineConverter converter = new WebCreditStatementLineConverterImpl();
        WebCreditStatementLineDTO result = converter.convertEntityToDto(getTestRow1());
        assertEquals(getTestWebCreditStatementLineDTO1(), result);
    }

    @Test
    public void shouldConvertEntityListToDtoList() {
        WebCreditStatementLineConverter converter = new WebCreditStatementLineConverterImpl();
        List<WebCreditStatementLineDTO> result = converter.convertEntityListToDtoList(getTestRowList2());
        assertEquals(2, result.size());
        assertTrue(result.contains(getTestWebCreditStatementLineDTO1()));
        assertTrue(result.contains(getTestWebCreditStatementLineDTO2()));
    }
}
