package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.converter.impl.PaymentCardConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PaymentCardDAO;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PaymentCardDataServiceImplTest {
    private PaymentCardDataServiceImpl service;
    private PaymentCardDAO mockDAO;

    @Before
    public void setUp() {
        service = mock(PaymentCardDataServiceImpl.class, CALLS_REAL_METHODS);
        mockDAO = mock(PaymentCardDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new PaymentCardConverterImpl());
    }

    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }

    @Test
    public void findByCustomerIdShouldReturnList() {
        when(mockDAO.findByExample(any(PaymentCard.class))).thenReturn(Arrays.asList(getTestNotExpiredPaymentCard()));
        List<PaymentCardDTO> actualList = service.findByCustomerId(TEST_PAYMENT_CARD_ID_2);
        assertEquals(1, actualList.size());
        assertEquals(TEST_PAYMENT_CARD_ID_2, actualList.get(0).getId());
    }

    @Test
    public void findByCustomerIdIfNickNameUsedByAnotherCardShouldReturnNull() {
        when(this.mockDAO.findByQueryUniqueResult(anyString(), anyLong(), anyLong(), anyString())).thenReturn(null);
        PaymentCardDTO result = this.service
                .findByCustomerIdIfNickNameUsedByAnotherCard(CUSTOMER_ID_1, TEST_PAYMENT_CARD_ID_1, TEST_NICK_NAME_1);
        assertNull(result);
        verify(this.mockDAO).findByQueryUniqueResult(anyString(), anyLong(), anyLong(), anyString());
    }

    @Test
    public void findByCustomerIdIfNickNameUsedByAnotherCardShouldReturnCard() {
        when(this.mockDAO.findByQueryUniqueResult(anyString(), anyLong(), anyLong(), anyString()))
                .thenReturn(getTestNotExpiredPaymentCard());
        PaymentCardDTO result = this.service
                .findByCustomerIdIfNickNameUsedByAnotherCard(CUSTOMER_ID_1, TEST_PAYMENT_CARD_ID_1, TEST_NICK_NAME_1);
        assertNotNull(result);
        verify(this.mockDAO).findByQueryUniqueResult(anyString(), anyLong(), anyLong(), anyString());
    }
}
