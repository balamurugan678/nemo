package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapReplyConverter;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.common.utils.ListUtil.getListAsCommaDelimitedString;

/**
 * CyberSource payment gateway reply DTO/model converter
 */
@Service("cyberSourceSoapReplyConverter")
public class CyberSourceSoapReplyConverterImpl implements CyberSourceSoapReplyConverter {
    @Override
    public CyberSourceSoapReplyDTO convertModelToDto(ReplyMessage reply) {
        assert (reply != null);
        assert (reply.getCcAuthReply() != null);
        assert (reply.getCcCaptureReply() != null);

        return new CyberSourceSoapReplyDTO(reply.getCcAuthReply().getAmount(), reply.getCcAuthReply().getAuthorizationCode(),
                reply.getCcAuthReply().getAuthorizedDateTime(), reply.getCcAuthReply().getPaymentNetworkTransactionID(),
                String.valueOf(reply.getCcAuthReply().getReasonCode()), reply.getCcCaptureReply().getAmount(),
                String.valueOf(reply.getCcCaptureReply().getReasonCode()), reply.getDecision(),
                getListAsCommaDelimitedString(reply.getInvalidField()), reply.getMerchantReferenceCode(),
                getListAsCommaDelimitedString(reply.getMissingField()), String.valueOf(reply.getReasonCode()),
                reply.getRequestID());
    }
}
