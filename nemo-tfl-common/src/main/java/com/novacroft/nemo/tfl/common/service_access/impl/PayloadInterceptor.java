package com.novacroft.nemo.tfl.common.service_access.impl;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Spring web service client interceptor to capture request and response payloads
 */
public class PayloadInterceptor implements ClientInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(PayloadInterceptor.class);
    protected ByteArrayOutputStream requestPayload;
    protected ByteArrayOutputStream responsePayload;

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        extractPayload(messageContext.getRequest(), this.requestPayload);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        extractPayload(messageContext.getResponse(), this.responsePayload);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) {
        extractPayload(messageContext.getResponse(), this.responsePayload);
        return true;
    }

    protected void extractPayload(WebServiceMessage webServiceMessage, ByteArrayOutputStream payload) {
        try {
            webServiceMessage.writeTo(payload);
            payload.flush();
            logger.debug(payload.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    public void setResponsePayload(ByteArrayOutputStream responsePayload) {
        this.responsePayload = responsePayload;
    }

    public void setRequestPayload(ByteArrayOutputStream requestPayload) {
        this.requestPayload = requestPayload;
    }
}
