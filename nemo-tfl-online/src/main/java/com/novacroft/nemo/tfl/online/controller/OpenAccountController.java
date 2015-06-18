package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * controller for open account
 */
@Controller
@RequestMapping(value = PageUrl.OPEN_ACCOUNT)
@SessionAttributes(PageCommand.CART)
public class OpenAccountController extends OnlineBaseController {
    @Autowired
    protected OysterCardValidator oysterCardValidator;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewOpenAccount() {
        return new ModelAndView(PageView.OPEN_ACCOUNT, PageCommand.CART, new CartCmdImpl());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_VALIDATE_CARD_NUMBER, method = RequestMethod.POST)
    public ModelAndView validateOysterCardNumber(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                                 BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(PageView.OPEN_ACCOUNT);
        oysterCardValidator.validate(cmd, result);

        if (result.hasErrors()) {
            if (result.getFieldError("cardNumber") != null &&
                    result.getFieldError("cardNumber").toString().contains(PrivateError.CARD_NUMBER_EXIST.message())) {
                modelAndView = new ModelAndView(PageView.OPEN_ACCOUNT_EXIST);
            }
        } else {
            cmd.setPageName(Page.OPEN_ACCOUNT);
            return new ModelAndView(new RedirectView(PageUrl.ACCOUNT_DETAILS));
        }
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.OYSTER_HOME));
    }

}