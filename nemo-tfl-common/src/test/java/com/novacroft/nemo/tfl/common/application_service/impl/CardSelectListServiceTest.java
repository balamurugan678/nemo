package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CardTestUtil.*;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CardSelectListService
 */
public class CardSelectListServiceTest {

    @Test
    public void shouldGetCards() {
        CardDataService mockCardDataService = mock(CardDataService.class);
        when(mockCardDataService.findByUsername(anyString())).thenReturn(getTestCards1And2());

        CardSelectListServiceImpl service = new CardSelectListServiceImpl();
        service.cardDataService = mockCardDataService;

        SelectListDTO selectListDTO = service.getCardsSelectList(USERNAME_1);
        assertEquals("Cards", selectListDTO.getName());
        assertEquals(2, selectListDTO.getOptions().size());
        assertTrue(selectListDTO.getOptions().contains(getTestCard1Option()));
        assertTrue(selectListDTO.getOptions().contains(getTestCard2Option()));
    }
}
