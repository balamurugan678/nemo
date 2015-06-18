package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CyberSourceSoapReplyTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CyberSourceSoapDeleteTokenReplyConverterImplTest {
    private CyberSourceSoapDeleteTokenReplyConverterImpl converter;
    private CyberSourceSoapReplyDTO mockCyberSourceSoapReplyDTO;
    private ReplyMessage mockReplyMessage;

    @Before
    public void setUp() {
        this.converter = mock(CyberSourceSoapDeleteTokenReplyConverterImpl.class, CALLS_REAL_METHODS);
        this.mockReplyMessage = mock(ReplyMessage.class);
    }

    @Test
    public void shouldConvertModelToDto() {
        when(this.mockReplyMessage.getDecision()).thenReturn(DECISION_1);
        when(this.mockReplyMessage.getInvalidField()).thenReturn(INVALID_FIELDS_1);
        when(this.mockReplyMessage.getMerchantReferenceCode()).thenReturn(MERCHANT_REFERENCE_CODE_1);
        when(this.mockReplyMessage.getMissingField()).thenReturn(MISSING_FIELDS_1);
        when(this.mockReplyMessage.getRequestID()).thenReturn(REQUEST_ID_1);

        CyberSourceSoapReplyDTO result = this.converter.convertModelToDto(this.mockReplyMessage);

        assertEquals(DECISION_1, result.getDecision());
        assertEquals(INVALID_FIELDS_AS_STRING_1, result.getInvalidFields());
        assertEquals(MERCHANT_REFERENCE_CODE_1, result.getMerchantReferenceCode());
        assertEquals(MISSING_FIELDS_AS_STRING_1, result.getMissingFields());
        assertEquals(REQUEST_ID_1, result.getRequestId());
    }
}