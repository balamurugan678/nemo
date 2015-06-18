package com.novacroft.nemo.mock_payment_gateway.cyber_source.end_point;

import com.novacroft.cyber_source.web_service.model.transaction.ObjectFactory;
import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service.SoapTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

import static com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceNameSpace.RUN_TRANSACTION_SOAP_ACTION;

/**
 * End point for CyberSource transaction web service.
 */
@Endpoint
public class SoapEndPoint {
    protected static final String REQUEST_MESSAGE_PART = "requestMessage";

    @Autowired
    protected SoapTransactionService soapTransactionService;

    @PayloadRoot(localPart = REQUEST_MESSAGE_PART, namespace = RUN_TRANSACTION_SOAP_ACTION)
    @ResponsePayload
    public JAXBElement<ReplyMessage> processTransaction(@RequestPayload JAXBElement<RequestMessage> requestMessage) {
        return new ObjectFactory()
                .createReplyMessage(this.soapTransactionService.fabricateReplyMessage(requestMessage.getValue()));
    }
}
