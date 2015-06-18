package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.CARD_PREFERENCES_ID_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesCmd1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTO1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTOWithNullStationId;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO3;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;

/**
 * Unit tests for CardPreferencesService
 */
public class CardPreferencesServiceTest {
    private CardPreferencesServiceImpl service;
    private CardPreferencesDataService mockCardPreferencesDataService;
    private CustomerDataService mockCustomerDataService;
    private CardDataService mockCardDataService;
    
    @Before
    public void setUp() {
        service = new CardPreferencesServiceImpl();
        
        mockCardPreferencesDataService = mock(CardPreferencesDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        
        service.cardPreferencesDataService = mockCardPreferencesDataService;
        service.customerDataService = mockCustomerDataService;
        service.cardDataService = mockCardDataService;
    }

    @Test
    public void shouldGetPreferencesForExistingPreferences() {
        when(mockCardPreferencesDataService.findByCardId(anyLong())).thenReturn(getTestCardPreferencesDTO1());
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());

        CardPreferencesCmdImpl cmd = service.getPreferences(CARD_ID_1, USERNAME_1);
        assertEquals(CARD_PREFERENCES_ID_1, cmd.getCardPreferencesId());
    }

    @Test
    public void shouldGetPreferencesForNullPreferences() {
        when(mockCardPreferencesDataService.findByCardId(anyLong())).thenReturn(null);
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());

        CardPreferencesCmdImpl actualCmd = service.getPreferences(CARD_ID_1, USERNAME_1);
        assertNotNull(actualCmd);
        assertEquals(CARD_ID_1, actualCmd.getCardId());
        verify(mockCardPreferencesDataService, atLeastOnce()).findByCardId(anyLong());
        verify(mockCustomerDataService, atLeastOnce()).findByUsernameOrEmail(anyString());
    }

    @Test
    public void shouldCreate() {
        CardPreferencesCmdImpl cmd = getTestCardPreferencesCmd1();
        cmd.setCardPreferencesId(null);
        cmd.setCardId(null);
        when(mockCardPreferencesDataService.createOrUpdate(any(CardPreferencesDTO.class)))
                .thenReturn(getTestCardPreferencesDTO1());

        CardPreferencesCmdImpl actualCmd = service.updatePreferences(cmd);
        assertNotNull(actualCmd);
        assertEquals(CARD_PREFERENCES_ID_1, actualCmd.getCardPreferencesId());
    }

    @Test
    public void shouldUpdate() {
        when(mockCardPreferencesDataService.findById(anyLong())).thenReturn(getTestCardPreferencesDTO1());
        when(mockCardPreferencesDataService.createOrUpdate(any(CardPreferencesDTO.class)))
                .thenReturn(getTestCardPreferencesDTO1());
        
        CardPreferencesCmdImpl actualCmd = service.updatePreferences(getTestCardPreferencesCmd1()); 
        assertNotNull(actualCmd);
        assertEquals(CARD_PREFERENCES_ID_1, actualCmd.getCardPreferencesId());
    }
    
    @Test
    public void shouldGetByCardIdAndUpdate() {
        CardPreferencesCmdImpl cmd = getTestCardPreferencesCmd1();
        cmd.setCardPreferencesId(null);
        when(mockCardPreferencesDataService.findById(anyLong())).thenReturn(getTestCardPreferencesDTO1());
        when(mockCardPreferencesDataService.createOrUpdate(any(CardPreferencesDTO.class)))
                .thenReturn(getTestCardPreferencesDTO1());
        
        CardPreferencesCmdImpl actualCmd = service.updatePreferences(cmd); 
        assertNotNull(actualCmd);
        assertEquals(CARD_PREFERENCES_ID_1, actualCmd.getCardPreferencesId());
    }
    
    @Test
    public void shouldUpdateForFindByCardId() {
        
        CardPreferencesCmdImpl cmd = getTestCardPreferencesCmd1();
        cmd.setCardPreferencesId(null);
        when(mockCardPreferencesDataService.findByCardId(anyLong())).thenReturn(getTestCardPreferencesDTO1());
        when(mockCardPreferencesDataService.createOrUpdate(any(CardPreferencesDTO.class)))
                .thenReturn(getTestCardPreferencesDTO1());
        
        CardPreferencesCmdImpl actualCmd = service.updatePreferences(cmd); 
        assertNotNull(actualCmd);
        assertEquals(CARD_PREFERENCES_ID_1, actualCmd.getCardPreferencesId());
    }
    
    @Test
    public void shouldUpdateAndSetDTOStationIdToNull() {
        CardPreferencesCmdImpl cmd = getTestCardPreferencesCmd1();
        cmd.setStationId(null);
        when(mockCardPreferencesDataService.findById(anyLong())).thenReturn(getTestCardPreferencesDTO1());
        when(mockCardPreferencesDataService.createOrUpdate(any(CardPreferencesDTO.class))).thenReturn(getTestCardPreferencesDTOWithNullStationId());
        CardPreferencesCmdImpl actualCmd = service.updatePreferences(cmd);
        assertNotNull(actualCmd);
        assertNull(actualCmd.getStationId());
    }
    
    @Test(expected=AssertionError.class)
    public void getPreferencesByCardNumberShouldThrowAssertError() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(null);
        service.getPreferencesByCardNumber(null);
    }
    
    @Test
    public void getPreferencesByCardIdShouldReturnCardPreferences() {
        CardDTO card = getTestCardDTO1();
        when(mockCardDataService.findById(anyLong())).thenReturn(card);
        when(mockCustomerDataService.findById(card.getCustomerId())).thenReturn(getTestCustomerDTO3());
        CardPreferencesCmdImpl cmd = service.getPreferencesByCardId(CardTestUtil.CARD_ID_1);
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        assertNotNull(cmd);
        assertTrue(cmd instanceof CardPreferencesCmdImpl);
    }

    @Test(expected = AssertionError.class)
    public void getPreferencesByCardIdShouldThrowAssertError() {
        when(mockCardDataService.findById(anyLong())).thenReturn(null);
        service.getPreferencesByCardId(CardTestUtil.CARD_ID_1);
    }

    @Test
    public void getPreferencesByCardNumberShouldReturnNotNull() {
        CardDTO card = getTestCardDTO1();
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(card);
        when(mockCustomerDataService.findById(card.getCustomerId())).thenReturn(getTestCustomerDTO1());
        assertNotNull(service.getPreferencesByCardNumber(OYSTER_NUMBER_1));
    }
    
    @Test
    public void shouldGetPreferredStationIdByCardId() {
        when(mockCardPreferencesDataService.getPreferredStationIdByCardId(anyLong())).thenReturn(null);
        
        service.getPreferredStationIdByCardId(getTestCardPreferencesCmd1());
        verify(mockCardPreferencesDataService).getPreferredStationIdByCardId(CARD_ID_1);
    }
}
