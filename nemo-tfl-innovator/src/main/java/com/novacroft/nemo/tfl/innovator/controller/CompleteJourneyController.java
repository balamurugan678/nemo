package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.controller.CommonController;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.impl.CompletedJourneyProcessingResult;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.form_validator.CompleteJourneyValidator;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(PageUrl.INV_COMPLETE_JOURNEY_DETAILS)
public class CompleteJourneyController extends CommonController {

    protected static final Logger logger = LoggerFactory.getLogger(CompleteJourneyController.class);

    @Autowired
    protected LocationSelectListService locationSelectListService;

    @Autowired
    protected IncompleteJourneyService incompleteJourneyService;

    @Autowired
    protected JourneyHistoryService journeyHistoryService;

    @Autowired
    protected SelectListService selectListService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected CardPreferencesService cardPreferencesService;

    @Autowired
    protected RefundService refundService;

    @Autowired
    protected CompleteJourneyValidator completeJourneyValidator;

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
        model.addAttribute(PageAttribute.REASON_FOR_MISSING_TAP,
                this.selectListService.getSelectList(PageSelectList.MISSING_TOUCH));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView journeyDetailsWithLinkedStation(
            @ModelAttribute(value = PageCommand.COMPLETE_JOURNEY) CompleteJourneyCommandImpl pageCommand) {
        CardPreferencesCmdImpl cardPreferencesCmdImpl = new CardPreferencesCmdImpl();
        cardPreferencesCmdImpl.setCardId(pageCommand.getCardId());
        pageCommand.setPreferredStation(cardPreferencesService.getPreferredStationIdByCardId(cardPreferencesCmdImpl));
        final JourneyDTO journeyDTO = journeyHistoryService
                .getIncompleteJourney(pageCommand.getCardId(), pageCommand.getLinkedTransactionTime(),
                        pageCommand.getLinkedStationKey());
        pageCommand.setJourney(journeyDTO);
        return new ModelAndView(PageView.INV_ONLN_COMPLETE_JOURNEY, PageCommand.COMPLETE_JOURNEY, pageCommand);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_COMPLETE_JOURNEY, method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute(PageCommand.COMPLETE_JOURNEY) CompleteJourneyCommandImpl pageCommand,
                                    BindingResult result, final RedirectAttributes redirectAttributes) {

        completeJourneyValidator.validate(pageCommand, result);
        if (result.hasErrors()) {
            return journeyDetailsWithLinkedStation(pageCommand);
        }
        pageCommand.setAgentCompleted(true);
        CompletedJourneyProcessingResult completedJourneyProcessingResult =
                refundService.processRefundsForCompletedJourney(pageCommand);
        setFlashAttribute(redirectAttributes, PageAttribute.COMPLETED_JOURNEY_RESPONSE, completedJourneyProcessingResult);
        setFlashAttribute(redirectAttributes, PageParameter.ID, pageCommand.getCardId());
        final String redirectUrl =
                PageUrl.INV_INCOMPLETE_JOURNEYS + "?" + PageParameter.ID + PageParameterArgument.NAME_VALUE_SEPARATOR +
                        pageCommand.getCardId();
        return new ModelAndView(new RedirectView(redirectUrl, true));
    }
}
