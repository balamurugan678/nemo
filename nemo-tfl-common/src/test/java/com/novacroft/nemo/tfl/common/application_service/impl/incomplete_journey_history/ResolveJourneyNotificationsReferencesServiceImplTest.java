package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.getTestIncompleteJourneyDTOWithNotificationDTO;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.test_support.PseudoTransactionTypeTestUtil.TRANSACTION_DISPLAY_DESCRIPTION_1;
import static com.novacroft.nemo.test_support.PseudoTransactionTypeTestUtil.getTestPseudoTransactionTypeLookupDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;

public class ResolveJourneyNotificationsReferencesServiceImplTest {
    private ResolveJourneyNotificationsReferencesServiceImpl service;
    private PseudoTransactionTypeLookupDataService mockTransactionTypeService;
    private LocationDataService mockLocationService;
    
    private InCompleteJourneyDTO testDTO;
    
    @Before
    public void setUp() {
        service = new ResolveJourneyNotificationsReferencesServiceImpl();
        mockTransactionTypeService = mock(PseudoTransactionTypeLookupDataService.class);
        mockLocationService = mock(LocationDataService.class);
        service.pseudoTransactionTypeLookupDataService = mockTransactionTypeService;
        service.locationDataService = mockLocationService;
        
        testDTO = getTestIncompleteJourneyDTOWithNotificationDTO();
    }
    
    @Test
    public void resolveReferencesShouldCallResolveJourneyReferences() {
        IncompleteJourneyMonthDTO dtoWith1Journey = new IncompleteJourneyMonthDTO();
        dtoWith1Journey.setIncompleteJourneyList(Arrays.asList(new InCompleteJourneyDTO()));
        
        ResolveJourneyNotificationsReferencesServiceImpl spyService = spy(service);
        doNothing().when(spyService).resolveLinkedTransactionType(any(InCompleteJourneyDTO.class));
        doNothing().when(spyService).resolveStation(any(InCompleteJourneyDTO.class));
        doNothing().when(spyService).resolveStatusMessage(any(InCompleteJourneyDTO.class));
        doNothing().when(spyService).resolveSSRSuitability(any(InCompleteJourneyDTO.class));
        
        spyService.resolveReferences(Arrays.asList(dtoWith1Journey));
        verify(spyService).resolveLinkedTransactionType(any(InCompleteJourneyDTO.class));
        verify(spyService).resolveStation(any(InCompleteJourneyDTO.class));
        verify(spyService).resolveStatusMessage(any(InCompleteJourneyDTO.class));
        verify(spyService).resolveSSRSuitability(any(InCompleteJourneyDTO.class));
    }
    
    @Test
    public void resolveLinkedTransactionTypeToEmptyString() {
        when(mockTransactionTypeService.findById(anyLong())).thenReturn(null);
        
        service.resolveLinkedTransactionType(testDTO);
        assertEquals(StringUtil.EMPTY_STRING, testDTO.getJourneyDisplayDTO().getPseudoTransactionTypeDisplayDescription());
    }
    
    @Test
    public void resolveLinkedTransactionTypeToDescription() {
        when(mockTransactionTypeService.findById(anyLong())).thenReturn(getTestPseudoTransactionTypeLookupDTO1());
        
        service.resolveLinkedTransactionType(testDTO);
        assertEquals(TRANSACTION_DISPLAY_DESCRIPTION_1, testDTO.getJourneyDisplayDTO().getPseudoTransactionTypeDisplayDescription());
    }
    
    @Test
    public void resolveStationShouldResolve() {
        when(mockLocationService.findById(anyLong())).thenReturn(getTestLocationDTO1());
        
        service.resolveStation(testDTO);
        assertEquals(LOCATION_NAME_1, testDTO.getJourneyDisplayDTO().getLinkedStation());
    }
    
    @Test
    public void resolveSSRSuitabilityShouldResolve() {
        service.resolveSSRSuitability(testDTO);
        assertTrue(testDTO.isAllowSSR());
    }
}
