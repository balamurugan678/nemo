package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.converter.impl.AlternativeZoneMappingConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AlternativeZoneMappingDAO;
import com.novacroft.nemo.tfl.common.data_service.impl.AlternativeZoneMappingDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.novacroft.nemo.test_support.AlternativeZoneMappingTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Alternative zone mapping data service unit tests.
 */
public class AlternativeZoneMappingDataServiceTest {
    static final Logger logger = LoggerFactory.getLogger(AlternativeZoneMappingDataServiceTest.class);

    private AlternativeZoneMappingDataService dataService;
    private AlternativeZoneMappingDAO mockDao;

    @Before
    public void setUp() {
        dataService = new AlternativeZoneMappingDataServiceImpl();
        mockDao = mock(AlternativeZoneMappingDAO.class);
        dataService.setConverter(new AlternativeZoneMappingConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByStartZoneAndEndZoneShouldFindAlternativeZoneMapping() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyInt(), anyInt())).thenReturn(getTestAlternativeZoneMapping1());

        AlternativeZoneMappingDTO resultsDTO = dataService.findByStartZoneAndEndZone(START_ZONE_1, END_ZONE_1);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), anyInt(), anyInt());
        assertEquals(ALTERNATIVE_START_ZONE_1, resultsDTO.getAlternativeStartZone());
        assertEquals(ALTERNATIVE_END_ZONE_1, resultsDTO.getAlternativeEndZone());
    }

    @Test
    public void findByStartZoneAndEndZoneShouldFindNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyInt(), anyInt())).thenReturn(null);

        AlternativeZoneMappingDTO resultsDTO = dataService.findByStartZoneAndEndZone(START_ZONE_1, END_ZONE_1);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), anyInt(), anyInt());
        assertNull(resultsDTO);
    }

}
