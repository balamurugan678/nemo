package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.command.impl.IncompleteJourneyCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for self service refunds for incomplete journeys
 */
@Controller
@RequestMapping(Page.INV_INCOMPLETE_JOURNEYS)
public class IncompleteJourneyController {
    protected static final Logger logger = LoggerFactory.getLogger(IncompleteJourneyController.class);

    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected SelectCardValidator selectCardValidator;
    @Autowired
    protected IncompleteJourneyService incompleteJourneyService;

    @Autowired
    protected LocationSelectListService locationSelectListService;

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage(@RequestParam(value = PageParameter.ID) Long cardId) {
        CompleteJourneyCommandImpl cmd = new CompleteJourneyCommandImpl();
        cmd.setCardId(cardId);
        cmd.setIncompleteJourneyHistoryDTO(incompleteJourneyService.getIncompleteJourneysForInnovator(cardId));
        return new ModelAndView(PageView.INV_INCOMPLETE_JOURNEYS, PageCommand.INCOMPLETE_JOURNEY, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SELECT_CARD, method = RequestMethod.POST)
    public ModelAndView selectCard(@ModelAttribute(PageCommand.INCOMPLETE_JOURNEY) IncompleteJourneyCmdImpl cmd,
                                   BindingResult result) {
        selectCardValidator.validate(cmd, result);
        ModelAndView modelAndView = new ModelAndView(PageView.INCOMPLETE_JOURNEY, PageCommand.INCOMPLETE_JOURNEY, cmd);
        if (!result.hasErrors()) {
            cmd.setIncompleteJourneyHistoryDTO(this.incompleteJourneyService.getIncompleteJourneys(cmd.getCardId()));
            modelAndView.getModelMap().addAttribute("notificationList", cmd.getIncompleteJourneyHistoryDTO());
        }
        return modelAndView;
    }

}

