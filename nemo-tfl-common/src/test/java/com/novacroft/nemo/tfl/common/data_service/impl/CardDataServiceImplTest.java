package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardObject1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.converter.impl.CardConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardDAO;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

public class CardDataServiceImplTest {

    private CardDataServiceImpl dataService;
    private CardDAO mockDao;
    private static int TOTAL_NUMBER_OF_CARDS_AFTER_DELETION = 2;
    private static int TOTAL_NUMBER_OF_CARDS_BEFORE_DELETION = 3;

    @Before
    public void setUp() throws Exception {
        dataService = new CardDataServiceImpl();
        mockDao = mock(CardDAO.class);

        dataService.setDao(mockDao);
        dataService.setConverter(new CardConverterImpl());
    }

    @Test
    public void findByCustomerIdReturnCards() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(getTestCardObject1());
        when(mockDao.findByQuery(anyString(), anyObject())).thenReturn(cards);
        List<CardDTO> list = dataService.findByCustomerId(CUSTOMER_ID_1);
        assertEquals(OYSTER_NUMBER_1, list.get(0).getCardNumber());
    }

    @Test(expected = DataServiceException.class)
    public void findByCustomerIdQueryThrowsExceptionl() {
        when(mockDao.findByQuery(anyString(), anyObject())).thenReturn(null);
        dataService.findByCustomerId(CUSTOMER_ID_1);
    }


    @Test
    public void findByUsernameShouldReturnDTOList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestCardObject1()));
        List<CardDTO> actualList = dataService.findByUsername(USERNAME_1);
        assertEquals(1, actualList.size());
        assertEquals(CARD_ID_1, actualList.get(0).getId());
    }

    @Test
    public void findByCardNumberShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        assertNull(dataService.findByCardNumber(OYSTER_NUMBER_1));
    }

    @Test
    public void findByCardNumberShouldReturnCardDTO() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestCardObject1());
        assertEquals(CARD_ID_1, dataService.findByCardNumber(OYSTER_NUMBER_1).getId());
    }

    @Test
    public void findByCustomerIdAndCardNumberShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        assertNull(dataService.findByCustomerIdAndCardNumber(CUSTOMER_ID_1, OYSTER_NUMBER_1));
    }

    @Test
    public void findByCustomerIdAndCardNumberShouldReturnCardDTO() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestCardObject1());
        CardDTO actualDTO = dataService.findByCustomerIdAndCardNumber(CUSTOMER_ID_1, OYSTER_NUMBER_1);
        assertNotNull(actualDTO);
        assertEquals(CARD_ID_1, actualDTO.getId());
    }

    @Test
    public void findByPaymentCardIdShouldReturnEmptyList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(null);
        assertTrue(dataService.findByPaymentCardId(CARD_ID_1).isEmpty());
    }

    @Test
    public void findByPaymentCardIdShouldReturnDTOList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestCardObject1()));
        List<CardDTO> actualList = dataService.findByPaymentCardId(CARD_ID_1);
        assertEquals(1, actualList.size());
        assertEquals(CARD_ID_1, actualList.get(0).getId());
    }

    @Test(expected = DataServiceException.class)
    public void findHotlistedCardsShouldThrowException() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(null);
        dataService.findHotlistedCards();
    }

    @Test
    public void findHotlistedCardsShouldDTOList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestCardObject1()));
        List<CardDTO> actualList = dataService.findHotlistedCards();
        assertEquals(1, actualList.size());
        assertEquals(CARD_ID_1, actualList.get(0).getId());
    }

    @Test(expected = DataServiceException.class)
    public void findHotlistedCardsWithReasonShouldThrowException() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(null);
        dataService.findHotlistedCardsWithReason();
    }

    @Test
    public void findHotlistedCardsWithReasonShouldDTOList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestCardObject1()));
        List<CardDTO> actualList = dataService.findHotlistedCardsWithReason();
        assertEquals(1, actualList.size());
        assertEquals(CARD_ID_1, actualList.get(0).getId());
    }

    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }

    @Test
    public void getAllCardsFromUserExceptCurrentShouldReturnNonEmptyList() {
        CardDataServiceImpl mockDataService = mock(CardDataServiceImpl.class);
        mockDataService.setDao(mockDao);
        mockDataService.setConverter(new CardConverterImpl());
        List<CardDTO> list = new ArrayList<CardDTO>();
        list.add(CardTestUtil.getTestCardDTO1());
        list.add(CardTestUtil.getTestCardDTO2());
        when(mockDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockDataService.findByCustomerId(anyLong())).thenReturn(list);
        when(mockDataService.deleteCardFromList(any(List.class), anyString())).thenReturn(list);
        when(mockDataService.getAllCardsFromUserExceptCurrent(anyString())).thenCallRealMethod();
        List<CardDTO> result = mockDataService.getAllCardsFromUserExceptCurrent(OYSTER_NUMBER_1);
        assertTrue(result instanceof List);
        assertFalse(result.isEmpty());
        verify(mockDataService).deleteCardFromList(list, OYSTER_NUMBER_1);
    }

    @Test
    public void shouldFindByCustomerIdAndExternalId() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(CardTestUtil.getTestCardObject3());
        CardDTO cardDTO =
                dataService.findByCustomerIdAndExternalId(CustomerTestUtil.CUSTOMER_ID_1, CardTestUtil.EXTERNAL_CARD_ID);
        assertNotNull(cardDTO);
    }

    @Test
    public void shouldNotFindByCustomerIdAndExternalId() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(null);
        CardDTO cardDTO =
                dataService.findByCustomerIdAndExternalId(CustomerTestUtil.CUSTOMER_ID_1, CardTestUtil.EXTERNAL_CARD_ID);
        assertNull(cardDTO);
    }

    @Test
    public void shouldDeleteCardFromTheList() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(CardTestUtil.getTestCardObject1());
        assertTrue(TOTAL_NUMBER_OF_CARDS_BEFORE_DELETION  == CardTestUtil.getListOfCards().size());
        List<CardDTO> listOfCards = dataService.deleteCardFromList(CardTestUtil.getListOfCards(), CardTestUtil.OYSTER_NUMBER_1);
        assertTrue(TOTAL_NUMBER_OF_CARDS_AFTER_DELETION  == listOfCards.size());
        
    }

}
