package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
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

import javax.servlet.http.HttpSession;

/**
 * controller for verify security details
 */
@Controller
@RequestMapping(value = PageUrl.VERIFY_SECURITY_QUESTION)
public class VerifySecurityQuestionController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(VerifySecurityQuestionController.class);

    @Autowired
    protected SecurityQuestionService securityQuestionService;
    @Autowired
    protected SecurityQuestionValidator securityQuestionValidator;
    @Autowired
    protected SelectListService selectListService;

    @ModelAttribute
    public void populateSecurityQuestionsSelectList(Model model) {
        model.addAttribute(PageAttribute.SECURITY_QUESTIONS,
                selectListService.getSelectList(PageSelectList.SECURITY_QUESTIONS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewSecurityQuestion(@ModelAttribute(PageCommand.SECURITY_QUESTION) SecurityQuestionCmdImpl cmd) {
        return new ModelAndView(PageView.VERIFY_SECURITY_QUESTION, PageCommand.SECURITY_QUESTION, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_VERIFY, method = RequestMethod.POST)
    public ModelAndView verifySecurityQuestion(HttpSession session,
                                               @ModelAttribute(PageCommand.SECURITY_QUESTION) SecurityQuestionCmdImpl cmd,
                                               BindingResult result, final RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView(PageView.VERIFY_SECURITY_QUESTION);
        securityQuestionValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            CustomerDTO customerDTO = securityService.getLoggedInCustomer();
            if (securityQuestionService.verifySecurityQuestionDetails(cmd, customerDTO.getId())) {
                setFlashAttribute(redirectAttributes, PageAttribute.CONFIRMATION_MESSAGE,
                        ContentCode.ADD_EXISTING_CARD_TO_ACCOUNT_UPDATE_SUCCESSFUL.textCode());
                setFlashAttribute(redirectAttributes, PageAttribute.CONFIRMATION_BREADCRUMB, Page.ADD_EXISTING_CARD_TO_ACCOUNT);
                return new ModelAndView(new RedirectView(PageUrl.CONFIRMATION));
            }
        }
        return modelAndView;
    }

}
