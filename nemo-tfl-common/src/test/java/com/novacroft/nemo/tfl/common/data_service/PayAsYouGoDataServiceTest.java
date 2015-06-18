package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.converter.impl.PayAsYouGoConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PayAsYouGoDAO;
import com.novacroft.nemo.tfl.common.data_service.impl.PayAsYouGoDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.novacroft.nemo.test_support.PayAsYouGoTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Pay as you go data service unit tests.
 */
public class PayAsYouGoDataServiceTest {
    static final Logger logger = LoggerFactory.getLogger(PayAsYouGoDataServiceTest.class);

    private PayAsYouGoDataService dataService;
    private PayAsYouGoDAO mockDao;

    @Before
    public void setUp() {
        dataService = new PayAsYouGoDataServiceImpl();
        mockDao = mock(PayAsYouGoDAO.class);
        dataService.setConverter(new PayAsYouGoConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByTicketPriceShouldFindPayAsYouGoItem() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyInt())).thenReturn(getTestPayAsYouGo1());

        PayAsYouGoDTO resultsDTO = dataService.findByTicketPrice(TICKET_PAY_AS_YOU_GO_PRICE_1);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), anyInt());
        assertEquals(PAY_AS_YOU_GO_NAME_1, resultsDTO.getPayAsYouGoName());
    }

    @Test
    public void findByTicketPriceShouldFindOtherPayAsYouGoItem() {
        when(mockDao.findByQueryUniqueResult(anyString(), eq(TICKET_PAY_AS_YOU_GO_PRICE_1))).thenReturn(null);
        when(mockDao.findByQueryUniqueResult(anyString(), eq(TICKET_PAY_AS_YOU_GO_PRICE_ZERO))).thenReturn(getTestPayAsYouGo1());

        PayAsYouGoDTO resultsDTO = dataService.findByTicketPrice(TICKET_PAY_AS_YOU_GO_PRICE_1);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), eq(TICKET_PAY_AS_YOU_GO_PRICE_1));
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), eq(TICKET_PAY_AS_YOU_GO_PRICE_ZERO));
        assertEquals(PAY_AS_YOU_GO_NAME_1, resultsDTO.getPayAsYouGoName());
    }

}
