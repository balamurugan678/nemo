package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.IncompleteJourneyTestUtil.getTestIncompleteJourneyNotificationDTO;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyDayDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.fare_aggregation_converter.FareAggregationEngineResponseConverter;
import com.novacroft.nemo.tfl.common.service_access.FareAggregationEngineServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterCharge;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.GetRecalculatedOysterChargeResponse;

public class FareAggregationDataServiceImplTest {
    private static final Integer MAGIC_NUMBER_61 = 61;
    private static final Integer MAGIC_NUMBER_62 = 62;
    
    private FareAggregationDataServiceImpl service;
    private FareAggregationEngineServiceAccess mockAccess;
    private FareAggregationEngineResponseConverter mockConverter;
    
    private JourneyDayDTO testJourneyDayDTO;
    
    @Before
    public void setUp() {
        service = new FareAggregationDataServiceImpl();
        mockAccess = mock(FareAggregationEngineServiceAccess.class);
        mockConverter = mock(FareAggregationEngineResponseConverter.class);
        
        service.fareAggregationEngineServiceAccess = mockAccess;
        service.fareAggregationEngineResponseConverter = mockConverter;
        
        testJourneyDayDTO = getTestJourneyDayDTO1();
    }
    
    @Test
    public void shouldGetRecalculatedOysterCharge() {
        GetRecalculatedOysterChargeResponse mockResponse = mock(GetRecalculatedOysterChargeResponse.class);
        when(mockAccess.getRecalculatedOysterCharge(any(GetRecalculatedOysterCharge.class))).thenReturn(mockResponse);
        
        RecalculatedOysterChargeResponseDTO mockDTO = mock(RecalculatedOysterChargeResponseDTO.class);
        when(mockConverter.convertModelToDto(any(GetRecalculatedOysterChargeResponse.class))).thenReturn(mockDTO);
        
        assertEquals(mockDTO, service.getRecalculatedOysterCharge(testJourneyDayDTO, OYSTER_NUMBER_1));
        verify(mockConverter).convertModelToDto(mockResponse);
    }
    
    @Test
    public void getMissingTransactionTypeShouldReturn62() {
        assertEquals(MAGIC_NUMBER_62, 
                        service.getMissingTransactionType(createIncompleteJourneyDTOWithTransactionType(MAGIC_NUMBER_61)));
    }
    
    @Test
    public void getMissingTransactionTypeShouldReturn61() {
        assertEquals(MAGIC_NUMBER_61, 
                        service.getMissingTransactionType(createIncompleteJourneyDTOWithTransactionType(MAGIC_NUMBER_62)));
    }
    
    private InCompleteJourneyDTO createIncompleteJourneyDTOWithTransactionType(Integer transactionType) {
        IncompleteJourneyNotificationDTO notificateDTO = getTestIncompleteJourneyNotificationDTO();
        notificateDTO.setLinkedTransactionType(transactionType);
        
        InCompleteJourneyDTO testDTO = new InCompleteJourneyDTO();
        testDTO.setJourneyNotificationDTO(notificateDTO);
        return testDTO;
    }
}
