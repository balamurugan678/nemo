package com.novacroft.nemo.tfl.online.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryItemValidator;

/**
 * Controller for journey history item
 */
@Controller
@RequestMapping(PageUrl.JOURNEY_HISTORY_ITEM)
@SessionAttributes(PageCommand.JOURNEY_HISTORY)
public class JourneyHistoryItemController extends OnlineBaseController {

    static final Logger logger = LoggerFactory.getLogger(JourneyHistoryItemController.class);

    @Autowired
    protected JourneyHistoryItemValidator journeyHistoryItemValidator;
    @Autowired
    protected JourneyHistoryService journeyHistoryService;
    @Autowired
    protected CardDataService cardDataService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtil.createShortDateFormatter(), true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage(@ModelAttribute(PageCommand.JOURNEY_HISTORY) JourneyHistoryCmdImpl historyCmd,
                    @ModelAttribute(PageCommand.JOURNEY_HISTORY_ITEM) JourneyHistoryItemCmdImpl cmd, BindingResult result) {
        cmd.setCardId(historyCmd.getCardId());
        cmd.setCardNumber(historyCmd.getCardNumber());
        journeyHistoryItemValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            try {
                cmd.setJourney(journeyHistoryService.getJourneyHistoryItem(cmd.getCardId(), cmd.getJourneyDate(), cmd.getJourneyId()));
            } catch (ServiceNotAvailableException e) {
                result.reject(ContentCode.SERVICE_BUSY.errorCode());
            }
        }
        return new ModelAndView(PageView.JOURNEY_HISTORY_ITEM, PageCommand.JOURNEY_HISTORY_ITEM, cmd);
    }

}
