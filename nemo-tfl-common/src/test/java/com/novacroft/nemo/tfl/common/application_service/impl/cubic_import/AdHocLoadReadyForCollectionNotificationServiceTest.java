package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAdHocLoadSettlementDTO1;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_STRING_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Locale;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * RefundReadyForCollectionNotificationService unit tests
 */
public class AdHocLoadReadyForCollectionNotificationServiceTest {

    @Test
    public void hasRefundBeenRequestedShouldReturnFalseWithNoSettlement() {
        AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(null);

        AdHocLoadReadyForCollectionNotificationServiceImpl service = new AdHocLoadReadyForCollectionNotificationServiceImpl();
        service.adHocLoadSettlementDataService = mockAdHocLoadSettlementDataService;

        assertFalse(service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalseWithSettlementNotHavingRequestedStatus() {
        AdHocLoadSettlementDTO testSettlement = getTestAdHocLoadSettlementDTO1();
        testSettlement.setStatus(SettlementStatus.CANCELLED.code());

        AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(testSettlement);

        AdHocLoadReadyForCollectionNotificationServiceImpl service = new AdHocLoadReadyForCollectionNotificationServiceImpl();
        service.adHocLoadSettlementDataService = mockAdHocLoadSettlementDataService;

        assertFalse(service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnTrueWithSettlementHavingRequestedStatus() {
        AdHocLoadSettlementDTO testSettlement = getTestAdHocLoadSettlementDTO1();
        testSettlement.setStatus(SettlementStatus.READY_FOR_PICK_UP.code());
        testSettlement.setModifiedUserId("tfl-batch");

        AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString()))
                .thenReturn(testSettlement);

        AdHocLoadReadyForCollectionNotificationServiceImpl service = new AdHocLoadReadyForCollectionNotificationServiceImpl();
        service.adHocLoadSettlementDataService = mockAdHocLoadSettlementDataService;

        assertTrue(service.hasAdHocLoadBeenRequested(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test
    public void shouldBuildAdditionalInformation() {
        AdHocLoadReadyForCollectionNotificationServiceImpl service = new AdHocLoadReadyForCollectionNotificationServiceImpl();
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        setField(service, "applicationContext", mockApplicationContext);
        when(mockApplicationContext.getMessage(anyString(), (Object[])any(), any(Locale.class))).thenReturn("Item ready for pick up notification email sent: ");
        final String expectedResult = "Item ready for pick up notification email sent: amount [20.00]; pick up at [Mornington Crescent]; Oyster card number [100000000001]; expires on [21/08/2013]";
        String result = service.buildAdditionalInformation(getTestAdHocLoadSettlementDTO1(), OYSTER_NUMBER_1, LOCATION_NAME_1);
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateEvent() {
        ApplicationEventService mockApplicationEventService = mock(ApplicationEventService.class);
        doNothing().when(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());

        AdHocLoadReadyForCollectionNotificationServiceImpl service = new AdHocLoadReadyForCollectionNotificationServiceImpl();
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        setField(service, "applicationContext", mockApplicationContext);
        when(mockApplicationContext.getMessage(anyString(), (Object[])any(), any(Locale.class))).thenReturn("Item ready for pick up notification email sent: ");
     
        service.applicationEventService = mockApplicationEventService;

        service.createEvent(getTestAdHocLoadSettlementDTO1(), OYSTER_NUMBER_1, LOCATION_NAME_1, getTestOrderDTO1());

        verify(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
    }

    @Test
    public void shouldSendEmail() {
        SystemParameterService mockSystemParameterService = mock(SystemParameterService.class);
        when(mockSystemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()))
                .thenReturn(BASE_URI_STRING_1);

        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(getTestCustomerDTO1());

        EmailMessageService mockAdHocLoadReadyForCollectionEmailService = mock(EmailMessageService.class);
        doNothing().when(mockAdHocLoadReadyForCollectionEmailService).sendMessage(any(EmailArgumentsDTO.class));

        AdHocLoadReadyForCollectionNotificationServiceImpl service = new AdHocLoadReadyForCollectionNotificationServiceImpl();
        service.systemParameterService = mockSystemParameterService;
        service.adHocLoadReadyForCollectionEmailService = mockAdHocLoadReadyForCollectionEmailService;
        service.customerDataService = mockCustomerDataService;

        service.sendEmail(getTestAdHocLoadSettlementDTO1(), OYSTER_NUMBER_1, LOCATION_NAME_1, getTestCustomerDTO1(), getTestOrderDTO1());

        verify(mockSystemParameterService).getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code());
        verify(mockAdHocLoadReadyForCollectionEmailService).sendMessage(any(EmailArgumentsDTO.class));
    }

    
}
