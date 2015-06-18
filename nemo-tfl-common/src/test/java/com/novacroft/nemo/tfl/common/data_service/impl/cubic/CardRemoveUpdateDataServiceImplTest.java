package com.novacroft.nemo.tfl.common.data_service.impl.cubic;

import static com.novacroft.nemo.test_support.CubicTestUtil.getFailureResponseXML1;
import static com.novacroft.nemo.test_support.CubicTestUtil.getRequestXML1;
import static com.novacroft.nemo.test_support.CubicTestUtil.getResponseXML1;
import static com.novacroft.nemo.test_support.CubicTestUtil.getTestRequestDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.converter.impl.BaseXmlModelConverter;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.support.NemoUserContextImpl;
import com.novacroft.nemo.test_support.CubicTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.CardRemoveUpdateService;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.converter.cubic.CardRemoveUpdateConverter;
import com.novacroft.nemo.tfl.common.converter.impl.cubic.CardRemoveUpdateConverterImpl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public class CardRemoveUpdateDataServiceImplTest {
    
    private CardRemoveUpdateDataServiceImpl dataService;

    private XmlModelConverter<CardRemoveUpdateResponse> responseConverter;
    private CardRemoveUpdateService cardRemoveUpdateService;
    private XmlModelConverter<RequestFailure> requestFailureConverter;
    private XmlModelConverter<CardRemoveUpdateRequest> requestConverter;
    private CardRemoveUpdateConverter converter;
    private ServiceCallLogService serviceCallLogService;
    private NemoUserContext nemoUserContext;
    private CustomerDataService customerDataService;
    private ArrayList<URL> castorMappingUrl;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        castorMappingUrl = new ArrayList<URL>() {
            {
                add(this.getClass().getClassLoader().getResource("test-castor-mapping.xml"));
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-PPT.xml"));
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-PPV.xml"));
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-OriginalRequestSequenceNumber.xml"));
                add(this.getClass().getClassLoader().getResource("test-castor-mapping-cardremove-update-response-RemovedRequestSequenceNumber.xml"));
            }
        };

        dataService = new CardRemoveUpdateDataServiceImpl();
        responseConverter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        responseConverter.setCastorMappingFileUrl(castorMappingUrl);
        requestFailureConverter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        requestFailureConverter.setCastorMappingFileUrl(castorMappingUrl);
        requestConverter = mock(BaseXmlModelConverter.class, CALLS_REAL_METHODS);
        requestConverter.setCastorMappingFileUrl(castorMappingUrl);
        converter = new CardRemoveUpdateConverterImpl();
        cardRemoveUpdateService = mock(CardRemoveUpdateService.class);
        serviceCallLogService = mock(ServiceCallLogService.class);
        nemoUserContext = new NemoUserContextImpl();
        customerDataService = mock(CustomerDataService.class);

        dataService.requestFailureConverter = requestFailureConverter;
        dataService.cardRemoveUpdateResponseConverter = responseConverter;
        dataService.cardRemoveUpdateService = cardRemoveUpdateService;
        dataService.cardRemoveUpdateRequestConverter = requestConverter;
        dataService.cardRemoveUpdateConverter = converter;
        dataService.serviceCallLogService = serviceCallLogService;
        dataService.customerDataService = customerDataService;
        dataService.nemoUserContext = nemoUserContext;
    }

    @Test
    public void removePendingUpdateFailure() {
        when(cardRemoveUpdateService.removePendingUpdate(anyString())).thenReturn(new StringBuffer(getFailureResponseXML1()));
        when(customerDataService.findByCardNumber(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(
                        serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(), nemoUserContext.getUserName(),
                                        CustomerTestUtil.getTestCustomerDTO1().getId())).thenReturn(null);
        doNothing().when(serviceCallLogService).finaliseCallLog(null, getRequestXML1(), getFailureResponseXML1());

        CardUpdateResponseDTO responseDTO = dataService.removePendingUpdate(getTestRequestDTO1());

        assertNotNull(responseDTO);
        assertEquals(responseDTO.getErrorCode().intValue(), CubicTestUtil.ERROR_CODE.intValue());
        assertNull(responseDTO.getRequestSequenceNumber());

    }

    @Test
    public void removePendingUpdateSuccess() {
        when(cardRemoveUpdateService.removePendingUpdate(anyString())).thenReturn(new StringBuffer(getResponseXML1()));
        when(customerDataService.findByCardNumber(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(
                        serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(), nemoUserContext.getUserName(),
                                        CustomerTestUtil.getTestCustomerDTO1().getId())).thenReturn(null);
        doNothing().when(serviceCallLogService).finaliseCallLog(null, getRequestXML1(), getResponseXML1());

        CardUpdateResponseDTO responseDTO = dataService.removePendingUpdate(getTestRequestDTO1());

        assertNotNull(responseDTO);
        assertEquals(responseDTO.getRequestSequenceNumber().intValue(), CubicTestUtil.REMOVED_REQUEST_SEQUENCE_NUMBER.intValue());
    }

    @Test
    public void shouldLogCubicRequestAndResponse() {
        when(cardRemoveUpdateService.removePendingUpdate(anyString())).thenReturn(new StringBuffer(getResponseXML1()));
        when(customerDataService.findByCardNumber(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(
                        serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(), nemoUserContext.getUserName(),
                                        CustomerTestUtil.getTestCustomerDTO1().getId())).thenReturn(null);
        doNothing().when(serviceCallLogService).finaliseCallLog(null, getRequestXML1(), getResponseXML1());

        CardUpdateResponseDTO responseDTO = dataService.removePendingUpdate(getTestRequestDTO1());

        verify(serviceCallLogService).initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(), nemoUserContext.getUserName(),
                        CustomerTestUtil.getTestCustomerDTO1().getId());
        verify(serviceCallLogService).finaliseCallLog(null, getRequestXML1(), getResponseXML1());
    }

    @Test
    public void shouldLogCubicRequestAndResponseForNullCustomer() {
        when(cardRemoveUpdateService.removePendingUpdate(anyString())).thenReturn(new StringBuffer(getResponseXML1()));
        when(customerDataService.findByCardNumber(anyString())).thenReturn(null);
        when(
                        serviceCallLogService.initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(), nemoUserContext.getUserName(),
                                        CustomerTestUtil.getTestCustomerDTO1().getId())).thenReturn(null);
        doNothing().when(serviceCallLogService).finaliseCallLog(null, getRequestXML1(), getResponseXML1());

        CardUpdateResponseDTO responseDTO = dataService.removePendingUpdate(getTestRequestDTO1());

        verify(serviceCallLogService).initialiseCallLog(WebServiceName.CUBIC_REMOVE_PENDING_UPDATE.code(), nemoUserContext.getUserName(), null);
        verify(serviceCallLogService).finaliseCallLog(null, getRequestXML1(), getResponseXML1());
    }
}
