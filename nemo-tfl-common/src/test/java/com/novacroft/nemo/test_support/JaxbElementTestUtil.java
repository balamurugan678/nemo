package com.novacroft.nemo.test_support;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * JAXBElement test utilities
 */
public final class JaxbElementTestUtil {
    private JaxbElementTestUtil() {
    }

    public static final JAXBElement<Integer> getIntegerJaxbElement(Integer value) {
        return new JAXBElement<Integer>(new QName("test"), Integer.class, value);
    }

    public static final JAXBElement<String> getStringJaxbElement(String value) {
        return new JAXBElement<String>(new QName("test"), String.class, value);
    }

    public static final JAXBElement<XMLGregorianCalendar> getXMLGregorianCalendarJaxbElement(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(new QName("test"), XMLGregorianCalendar.class, value);
    }
}
