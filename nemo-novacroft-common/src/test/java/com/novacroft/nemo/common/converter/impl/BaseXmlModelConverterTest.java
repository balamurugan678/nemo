package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.SystemParameter;
import com.novacroft.nemo.test_support.SystemParameterTestUtil;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.junit.Test;

import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * BaseXmlModelConverter unit tests
 */
@SuppressWarnings("unchecked")
public class BaseXmlModelConverterTest {

    @Test(expected = AssertionError.class)
    public void getMappingShouldErrorWithNullMapping() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.getMapping();
    }

    @Test
    public void shouldGetMapping() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        List<URL> testMappingUrl = new ArrayList<URL>() {{
            add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
        }};
        converter.setCastorMappingFileUrl(testMappingUrl);
        Mapping result = converter.getMapping();
        assertNotNull(result);
    }

    @Test
    public void shouldGetMarshaller() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        List<URL> testMappingUrl = new ArrayList<URL>() {{
            add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
        }};
        converter.setCastorMappingFileUrl(testMappingUrl);
        StringWriter testWriter = new StringWriter();
        Marshaller result = converter.getMarshaller(testWriter);
        assertNotNull(result);
    }

    @Test
    public void shouldGetUnmarshaller() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        List<URL> testMappingUrl = new ArrayList<URL>() {{
            add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
        }};
        converter.setCastorMappingFileUrl(testMappingUrl);
        Unmarshaller result = converter.getUnmarshaller();
        assertNotNull(result);
    }

    @Test
    public void shouldConvertModelToXml() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        List<URL> testMappingUrl = new ArrayList<URL>() {{
            add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
        }};
        converter.setCastorMappingFileUrl(testMappingUrl);
        SystemParameter testModel = SystemParameterTestUtil.getTestSystemParameter1();
        String result = converter.convertModelToXml(testModel);
        String expectedResult =
                "<SystemParameter><Code>test-param-1</Code><Value>test-value-1</Value><Purpose>test-purpose-1</Purpose" +
                        "></SystemParameter>";
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldConvertXmlToModel() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        List<URL> testMappingUrl = new ArrayList<URL>() {{
            add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
        }};
        converter.setCastorMappingFileUrl(testMappingUrl);
        String testXml = "<SystemParameter><Code>test-param-1</Code><Value>test-value-1</Value>" +
                "<Purpose>test-purpose-1</Purpose></SystemParameter>";
        SystemParameter expectedResult = SystemParameterTestUtil.getTestSystemParameter1();
        SystemParameter result = converter.convertXmlToModel(testXml);
        assertEquals(expectedResult.getCode(), result.getCode());
        assertEquals(expectedResult.getValue(), result.getValue());
        assertEquals(expectedResult.getPurpose(), result.getPurpose());
    }

    @Test
    public void test() {
        BaseXmlModelConverter<Object> converter = mock(BaseXmlModelConverter.class);
        List<URL> testMappingUrl = new ArrayList<URL>() {
            {
                add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
            }
        };
        converter.setCastorMappingFileUrl(testMappingUrl);
        String testXML =
                "<CardInfoResponseV2><CardCapability>1</CardCapability><CardDeposit>0</CardDeposit><CardType>10</CardType" +
                        "><CCCLostStolenDateTime></CCCLostStolenDateTime><PrestigeID>100000015113</PrestigeID" +
                        "><PhotocardNumber></PhotocardNumber><Registered>0</Registered><PassengerType></PassengerType" +
                        "><AutoloadState>1</AutoloadState><DiscountEntitlement1></DiscountEntitlement1><DiscountExpiry1" +
                        "></DiscountExpiry1><DiscountEntitlement2></DiscountEntitlement2><DiscountExpiry2></DiscountExpiry2" +
                        "><DiscountEntitlement3></DiscountEntitlement3><DiscountExpiry3></DiscountExpiry3><HotlistReasons" +
                        "/><CardHolderDetails><Title></Title><FirstName></FirstName><MiddleInitial></MiddleInitial><LastName" +
                        "></LastName><DayTimePhoneNumber></DayTimePhoneNumber><HouseNumber></HouseNumber><HouseName" +
                        "></HouseName><Street></Street><Town></Town><County></County><Postcode></Postcode><EmailAddress" +
                        "></EmailAddress><Password></Password></CardHolderDetails></CardInfoResponseV2>";
        when(converter.convertXmlToModel(anyString())).thenReturn(new Object());
        Object result = converter.convertXmlToModel(testXML);
        assertNotNull(result);
    }

    //  fixme  @Test(expected = ApplicationServiceException.class)
    public void convertXmlToObjectThrowsApplicationServiceException() {
        BaseXmlModelConverter<Object> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        List<URL> testMappingUrl = new ArrayList<URL>() {
            {
                add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
            }
        };
        converter.setCastorMappingFileUrl(testMappingUrl);
        String testXML =
                "<CardInfoResponseV2><CardCapability>1</CardCapability><CardDeposit>0</CardDeposit><CardType>10</CardType" +
                        "><CCCLostStolenDateTime></CCCLostStolenDateTime><PrestigeID>100000015113</PrestigeID" +
                        "><PhotocardNumber></PhotocardNumber><Registered>0</Registered><PassengerType></PassengerType" +
                        "><AutoloadState>1</AutoloadState><DiscountEntitlement1></DiscountEntitlement1><DiscountExpiry1" +
                        "></DiscountExpiry1><DiscountEntitlement2></DiscountEntitlement2><DiscountExpiry2></DiscountExpiry2" +
                        "><DiscountEntitlement3></DiscountEntitlement3><DiscountExpiry3></DiscountExpiry3><HotlistReasons" +
                        "/><CardHolderDetails><Title></Title><FirstName></FirstName><MiddleInitial></MiddleInitial><LastName" +
                        "></LastName><DayTimePhoneNumber></DayTimePhoneNumber><HouseNumber></HouseNumber><HouseName" +
                        "></HouseName><Street></Street><Town></Town><County></County><Postcode></Postcode><EmailAddress" +
                        "></EmailAddress><Password></Password></CardHolderDetails></CardInfoResponseV2>";
        converter.convertXmlToModel(testXML);
    }

}
