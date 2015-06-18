package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAdHocLoadSettlementList1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestAdHocLoadSettlementList2;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.converter.impl.AdHocLoadSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AdHocLoadSettlementDAO;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

/**
 * AdHocLoadSettlementDataService unit tests
 */
public class AdHocLoadSettlementDataServiceTest {

    AdHocLoadSettlementDataServiceImpl service;
    AdHocLoadSettlementConverterImpl converter;
    AdHocLoadSettlementDAO dao;

    @Before
    public void setUp() {
        service = new AdHocLoadSettlementDataServiceImpl();
        converter = new AdHocLoadSettlementConverterImpl();
        dao = mock(AdHocLoadSettlementDAO.class);

        service.setConverter(converter);
        service.setDao(dao);
    }

    @Test
    public void findByRequestSequenceNumberAndCardNumberShouldReturnOneRecord() {
        when(dao.findByQuery(anyString(), anyString(), anyString())).thenReturn(getTestAdHocLoadSettlementList1());
        AdHocLoadSettlementDTO result =
                service.findByRequestSequenceNumberAndCardNumber(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
        assertNotNull(result);
    }

    @Test
    public void findByRequestSequenceNumberAndCardNumberShouldReturnNull() {
        when(dao.findByQuery(anyString(), anyString(), anyString())).thenReturn(Collections.EMPTY_LIST);

        assertNull(service.findByRequestSequenceNumberAndCardNumber(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test(expected = DataServiceException.class)
    public void findByRequestSequenceNumberAndCardNumberShouldError() {
        when(dao.findByQuery(anyString(), anyString(), anyString())).thenReturn(getTestAdHocLoadSettlementList2());

        service.findByRequestSequenceNumberAndCardNumber(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
    }

    @Test
    public void findByOrderId(){
        when(dao.findByExample(any(AdHocLoadSettlement.class))).thenReturn(getTestAdHocLoadSettlementList1());
        assertNotNull(service.findByOrderId(OrderTestUtil.ORDER_ID));
    }

    @Test
    public void findByOrderIdReturnsNull() {
        when(dao.findByExample(any(AdHocLoadSettlement.class))).thenReturn(new ArrayList<AdHocLoadSettlement>());
        assertNull(service.findByOrderId(OrderTestUtil.ORDER_ID));
    }

    @Test
    public void getNewEntity() {
        assertNotNull(service.getNewEntity());
    }
}
