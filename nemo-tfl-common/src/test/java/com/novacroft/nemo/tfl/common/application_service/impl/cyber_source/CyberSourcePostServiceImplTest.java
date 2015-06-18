package com.novacroft.nemo.tfl.common.application_service.impl.cyber_source;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSecurityService;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

/**
 * CyberSourcePostService unit tests
 */
public class CyberSourcePostServiceImplTest {
    private static final String TEST_IP_ADDRESS = "192.168.0.0";
    private CyberSourcePostServiceImpl service;
    private SystemParameterService mockSystemParameterService;
    private CyberSourceSecurityService mockCyberSourceSecurityService;
    private OrderDTO mockOrderDTO;
    private PaymentCardSettlementDTO mockPaymentCardSettlementDTO;

    @Before
    public void setUp() {
        this.service = mock(CyberSourcePostServiceImpl.class, CALLS_REAL_METHODS);
        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.service.systemParameterService = mockSystemParameterService;
        this.mockCyberSourceSecurityService = mock(CyberSourceSecurityService.class);
        this.service.cyberSourceSecurityService = this.mockCyberSourceSecurityService;

        this.mockOrderDTO = mock(OrderDTO.class);
        this.mockPaymentCardSettlementDTO = mock(PaymentCardSettlementDTO.class);
    }

    @Test
    public void shouldPreparePaymentRequestDataWithCreateToken() {
        when(this.mockSystemParameterService.getParameterValue(anyString())).thenReturn("");
        doNothing().when(this.mockCyberSourceSecurityService).signPostRequest(any(CyberSourcePostRequestDTO.class));

        CyberSourcePostRequestDTO result = this.service
                .preparePaymentRequestData(this.mockOrderDTO, this.mockPaymentCardSettlementDTO, true, true, TEST_IP_ADDRESS);

        assertEquals(CyberSourcePostTransactionType.SALE_AND_CREATE_PAYMENT_TOKEN.code(), result.getTransactionType());

        verify(this.mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(this.mockCyberSourceSecurityService).signPostRequest(any(CyberSourcePostRequestDTO.class));
    }

    @Test
    public void shouldPreparePaymentRequestDataWithoutCreateToken() {
        when(this.mockSystemParameterService.getParameterValue(anyString())).thenReturn("");
        doNothing().when(this.mockCyberSourceSecurityService).signPostRequest(any(CyberSourcePostRequestDTO.class));

        CyberSourcePostRequestDTO result = this.service
                .preparePaymentRequestData(this.mockOrderDTO, this.mockPaymentCardSettlementDTO, false, true, TEST_IP_ADDRESS);

        assertNotEquals(CyberSourcePostTransactionType.SALE_AND_CREATE_PAYMENT_TOKEN.code(), result.getTransactionType());

        verify(this.mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(this.mockCyberSourceSecurityService).signPostRequest(any(CyberSourcePostRequestDTO.class));
    }
}
