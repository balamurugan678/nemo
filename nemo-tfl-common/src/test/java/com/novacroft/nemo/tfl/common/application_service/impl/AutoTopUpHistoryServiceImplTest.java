package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AutoTopUpHistoryServiceTestUtil.getAutoTopUpHistoryItems;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

/**
 * AutoTopUpConfigurationService unit tests
 */
public class AutoTopUpHistoryServiceImplTest {
    private AutoTopUpHistoryServiceImpl service;
    private ItemDataService mockItemDataService;
    
    @Before
    public void setUp() {
        service = new AutoTopUpHistoryServiceImpl();        
        mockItemDataService = mock(ItemDataService.class);        
        service.itemDataService = mockItemDataService;
    }
    
    @Test
    public void shouldGetAutoTopUpHistoryForOysterCard() {
        when(mockItemDataService.findAllAutoTopUpsForCard(any(Long.class))).thenReturn(getAutoTopUpHistoryItems());        
        List<AutoTopUpHistoryItemDTO> result = service.getAutoTopUpHistoryForOysterCard(any(Long.class));
        assertNotNull(result);
        verify(mockItemDataService, atLeastOnce()).findAllAutoTopUpsForCard(any(Long.class));
    }

}
