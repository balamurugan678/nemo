package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.CUSTOM_PERIOD_OPTION_CODE;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator;

@Controller
@RequestMapping(value = Page.JOURNEY_HISTORY)
public class JourneyHistoryController {
    static final Logger logger = LoggerFactory.getLogger(JourneyHistoryController.class);

    @Autowired
    protected JourneyHistoryValidator journeyHistoryValidator;
    @Autowired
    protected JourneyHistoryService journeyHistoryService;

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(DateUtil.createShortDateFormatter(), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage(@RequestParam(value = PageParameter.ID, required = false) Long cardId,
                                 @RequestParam(CARD_NUMBER) String cardNumber) {
        JourneyHistoryCmdImpl cmd = new JourneyHistoryCmdImpl();
        cmd.setCardId(cardId != null ? cardId : 0L);
        cmd.setCardNumber(cardNumber);
        return new ModelAndView(PageView.JOURNEY_HISTORY, PageCommand.JOURNEY_HISTORY, cmd);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView showPageAndAddIdToModel(@RequestParam(value = PageParameter.ID, required = false) Long cardId) {
        JourneyHistoryCmdImpl cmd = new JourneyHistoryCmdImpl();
        cmd.setCardId(cardId != null ? cardId : 0L);
        ModelAndView modelAndView = new ModelAndView(PageView.JOURNEY_HISTORY, PageCommand.JOURNEY_HISTORY, cmd);
        modelAndView.addObject(PageParameter.ID, cardId);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_GET_DETAILS, method = RequestMethod.POST)
    public ModelAndView showJourneyHistory(@ModelAttribute(PageCommand.JOURNEY_HISTORY) JourneyHistoryCmdImpl cmd,
                                           BindingResult result) {
    	setDefaultWeekRangeOnCommand(cmd);
        this.journeyHistoryValidator.validate(cmd, result);
        cmd.setJourneyHistory(null);
        if (!result.hasErrors()) {
            try {
                cmd.setJourneyHistory(
                        this.journeyHistoryService.getJourneyHistory(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate()));
            } catch (ServiceNotAvailableException e) {
                result.reject(ContentCode.SERVICE_BUSY.errorCode());
            }
        }
        return new ModelAndView(PageView.JOURNEY_HISTORY, PageCommand.JOURNEY_HISTORY, cmd);
    }
    
    protected void setDefaultWeekRangeOnCommand(JourneyHistoryCmdImpl cmd) {
        if (null == cmd.getWeekNumberFromToday()) {
            cmd.setWeekNumberFromToday(CUSTOM_PERIOD_OPTION_CODE);
        }
    }
}
