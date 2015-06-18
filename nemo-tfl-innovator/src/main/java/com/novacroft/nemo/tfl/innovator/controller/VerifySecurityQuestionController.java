package com.novacroft.nemo.tfl.innovator.controller;

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
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;


@Controller
@RequestMapping(value = Page.VERIFY_SECURITY_QUESTION)
public class VerifySecurityQuestionController extends BaseController {
    static final Logger logger = LoggerFactory.getLogger(VerifySecurityQuestionController.class);

    @Autowired
    protected SecurityQuestionService securityQuestionService;
    @Autowired
    protected SecurityQuestionValidator securityQuestionValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CartService cartService;

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
                                               BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(PageView.VERIFY_SECURITY_QUESTION);
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        securityQuestionValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            securityQuestionService.addSecurityQuestionDetails(cmd, cartDTO.getCustomerId(), cartSessionData);
            return new ModelAndView(new RedirectView(PageUrl.CHECKOUT));
        }
        return modelAndView;    
    }

}
