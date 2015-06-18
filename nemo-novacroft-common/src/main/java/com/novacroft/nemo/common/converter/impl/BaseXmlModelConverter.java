package com.novacroft.nemo.common.converter.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

/**
 * XML (as String) / model object converter implementation using Castor
 * 
 * <p>
 * Sub-classes / implementations should ensure that <code>castorMappingFileUrl</code> is populated.
 * </p>
 */
public abstract class BaseXmlModelConverter<MODEL> implements XmlModelConverter<MODEL> {
    static final Logger logger = LoggerFactory.getLogger(BaseXmlModelConverter.class);
    // Due to the castor mappings having the same map to element, 2 castor files have been added to work around this.
    static final String CARD_HOLDER_DETAILS = "CardHolderDetails";
    static final String CARD = "card";
    static final String PRE_PAY_TICKET = "PPT";
    static final String PRE_PAY_VALUE = "PPV";
    static final String ORIGINAL_REQUEST_SEQUENCE_NUMBER = "OriginalRequestSequenceNumber";
    static final String REMOVED_REQUESTED_SEQUENCE_NUMBER = "RemovedRequestSequenceNumber";
    protected List<URL> castorMappingFileUrl;
    static Map<String, String> unmarshallerTypes;
    

    static{
        unmarshallerTypes = new HashMap<String, String>();
        unmarshallerTypes.put(getTag(CARD_HOLDER_DETAILS), CARD);
        unmarshallerTypes.put(getTag(PRE_PAY_TICKET), PRE_PAY_TICKET);
        unmarshallerTypes.put(getTag(PRE_PAY_VALUE), PRE_PAY_VALUE);
        unmarshallerTypes.put(getTag(ORIGINAL_REQUEST_SEQUENCE_NUMBER), ORIGINAL_REQUEST_SEQUENCE_NUMBER);
        unmarshallerTypes.put(getTag(REMOVED_REQUESTED_SEQUENCE_NUMBER), REMOVED_REQUESTED_SEQUENCE_NUMBER);
    }
    
    private static String getTag(String value) {
        return "<".concat(value).concat(">");
    }

    @Override
    @SuppressWarnings("unchecked")
    public MODEL convertXmlToModel(String xml) {
        return (MODEL) convertXmlToObject(xml);
    }

    @Override
    public Object convertXmlToObject(String xml) {
        try {
            for (String key : unmarshallerTypes.keySet()) {
                if (xml.indexOf(key) > 1) {
                    return getUnmarshaller(unmarshallerTypes.get(key)).unmarshal(new StringReader(xml));
                }
            }
            return getUnmarshaller().unmarshal(new StringReader(xml));
        } catch (MarshalException | ValidationException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String convertModelToXml(MODEL model) {
        try {
            StringWriter response = new StringWriter();
            getMarshaller(response).marshal(model);
            return response.toString();
        } catch (MarshalException | ValidationException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected Unmarshaller getUnmarshaller() {
        try {
            return new Unmarshaller(getMapping());
        } catch (MappingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected Unmarshaller getUnmarshaller(String type) {
        try {
            return new Unmarshaller(getMapping(type));
        } catch (MappingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected Marshaller getMarshaller(StringWriter writer) {
        try {
            Marshaller marshaller = new Marshaller(writer);
            marshaller.setMapping(getMapping());
            marshaller.setSupressXMLDeclaration(true);
            return marshaller;
        } catch (IOException | MappingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected Mapping getMapping() {
        try {
            Mapping mapping = new Mapping();
            assert (this.castorMappingFileUrl != null);
            for (URL url : this.castorMappingFileUrl) {
                mapping.loadMapping(url);
            }
            return mapping;
        } catch (IOException | MappingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected Mapping getMapping(String type) {
        try {
            Mapping mapping = new Mapping();
            assert (this.castorMappingFileUrl != null);
            for (URL url : this.castorMappingFileUrl) {
                if (url.getPath().indexOf(type) > -1) {
                    mapping.loadMapping(url);
                }
            }
            return mapping;
        } catch (IOException | MappingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCastorMappingFileUrl(List<URL> castorMappingFileUrl) {
        this.castorMappingFileUrl = castorMappingFileUrl;
    }

}
