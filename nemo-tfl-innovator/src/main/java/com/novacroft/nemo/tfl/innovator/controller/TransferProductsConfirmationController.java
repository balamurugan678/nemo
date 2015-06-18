package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_BACK_TO_CUSTOMER_PAGE;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Controller
@RequestMapping(PageUrl.TRANSFER_CONFIRMATION)
public class TransferProductsConfirmationController {

    @Autowired
    protected AddUnattachedCardService addUnattachedCardService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showConfirmation(RedirectAttributes redirectAttributes) {
        return new ModelAndView(PageView.TRANSFER_CONFIRMATION);
    }

    @RequestMapping(params = TARGET_ACTION_BACK_TO_CUSTOMER_PAGE, method = RequestMethod.POST)
    public ModelAndView backToCustomerPage(HttpSession session) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetails(cartSessionData.getSourceCardNumber());
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER));
        redirectView.addObject("id", personalDetailsByCustomerId.getCustomerId());
        CartUtil.removeCartSessionDataDTOFromSession(session);
        return redirectView;
    }
}
