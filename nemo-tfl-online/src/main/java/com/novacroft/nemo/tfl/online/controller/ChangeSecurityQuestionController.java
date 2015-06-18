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
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;

/**
 * Controller for changing the security details of an oyster card.
 */
@Controller
@RequestMapping(value = PageUrl.CHANGE_SECURITY_QUESTION)
public class ChangeSecurityQuestionController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(ChangeSecurityQuestionController.class);

    @Autowired
    protected SecurityQuestionService securityQuestionService;
    @Autowired
    protected SecurityQuestionValidator securityQuestionValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CardDataService cardDataService;

    @ModelAttribute
    public void populateSecurityQuestionsSelectList(Model model) {
        model.addAttribute(PageAttribute.SECURITY_QUESTIONS, selectListService.getSelectList(PageSelectList.SECURITY_QUESTIONS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showChangeSecurityQuestion(@ModelAttribute(PageCommand.SECURITY_QUESTION) SecurityQuestionCmdImpl cmd,
                    HttpSession session) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setCardId(cardId);
        cmd.setCardNumber(cardDataService.findById(cardId).getCardNumber());
        cmd = securityQuestionService.getSecurityQuestionDetails(cmd.getCardId());
        return new ModelAndView(PageView.CHANGE_SECURITY_QUESTION, PageCommand.SECURITY_QUESTION, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute(PageCommand.SECURITY_QUESTION) SecurityQuestionCmdImpl cmd,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = null;
        securityQuestionValidator.validate(cmd, result);
        if (result.hasErrors()) {
        	modelAndView = new ModelAndView(PageView.CHANGE_SECURITY_QUESTION);
            modelAndView.addObject(PageCommand.SECURITY_QUESTION, cmd);
        } else {
            cmd = securityQuestionService.updateSecurityQuestionDetails(cmd);
            setFlashStatusMessage(redirectAttributes, ContentCode.CHANGE_SECURITY_QUESTION_UPDATE_SUCCESSFUL.textCode());
            modelAndView = new ModelAndView(PageView.CARD_SECURITY_QUESTION_SUCCESS_UPDATE);
        }
        return modelAndView;
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }
}
