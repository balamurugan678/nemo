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
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * controller for add security details
 */
@Controller
@RequestMapping(value = PageUrl.SECURITY_QUESTION)
public class AddSecurityQuestionController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(AddSecurityQuestionController.class);

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
        return new ModelAndView(PageView.SECURITY_QUESTION, PageCommand.SECURITY_QUESTION, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD, method = RequestMethod.POST)
    public ModelAndView addSecurityQuestion(HttpSession session,
                                            @ModelAttribute(PageCommand.SECURITY_QUESTION) SecurityQuestionCmdImpl cmd,
                                            BindingResult result, final RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView(PageView.SECURITY_QUESTION);
        securityQuestionValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
            securityQuestionService.addSecurityQuestionDetails(cmd, getLoggedInUserCustomerId(), cartSessionData);
            return redirectModelAndView(session, redirectAttributes, cartSessionData, cmd);
        }
        return modelAndView;
    }

    protected ModelAndView redirectModelAndView(HttpSession session, final RedirectAttributes redirectAttributes,
                                                CartSessionData cartSessionData, SecurityQuestionCmdImpl cmd) {
        if (cartSessionData != null && Page.CART.equals(cartSessionData.getPageName())) {
            return redirectCheckoutPage(session, cartSessionData);
        } else if (null != cartSessionData && Page.TRANSFER_PRODUCT.equalsIgnoreCase(cartSessionData.getPageName())) {
            cartSessionData.setTargetCardNumber(cmd.getCardNumber());
            return redirectCollectPurchasePage(session, cartSessionData);
        } else {
            return redirectConfirmationPage(redirectAttributes);
        }
    }

    private ModelAndView redirectConfirmationPage(final RedirectAttributes redirectAttributes) {
        setFlashAttribute(redirectAttributes, PageAttribute.CONFIRMATION_MESSAGE,
                ContentCode.ADD_EXISTING_CARD_TO_ACCOUNT_UPDATE_SUCCESSFUL.textCode());
        setFlashAttribute(redirectAttributes, PageAttribute.CONFIRMATION_BREADCRUMB, Page.ADD_EXISTING_CARD_TO_ACCOUNT);
        return new ModelAndView(new RedirectView(PageUrl.CONFIRMATION));
    }

    protected ModelAndView redirectCollectPurchasePage(HttpSession session, CartSessionData cartSessionData) {
        cartSessionData.setTransferProductMode(Boolean.TRUE);
        cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        return new ModelAndView(new RedirectView(PageUrl.COLLECT_PURCHASE));
    }

    private ModelAndView redirectCheckoutPage(HttpSession session, CartSessionData cartSessionData) {
        cartSessionData.setPageName(Page.SECURITY_QUESTION);
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        return new ModelAndView(new RedirectView(PageUrl.CHECKOUT));
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.DASHBOARD));
    }

}
