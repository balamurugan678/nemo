package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.converter.impl.CardPreferencesConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardPreferencesDAO;
import com.novacroft.nemo.tfl.common.domain.CardPreferences;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;

import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.CARD_PREFERENCES_ID_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.EMAIL_FREQUENCY_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.STATION_ID_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferences1;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardPreferencesDataServiceImplTest {
    private CardPreferencesDataServiceImpl service;
    private CardPreferencesDAO mockDAO;
    
    @Before
    public void setUp() {
        mockDAO = mock(CardPreferencesDAO.class);
        service = new CardPreferencesDataServiceImpl();
        service.setDao(mockDAO);
        service.setConverter(new CardPreferencesConverterImpl());
    }

    @Test(expected=DataServiceException.class)
    public void findByCardIdShouldThrowException() {
        when(mockDAO.findByQuery(anyString(), anyVararg()))
            .thenReturn(Arrays.asList(new CardPreferences(), new CardPreferences()));
        service.findByCardId(CARD_PREFERENCES_ID_1);
    }
    
    @Test
    public void findByCardIdShouldReturnNull() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(new ArrayList<>());
        assertNull(service.findByCardId(CARD_PREFERENCES_ID_1));
    }
    
    @Test
    public void findByCardIdShouldReturnDTO() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestCardPreferences1()));
        assertEquals(CARD_PREFERENCES_ID_1, service.findByCardId(CARD_PREFERENCES_ID_1).getId());
    }
    
    @Test
    public void getPreferredStationIdByCardIdShouldReturnNull() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(new ArrayList<>());
        assertNull(service.getPreferredStationIdByCardId(CARD_PREFERENCES_ID_1));
    }
    
    @Test
    public void getPreferredStationIdByCardIdShouldReturnStationId() {
        when(mockDAO.findByQuery(anyString(), anyVararg()))
            .thenReturn(Arrays.asList(getTestCardPreferences1()));
        assertEquals(STATION_ID_1, service.getPreferredStationIdByCardId(CARD_PREFERENCES_ID_1));
    }
    
    @Test
    public void findByEmailPreferenceShouldReturnNull() {
        when(mockDAO.findByExample(any(CardPreferences.class))).thenReturn(null);
        assertNull(service.findByEmailPreference(null));
    }
    
    @Test
    public void findByEmailPreferenceShouldReturnDTOList() {
        when(mockDAO.findByExample(any(CardPreferences.class)))
                .thenReturn(Arrays.asList(getTestCardPreferences1()));
        List<CardPreferencesDTO> actualDTOList = service.findByEmailPreference(EMAIL_FREQUENCY_1); 
        assertEquals(1, actualDTOList.size());
        assertEquals(EMAIL_FREQUENCY_1, actualDTOList.get(0).getEmailFrequency());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
}
