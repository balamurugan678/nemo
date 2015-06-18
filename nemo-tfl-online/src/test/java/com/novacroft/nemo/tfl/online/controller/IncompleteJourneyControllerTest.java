package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.SecurityServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history.IncompleteJourneyServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.command.impl.IncompleteJourneyCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards1List;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards2List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Unit tests for IncompleteJourneyController
 */
public class IncompleteJourneyControllerTest {
    @Test
    public void showIncompleteJourneysShouldShowCardList() {
        SecurityService mockSecurityService = mock(SecurityServiceImpl.class);
        IncompleteJourneyService mockIncompleteJourneyService = mock(IncompleteJourneyServiceImpl.class);
        when(mockIncompleteJourneyService.getIncompleteJourneys(anyLong())).thenReturn(null);
        IncompleteJourneyController controller = new IncompleteJourneyController();
        setField(controller, "securityService", mockSecurityService);
        controller.incompleteJourneyService = mockIncompleteJourneyService;
        ModelAndView result = controller.showIncompleteJourneys(getTestCards2List());
        assertEquals(PageView.INCOMPLETE_JOURNEY, result.getViewName());
        assertNull(((IncompleteJourneyCmdImpl) result.getModel().get(PageCommand.INCOMPLETE_JOURNEY)).getCardId());
    }

    @Test
    public void showIncompleteJourneysShouldAutoSelectSingleCard() {
        SecurityService mockSecurityService = mock(SecurityServiceImpl.class);
        IncompleteJourneyService mockIncompleteJourneyService = mock(IncompleteJourneyServiceImpl.class);
        when(mockIncompleteJourneyService.getIncompleteJourneys(anyLong())).thenReturn(null);
        IncompleteJourneyController controller = new IncompleteJourneyController();
        setField(controller, "securityService", mockSecurityService);
        controller.incompleteJourneyService = mockIncompleteJourneyService;
        ModelAndView result = controller.showIncompleteJourneys(getTestCards1List());
        assertEquals(PageView.INCOMPLETE_JOURNEY, result.getViewName());
        assertNotNull(((IncompleteJourneyCmdImpl) result.getModel().get(PageCommand.INCOMPLETE_JOURNEY)).getCardId());
    }
}
