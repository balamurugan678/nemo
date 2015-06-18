package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapDeleteTokenReplyConverter;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.common.utils.ListUtil.getListAsCommaDelimitedString;

/**
 * CyberSource payment gateway delete token reply DTO/model converter
 */
@Service("cyberSourceSoapDeleteTokenReplyConverter")
public class CyberSourceSoapDeleteTokenReplyConverterImpl implements CyberSourceSoapDeleteTokenReplyConverter {
    @Override
    public CyberSourceSoapReplyDTO convertModelToDto(ReplyMessage reply) {
        assert (reply != null);

        return new CyberSourceSoapReplyDTO(reply.getDecision(), getListAsCommaDelimitedString(reply.getInvalidField()),
                reply.getMerchantReferenceCode(), getListAsCommaDelimitedString(reply.getMissingField()),
                String.valueOf(reply.getReasonCode()), reply.getRequestID());
    }
}
