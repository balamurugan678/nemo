package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.ApplicationEventTestUtil.CUSTOMER_ID_AS_STRING;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CARD_ADMIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;



public class CardAdminControllerTest {
    private CardAdminController controller;
    private CardAdminController mockController;
    private CardDataService mockCardDataService;
    private HttpSession mockHttpSession;
    
    public static final Integer CARD_ID_ZERO = 0;

    @Before
    public void setUp() throws Exception {
        controller = new CardAdminController();        
        mockCardDataService = mock(CardDataService.class);
        controller.cardDataService = mockCardDataService;
        
        mockController = mock(CardAdminController.class);
        mockController.cardDataService = mockCardDataService;
        mockHttpSession = mock(HttpSession.class);
    }

    @Test
    public void viewShouldReturnCardAdminView() {
        ModelAndView result = controller.view(OYSTER_NUMBER_1, 1L);
        assertEquals(INV_CARD_ADMIN, result.getViewName());
    }

    @Test
    public void shouldLoadJourneyHistoryAndSetCardIdIfCardNumberExists() {
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(CardTestUtil.getTestCardDTO1());        
        ModelAndView result = controller.loadJourneyHistory(OYSTER_NUMBER_1);
        assertEquals(CardTestUtil.getTestCardDTO1().getId(), result.getModelMap().get("id"));
        assertNull(result.getViewName());
    }
    
    @Test
    public void shouldLoadJourneyHistoryAndSetCardIdToDefaultIfCardNumberDoesNotExist() {
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(null);        
        ModelAndView result = controller.loadJourneyHistory(OYSTER_NUMBER_1);
        assertEquals(CARD_ID_ZERO, Integer.valueOf(result.getModelMap().get("id").toString()));
        assertNull(result.getViewName());
    }
    
    @Test
    public void shouldLoadUnFinishedJourneysAndSetCardIdIfCardNumberExists() {
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(CardTestUtil.getTestCardDTO1());        
        ModelAndView result = controller.loadUnFinishedJourneys(OYSTER_NUMBER_1);        
        assertEquals(CardTestUtil.getTestCardDTO1().getId(), result.getModelMap().get("id"));
        assertNull(result.getViewName());
    }
    
    @Test
    public void shouldLoadUnFinishedJourneysAndSetCardIdToDefaultIfCardNumberDoesNotExist() {
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(null);        
        ModelAndView result = controller.loadUnFinishedJourneys(OYSTER_NUMBER_1);        
        assertEquals(CARD_ID_ZERO, Integer.valueOf(result.getModelMap().get("id").toString()));
        assertNull(result.getViewName());
    }
    
    @Test
    public void loadEditCardPreferencesShouldReturnJourneyHistoryView() {
    	ModelAndView result = controller.loadEditCardPreferences(OYSTER_NUMBER_1);
    	
        assertNull(result.getViewName());
    }

    @Test
    public void shouldLoadManageAutoTopUp() {
        when(mockController.loadManageAutoTopUp(anyString(), anyString(), (HttpSession)any())).thenCallRealMethod();
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(CardTestUtil.getTestCardDTO1());        
        ModelAndView result = mockController.loadManageAutoTopUp(OYSTER_NUMBER_1, CUSTOMER_ID_AS_STRING, mockHttpSession);        
        assertNull(result.getViewName());
    }
    
}
