package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardPreferencesTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.CompletedJourneyProcessingResult;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.form_validator.CompleteJourneyValidator;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

@RunWith(MockitoJUnitRunner.class)
public class CompleteJourneyControllerTest {

    @Mock
    protected LocationSelectListService locationSelectListService;

    @Mock
    protected SelectListService selectListService;

    @Mock
    protected IncompleteJourneyService incompleteJourneyService;

    @Mock
    protected JourneyHistoryService journeyHistoryService;

    @Mock
    protected CustomerService customerService;

    @Mock
    protected RefundService refundService;
    @Mock
    protected SecurityService securityService;

    @Mock
    protected CompleteJourneyValidator completeJourneyValidator;

    @Mock
    protected CardPreferencesDataService cardPreferencesDataService;

    @Mock
    protected Model model;

    protected final Long preferredStationId = 3L;

    protected final Long loggedinCustomer = 1L;

    protected CompleteJourneyController completeJourneyController = new CompleteJourneyController();

    @Before
    public void setUp() {
        completeJourneyController.customerService = customerService;
        completeJourneyController.refundService = refundService;
        completeJourneyController.journeyHistoryService = journeyHistoryService;
        completeJourneyController.locationSelectListService = locationSelectListService;
        completeJourneyController.selectListService = selectListService;
        completeJourneyController.cardPreferencesDataService = cardPreferencesDataService;

    }

    @Test
    public void journeyDetailsWithLinkedStationShouldSetDataReturned() {
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        CompleteJourneyController mockedController = mock(CompleteJourneyController.class);
        JourneyDTO journeyDTO = new JourneyDTO();
        mockedController.customerService = customerService;
        mockedController.journeyHistoryService = journeyHistoryService;
        when(mockedController.getLoggedInUserCustomerId()).thenReturn(preferredStationId);
        when(customerService.getPreferredStationId(loggedinCustomer)).thenReturn(preferredStationId);
        when(journeyHistoryService.getIncompleteJourney(anyLong(), any(Date.class), anyInt())).thenReturn(journeyDTO);
        doCallRealMethod().when(mockedController).journeyDetailsWithLinkedStation(completeJourneyCommandImpl);
        assertEquals(mockedController.journeyDetailsWithLinkedStation(completeJourneyCommandImpl).getViewName(),
                PageView.ONLN_COMPLETE_JOURNEY);
        assertEquals(mockedController.journeyDetailsWithLinkedStation(completeJourneyCommandImpl).getModelMap()
                .get(PageCommand.COMPLETE_JOURNEY), completeJourneyCommandImpl);

    }

    @Test
    public void journeyDetailsForJourneyIdShouldSetDataReturned() {
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        CompleteJourneyController mockedController = mock(CompleteJourneyController.class);
        JourneyDTO journeyDTO = new JourneyDTO();
        mockedController.customerService = customerService;
        mockedController.journeyHistoryService = journeyHistoryService;
        mockedController.incompleteJourneyService = incompleteJourneyService;
        mockedController.cardPreferencesDataService = cardPreferencesDataService;
        when(mockedController.getLoggedInUserCustomerId()).thenReturn(preferredStationId);
        when(cardPreferencesDataService.findByCardId(anyLong())).thenReturn(CardPreferencesTestUtil.getTestCardPreferencesDTO1());
        when(customerService.getPreferredStationId(loggedinCustomer)).thenReturn(preferredStationId);
        when(incompleteJourneyService.getSSREligibleJourneyHistoryItem(anyLong(), any(Date.class), anyInt()))
                .thenReturn(journeyDTO);
        doCallRealMethod().when(mockedController).journeyDetailsForJourneyId(completeJourneyCommandImpl);
        doCallRealMethod().when(mockedController).setPreferredStation(completeJourneyCommandImpl);
        assertEquals(mockedController.journeyDetailsForJourneyId(completeJourneyCommandImpl).getViewName(),
                PageView.ONLN_COMPLETE_JOURNEY);
        assertEquals(mockedController.journeyDetailsForJourneyId(completeJourneyCommandImpl).getModelMap()
                .get(PageCommand.COMPLETE_JOURNEY), completeJourneyCommandImpl);

    }

    @Test
    public void journeyDetailsWithoutCardPreferenceForJourneyIdShouldSetDataReturned() {
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        CompleteJourneyController mockedController = mock(CompleteJourneyController.class);
        JourneyDTO journeyDTO = new JourneyDTO();
        mockedController.customerService = customerService;
        mockedController.journeyHistoryService = journeyHistoryService;
        mockedController.incompleteJourneyService = incompleteJourneyService;
        mockedController.cardPreferencesDataService = cardPreferencesDataService;
        when(mockedController.getLoggedInUserCustomerId()).thenReturn(preferredStationId);
        when(cardPreferencesDataService.findByCardId(anyLong())).thenReturn(null);
        when(customerService.getPreferredStationId(loggedinCustomer)).thenReturn(preferredStationId);
        when(incompleteJourneyService.getSSREligibleJourneyHistoryItem(anyLong(), any(Date.class), anyInt())).thenReturn(journeyDTO);
        doCallRealMethod().when(mockedController).setPreferredStation(completeJourneyCommandImpl);
        doCallRealMethod().when(mockedController).journeyDetailsForJourneyId(completeJourneyCommandImpl);
        assertEquals(mockedController.journeyDetailsForJourneyId(completeJourneyCommandImpl).getViewName(), PageView.ONLN_COMPLETE_JOURNEY);
        assertEquals(mockedController.journeyDetailsForJourneyId(completeJourneyCommandImpl).getModelMap().get(PageCommand.COMPLETE_JOURNEY),
                        completeJourneyCommandImpl);

    }

    @Test
    public void populateShouldSetTheReturnedValues() {
        SelectListDTO locatioSelectListDTOToReturn = new SelectListDTO();
        SelectListDTO missingTouchSelectListDTOToReturn = new SelectListDTO();
        when(locationSelectListService.getLocationSelectList()).thenReturn(locatioSelectListDTOToReturn);
        when(selectListService.getSelectList(PageSelectList.MISSING_TOUCH)).thenReturn(missingTouchSelectListDTOToReturn);
        when(model.addAttribute(PageAttribute.LOCATIONS, locatioSelectListDTOToReturn)).thenReturn(model);
        when(model.addAttribute(PageAttribute.REASON_FOR_MISSING_TAP, missingTouchSelectListDTOToReturn)).thenReturn(model);
        completeJourneyController.populateLocationsSelectList(model);
        verify(selectListService).getSelectList(PageSelectList.MISSING_TOUCH);
        verify(locationSelectListService).getLocationSelectList();
        verify(model).addAttribute(PageAttribute.LOCATIONS, locatioSelectListDTOToReturn);
        verify(model).addAttribute(PageAttribute.REASON_FOR_MISSING_TAP, missingTouchSelectListDTOToReturn);
    }

    @Test
    public void saveChangesReturnsTheRedirectView() {
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        final CompletedJourneyProcessingResult completedJourneyProcessingResult =
                new CompletedJourneyProcessingResult(100, true);
        CompleteJourneyController mockedController = mock(CompleteJourneyController.class);
        mockedController.completeJourneyValidator = completeJourneyValidator;
        mockedController.refundService = refundService;
        BindingResult result = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(refundService.processRefundsForCompletedJourney(completeJourneyCommandImpl))
                .thenReturn(completedJourneyProcessingResult);
        doCallRealMethod().when(mockedController).saveChanges(completeJourneyCommandImpl, result, redirectAttributes);
        assertEquals(mockedController.saveChanges(completeJourneyCommandImpl, result, redirectAttributes).getView().getClass()
                .getName(), RedirectView.class.getName());
        verify(refundService).processRefundsForCompletedJourney(completeJourneyCommandImpl);
    }

    @Test
    public void saveChangesWithErrorsReturnsTheRedirectView() {
        CompleteJourneyCommandImpl completeJourneyCommandImpl = new CompleteJourneyCommandImpl();
        CompleteJourneyController mockedController = mock(CompleteJourneyController.class);
        mockedController.completeJourneyValidator = completeJourneyValidator;
        mockedController.customerService = customerService;
        mockedController.journeyHistoryService = journeyHistoryService;
        BindingResult result = mock(BindingResult.class);
        JourneyDTO journeyDTO = new JourneyDTO();
        when(result.hasErrors()).thenReturn(true);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        doCallRealMethod().when(mockedController).saveChanges(completeJourneyCommandImpl, result, redirectAttributes);
        doCallRealMethod().when(mockedController).journeyDetailsWithLinkedStation(completeJourneyCommandImpl);
        when(journeyHistoryService.getIncompleteJourney(anyLong(), any(Date.class), anyInt())).thenReturn(journeyDTO);
        mockedController.saveChanges(completeJourneyCommandImpl, result, redirectAttributes);
        verify(customerService).getPreferredStationId(anyLong());
    }
}
