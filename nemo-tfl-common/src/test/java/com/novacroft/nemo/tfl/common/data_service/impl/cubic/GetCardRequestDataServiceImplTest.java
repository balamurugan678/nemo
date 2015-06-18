package com.novacroft.nemo.tfl.common.data_service.impl.cubic;

import com.novacroft.nemo.tfl.common.constant.ConfigurationFile;
import com.novacroft.nemo.tfl.common.converter.impl.CardInfoRequestV2ConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.CardInfoResponseV2ConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.RequestFailureConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.cubic.GetCardConverterImpl;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static com.novacroft.nemo.test_support.GetCardTestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * AutoLoadConfigurationChangePushToGateDataService unit tests
 */
public class GetCardRequestDataServiceImplTest {

    private GetCardRequestDataServiceImpl service;
    private Resource commonMappingResource;
    private Resource getCardMappingResource;
    private ApplicationContext mockApplicationContext;
    private GetCardConverterImpl getCardConverter;
    private CardInfoRequestV2ConverterImpl cardInfoRequestV2Converter;
    private CardInfoResponseV2ConverterImpl cardInfoResponseV2Converter;
    private RequestFailureConverterImpl requestFailureConverter;
    private CubicServiceAccess mockCubicServiceAccess;

    @Before
    public void setUp() {
        service = new GetCardRequestDataServiceImpl();

        commonMappingResource = new ClassPathResource("nemo-tfl-common-castor-mapping.xml");
        getCardMappingResource = new ClassPathResource("nemo-tfl-common-castor-mapping-getcard.xml");
        cardInfoRequestV2Converter = new CardInfoRequestV2ConverterImpl();
        getCardConverter = new GetCardConverterImpl();
        cardInfoResponseV2Converter = new CardInfoResponseV2ConverterImpl();
        requestFailureConverter = new RequestFailureConverterImpl();

        mockApplicationContext = mock(ApplicationContext.class);

        when(mockApplicationContext.getResource(anyString())).thenReturn(getCardMappingResource);
        when(mockApplicationContext.getResource(ConfigurationFile.TFL_COMMON_CASTOR_MAPPING)).thenReturn(commonMappingResource);
        setField(cardInfoRequestV2Converter, "applicationContext", mockApplicationContext);
        cardInfoRequestV2Converter.initialiseMapping();
        setField(cardInfoResponseV2Converter, "applicationContext", mockApplicationContext);
        cardInfoResponseV2Converter.initialiseMapping();
        setField(requestFailureConverter, "applicationContext", mockApplicationContext);
        requestFailureConverter.initialiseMapping();

        service.getCardConverter = getCardConverter;
        service.cardInfoRequestV2Converter = cardInfoRequestV2Converter;
        service.cardInfoResponseV2Converter = cardInfoResponseV2Converter;
        service.requestFailureConverter = requestFailureConverter;

        mockCubicServiceAccess = mock(CubicServiceAccess.class);
        service.cubicServiceAccess = mockCubicServiceAccess;
    }

    @Test
    public void isSuccessResponseShouldReturnTrue() {
        Object modelResponse = new CardInfoResponseV2();
        assertTrue(service.isSuccessResponse(modelResponse));
    }

    @Test
    public void isSuccessResponseShouldReturnFalse() {
        Object modelResponse = new RequestFailure();
        assertFalse(service.isSuccessResponse(modelResponse));
    }

    @Test
    public void getCardShouldReturnSuccessResponse() {
        when(mockCubicServiceAccess.callCubic(anyString())).thenReturn(new StringBuffer(getTestGetCardResposneXml1()));
        assertEquals(getTestSuccessCardInfoResponseV2DTO1(), service.getCard(new CardInfoRequestV2DTO()));
        verify(mockCubicServiceAccess, atLeastOnce()).callCubic(anyString());
    }

    @Test
    public void getCardShouldReturnFailResponse() {
        when(mockCubicServiceAccess.callCubic(anyString())).thenReturn(new StringBuffer(getTestRequestFailureXml1()));
        assertEquals(getTestFailureCardInfoResponseV2DTO2(), service.getCard(new CardInfoRequestV2DTO()));
        verify(mockCubicServiceAccess, atLeastOnce()).callCubic(anyString());
    }
}
