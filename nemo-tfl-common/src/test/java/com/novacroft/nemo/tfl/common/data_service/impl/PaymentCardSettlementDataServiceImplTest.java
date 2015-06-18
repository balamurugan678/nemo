package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static com.novacroft.nemo.test_support.SettlementTestUtil.PAYMENT_CARD_ID_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlement1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.PaymentCardSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PaymentCardSettlementDAO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;

public class PaymentCardSettlementDataServiceImplTest {
    private PaymentCardSettlementDataServiceImpl service;
    private PaymentCardSettlementDAO mockDAO;
    
    @Before
    public void setUp() {
        service = new PaymentCardSettlementDataServiceImpl();
        mockDAO = mock(PaymentCardSettlementDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new PaymentCardSettlementConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findByOrderIdShouldReturnEmptyList() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(null);
        assertTrue(service.findByOrderId(ORDER_ID).isEmpty());
    }
    
    @Test
    public void findByOrderIdShouldReturnDTOList() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestPaymentCardSettlement1()));
        List<PaymentCardSettlementDTO> actualList = service.findByOrderId(ORDER_ID);
        assertEquals(1, actualList.size());
        assertEquals(PAYMENT_CARD_ID_1, actualList.get(0).getPaymentCardId());
    }
}
