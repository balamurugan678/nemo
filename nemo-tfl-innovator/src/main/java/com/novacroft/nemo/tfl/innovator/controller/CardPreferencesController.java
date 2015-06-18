package com.novacroft.nemo.tfl.innovator.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.form_validator.EmailPreferencesValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;

@Controller
@RequestMapping(value = Page.INV_CARD_PREFERENCES)
public class CardPreferencesController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CardPreferencesController.class);
    
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    
    @Autowired
    protected LocationSelectListService locationSelectListService;
    
    @Autowired
    protected SelectListService selectListService;
    
    @Autowired
    protected EmailPreferencesValidator emailPreferencesValidator;
    
    @Autowired
    protected SelectStationValidator selectStationValidator;
    
    @Autowired
    protected MessageSource messageSource;

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
    }
    
    @ModelAttribute
    public void populateStatementEmailFrequenciesSelectList(Model model) {
        model.addAttribute(PageAttribute.STATEMENT_EMAIL_FREQUENCIES,
                selectListService.getSelectList(PageSelectList.STATEMENT_EMAIL_FREQUENCIES));
    }

    @ModelAttribute
    public void populateAttachmentTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.STATEMENT_ATTACHMENT_TYPES,
                selectListService.getSelectList(PageSelectList.STATEMENT_ATTACHMENT_TYPES));
    }
    
    @RequestMapping(params = "id", method = RequestMethod.GET)
    public ModelAndView load(@RequestParam("id") String cardNumber, HttpServletRequest request) {
        logger.debug(cardNumber);
        CardPreferencesCmdImpl cmd = this.cardPreferencesService.getPreferencesByCardNumber(cardNumber);
        return new ModelAndView(PageView.INV_CARD_PREFERENCES, PageCommand.CARD_PREFERENCES, cmd);
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(PageCommand.CARD_PREFERENCES) CardPreferencesCmdImpl cmd, BindingResult result) {
        this.emailPreferencesValidator.validate(cmd, result);
        ModelAndView modelAndView = new ModelAndView(PageView.INV_CARD_PREFERENCES);
        if (!result.hasErrors()) {
            this.cardPreferencesService.updatePreferences(cmd);
            modelAndView.addObject("message",
                            messageSource.getMessage(ContentCode.CHANGE_CARD_PREFERENCES_UPDATE_SUCCESSFUL.textCode(), null, "", null));
            modelAndView.addObject(PageCommand.CARD_PREFERENCES, this.cardPreferencesService.getPreferencesByCardId(cmd.getCardId()));
        } else {
            modelAndView.addObject(PageCommand.CARD_PREFERENCES, getCardPreferencesCmdForValidationError(cmd));
        }

        return modelAndView;
    }

    protected CardPreferencesCmdImpl getCardPreferencesCmdForValidationError(CardPreferencesCmdImpl cmd) {
        CardPreferencesCmdImpl cardPreferencesFromDB = this.cardPreferencesService.getPreferencesByCardId(cmd.getCardId());
        cmd.setCardNumber(cardPreferencesFromDB.getCardNumber());
        cmd.setEmailAddress(cardPreferencesFromDB.getEmailAddress());
        return cmd;
    }
        
}
