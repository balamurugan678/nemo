package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameter.TOKEN;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.ONLINE_SYSTEM_BASE_URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

@Controller
@RequestMapping(PageUrl.OYSTER_HOME)
public class OysterHomeController extends OnlineBaseController {
    @Autowired
    protected SingleSignOnSecurityService singleSignOnSecurityService;

    @ModelAttribute
    public void populateModelAttributes(Model model) {
        model.addAttribute(PageAttribute.RETURN_URL, systemParameterService.getParameterValue(ONLINE_SYSTEM_BASE_URI.code()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showOysterHome(HttpSession session, HttpServletRequest request, @CookieValue(value = TOKEN, required = false) String token,
                    @ModelAttribute(PageAttribute.IS_USER_LOGGED_IN) boolean isUserLoggedIn) {
        if (session != null && this.getFromSession(session, CartAttribute.CARD_ID) != null) {
            this.deleteAttributeFromSession(session, CartAttribute.CARD_ID);
        }
        if (session != null && this.getFromSession(session, CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA) != null) {
            this.deleteAttributeFromSession(session, CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
        }

        if (!isUserLoggedIn && isAutoLoginEligible(token)) {
            Object jsonUserDetail = singleSignOnSecurityService.checkSessionActive(token, request.getRequestedSessionId());
            if (singleSignOnSecurityService.autoLogin(token, jsonUserDetail, request)) {
                return new ModelAndView(new RedirectView(PageUrl.OYSTER_HOME));
            }
        }

        return new ModelAndView(PageView.OYSTER_HOME, PageCommand.LOGIN, new LoginCmdImpl());
    }
    
    protected boolean isAutoLoginEligible(String token) {
        return isSingleSignOnAuthenticationOn() && StringUtils.isNotBlank(token);
    }
}
