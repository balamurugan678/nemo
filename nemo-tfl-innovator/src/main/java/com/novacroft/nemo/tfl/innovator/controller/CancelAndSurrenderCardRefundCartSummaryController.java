package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_BACK_TO_CUSTOMER_PAGE;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.controller.BaseController;

@Controller
@RequestMapping(value = Page.INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY)
@SessionAttributes(CART)
public class CancelAndSurrenderCardRefundCartSummaryController extends BaseController {
    static final Logger logger = LoggerFactory.getLogger(CancelAndSurrenderCardRefundCartSummaryController.class);
    public static final int HOTLIST_REASON_ID = 1;

    @Autowired
    protected HotlistCardService hotlistCardService;

    @Autowired
    protected AddUnattachedCardService addUnattachedCardService;

    @InitBinder
    protected final void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        final CustomDateEditor editor = new CustomDateEditor(DateUtil.createShortDateFormatter(), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        return new ModelAndView(INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY, CART, cmd);
    }

    @RequestMapping(params = TARGET_ACTION_BACK_TO_CUSTOMER_PAGE, method = RequestMethod.POST)
    public ModelAndView refundFailedCard(@ModelAttribute(CART) CartCmdImpl cmd) {

        PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetails(cmd.getCardNumber());

        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER));
        redirectView.addObject("id", personalDetailsByCustomerId.getCustomerId());
        return redirectView;
    }
}
