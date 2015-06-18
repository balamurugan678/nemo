package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.IncompleteJourneyCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

public class IncompleteJourneyControllerTest {
    
    private IncompleteJourneyController controller;
    private IncompleteJourneyController mockController;

    private IncompleteJourneyCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private CardSelectListService mockCardSelectListService;
    private SelectCardValidator mockSelectCardValidator;
    private IncompleteJourneyService mockIncompleteJourneyService;
    private LocationSelectListService mockLocationSelectListService;
    private HttpSession mockSession;
    private CartSessionData cartSessionData;
    private CartDTO cartDTO;
    private Model mockModel;   
    

    @Before
    public void setUp() {
        controller = new IncompleteJourneyController();
        mockController = mock(IncompleteJourneyController.class);
        mockCmd = mock(IncompleteJourneyCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockCardSelectListService = mock(CardSelectListService.class);
        mockSelectCardValidator = mock(SelectCardValidator.class);
        mockIncompleteJourneyService = mock(IncompleteJourneyService.class);
        mockSession = mock(HttpSession.class);
        cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO(CardTestUtil.CARD_ID);
        cartDTO = CartTestUtil.getTestCartDTO1();
        mockModel = mock(Model.class);
        mockLocationSelectListService = mock(LocationSelectListService.class);

        controller.selectCardValidator = mockSelectCardValidator;
        mockController.cardSelectListService = mockCardSelectListService;
        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.incompleteJourneyService = mockIncompleteJourneyService;
        mockController.locationSelectListService = mockLocationSelectListService;        

        doNothing().when(mockSelectCardValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }
    
    @Test
    public void populateLocationsSelectList() {
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(new SelectListDTO());
        doCallRealMethod().when(mockController).populateLocationsSelectList(mockModel);
        mockController.populateLocationsSelectList(mockModel);
        verify(mockModel).addAttribute(anyString(), anyObject());
    }
    
    @Test
    public void showPage() {
        when(mockIncompleteJourneyService.getIncompleteJourneysForInnovator(anyLong())).thenReturn(new IncompleteJourneyHistoryDTO());
        doCallRealMethod().when(mockController).showPage(anyLong());
        ModelAndView modelAndView = mockController.showPage(CartTestUtil.CARD_ID_1);
        assertNotNull(modelAndView);
        assertEquals(CartTestUtil.INV_INCOMPLETE_JOURNEYS, modelAndView.getViewName());        
    }
    
    @Test
    public void selectCard() {
        when(mockResult.hasErrors()).thenReturn(false);
        doNothing().when(mockSelectCardValidator).validate(anyObject(), any(Errors.class));
        when(mockCmd.getCardId()).thenReturn(CartTestUtil.CARD_ID_1);
        when(mockIncompleteJourneyService.getIncompleteJourneys(anyLong())).thenReturn(new IncompleteJourneyHistoryDTO());        
        
        doCallRealMethod().when(mockController).selectCard((IncompleteJourneyCmdImpl)anyObject(), (BindingResult)anyObject());
        ModelAndView modelAndView = mockController.selectCard(mockCmd, mockResult);
        assertNotNull(modelAndView);
        assertEquals(CartTestUtil.INCOMPLETE_JOURNEY, modelAndView.getViewName());
        assertTrue(modelAndView.getModelMap().containsAttribute(CartTestUtil.NOTIFICATION_LIST));        
    }

}
