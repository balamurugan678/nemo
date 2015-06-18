package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.JourneyCompletedRefundItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.JourneyCompletedRefundItemDAO;
import com.novacroft.nemo.tfl.common.domain.JourneyCompletedRefundItem;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

public class JourneyCompletedRefundItemDataServiceImplTest {
    private static final Long TEST_CARD_ID = 100l;
    
    private JourneyCompletedRefundItemDataServiceImpl service;
    private JourneyCompletedRefundItemDAO mockDAO;
    
    @Before
    public void setUp() {
        service = new JourneyCompletedRefundItemDataServiceImpl();
        mockDAO = mock(JourneyCompletedRefundItemDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new JourneyCompletedRefundItemConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findByCustomerIdShouldReturnList() {
        JourneyCompletedRefundItem testItem = new JourneyCompletedRefundItem();
        testItem.setCardId(TEST_CARD_ID);
        when(mockDAO.findByExample(any(JourneyCompletedRefundItem.class))).thenReturn(Arrays.asList(testItem));
        
        List<JourneyCompletedRefundItemDTO> actualList = service.findByCardId(TEST_CARD_ID);
        assertEquals(1, actualList.size());
        assertEquals(TEST_CARD_ID, actualList.get(0).getCardId());
    }
}
