package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ServiceAccessException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.GetCardTestUtil;
import com.novacroft.nemo.tfl.common.converter.AutoLoadChangeConverter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * AutoLoadConfigurationChangePushToGateDataService unit tests
 */
public class AutoLoadConfigurationChangePushToGateDataServiceImplTest {
    private AutoLoadConfigurationChangePushToGateDataServiceImpl service;
    private XmlModelConverter<AutoLoadRequest> mockAutoLoadRequestConverter;
    private AutoLoadChangeConverter mockAutoLoadChangeConverter;
    private XmlModelConverter<AutoLoadResponse> mockAutoLoadResponseConverter;
    private XmlModelConverter<RequestFailure> mockRequestFailureConverter;
    private ServiceCallLogService mockServiceCallLogService;
    private CustomerDataService mockCustomerDataService;
    private CubicServiceAccess mockCubicServiceAccess;

    private NemoUserContext mockNemoUserContext;

    private CustomerDTO mockCustomerDTO;
    private AutoLoadChangeRequestDTO mockAutoLoadChangeRequestDTO;
    private AutoLoadRequest mockAutoLoadRequest;
    private ServiceCallLogDTO mockServiceCallLogDTO;
    private AutoLoadResponse mockAutoLoadResponse;
    private AutoLoadChangeResponseDTO mockAutoLoadChangeResponseDTO;
    private RequestFailure mockRequestFailure;

    @Before
    public void setUp() {
        this.service = mock(AutoLoadConfigurationChangePushToGateDataServiceImpl.class);

        this.mockAutoLoadRequestConverter = mock(XmlModelConverter.class);
        this.service.autoLoadRequestConverter = this.mockAutoLoadRequestConverter;

        this.mockAutoLoadChangeConverter = mock(AutoLoadChangeConverter.class);
        this.service.autoLoadChangeConverter = this.mockAutoLoadChangeConverter;

        this.mockAutoLoadResponseConverter = mock(XmlModelConverter.class);
        this.service.autoLoadResponseConverter = this.mockAutoLoadResponseConverter;

        this.mockRequestFailureConverter = mock(XmlModelConverter.class);
        this.service.requestFailureConverter = this.mockRequestFailureConverter;

        this.mockServiceCallLogService = mock(ServiceCallLogService.class);
        this.service.serviceCallLogService = this.mockServiceCallLogService;

        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.service.customerDataService = this.mockCustomerDataService;

        this.mockCubicServiceAccess = mock(CubicServiceAccess.class);
        this.service.cubicServiceAccess = this.mockCubicServiceAccess;

        this.mockNemoUserContext = mock(NemoUserContext.class);
        this.service.nemoUserContext = this.mockNemoUserContext;

        this.mockCustomerDTO = mock(CustomerDTO.class);
        this.mockAutoLoadChangeRequestDTO = mock(AutoLoadChangeRequestDTO.class);
        this.mockAutoLoadRequest = mock(AutoLoadRequest.class);
        this.mockServiceCallLogDTO = mock(ServiceCallLogDTO.class);
        this.mockAutoLoadResponse = mock(AutoLoadResponse.class);
        this.mockAutoLoadChangeResponseDTO = mock(AutoLoadChangeResponseDTO.class);
        this.mockRequestFailure = mock(RequestFailure.class);
    }

    @Test
    public void isSuccessResponseShouldReturnTrue() {
        when(this.service.isSuccessResponse(anyObject())).thenCallRealMethod();
        Object modelResponse = new AutoLoadResponse();
        assertTrue(this.service.isSuccessResponse(modelResponse));
    }

    @Test
    public void isSuccessResponseShouldReturnFalse() {
        when(this.service.isSuccessResponse(anyObject())).thenCallRealMethod();
        Object modelResponse = new RequestFailure();
        assertFalse(this.service.isSuccessResponse(modelResponse));
    }

    @Test
    public void shouldGetCustomerId() {
        when(this.service.getCustomerId(anyString())).thenCallRealMethod();
        when(this.mockCustomerDataService.findByCardNumber(anyString())).thenReturn(this.mockCustomerDTO);
        when(this.mockCustomerDTO.getId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);

        this.service.getCustomerId(CardTestUtil.OYSTER_NUMBER_1);

        verify(this.mockCustomerDataService).findByCardNumber(anyString());
        verify(this.mockCustomerDTO).getId();
    }

    @Test(expected = ServiceAccessException.class)
    public void getCustomerIdShouldError() {
        when(this.service.getCustomerId(anyString())).thenCallRealMethod();
        when(this.mockCustomerDataService.findByCardNumber(anyString())).thenReturn(null);

        this.service.getCustomerId(CardTestUtil.OYSTER_NUMBER_1);
    }

    @Test
    public void changeAutoLoadConfigurationRequestShouldReturnSuccessResponse() {
        changeAutoLoadConfigurationRequestCommonSetUp();
        when(this.service.isSuccessResponse(anyObject())).thenReturn(Boolean.TRUE);
        when(this.mockCubicServiceAccess.callCubic(anyString()))
                .thenReturn(new StringBuffer(GetCardTestUtil.getTestGetCardResposneXml1()));

        this.service.changeAutoLoadConfigurationRequest(this.mockAutoLoadChangeRequestDTO);

        changeAutoLoadConfigurationRequestCommonVerify();
        verify(this.mockAutoLoadChangeConverter).convertToDto(any(AutoLoadResponse.class));
        verify(this.mockRequestFailureConverter, never()).convertXmlToModel(anyString());
        verify(this.mockAutoLoadChangeConverter, never()).convertToDto(any(RequestFailure.class));
    }

    @Test
    public void changeAutoLoadConfigurationRequestShouldReturnFailResponse() {
        changeAutoLoadConfigurationRequestCommonSetUp();
        when(this.service.isSuccessResponse(anyObject())).thenReturn(Boolean.FALSE);
        when(this.mockCubicServiceAccess.callCubic(anyString()))
                .thenReturn(new StringBuffer(GetCardTestUtil.getTestGetCardResposneXml1()));

        this.service.changeAutoLoadConfigurationRequest(this.mockAutoLoadChangeRequestDTO);

        changeAutoLoadConfigurationRequestCommonVerify();
        verify(this.mockAutoLoadChangeConverter, never()).convertToDto(any(AutoLoadResponse.class));
        verify(this.mockRequestFailureConverter).convertXmlToModel(anyString());
        verify(this.mockAutoLoadChangeConverter).convertToDto(any(RequestFailure.class));
    }

    private void changeAutoLoadConfigurationRequestCommonSetUp() {
        when(this.service.changeAutoLoadConfigurationRequest(any(AutoLoadChangeRequestDTO.class))).thenCallRealMethod();
        when(this.service.getCustomerId(anyString())).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);

        when(this.mockAutoLoadChangeConverter.convertToModel(any(AutoLoadChangeRequestDTO.class)))
                .thenReturn(this.mockAutoLoadRequest);
        when(this.mockAutoLoadChangeConverter.convertToDto(any(AutoLoadResponse.class)))
                .thenReturn(this.mockAutoLoadChangeResponseDTO);
        when(this.mockAutoLoadChangeConverter.convertToDto(any(RequestFailure.class)))
                .thenReturn(this.mockAutoLoadChangeResponseDTO);

        when(this.mockAutoLoadRequestConverter.convertModelToXml(any(AutoLoadRequest.class)))
                .thenReturn(StringUtil.EMPTY_STRING);

        when(this.mockAutoLoadResponseConverter.convertXmlToObject(anyString())).thenReturn(this.mockAutoLoadResponse);

        when(this.mockNemoUserContext.getUserName()).thenReturn(CustomerTestUtil.USERNAME_1);

        when(this.mockRequestFailureConverter.convertXmlToModel(anyString())).thenReturn(this.mockRequestFailure);

        when(this.mockServiceCallLogService.initialiseCallLog(anyString(), anyString(), anyLong()))
                .thenReturn(this.mockServiceCallLogDTO);
        doNothing().when(this.mockServiceCallLogService)
                .finaliseCallLog(any(ServiceCallLogDTO.class), anyString(), anyString());
    }

    private void changeAutoLoadConfigurationRequestCommonVerify() {
        verify(this.service).isSuccessResponse(anyObject());
        verify(this.service).getCustomerId(anyString());
        verify(this.mockAutoLoadChangeConverter).convertToModel(any(AutoLoadChangeRequestDTO.class));
        verify(this.mockAutoLoadRequestConverter).convertModelToXml(any(AutoLoadRequest.class));
        verify(this.mockAutoLoadResponseConverter).convertXmlToObject(anyString());
        verify(this.mockNemoUserContext).getUserName();
        verify(this.mockServiceCallLogService).initialiseCallLog(anyString(), anyString(), anyLong());
        verify(this.mockServiceCallLogService).finaliseCallLog(any(ServiceCallLogDTO.class), anyString(), anyString());
    }
}
