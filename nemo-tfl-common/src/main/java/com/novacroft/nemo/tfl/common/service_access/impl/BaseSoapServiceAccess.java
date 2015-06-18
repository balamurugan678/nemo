package com.novacroft.nemo.tfl.common.service_access.impl;

import static com.novacroft.nemo.common.utils.OysterCardNumberUtil.getFullCardNumber;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ServiceAccessException;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Base class for SOAP web service access that provides service call logging to DB
 */
public abstract class BaseSoapServiceAccess {
    protected static final Logger logger = LoggerFactory.getLogger(BaseSoapServiceAccess.class);
    @Autowired
    protected ServiceCallLogService serviceCallLogService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CustomerDataService customerDataService;

    protected Object callSoapServiceWithLogging(WebServiceTemplate webServiceTemplate, final String soapAction, Object request,
                                                String serviceName, Long customerId, String userId) {
        ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
        setOutputStreamsOnPayloadInterceptor(webServiceTemplate.getInterceptors(), requestOutputStream, responseOutputStream);
        ServiceCallLogDTO serviceCallLogDTO = this.serviceCallLogService.initialiseCallLog(serviceName, userId, customerId);
        try {
            return webServiceTemplate.marshalSendAndReceive(request, new WebServiceMessageCallback() {
                public void doWithMessage(WebServiceMessage message) {
                    ((SoapMessage) message).setSoapAction(soapAction);
                }
            });
        } finally {
            this.serviceCallLogService
                    .finaliseCallLog(serviceCallLogDTO, requestOutputStream.toString(), responseOutputStream.toString());
            try {
                requestOutputStream.close();
                responseOutputStream.close();
            } catch (IOException e) {
                // log and ignore
                logger.error(e.getMessage(), e);
            }
        }
		
    }

    protected void setOnlineTimeout(WebServiceTemplate webServiceTemplate) {
        setTimeout(webServiceTemplate, this.systemParameterService
                .getIntegerParameterValue(SystemParameterCode.WEB_SERVICE_ONLINE_ACCESS_TIMEOUT.code()));
    }

    protected void setBatchTimeout(WebServiceTemplate webServiceTemplate) {
        setTimeout(webServiceTemplate, this.systemParameterService
                .getIntegerParameterValue(SystemParameterCode.WEB_SERVICE_BATCH_ACCESS_TIMEOUT.code()));
    }

    protected void setTimeout(WebServiceTemplate webServiceTemplate, int timeoutInMilliSeconds) {
        WebServiceMessageSender[] messageSenders = webServiceTemplate.getMessageSenders();
        for (int i = 0; i < messageSenders.length; i++) {
            if (messageSenders[i] instanceof HttpComponentsMessageSender) {
                HttpComponentsMessageSender httpComponentsMessageSender = (HttpComponentsMessageSender) messageSenders[i];
                httpComponentsMessageSender.setReadTimeout(timeoutInMilliSeconds);
            } else {
                logger.error(String.format(PrivateError.SET_TIMEOUT_ERROR_FOR_MESSAGE_SENDER.message(),
                        messageSenders[i].getClass().getName()));
            }
        }
    }

    protected void setOutputStreamsOnPayloadInterceptor(ClientInterceptor[] interceptors,
                                                        ByteArrayOutputStream requestOutputStream,
                                                        ByteArrayOutputStream responseOutputStream) {
        for (ClientInterceptor interceptor : interceptors) {
            if (interceptor instanceof PayloadInterceptor) {
                PayloadInterceptor payloadInterceptor = (PayloadInterceptor) interceptor;
                payloadInterceptor.setRequestPayload(requestOutputStream);
                payloadInterceptor.setResponsePayload(responseOutputStream);
            }
        }
    }
    

    protected Long getCustomerId(Long nineDigitCardNumber) {
        CustomerDTO customerDTO = this.customerDataService.findByCardNumber(getFullCardNumber(nineDigitCardNumber));
        if (customerDTO == null) {
            throw new ServiceAccessException(
                    String.format(PrivateError.FOUND_NO_RECORDS.message(), "customerDataService.findByCardNumber",
                            nineDigitCardNumber)
            );
        }
        return customerDTO.getId();
    }
}
