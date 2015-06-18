package com.novacroft.nemo.tfl.online.controller;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.CustomerRegistrationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * controller for customer account details
 *
 * @deprecated will be obsoleted by SSO
 */
@Controller
@RequestMapping(value = PageUrl.ACCOUNT_DETAILS)
@Deprecated
@SessionAttributes(PageCommand.CART)
public class AccountDetailsController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(AccountDetailsController.class);

    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected CustomerRegistrationValidator customerRegistrationValidator;
    @Autowired
    protected PAFService pafService;
    @Autowired
    protected PostcodeValidator postcodeValidator;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CountrySelectListService countrySelectListService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CardDataService cardDataService;
    
    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateCountrySelectList(Model model) {
        model.addAttribute(PageAttribute.COUNTRIES, this.countrySelectListService.getSelectList());
    }
    
    @ModelAttribute
    public void populateTitlesSelectList(Model model) {
        model.addAttribute(PageAttribute.TITLES, selectListService.getSelectList(PageSelectList.TITLES));
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewAccountDetails(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        String cardNumber = cmd.getCardNumber();
        cmd = new CartCmdImpl();
        cmd.setCardNumber(cardNumber);

        return new ModelAndView(PageView.ACCOUNT_DETAILS, PageCommand.CART, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_FIND_ADDRESS, method = RequestMethod.POST)
    public ModelAndView findAddress(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(PageView.ACCOUNT_DETAILS);
        postcodeValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            modelAndView
                    .addObject("addressesForPostcode", this.pafService.getAddressesForPostcodeSelectList(cmd.getPostcode()));
        }
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SELECT_ADDRESS, method = RequestMethod.POST)
    public ModelAndView selectAddress(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        if (isNotEmpty(cmd.getAddressForPostcode())) {
            cmd = (CartCmdImpl) this.pafService.populateAddressFromJson(cmd, cmd.getAddressForPostcode().replaceAll("&quot;","\""));
        }
        return new ModelAndView(PageView.ACCOUNT_DETAILS, PageCommand.CART, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_PROCESS, method = RequestMethod.POST)
    public ModelAndView processAccountDetails(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result,
                                              HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView(PageView.ACCOUNT_DETAILS);
        customerRegistrationValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            if (cmd.getPageName() != null) {
                return addCustomer(cmd, request, session);
            } else {
                return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
            }
        }
        return modelAndView;
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
    }

    protected void linkCartToCustomer(CartSessionData cartSessionData) {
        if (isNotCartInSession(cartSessionData)) {
            return;
        }
        CartDTO cartDTO = this.cartService.findById(cartSessionData.getCartId());
        cartDTO.setCustomerId(getLoggedInUserCustomerId());
        this.cartService.updateCart(cartDTO);
    }

    protected Boolean isCartInSession(CartSessionData cartSessionData) {
        return null != cartSessionData && null != cartSessionData.getCartId();
    }

    protected Boolean isNotCartInSession(CartSessionData cartSessionData) {
        return !isCartInSession(cartSessionData);
    }

    protected ModelAndView addCustomer(CartCmdImpl cmd, HttpServletRequest request, HttpSession session) {

        if (cmd.getPageName().equalsIgnoreCase(Page.CART)) {
            customerService.addCustomer(cmd);
            try {
                securityService.login(cmd.getUsername(), cmd.getNewPassword(), request);
            } catch (BadCredentialsException e) {
                return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
            }
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.SECURITY_QUESTION));
        } else if (cmd.getPageName().equalsIgnoreCase(Page.OPEN_ACCOUNT)) {
            customerService.addCustomer(cmd);
            try {
                securityService.login(cmd.getUsername(), cmd.getNewPassword(), request);
            } catch (BadCredentialsException e) {
                return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
            }
            this.customerService.createCard(this.securityService.getLoggedInCustomer().getId(), cmd.getCardNumber());
            CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
            linkCartToCustomer(cartSessionData);
            return new ModelAndView(isCartInSession(cartSessionData) ? getRedirectViewWithoutExposedAttributes(PageUrl.SECURITY_QUESTION)
                            : getRedirectViewWithoutExposedAttributes(PageUrl.DASHBOARD));
        }
        return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
    }
}
