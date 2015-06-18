package com.novacroft.nemo.common.utils;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;

/**
 * XML Utility class.
 */
public class XMLUtil {
    protected static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);

    private XMLUtil() {

    }

    public static final String outputDocument(final Document doc) {
        return outputDocument(doc, false);
    }

    public static final String outputDocument(final Document doc, boolean noNewLines) {
        String output = "";
        try {
            final TransformerFactory tf = TransformerFactory.newInstance();
            final Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            final StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            if (noNewLines) {
                output = writer.getBuffer().toString().replaceAll("\n|\r", "");
            } else {
                output = writer.getBuffer().toString();
            }
        } catch (TransformerException e) {
            logger.error(String.format(CommonPrivateError.TRANSFORMING_XML.message(), doc), e);
        }
        return output;
    }

    public static final Document convertStringToXmlDocument(String xmlString) {
        Document doc = null;
        String xml = xmlString;
        try {
            xml = xml.trim();
            if (xml.lastIndexOf('=') > -1 && xml.lastIndexOf('=') == xml.length() - 1) {
                xml = xml.substring(0, xml.length() - 2);
            }
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            doc = docBuilder.parse(new InputSource(new StringReader(xml)));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            logger.error(String.format(CommonPrivateError.UNABLE_TO_CONVERT_STRING.message(), xml));
        }
        return doc;
    }

    public static final String getObjectValue(final String findValue, final Object main) {
        String foundValue = "";
        final Method[] methods = main.getClass().getMethods();
        for (Method method : methods) {
            if (isAccessor(method, findValue)) {
                foundValue = getValueFromMethod(method, main, findValue);
            }
        }
        return foundValue;
    }

    public static boolean isAccessor(Method method, String findValue) {
        boolean accessor = false;
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final String methodName = method.getName();
        if (parameterTypes.length == 0 &&
                method.getReturnType() != null &&
                (methodName.startsWith("get") || methodName.startsWith("is")) &&
                methodName.toLowerCase().indexOf(findValue.toLowerCase()) > -1) {
            accessor = true;
        }
        return accessor;
    }

    public static String getValueFromMethod(Method method, Object main, String findValue) {
        String foundValue = "";
        final Object object = main;
        try {
            Object result = method.invoke(object, new Object[]{});
            foundValue = (result != null) ? result.toString() : "";
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error(String.format(CommonPrivateError.ERROR_FINDING_METHOD.message(), findValue));
        }
        return foundValue;
    }

    public static final Element parentElement(final String tagName, final Document doc) {
        return doc.createElement(tagName);
    }

    public static final void createTextElement(final String tagName, final Object object, final Document doc,
                                               final Element element) {
        final String text = getObjectValue(tagName, object);
        createTextElement(tagName, text, doc, element);
    }

    public static final void createTextElement(final String tagName, final String value, final Document doc,
                                               final Element element) {
        final Element child = doc.createElement(tagName);
        if (isNotEmpty(value)) {
            final Text textNode = doc.createTextNode(value);
            child.appendChild(textNode);
        }
        element.appendChild(child);
    }

    public static final void createCDATAElement(final String tagName, final Object object, final Document doc,
                                                final Element element) {
        final String text = getObjectValue(tagName, object);
        createTextElement(tagName, text, doc, element);
    }

    public static final void createCDATAElement(final String tagName, final String value, final Document doc,
                                                final Element element) {
        final Element child = doc.createElement(tagName);
        CDATASection section = doc.createCDATASection(value);
        child.appendChild(section);
        element.appendChild(child);
    }

    public static final String getData(Node node) {
        Element e = (Element) node;
        NodeList childNodes = e.getChildNodes();
        Text text = (Text) childNodes.item(0);
        return (text != null) ? text.getData() : "";
    }

    public static final String getValueFromNodes(final NodeList list, String element) {
        String value = "";
        for (int i = 0; i < list.getLength(); i++) {
            final Node node = list.item(i);
            final String nodeName = node.getNodeName();
            if (element.equalsIgnoreCase(nodeName)) {
                value = getData(node);
                break;
            }
        }
        return value;
    }

    public static final String traverseNodes(final NodeList list, String element) {
        String foundValue = "";
        for (int i = 0; i < list.getLength(); i++) {
            final Node node = list.item(i);
            if (node.hasChildNodes()) {
                String value = getValueFromNodes(node.getChildNodes(), element);
                if (StringUtil.isNotEmpty(value)) {
                    foundValue = value;
                    break;
                }
                traverseNodes(node.getChildNodes(), element);
            }
        }
        return foundValue;
    }

    public static final String getValueFromDocument(Document document, String element) {
        Element rootElement = document.getDocumentElement();
        NodeList childNodes = rootElement.getChildNodes();
        return traverseNodes(childNodes, element);
    }

    public static final Document createDocument() {
        return createDocument("Default");
    }

    public static final Document createDocument(String parentNode) {
        Document document = null;
        try {
            final DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = documentBuildFactory.newDocumentBuilder();

            final Document finalDoc = builder.newDocument();
            Element rootElement = parentElement(parentNode, finalDoc);
            finalDoc.appendChild(rootElement);
            document = finalDoc;
        } catch (ParserConfigurationException e) {
            logger.error(String.format(CommonPrivateError.TRANSFORMING_XML.message(), parentNode), e);
        }
        return document;
    }

    public static final Element getRootElement(Document document) {
        return document.getDocumentElement();
    }

    public static final boolean checkRootElement(Document document, String elementName) {
        return document.getDocumentElement().getNodeName().toUpperCase().equals(elementName);
    }

    public static final String prettyPrint(String xmlAsString) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer writer = new StringWriter();
            transformer.transform(new DOMSource(convertStringToXmlDocument(xmlAsString)), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }
}
