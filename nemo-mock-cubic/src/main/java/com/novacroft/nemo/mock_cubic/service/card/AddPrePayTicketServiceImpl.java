package com.novacroft.nemo.mock_cubic.service.card;

import static com.novacroft.nemo.common.utils.XMLUtil.createTextElement;
import static com.novacroft.nemo.common.utils.XMLUtil.parentElement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.novacroft.nemo.mock_cubic.command.AddCardPrePayTicketCmd;

@Transactional
@Service(value = "addPrePayTicketService")
public class AddPrePayTicketServiceImpl implements AddPrePayTicketService {
    protected static final Logger logger = LoggerFactory.getLogger(AddPrePayTicketServiceImpl.class);
    
    /**
     * 
     * <CardUpdateResponse>
<PrestigeID>123456789</PrestigeID>
<RequestSequenceNumber>1234</RequestSequenceNumber>
<LocationInfo>
<PickupLocation>340</PickupLocation>
<AvailableSlots>100</AvailableSlots>
</LocationInfo>
</CardUpdateResponse>
     */
    @Override
    public Document createPrePayTicketResponse(AddCardPrePayTicketCmd cmd) {
        if (StringUtils.isEmpty(cmd.getErrorDescription())) {
            return createCardUpdateResponseXML(cmd);
        } else {
            return createErrorCardUpdateResponseXML(cmd);
        }
    }

    protected Document createCardUpdateResponseXML(AddCardPrePayTicketCmd cmd) {
        Document doc = null;
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = dbf.newDocumentBuilder();
            final Document finalDoc = builder.newDocument();
            final Element rootElement = parentElement("CardUpdateResponse", finalDoc);
            finalDoc.appendChild(rootElement);
            createTextElement("PrestigeID", cmd, finalDoc, rootElement);
            createTextElement("RequestSequenceNumber", cmd, finalDoc, rootElement);
            createLocationInfo(cmd, finalDoc, rootElement);
            doc = finalDoc;
        } catch (ParserConfigurationException e) {
            logger.error("Parser Error");
        }
        return doc;
    }

    protected void createLocationInfo(final AddCardPrePayTicketCmd cmd, final Document doc, final Element rootElement) {
        final Element parent = parentElement("LocationInfo", doc);
        createTextElement("PickupLocation", cmd, doc, parent);
        createTextElement("AvailableSlots", cmd, doc, parent);
        rootElement.appendChild(parent);
    }
    

    protected Document createErrorCardUpdateResponseXML(AddCardPrePayTicketCmd cmd) {
        Document doc = null;
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = dbf.newDocumentBuilder();
            final Document finalDoc = builder.newDocument();
            final Element rootElement = parentElement("RequestFailure", finalDoc);
            finalDoc.appendChild(rootElement);
            createTextElement("ErrorCode", cmd, finalDoc, rootElement);
            createTextElement("ErrorDescription", cmd, finalDoc, rootElement);
            doc = finalDoc;
        } catch (ParserConfigurationException e) {
            logger.error("Parser Error");
        }
        return doc;
    }

}
