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
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Auto top-up product purchase controller
 */
@Controller
@RequestMapping(value = PageUrl.AUTO_TOP_UP_PURCHASE)
public class AutoTopUpPurchaseController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(AutoTopUpPurchaseController.class);

    @Autowired
    protected LocationSelectListService locationSelectListService;
    @Autowired
    protected PaymentService paymentService;
    @Autowired
    protected PickUpLocationValidator pickUpLocationValidator;
    @Autowired
    protected CartService cartService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAutoTopUpPurchase(HttpSession session) {
        return new ModelAndView(PageView.AUTO_TOP_UP_PURCHASE, PageCommand.CART, new CartCmdImpl());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView autoTopUpPurchase(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                          BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(PageView.AUTO_TOP_UP_PURCHASE);
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        cmd.setCardId(cartDTO.getCardId());
        cartSessionData.setStationId(null);
        pickUpLocationValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            cartSessionData.setStationId(cmd.getStationId());
            return new ModelAndView(new RedirectView(PageUrl.CHECKOUT));
        }
        return modelAndView;
    }
}
