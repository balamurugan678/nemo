package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.cyber_source.web_service.model.transaction.*;
import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.SoapSettings;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Transaction web service
 */
@Service("soapTransactionService")
public class SoapTransactionServiceImpl implements SoapTransactionService {
    @Autowired
    protected TokenGenerator tokenGenerator;

    @Override
    public ReplyMessage fabricateReplyMessage(RequestMessage requestMessage) {
        if (isAuthoriseAndCaptureRequest(requestMessage)) {
            return fabricateTransactionReplyMessage(requestMessage);
        }

        if (isDeleteProfileRequest(requestMessage)) {
            return fabricateDeleteProfileReplyMessage(requestMessage);
        }

        throw new ApplicationServiceException("Don't know what to do with this request message!");
    }

    protected ReplyMessage fabricateTransactionReplyMessage(RequestMessage requestMessage) {
        Date now = new Date();
        ObjectFactory objectFactory = new ObjectFactory();
        ReplyMessage replyMessage = objectFactory.createReplyMessage();

        updateReplyWithSoapSettings(replyMessage);

        replyMessage.setMerchantReferenceCode(requestMessage.getMerchantReferenceCode());
        replyMessage.setRequestID(this.tokenGenerator.createUrlSafeToken(16));
        replyMessage.setRequestToken(this.tokenGenerator.createUrlSafeToken(32));

        CCAuthReply ccAuthReply = objectFactory.createCCAuthReply();
        ccAuthReply.setAmount(requestMessage.getPurchaseTotals().getGrandTotalAmount());
        ccAuthReply.setAuthorizationCode(String.valueOf(now.getTime()));
        ccAuthReply.setAuthorizedDateTime(DateUtil.dateToString(now));
        ccAuthReply.setReasonCode(replyMessage.getReasonCode());
        ccAuthReply.setTransactionID(String.valueOf(now.getTime()));
        replyMessage.setCcAuthReply(ccAuthReply);

        CCCaptureReply ccCaptureReply = objectFactory.createCCCaptureReply();
        ccCaptureReply.setAmount(requestMessage.getPurchaseTotals().getGrandTotalAmount());
        ccCaptureReply.setReasonCode(replyMessage.getReasonCode());
        replyMessage.setCcCaptureReply(ccCaptureReply);

        return replyMessage;
    }

    protected ReplyMessage fabricateDeleteProfileReplyMessage(RequestMessage requestMessage) {
        ObjectFactory objectFactory = new ObjectFactory();
        ReplyMessage replyMessage = objectFactory.createReplyMessage();

        updateReplyWithSoapSettings(replyMessage);

        replyMessage.setMerchantReferenceCode(requestMessage.getMerchantReferenceCode());
        replyMessage.setRequestID(this.tokenGenerator.createUrlSafeToken(16));

        PaySubscriptionDeleteReply paySubscriptionDeleteReply = objectFactory.createPaySubscriptionDeleteReply();
        paySubscriptionDeleteReply.setSubscriptionID(requestMessage.getRecurringSubscriptionInfo().getSubscriptionID());
        paySubscriptionDeleteReply.setReasonCode(replyMessage.getReasonCode());
        replyMessage.setPaySubscriptionDeleteReply(paySubscriptionDeleteReply);

        return replyMessage;
    }

    protected void updateReplyWithSoapSettings(ReplyMessage replyMessage) {
        SoapSettings settings = SoapSettings.getInstance();
        replyMessage.setDecision(settings.getDecision());
        replyMessage.setReasonCode(valueOf(settings.getReasonCode()));
        replyMessage.getMissingField().addAll(settings.getMissingFields());
        replyMessage.getInvalidField().addAll(settings.getInvalidFields());

    }

    protected boolean isAuthoriseAndCaptureRequest(RequestMessage requestMessage) {
        return (requestMessage.getCcAuthService() != null && Boolean.valueOf(requestMessage.getCcAuthService().getRun())) ||
                (requestMessage.getCcCaptureService() != null &&
                        Boolean.valueOf(requestMessage.getCcCaptureService().getRun()));
    }

    protected boolean isDeleteProfileRequest(RequestMessage requestMessage) {
        return requestMessage.getPaySubscriptionDeleteService() != null &&
                Boolean.valueOf(requestMessage.getPaySubscriptionDeleteService().getRun());

    }

    protected BigInteger valueOf(String value) {
        try {
            DecimalFormat formatter = new DecimalFormat();
            formatter.setParseIntegerOnly(true);
            return BigInteger.valueOf((Long) formatter.parse(value));
        } catch (ParseException e) {
            return BigInteger.valueOf(0L);
        }
    }
}
