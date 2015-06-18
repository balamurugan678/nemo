package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.CCAuthReply;
import com.novacroft.cyber_source.web_service.model.transaction.CCCaptureReply;
import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapReplyConverter;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CyberSourceSoapReplyTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CyberSourceSoapReplyConverterImpl unit tests
 */
public class CyberSourceSoapReplyConverterImplTest {

    private CyberSourceSoapReplyConverter converter;

    private ReplyMessage mockReplyMessage;
    private CCAuthReply mockCCAuthReply;
    private CCCaptureReply mockCCCaptureReply;

    @Before
    public void setUp() {
        this.converter = new CyberSourceSoapReplyConverterImpl();
        this.mockReplyMessage = mock(ReplyMessage.class);
        this.mockCCAuthReply = mock(CCAuthReply.class);
        when(this.mockReplyMessage.getCcAuthReply()).thenReturn(this.mockCCAuthReply);
        this.mockCCCaptureReply = mock(CCCaptureReply.class);
        when(this.mockReplyMessage.getCcCaptureReply()).thenReturn(this.mockCCCaptureReply);
    }

    @Test
    public void shouldConvertModelToDto() {
        when(this.mockCCAuthReply.getAmount()).thenReturn(AUTHORIZED_AMOUNT_1);
        when(this.mockCCAuthReply.getAuthorizationCode()).thenReturn(AUTHORIZATION_CODE_1);
        when(this.mockCCAuthReply.getAuthorizedDateTime()).thenReturn(AUTHORIZED_AT_1);
        when(this.mockCCAuthReply.getPaymentNetworkTransactionID()).thenReturn(AUTHORIZATION_PAYMENT_NETWORK_TRANSACTION_ID_1);
        when(this.mockCCAuthReply.getReasonCode()).thenReturn(AUTHORIZATION_REASON_CODE_1);
        when(this.mockCCCaptureReply.getAmount()).thenReturn(CAPTURE_AMOUNT_1);
        when(this.mockCCCaptureReply.getReasonCode()).thenReturn(CAPTURE_REASON_CODE_1);
        when(this.mockReplyMessage.getDecision()).thenReturn(DECISION_1);
        when(this.mockReplyMessage.getInvalidField()).thenReturn(INVALID_FIELDS_1);
        when(this.mockReplyMessage.getMerchantReferenceCode()).thenReturn(MERCHANT_REFERENCE_CODE_1);
        when(this.mockReplyMessage.getMissingField()).thenReturn(MISSING_FIELDS_1);
        when(this.mockReplyMessage.getReasonCode()).thenReturn(REASON_CODE_1);
        when(this.mockReplyMessage.getRequestID()).thenReturn(REQUEST_ID_1);

        CyberSourceSoapReplyDTO result = this.converter.convertModelToDto(this.mockReplyMessage);

        assertEquals(AUTHORIZED_AMOUNT_1, result.getAuthorizedAmount());
        assertEquals(AUTHORIZATION_CODE_1, result.getAuthorizationCode());
        assertEquals(AUTHORIZED_AT_1, result.getAuthorizedAt());
        assertEquals(AUTHORIZATION_PAYMENT_NETWORK_TRANSACTION_ID_1, result.getAuthorizationPaymentNetworkTransactionId());
        assertEquals(AUTHORIZATION_REASON_CODE_AS_STRING_1, result.getAuthorizationReasonCode());
        assertEquals(CAPTURE_AMOUNT_1, result.getCaptureAmount());
        assertEquals(CAPTURE_REASON_CODE_AS_STRING_1, result.getCaptureReasonCode());
        assertEquals(DECISION_1, result.getDecision());
        assertEquals(INVALID_FIELDS_AS_STRING_1, result.getInvalidFields());
        assertEquals(MERCHANT_REFERENCE_CODE_1, result.getMerchantReferenceCode());
        assertEquals(MISSING_FIELDS_AS_STRING_1, result.getMissingFields());
        assertEquals(REASON_CODE_AS_STRING_1, result.getReasonCode());
        assertEquals(REQUEST_ID_1, result.getRequestId());
    }
}
