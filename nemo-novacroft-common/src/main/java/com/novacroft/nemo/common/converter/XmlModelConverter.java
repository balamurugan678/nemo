package com.novacroft.nemo.common.converter;

import java.net.URL;
import java.util.List;

/**
 * XML (as String) / model object converter
 */
public interface XmlModelConverter<MODEL> {
    MODEL convertXmlToModel(String xml);

    Object convertXmlToObject(String xml);

    /**
     * @return String representation of XML
     */
    String convertModelToXml(MODEL model);

    void setCastorMappingFileUrl(List<URL> castorMappingFileUrl);
}
