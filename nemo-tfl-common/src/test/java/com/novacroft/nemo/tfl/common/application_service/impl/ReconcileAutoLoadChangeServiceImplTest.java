package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;

import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_AUTO_LOAD_STATE_2;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_AUTO_LOAD_STATE_3;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.*;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * ReconcileAutoLoadChangeService unit tests
 */
public class ReconcileAutoLoadChangeServiceImplTest {

    @Test
    public void shouldBuildAdditionalInformation() {
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        String expectedResult = "status [OK]; failure reason code [99]; Oyster card number [100000000001]";
        assertEquals(expectedResult,
                service.buildAdditionalInformation(OYSTER_NUMBER_1, ACTION_STATUS_OK, FAILURE_REASON_CODE_1));
    }

    @Test
    public void shouldBuildAdditionalInformationWithAutoLoadConfigurationMisMatch() {
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        String expectedResult =
                "new configuration [3] does not match requested configuration [2]; status [OK]; failure reason code [99]; " +
                        "Oyster card number [100000000001]";
        assertEquals(expectedResult,
                service.buildAdditionalInformation(OYSTER_NUMBER_1, ACTION_STATUS_OK, FAILURE_REASON_CODE_1,
                        TEST_AUTO_LOAD_STATE_2, TEST_AUTO_LOAD_STATE_3));
    }

    @Test
    public void shouldCreateEvent() {
        OrderDataService mockOrderDataService = mock(OrderDataService.class);
        when(mockOrderDataService.findById(anyLong())).thenReturn(OrderTestUtil.getTestOrderDTO1());
        
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(getTestCustomerDTO1());

        ApplicationEventService mockApplicationEventService = mock(ApplicationEventService.class);
        doNothing().when(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());

        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        service.orderDataService = mockOrderDataService;
        service.applicationEventService = mockApplicationEventService;
        service.customerDataService = mockCustomerDataService;

        service.createEvent(getTestAutoLoadChangeSettlementDTO1(), EVENT_INFORMATION_1);

        verify(mockOrderDataService).findById(anyLong());
        verify(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }

    @Test
    public void resolveStatusShouldReturnComplete() {
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        assertEquals(SettlementStatus.COMPLETE, service.resolveStatus(ACTION_STATUS_OK));
    }

    @Test
    public void resolveStatusShouldReturnFailed() {
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        assertEquals(SettlementStatus.FAILED, service.resolveStatus(ACTION_STATUS_FAILED));
    }

    @Test
    public void shouldUpdateSettlement() {
        AutoLoadChangeSettlementDataService mockAutoLoadChangeSettlementDataService =
                mock(AutoLoadChangeSettlementDataService.class);
        when(mockAutoLoadChangeSettlementDataService.createOrUpdate(any(AutoLoadChangeSettlementDTO.class)))
                .thenReturn(getTestAutoLoadChangeSettlementDTO1());

        ReconcileAutoLoadChangeServiceImpl service = mock(ReconcileAutoLoadChangeServiceImpl.class);
        doCallRealMethod().when(service).updateSettlement(any(AutoLoadChangeSettlementDTO.class), anyString());
        when(service.resolveStatus(anyString())).thenReturn(SettlementStatus.COMPLETE);
        service.autoLoadChangeSettlementDataService = mockAutoLoadChangeSettlementDataService;

        service.updateSettlement(getTestAutoLoadChangeSettlementDTO1(), ACTION_STATUS_OK);
        verify(service).resolveStatus(anyString());
        verify(mockAutoLoadChangeSettlementDataService).createOrUpdate(any(AutoLoadChangeSettlementDTO.class));
    }

    @Test(expected = ApplicationServiceException.class)
    public void getSettlementShouldError() {
        AutoLoadChangeSettlementDataService mockAutoLoadChangeSettlementDataService =
                mock(AutoLoadChangeSettlementDataService.class);
        when(mockAutoLoadChangeSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(null);
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        service.autoLoadChangeSettlementDataService = mockAutoLoadChangeSettlementDataService;
        service.getSettlement(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
    }

    @Test
    public void shouldGetSettlement() {
        AutoLoadChangeSettlementDataService mockAutoLoadChangeSettlementDataService =
                mock(AutoLoadChangeSettlementDataService.class);
        when(mockAutoLoadChangeSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(getTestAutoLoadChangeSettlementDTO1());
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        service.autoLoadChangeSettlementDataService = mockAutoLoadChangeSettlementDataService;
        AutoLoadChangeSettlementDTO result = service.getSettlement(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
        assertEquals(REQUEST_SEQUENCE_NUMBER, result.getRequestSequenceNumber());
        verify(mockAutoLoadChangeSettlementDataService).findByRequestSequenceNumberAndCardNumber(anyInt(), anyString());
    }

    @Test
    public void shouldReconcileChange() {
        ReconcileAutoLoadChangeServiceImpl service = mock(ReconcileAutoLoadChangeServiceImpl.class);
        doCallRealMethod().when(service).reconcileChange(anyInt(), anyString(), anyInt(), anyString(), anyInt());
        when(service.getSettlement(anyInt(), anyString())).thenReturn(getTestAutoLoadChangeSettlementDTO1());
        when(service.isNewAutoLoadConfigurationSameAsRequested(anyInt(), anyInt())).thenReturn(Boolean.TRUE);
        doNothing().when(service).updateSettlement(any(AutoLoadChangeSettlementDTO.class), anyString());
        doNothing().when(service).createEvent(any(AutoLoadChangeSettlementDTO.class), anyString());
        when(service.buildAdditionalInformation(anyString(), anyString(), anyInt())).thenReturn(EVENT_INFORMATION_1);

        service.reconcileChange(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1, TEST_AUTO_LOAD_STATE_2, ACTION_STATUS_OK,
                FAILURE_REASON_CODE_1);

        verify(service).getSettlement(anyInt(), anyString());
        verify(service).updateSettlement(any(AutoLoadChangeSettlementDTO.class), anyString());
        verify(service).createEvent(any(AutoLoadChangeSettlementDTO.class), anyString());
        verify(service).buildAdditionalInformation(anyString(), anyString(), anyInt());
    }

    @Test
    public void isNewAutoLoadConfigurationSameAsRequestedShouldReturnTrue() {
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        assertTrue(service.isNewAutoLoadConfigurationSameAsRequested(TEST_AUTO_LOAD_STATE_2, TEST_AUTO_LOAD_STATE_2));
    }

    @Test
    public void isNewAutoLoadConfigurationSameAsRequestedShouldReturnFalse() {
        ReconcileAutoLoadChangeServiceImpl service = new ReconcileAutoLoadChangeServiceImpl();
        assertFalse(service.isNewAutoLoadConfigurationSameAsRequested(TEST_AUTO_LOAD_STATE_2, TEST_AUTO_LOAD_STATE_3));
    }
}
