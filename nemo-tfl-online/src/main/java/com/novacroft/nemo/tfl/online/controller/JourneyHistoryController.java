package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.getDayBefore;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator.CUSTOM_PERIOD_OPTION_CODE;
import static com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator.LAST_SEVEN_DAYS_OPTION_CODE;
import static com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator.LAST_WEEK_OPTION_CODE;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.createDatedFileName;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.impl.journey_history.JourneyHistoryServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.JourneyHistoryOutput;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.util.JourneyStatementDateUtil;

/**
 * Controller for journey history
 */
@Controller
@RequestMapping(PageUrl.JOURNEY_HISTORY)
@SessionAttributes(PageCommand.JOURNEY_HISTORY)
public class JourneyHistoryController extends OnlineBaseController {

    static final Logger logger = LoggerFactory.getLogger(JourneyHistoryController.class);

    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    @Autowired
    protected SelectCardValidator selectCardValidator;
    @Autowired
    protected JourneyHistoryValidator journeyHistoryValidator;
    @Autowired
    protected JourneyHistoryService journeyHistoryService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected SelectListService selectListService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtil.createShortDateFormatter(), true));
    }

    @ModelAttribute
    public void populateCardsSelectModelAttribute(Model model) {
        model.addAttribute(PageAttribute.CARDS, cardSelectListService.getCardsSelectList(this.securityService.getLoggedInUsername()));
    }

    @ModelAttribute
    public void populateWeekRangeSelectModelAttribute(Model model) {
        model.addAttribute(PageAttribute.WEEK_START_END_DATES, journeyHistoryService.getWeekRangeSelectListDTO());
    }

    @ModelAttribute
    public void populateCardIdModelAttributeFromSession(Model model, HttpSession session) {
        model.addAttribute(CartAttribute.CARD_ID, getFromSession(session, CartAttribute.CARD_ID));
    }

    @ModelAttribute
    public void populateStatementEmailFrequenciesSelectList(Model model) {
        model.addAttribute(PageAttribute.STATEMENT_EMAIL_FREQUENCIES,
                selectListService.getSelectList(PageSelectList.STATEMENT_EMAIL_FREQUENCIES));
    }
    
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage(@ModelAttribute(CartAttribute.CARD_ID) Long cardId,
                    @ModelAttribute(PageAttribute.CARDS) SelectListDTO cardSelectList,
                    @ModelAttribute(PageAttribute.WEEK_START_END_DATES) SelectListDTO weekRangeSelectList,
                    @ModelAttribute(PageAttribute.STATEMENT_EMAIL_FREQUENCIES) SelectListDTO statementEmailSelectList,JourneyHistoryCmdImpl cmd,
                    BindingResult result, Model model) {
        cmd.setCardId(null);
        setDefaultCardIdOnCommand(cardId, cmd, cardSelectList);
        setEmailStatementPreferenceOnCommand(cmd, cardSelectList);
        if (null != cmd.getCardId()) {
            setDefaultWeekRangeOnCommand(cmd, weekRangeSelectList);
            return showJourneyHistory(cmd, result, model);
        }
        return new ModelAndView(PageView.JOURNEY_HISTORY, PageCommand.JOURNEY_HISTORY, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_GET_DETAILS, method = RequestMethod.GET)
    public ModelAndView reloadJourneyHistory(@ModelAttribute(PageCommand.JOURNEY_HISTORY) JourneyHistoryCmdImpl cmd, BindingResult result, Model model) {
        return showJourneyHistory(cmd, result, model);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_GET_DETAILS, method = RequestMethod.POST)
    public ModelAndView showJourneyHistory(JourneyHistoryCmdImpl cmd, BindingResult result, Model model) {

        this.selectCardValidator.validate(cmd, result);
        this.journeyHistoryValidator.validate(cmd, result);

        if (!result.hasErrors() && doesCardIdBelongToUser(cmd.getCardId())) {
            try {
                JourneyHistoryDTO historyDTO = getJourneyHistoryData(cmd);
                cmd.setCardNumber(historyDTO.getCardNumber());
                model.addAttribute(PageAttribute.JOURNEY_HISTORY_DTO, historyDTO);
            } catch (ServiceNotAvailableException e) {
                result.reject(ContentCode.SERVICE_BUSY.errorCode());
            }
        }
        return new ModelAndView(PageView.JOURNEY_HISTORY, PageCommand.JOURNEY_HISTORY, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_PDF, method = RequestMethod.GET)
    public void downloadAsPDF(@ModelAttribute(PageCommand.JOURNEY_HISTORY) JourneyHistoryCmdImpl cmd, HttpServletResponse response,
                    OutputStream outputStream) {
        if (doesCardIdBelongToUser(cmd.getCardId())) {
            JourneyHistoryDTO dto = getJourneyHistoryData(cmd);
            streamFile(response, outputStream, getPdfFileName(cmd.getCardId()), JourneyHistoryOutput.PDF.contentType(),
                            this.journeyHistoryService.getJourneyHistoryAsPdf(dto));
        } else {
            logAndThrowIllegalArgumentException(cmd.getCardId());
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CSV, method = RequestMethod.GET)
    public void downloadAsCSV(@ModelAttribute(PageCommand.JOURNEY_HISTORY) JourneyHistoryCmdImpl cmd, HttpServletResponse response,
                    OutputStream outputStream) {
        if (doesCardIdBelongToUser(cmd.getCardId())) {
            JourneyHistoryDTO dto = getJourneyHistoryData(cmd);
            streamFile(response, outputStream, getCsvFileName(cmd.getCardId()), JourneyHistoryOutput.CSV.contentType(),
                            journeyHistoryService.getJourneyHistoryAsCsv(dto));
        } else {
            logAndThrowIllegalArgumentException(cmd.getCardId());
        }
    }

    protected String getCsvFileName(Long cardId) {
        return getFileName(cardId, JourneyHistoryOutput.CSV.code());
    }

    protected String getPdfFileName(Long cardId) {
        return getFileName(cardId, JourneyHistoryOutput.PDF.code());
    }

    protected String getFileName(Long cardId, String suffix) {
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        return createDatedFileName(cardDTO.getCardNumber(), suffix);
    }

    protected void setDefaultCardIdOnCommand(Long cardId, JourneyHistoryCmdImpl cmd, SelectListDTO cardSelectList) {
        if (cardSelectList.getOptions().size() == 1) {
            cmd.setCardId(new Long(cardSelectList.getOptions().iterator().next().getValue()));
        } else if (null != cardId) {
            cmd.setCardId(cardId);
        }
    }

    
    protected void setEmailStatementPreferenceOnCommand(JourneyHistoryCmdImpl cmd, SelectListDTO cardSelectList) {
         if (cardSelectList.getOptions().size() == 1) {
             CardPreferencesCmdImpl cardPreferencesCmd = cardPreferencesService
                     .getPreferences(new Long(cardSelectList.getOptions().iterator().next().getValue()),
                             this.securityService.getLoggedInUsername());
             cmd.setEmailStatementPreference(cardPreferencesCmd.getEmailFrequency());
         } 
         else {
             cmd.setEmailStatementPreference("");
         }
    }
    
    protected void setDefaultWeekRangeOnCommand(JourneyHistoryCmdImpl cmd, SelectListDTO weekRangeSelectList) {
        if (null == cmd.getWeekNumberFromToday()) {
            cmd.setWeekNumberFromToday(Integer.valueOf(weekRangeSelectList.getOptions().get(0).getValue()));
        }
    }

    protected JourneyHistoryDTO getJourneyHistoryData(JourneyHistoryCmdImpl cmd) {
        JourneyHistoryDTO dto = null;
        Integer weekNumberFromToday = cmd.getWeekNumberFromToday();
        if (null != weekNumberFromToday && weekNumberFromToday != CUSTOM_PERIOD_OPTION_CODE) {
            cmd = computeAndSetStartDateAndEndDate(cmd, weekNumberFromToday);
        }
        Date startDate = cmd.getStartDate();
        Date endDate = cmd.getEndDate();
        dto = this.journeyHistoryService.getJourneyHistory(cmd.getCardId(), startDate, endDate);
        return dto;
    }

    protected JourneyHistoryCmdImpl computeAndSetStartDateAndEndDate(JourneyHistoryCmdImpl cmd, Integer weekNumberFromToday) {
        Date today = new Date();
        if (weekNumberFromToday == LAST_WEEK_OPTION_CODE) {
            cmd.setStartDate(DateUtil.addDaysToDate(new Date(), JourneyHistoryServiceImpl.MINUS_56_DAYS));
            cmd.setEndDate(JourneyStatementDateUtil.getEndOfWeek(cmd.getStartDate()));
        } else if (weekNumberFromToday == LAST_SEVEN_DAYS_OPTION_CODE) {
            Date yesterday = DateUtil.getDayBefore(today);
            cmd.setStartDate(DateUtil.getSevenDaysBefore(yesterday));
            cmd.setEndDate(getDayBefore(parse(formatDate(new Date()))));
        } else {
            cmd.setStartDate(getStartDate(today, weekNumberFromToday));
            cmd.setEndDate(JourneyStatementDateUtil.getEndOfWeek(cmd.getStartDate()));
        }
        return cmd;
    }

    protected Date getStartDate(Date today, Integer weekNumberFromToday) {
        Date startDate;
        if (JourneyStatementDateUtil.isMonday(today)) {
            startDate = JourneyStatementDateUtil.getStartOfWeek((weekNumberFromToday + 1) * -1);
        } else {
            startDate = JourneyStatementDateUtil.getStartOfWeek(weekNumberFromToday * -1);
        }
        return startDate;
    }

    protected boolean doesCardIdBelongToUser(Long cardId) {
        List<CardDTO> cardsBelongingToUser = cardDataService.findByCustomerId(this.getCustomer().getId());
            for (CardDTO card : cardsBelongingToUser) {
                if (card.getId().equals(cardId)) {
                    return true;
                }
            }
        return false;

    }

    private void logAndThrowIllegalArgumentException(Long cardId) {
        String errorMessage = String.format(PrivateError.CARD_DETAILS_NOT_ASSIGNED_TO_USER.message(), cardId);
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }

}