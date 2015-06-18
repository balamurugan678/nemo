package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.CompletedJourneyAuditReferencesService;
import com.novacroft.nemo.tfl.common.data_service.JourneyCompletedRefundItemDataService;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

public class CompletedJourneyAuditServiceImplTest {
    private CompletedJourneyAuditServiceImpl service;
    private JourneyCompletedRefundItemDataService mockDataService;
    private CompletedJourneyAuditReferencesService mockReferenceService;
    
    @Before
    public void setUp() {
        service = new CompletedJourneyAuditServiceImpl();
        mockDataService = mock(JourneyCompletedRefundItemDataService.class);
        mockReferenceService = mock(CompletedJourneyAuditReferencesService.class);
        service.journeyCompletedRefundItemDataService = mockDataService;
        service.completedJourneyAuditReferencesService = mockReferenceService;
    }
    
    @Test
    public void getAuditLogShouldReturnDTOList() {
        List<JourneyCompletedRefundItemDTO> mockDTOList = mock(List.class);
        when(mockDataService.findByCardId(anyLong())).thenReturn(mockDTOList);
        doNothing().when(mockReferenceService).resovleReferences(anyListOf(JourneyCompletedRefundItemDTO.class));
        assertEquals(mockDTOList, service.getAuditLog(CARD_ID_1));
        verify(mockDataService).findByCardId(CARD_ID_1);
        verify(mockReferenceService).resovleReferences(mockDTOList);
    }
}
