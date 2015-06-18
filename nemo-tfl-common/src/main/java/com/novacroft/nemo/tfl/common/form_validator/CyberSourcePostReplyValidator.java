package com.novacroft.nemo.tfl.common.form_validator;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSecurityService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentGatewayReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;

/**
 * Validate CyberSource payment gateway HTTP POST reply
 */
@Component("cyberSourcePostReplyValidator")
public class CyberSourcePostReplyValidator implements Validator {
    @Autowired
    protected CyberSourceSecurityService cyberSourceSecurityService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PaymentGatewayReplyDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentGatewayReplyDTO cmd = (PaymentGatewayReplyDTO) target;
        validateReplyMandatoryFields(cmd, errors);
        if (errors.hasErrors()) {
            return;
        }

        validateSignature(cmd, errors);
        if (errors.hasErrors()) {
            return;
        }

        validateTransactionUuid(cmd, errors);

    }

    protected void validateReplyMandatoryFields(PaymentGatewayReplyDTO cmd, Errors errors) {
        if (cmd.getCyberSourceReply() == null) {
            errors.reject(ContentCode.PAYMENT_GATEWAY_REPLY.errorCode(), new Object[] {}, null);
            return;
        }
        CyberSourcePostReplyDTO reply = (CyberSourcePostReplyDTO) cmd.getCyberSourceReply();
        if (isReplyMandatoryFieldMissing(reply)) {
            errors.reject(ContentCode.PAYMENT_GATEWAY_REPLY.errorCode(), new Object[]{}, null);
        }
    }

    protected void validateSignature(PaymentGatewayReplyDTO orderCmd, Errors errors) {
        if (!(orderCmd.getCyberSourceReply() instanceof CyberSourcePostReplyDTO)) {
            return;
        }
        if (!this.cyberSourceSecurityService
                .isPostReplySignatureValid((CyberSourcePostReplyDTO) orderCmd.getCyberSourceReply())) {
            errors.reject(ContentCode.PAYMENT_GATEWAY_REPLY.errorCode(), new Object[]{}, null);
        }
    }

    protected void validateTransactionUuid(PaymentGatewayReplyDTO orderCmd, Errors errors) {
        if (orderCmd.getCyberSourceReply() == null || orderCmd.getPaymentCardSettlement() == null) {
            errors.reject(ContentCode.PAYMENT_GATEWAY_REPLY.errorCode(), new Object[]{}, null);
            return;
        }
        CyberSourcePostReplyDTO reply = (CyberSourcePostReplyDTO) orderCmd.getCyberSourceReply();
        PaymentCardSettlementDTO settlement = orderCmd.getPaymentCardSettlement();
        if (isNotTransactionUuidRequestSameAsReply(settlement.getTransactionUuid(), reply.getReqTransactionUuid())) {
            errors.reject(ContentCode.PAYMENT_GATEWAY_REPLY.errorCode(), new Object[]{}, null);
        }
    }

    protected Boolean isReplyMandatoryFieldMissing(CyberSourcePostReplyDTO reply) {
        return isBlank(reply.getAuthAmount()) || isBlank(reply.getAuthTime()) || isBlank(reply.getDecision()) ||
                isBlank(reply.getReqReferenceNumber()) || isBlank(reply.getReqTransactionUuid()) ||
                isBlank(reply.getSignature()) || isBlank(reply.getSignedDateTime()) || isBlank(reply.getSignedFieldNames()) ||
                isBlank(reply.getTransactionId());
    }

    protected Boolean isNotTransactionUuidRequestSameAsReply(String requestUuid, String replyUuid) {
        return !(isNotBlank(requestUuid) && isNotBlank(replyUuid) && requestUuid.equals(replyUuid));
    }
}
