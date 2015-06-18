package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseXmlModelConverter;
import com.novacroft.nemo.common.domain.SystemParameter;
import com.novacroft.nemo.test_support.CardUpdateRequestTestUtil;
import com.novacroft.nemo.test_support.SystemParameterTestUtil;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

/**
 * BaseXmlModelConverter unit tests
 *
 * fixme: Test is crashing Sonar Jenkins job
 */
@SuppressWarnings("unchecked")
public class XmlModelConverterTest {

    private List<URL> testMappingUrl;

    @Before
    public void setUp() {
        testMappingUrl = new ArrayList<URL>() {
            {
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-PPT.xml"));
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-PPV.xml"));
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-OriginalRequestSequenceNumber.xml"));
//  fixme  add(this.getClass().getClassLoader().getResource
// ("test-castor-mapping-cardremove-update-response-RemovedRequestSequenceNumber.xml"));
            }
        };
    }

    @Test
    public void shouldConvertXmlToModel() {
        BaseXmlModelConverter<SystemParameter> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
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
    public void shouldConvertXmlToModelToCardUpdatePrePayValue() {
        BaseXmlModelConverter<CardUpdatePrePayValueRequest> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        String testXml =
                "<CardUpdateRequest><RealTimeFlag>N</RealTimeFlag><PrestigeID>123456789</PrestigeID><Action>ADD</Action>" +
                        "<PPV><PrePayValue>1000</PrePayValue><Currency>0</Currency></PPV><PaymentMethod>32</PaymentMethod>" +
                        "<PickupLocation>100</PickupLocation><OriginatorInfo><UserID>Test</UserID><Password>Test</Password" +
                        "></OriginatorInfo>" +
                        "</CardUpdateRequest>";
        CardUpdatePrePayValueRequest expectedResult = CardUpdateRequestTestUtil.getCardUpdatePrePayValueRequest1();
        CardUpdatePrePayValueRequest result = converter.convertXmlToModel(testXml);
        assertEquals(expectedResult.getRealTimeFlag(), result.getRealTimeFlag());
        assertEquals(expectedResult.getPrestigeId(), result.getPrestigeId());
    }

    @Test
    public void shouldConvertXmlToModelToCardUpdatePrePayTicket() {
        BaseXmlModelConverter<CardUpdatePrePayTicketRequest> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        String testXml =
                "<CardUpdateRequest><RealTimeFlag>N</RealTimeFlag><PrestigeID>123456789</PrestigeID><Action>ADD</Action>" +
                        "<PPT><ProductCode>123</ProductCode><StartDate>10/10/2002</StartDate><ExpiryDate>11/10/2002" +
                        "</ExpiryDate><ProductPrice>360</ProductPrice>" +
                        "<Currency>0</Currency></PPT><PaymentMethod>32</PaymentMethod>" +
                        "<PickupLocation>100</PickupLocation><OriginatorInfo><UserID>Test</UserID><Password>Test</Password" +
                        "></OriginatorInfo>" +
                        "</CardUpdateRequest>";
        CardUpdatePrePayTicketRequest expectedResult = CardUpdateRequestTestUtil.getCardUpdatePrePayTicketRequest1();
        CardUpdatePrePayTicketRequest result = converter.convertXmlToModel(testXml);
        assertEquals(expectedResult.getRealTimeFlag(), result.getRealTimeFlag());
        assertEquals(expectedResult.getPrestigeId(), result.getPrestigeId());
    }

    @Test
    public void shouldConvertXmlToModelToCardRemoveUpdateRequest() {
        BaseXmlModelConverter<CardRemoveUpdateRequest> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        String testXml = "<CardUpdateRequest><PrestigeID>123456789</PrestigeID><Action>REMOVE</Action>" +
                "<OriginalRequestSequenceNumber>5</OriginalRequestSequenceNumber><OriginatorInfo><UserID>Test</UserID" +
                "><Password>Test</Password></OriginatorInfo>" +
                "</CardUpdateRequest>";
        CardRemoveUpdateRequest expectedResult = CardUpdateRequestTestUtil.getCardRemoveUpdateRequest1();
        CardRemoveUpdateRequest result = converter.convertXmlToModel(testXml);
        assertEquals(expectedResult.getOriginalRequestSequenceNumber(), result.getOriginalRequestSequenceNumber());
        assertEquals(expectedResult.getPrestigeId(), result.getPrestigeId());
    }

    //  fixme  @Test
    public void shouldConvertXmlToModelToCardRemoveUpdateResponse() {
        BaseXmlModelConverter<CardRemoveUpdateResponse> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        CardRemoveUpdateResponse result =
                converter.convertXmlToModel(CardUpdateRequestTestUtil.getTestCardUpdateRemoveResponse());
        assertEquals(CardUpdateRequestTestUtil.PRESTIGE_ID, result.getPrestigeId());
        assertEquals(CardUpdateRequestTestUtil.REQUEST_SEQUENCE_NUMBER, result.getRequestSequenceNumber());
        assertEquals(CardUpdateRequestTestUtil.REMOVED_REQUEST_SEQUENCE_NUMBER.intValue(),
                result.getRemovedRequestSequenceNumber().intValue());
    }

    //  fixme  @Test
    public void shouldConvertCardUpdatePrePayTicketRequestToXml() {
        BaseXmlModelConverter<CardUpdatePrePayTicketRequest> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        String xmlResult = converter.convertModelToXml(CardUpdateRequestTestUtil.getCardUpdatePrePayTicketRequest1());
        assertEquals(CardUpdateRequestTestUtil.getTestRequestPrePayTicketXml1(), xmlResult);
    }

    //  fixme  @Test(expected = ApplicationServiceException.class)
    public void shouldThrowExceptionConvertXmlToModel() {
        BaseXmlModelConverter<CardRemoveUpdateResponse> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        converter.convertXmlToModel(CardUpdateRequestTestUtil.getRequestFailureXml1());
    }

    //  fixme  @Test(expected = ApplicationServiceException.class)
    public void shouldThrowExceptionConvertXmlToObject() {
        BaseXmlModelConverter<Object> converter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        converter.setCastorMappingFileUrl(testMappingUrl);
        converter.convertXmlToObject(CardUpdateRequestTestUtil.getRequestFailureXml1());
    }
}
