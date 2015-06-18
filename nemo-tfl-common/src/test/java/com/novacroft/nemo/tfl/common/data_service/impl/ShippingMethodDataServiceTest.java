package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.converter.impl.ShippingMethodConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ShippingMethodDAO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Shipping method data service unit tests.
 */
public class ShippingMethodDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(ShippingMethodDataServiceTest.class);

    private ShippingMethodDataServiceImpl dataService;
    private ShippingMethodDAO mockDao;

    @Before
    public void setUp() {
        dataService = new ShippingMethodDataServiceImpl();
        mockDao = mock(ShippingMethodDAO.class);

        dataService.setConverter(new ShippingMethodConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByShippingMethodNameShouldFindShippingMethod() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getTestShippingMethod1());

        ShippingMethodDTO resultsDTO = dataService.findByShippingMethodName(SHIPPING_METHOD_RECORDED_DELIVERY_1, false);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), anyString());

        assertEquals(SHIPPING_METHOD_RECORDED_DELIVERY_1, resultsDTO.getName());
        assertEquals(SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1, resultsDTO.getPrice());
    }

    @Test
    public void findByShippingMethodNameShouldFindNullWithNullShippingMethod() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(null);

        ShippingMethodDTO resultsDTO = dataService.findByShippingMethodName(SHIPPING_METHOD_RECORDED_DELIVERY_1, true);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), anyString());

        assertNull(resultsDTO);
    }

    @Test
    public void findByShippingMethodNameShouldFindNullWithNullShippingMethodName() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getTestShippingMethod1());

        ShippingMethodDTO resultsDTO = dataService.findByShippingMethodName(null, false);
        verify(mockDao, never()).findByQueryUniqueResult(anyString(), anyString());

        assertNull(resultsDTO);
    }

}
