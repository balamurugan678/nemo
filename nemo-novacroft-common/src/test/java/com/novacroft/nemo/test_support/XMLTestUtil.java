package com.novacroft.nemo.test_support;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTestUtil {

    
    public static  Document createDocument() {
        Document finalDoc = null;
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = dbf.newDocumentBuilder();
            finalDoc = builder.newDocument();
            Element root = finalDoc.createElement("CardResponse");
            finalDoc.appendChild(root);
        } catch (ParserConfigurationException e) {
            finalDoc = null;
        }
        return finalDoc;
    }
}
