package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.cyber_source.web_service.model.transaction.PurchaseTotals;
import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapDeleteTokenReplyConverter;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapDeleteTokenRequestConverter;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapReplyConverter;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapRequestConverter;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.cyber_source.CyberSourceTransactionDataServiceImpl;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceTransactionServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * CyberSourceTransactionDataServiceImpl unit tests
 */
public class CyberSourceTransactionDataServiceImplTest {
    private static final String MERCHANT_ID = "merchant-id";
    private CyberSourceTransactionDataService service;
    private CyberSourceTransactionServiceAccess mockCyberSourceTransactionServiceAccess;
    private CyberSourceSoapReplyConverter mockCyberSourceSoapReplyConverter;
    private CyberSourceSoapRequestConverter mockCyberSourceSoapRequestConverter;
    private RequestMessage mockRequestMessage;
    private ReplyMessage mockReplyMessage;
    private PurchaseTotals mockPurchaseTotals;
    private CyberSourceSoapReplyDTO mockCyberSourceSoapReplyDTO;
    private CyberSourceSoapRequestDTO mockCyberSourceSoapRequestDTO;
    private CyberSourceSoapDeleteTokenRequestConverter mockCyberSourceSoapDeleteTokenRequestConverter;
    private CyberSourceSoapDeleteTokenReplyConverter mockCyberSourceSoapDeleteTokenReplyConverter;

    @Before
    public void setUp() {
        this.service = mock(CyberSourceTransactionDataServiceImpl.class, CALLS_REAL_METHODS);
        setField(this.service, "merchantId", MERCHANT_ID);

        this.mockCyberSourceTransactionServiceAccess = mock(CyberSourceTransactionServiceAccess.class);
        setField(this.service, "cyberSourceTransactionServiceAccess", this.mockCyberSourceTransactionServiceAccess);

        this.mockCyberSourceSoapReplyConverter = mock(CyberSourceSoapReplyConverter.class);
        setField(this.service, "cyberSourceSoapReplyConverter", this.mockCyberSourceSoapReplyConverter);

        this.mockCyberSourceSoapRequestConverter = mock(CyberSourceSoapRequestConverter.class);
        setField(this.service, "cyberSourceSoapRequestConverter", this.mockCyberSourceSoapRequestConverter);

        this.mockCyberSourceSoapDeleteTokenRequestConverter = mock(CyberSourceSoapDeleteTokenRequestConverter.class);
        setField(this.service, "cyberSourceSoapDeleteTokenRequestConverter",
                this.mockCyberSourceSoapDeleteTokenRequestConverter);

        this.mockCyberSourceSoapDeleteTokenReplyConverter = mock(CyberSourceSoapDeleteTokenReplyConverter.class);
        setField(this.service, "cyberSourceSoapDeleteTokenReplyConverter", this.mockCyberSourceSoapDeleteTokenReplyConverter);

        this.mockRequestMessage = mock(RequestMessage.class);
        this.mockReplyMessage = mock(ReplyMessage.class);
        this.mockPurchaseTotals = mock(PurchaseTotals.class);
        this.mockCyberSourceSoapReplyDTO = mock(CyberSourceSoapReplyDTO.class);
        this.mockCyberSourceSoapRequestDTO = mock(CyberSourceSoapRequestDTO.class);
    }

    @Test
    public void shouldRunTransaction() {
        when(this.mockCyberSourceSoapRequestConverter.convertDtoToModel(any(CyberSourceSoapRequestDTO.class)))
                .thenReturn(this.mockRequestMessage);
        doNothing().when(this.mockRequestMessage).setMerchantID(anyString());
        when(this.mockRequestMessage.getPurchaseTotals()).thenReturn(this.mockPurchaseTotals);
        doNothing().when(this.mockPurchaseTotals).setCurrency(anyString());
        when(this.mockCyberSourceTransactionServiceAccess.runTransaction(any(RequestMessage.class)))
                .thenReturn(this.mockReplyMessage);
        when(this.mockCyberSourceSoapReplyConverter.convertModelToDto(any(ReplyMessage.class)))
                .thenReturn(this.mockCyberSourceSoapReplyDTO);

        this.service.runTransaction(this.mockCyberSourceSoapRequestDTO);

        verify(this.mockCyberSourceSoapRequestConverter).convertDtoToModel(any(CyberSourceSoapRequestDTO.class));
        verify(this.mockRequestMessage).setMerchantID(anyString());
        verify(this.mockRequestMessage).getPurchaseTotals();
        verify(this.mockPurchaseTotals).setCurrency(anyString());
        verify(this.mockCyberSourceTransactionServiceAccess).runTransaction(any(RequestMessage.class));
        verify(this.mockCyberSourceSoapReplyConverter).convertModelToDto(any(ReplyMessage.class));
    }

    @Test
    public void shouldDeleteToken() {
        when(this.mockCyberSourceSoapDeleteTokenRequestConverter.convertDtoToModel(any(CyberSourceSoapRequestDTO.class)))
                .thenReturn(this.mockRequestMessage);

        doNothing().when(this.mockRequestMessage).setMerchantID(anyString());
        doNothing().when(this.mockRequestMessage).setCustomerID(anyString());

        when(this.mockCyberSourceTransactionServiceAccess.runTransaction(any(RequestMessage.class)))
                .thenReturn(this.mockReplyMessage);

        when(this.mockCyberSourceSoapDeleteTokenReplyConverter.convertModelToDto(any(ReplyMessage.class)))
                .thenReturn(this.mockCyberSourceSoapReplyDTO);

        this.service.deleteToken(this.mockCyberSourceSoapRequestDTO);

        verify(this.mockRequestMessage).setMerchantID(anyString());
        verify(this.mockRequestMessage).setCustomerID(anyString());
        verify(this.mockCyberSourceTransactionServiceAccess).runTransaction(any(RequestMessage.class));
        verify(this.mockCyberSourceSoapDeleteTokenReplyConverter).convertModelToDto(any(ReplyMessage.class));
    }
}
