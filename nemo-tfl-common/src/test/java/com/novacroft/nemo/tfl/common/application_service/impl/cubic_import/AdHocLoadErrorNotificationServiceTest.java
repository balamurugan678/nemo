package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAdHocLoadSettlementDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Locale;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * RefundErrorNotificationService unit tests
 */
public class AdHocLoadErrorNotificationServiceTest {
    
    @Test
    public void shouldBuildAdditionalInformation() {
        AdHocLoadErrorNotificationServiceImpl service = new AdHocLoadErrorNotificationServiceImpl();
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        setField(service, "applicationContext", mockApplicationContext);
        when(mockApplicationContext.getMessage(anyString(), (Object[])any(), any(Locale.class))).thenReturn("Item pick up error notification email sent: ");
        final String expectedResult =
                "Item pick up error notification email sent: amount [20.00]; pick up at [Mornington Crescent]; Oyster card number [100000000001]";
        String result = service.buildAdditionalInformation(getTestAdHocLoadSettlementDTO1(), OYSTER_NUMBER_1, LOCATION_NAME_1);
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void shouldGetEmailMessageService() {
        EmailMessageService mockAdHocLoadErrorEmailService = mock(EmailMessageService.class);
        AdHocLoadErrorNotificationServiceImpl service = mock(AdHocLoadErrorNotificationServiceImpl.class);
        when(service.getEmailMessageService()).thenCallRealMethod();
        when(service.getEmailMessageService()).thenReturn(mockAdHocLoadErrorEmailService);
        EmailMessageService result = service.getEmailMessageService();
        assertNotNull(result);
    }

    @Test
    public void shouldNotifyCustomer() {
        AdHocLoadErrorNotificationServiceImpl service = mock(AdHocLoadErrorNotificationServiceImpl.class);
        doCallRealMethod().when(service).notifyCustomer(anyInt(), anyString());
        when(service.getSettlement(anyInt(), anyString())).thenReturn(getTestAdHocLoadSettlementDTO1());
        when(service.lookupLocationName(anyInt())).thenReturn(LOCATION_NAME_1);
        doNothing().when(service)
                .sendEmail(any(AdHocLoadSettlementDTO.class), anyString(), anyString(), any(CustomerDTO.class), any(OrderDTO.class));
        doNothing().when(service).createEvent(any(AdHocLoadSettlementDTO.class), anyString(), anyString(), any(OrderDTO.class));
        when(service.getOrder(anyLong())).thenReturn(getTestOrderDTO1());
        when(service.getCustomer(anyLong())).thenReturn(getTestCustomerDTO1());

        service.notifyCustomer(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);

        verify(service).getSettlement(anyInt(), anyString());
        verify(service).lookupLocationName(anyInt());
        verify(service).sendEmail(any(AdHocLoadSettlementDTO.class), anyString(), anyString(), any(CustomerDTO.class), any(OrderDTO.class));
        verify(service).createEvent(any(AdHocLoadSettlementDTO.class), anyString(), anyString(), any(OrderDTO.class));
    }
}
