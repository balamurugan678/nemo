package com.novacroft.nemo.tfl.online.controller;

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

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.command.impl.IncompleteJourneyCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;

/**
 * Controller for self service refunds for incomplete journeys
 */
@Controller
@RequestMapping(PageUrl.INCOMPLETE_JOURNEY)
public class IncompleteJourneyController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(IncompleteJourneyController.class);

    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected SelectCardValidator selectCardValidator;
    @Autowired
    protected IncompleteJourneyService incompleteJourneyService;

    @ModelAttribute
    public void populateCardsSelectList(Model model) {
        model.addAttribute(PageAttribute.CARDS, cardSelectListService.getCardsSelectList(this.securityService.getLoggedInUsername()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showIncompleteJourneys(@ModelAttribute(PageAttribute.CARDS) SelectListDTO cardSelectList) {
        if (cardSelectList.getOptions().size() == 1) {
            IncompleteJourneyCmdImpl cmd = new IncompleteJourneyCmdImpl();
            cmd.setCardId(new Long(cardSelectList.getOptions().iterator().next().getValue()));
            cmd.setIncompleteJourneyHistoryDTO(this.incompleteJourneyService.getIncompleteJourneys(cmd.getCardId()));
            ModelAndView modelAndView = new ModelAndView(PageView.INCOMPLETE_JOURNEY, PageCommand.INCOMPLETE_JOURNEY, cmd);
            modelAndView.getModelMap().addAttribute("notificationList", cmd.getIncompleteJourneyHistoryDTO());
            return modelAndView;
        } else {
            return new ModelAndView(PageView.INCOMPLETE_JOURNEY, PageCommand.INCOMPLETE_JOURNEY,
                    new IncompleteJourneyCmdImpl());
        }
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
