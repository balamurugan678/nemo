package com.novacroft.nemo.tfl.common.service_access.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ObjectFactory;
import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceTransactionServiceAccess;
import com.novacroft.nemo.tfl.common.service_access.impl.BaseSoapServiceAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import javax.xml.bind.JAXBElement;

import static com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceNameSpace.RUN_TRANSACTION_SOAP_ACTION;

/**
 * CyberSource payment gateway transaction service access
 */
@Service("cyberSourceTransactionServiceAccess")
public class CyberSourceTransactionServiceAccessImpl extends BaseSoapServiceAccess
        implements CyberSourceTransactionServiceAccess {
    protected static final Logger logger = LoggerFactory.getLogger(CyberSourceTransactionServiceAccessImpl.class);

    @Autowired(required = false)
    protected WebServiceTemplate cyberSourceTransactionWebServiceTemplate;
    @Autowired
    protected NemoUserContext nemoUserContext;

    @Override
    public ReplyMessage runTransaction(RequestMessage requestMessage) {
        ObjectFactory objectFactory = new ObjectFactory();
        return runTransaction(objectFactory.createRequestMessage(requestMessage)).getValue();
    }

    @SuppressWarnings("unchecked")
    protected JAXBElement<ReplyMessage> runTransaction(JAXBElement<RequestMessage> requestMessage) {
        assert (this.cyberSourceTransactionWebServiceTemplate != null);
        try {
            return (JAXBElement<ReplyMessage>) callSoapServiceWithLogging(this.cyberSourceTransactionWebServiceTemplate,
                    RUN_TRANSACTION_SOAP_ACTION, requestMessage, WebServiceName.CYBER_SOURCE_RUN_TRANSACTION.code(),
                    getCustomerId(requestMessage.getValue()), this.nemoUserContext.getUserName());
        } catch (SoapFaultClientException | WebServiceIOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceNotAvailableException(e.getMessage(), e);
        }
    }

    protected Long getCustomerId(RequestMessage requestMessage) {
        return (requestMessage != null && requestMessage.getCustomerID() != null) ?
                Long.valueOf(requestMessage.getCustomerID()) : null;
    }
}
