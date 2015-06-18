package com.novacroft.nemo.tfl.online.controller;

import javax.servlet.http.HttpSession;

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
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.EmailPreferencesValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;

/**
 * controller for changing personal details
 */
@Controller
@RequestMapping(PageUrl.CHANGE_CARD_PREFERENCES)
public class ChangeCardPreferencesController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(ChangeCardPreferencesController.class);

    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    @Autowired
    protected SelectCardValidator selectCardValidator;
    @Autowired
    protected EmailPreferencesValidator emailPreferencesValidator;
    @Autowired
    protected LocationSelectListService locationSelectListService;
    @Autowired
    protected SelectStationValidator selectStationValidator;
    @Autowired
    protected CardUpdateService cardUpdateService;


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

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showChangeCardPreferences(HttpSession session) {

        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);

        if (cardId != null) {
            CardPreferencesCmdImpl cmd = this.cardPreferencesService.getPreferences(cardId, this.securityService.getLoggedInUsername());
            return new ModelAndView(PageView.CHANGE_CARD_PREFERENCES, PageCommand.CARD_PREFERENCES, cmd);
        } else {
            return new ModelAndView(PageView.CHANGE_CARD_PREFERENCES, PageCommand.CARD_PREFERENCES,
                    new CardPreferencesCmdImpl());
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute(PageCommand.CARD_PREFERENCES) CardPreferencesCmdImpl cmd,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        this.selectStationValidator.validate(cmd, result);
        this.emailPreferencesValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.CHANGE_CARD_PREFERENCES, PageCommand.CARD_PREFERENCES, cmd);
        } else {
            this.cardPreferencesService.updatePreferences(cmd);
            setFlashAttribute(redirectAttributes, PageAttribute.CONFIRMATION_MESSAGE, ContentCode.CHANGE_CARD_PREFERENCES_UPDATE_SUCCESSFUL.textCode());
            setFlashAttribute(redirectAttributes, PageAttribute.CONFIRMATION_BREADCRUMB ,Page.CHANGE_CARD_PREFERENCES);
            return new ModelAndView(new RedirectView(PageUrl.CONFIRMATION));
        }
    }
 
    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }
}
