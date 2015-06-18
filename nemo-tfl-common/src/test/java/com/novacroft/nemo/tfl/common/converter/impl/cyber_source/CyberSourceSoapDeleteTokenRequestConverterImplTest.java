package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.PaymentCardTestUtil;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CyberSourceSoapDeleteTokenRequestConverterImplTest {
    private CyberSourceSoapDeleteTokenRequestConverterImpl converter;
    private CyberSourceSoapRequestDTO mockCyberSourceSoapRequestDTO;

    @Before
    public void setUp() {
        this.converter = mock(CyberSourceSoapDeleteTokenRequestConverterImpl.class, CALLS_REAL_METHODS);
        this.mockCyberSourceSoapRequestDTO = mock(CyberSourceSoapRequestDTO.class);
    }

    @Test
    public void shouldConvertDtoToModel() {
        when(this.mockCyberSourceSoapRequestDTO.getMerchantReferenceCode()).thenReturn(OrderTestUtil.ORDER_NUMBER.toString());
        when(this.mockCyberSourceSoapRequestDTO.getPaymentCardToken()).thenReturn(PaymentCardTestUtil.TEST_TOKEN_1);

        RequestMessage result = this.converter.convertDtoToModel(this.mockCyberSourceSoapRequestDTO);

        assertEquals(OrderTestUtil.ORDER_NUMBER.toString(), result.getMerchantReferenceCode());
        assertEquals(PaymentCardTestUtil.TEST_TOKEN_1, result.getRecurringSubscriptionInfo().getSubscriptionID());
        assertTrue(Boolean.valueOf(result.getPaySubscriptionDeleteService().getRun()));
    }
}