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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.impl.CompletedJourneyProcessingResult;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.form_validator.CompleteJourneyValidator;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

@Controller
@RequestMapping(PageUrl.COMPLETE_JOURNEY_DETAILS)
public class CompleteJourneyController extends OnlineBaseController {

    protected static final Logger logger = LoggerFactory.getLogger(CompleteJourneyController.class);

    @Autowired
    protected LocationSelectListService locationSelectListService;

    @Autowired
    protected SelectListService selectListService;

    @Autowired
    protected IncompleteJourneyService incompleteJourneyService;

    @Autowired
    protected JourneyHistoryService journeyHistoryService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected RefundService refundService;

    @Autowired
    protected CompleteJourneyValidator completeJourneyValidator;

    @Autowired
    protected CardPreferencesDataService cardPreferencesDataService;

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
        model.addAttribute(PageAttribute.REASON_FOR_MISSING_TAP, this.selectListService.getSelectList(PageSelectList.MISSING_TOUCH));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView journeyDetailsWithLinkedStation(@ModelAttribute(value = PageCommand.COMPLETE_JOURNEY) CompleteJourneyCommandImpl cmd) {
        cmd.setPreferredStation(customerService.getPreferredStationId(getLoggedInUserCustomerId()));
        final JourneyDTO journeyDTO = journeyHistoryService.getIncompleteJourney(cmd.getCardId(), cmd.getLinkedTransactionTime(),
                        cmd.getLinkedStationKey());
        cmd.setJourney(journeyDTO);
        return new ModelAndView(PageView.ONLN_COMPLETE_JOURNEY, PageCommand.COMPLETE_JOURNEY, cmd);
    }

    @RequestMapping(method = RequestMethod.GET, params = "journeyId")
    public ModelAndView journeyDetailsForJourneyId(@ModelAttribute(value = PageCommand.COMPLETE_JOURNEY) CompleteJourneyCommandImpl cmd) {
        setPreferredStation(cmd);
        cmd.setPickUpStation(cmd.getPreferredStation());
        final JourneyDTO journeyDTO = incompleteJourneyService.getSSREligibleJourneyHistoryItem(cmd.getCardId(), cmd.getJourneyDate(),
                        cmd.getJourneyId());
        cmd.setJourney(journeyDTO);
        journeyHistoryService.resolveLinkedStationAndTransaction(cmd);
        return new ModelAndView(PageView.ONLN_COMPLETE_JOURNEY, PageCommand.COMPLETE_JOURNEY, cmd);
    }

    protected void setPreferredStation(CompleteJourneyCommandImpl cmd) {
        CardPreferencesDTO cardPreferencesDTO = cardPreferencesDataService.findByCardId(cmd.getCardId());
        if (null != cardPreferencesDTO) {
            cmd.setPreferredStation(cardPreferencesDTO.getStationId());
        } else {
            cmd.setPreferredStation(customerService.getPreferredStationId(getLoggedInUserCustomerId()));
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_COMPLETE_JOURNEY, method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute(PageCommand.COMPLETE_JOURNEY) CompleteJourneyCommandImpl cmd, BindingResult result,
                    final RedirectAttributes redirectAttributes) {
        completeJourneyValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return journeyDetailsWithLinkedStation(cmd);
        }
        final CompletedJourneyProcessingResult completedJourneyProcessingResult = refundService.processRefundsForCompletedJourney(cmd);
        setFlashAttribute(redirectAttributes, PageAttribute.COMPLETED_JOURNEY_RESPONSE, completedJourneyProcessingResult);
        return new ModelAndView(new RedirectView(PageUrl.INCOMPLETE_JOURNEY));
    }

}
