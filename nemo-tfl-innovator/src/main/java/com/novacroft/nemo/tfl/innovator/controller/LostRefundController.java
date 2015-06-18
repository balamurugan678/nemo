package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CART_ITEM_ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_TRADED_TICKET;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_UPDATE_CART_TOTAL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_VIEW_CART_USING_CUBIC;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_LOST_REFUND;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartValidator;

@Controller
@RequestMapping(value = Page.INV_LOST_REFUND)
@SessionAttributes(CART)
public class LostRefundController extends RefundController {
    static final Logger logger = LoggerFactory.getLogger(LostRefundController.class);
    public static final int HOTLIST_REASON_ID = 1;

    @Autowired
    protected GoodwillService goodwillService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected ProductDataService productDataService;
    @Autowired
    protected RefundCartValidator refundCartValidator;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    protected String getRefundType() {
        return INV_LOST_REFUND;
    }

    @Override
    protected String getCartTypeCode() {
        return CartType.LOST_REFUND.code();
    }

    @Override
    protected BaseValidator getValidator() {
        return refundCartValidator;
    }

    @InitBinder
    protected final void initBinder(final ServletRequestDataBinder binder) {
        super.initBinder(binder);
    }

    @ModelAttribute
    public void populateModelAttributes(Model model) {
        super.populateModelAttributes(model);
    }


    @RequestMapping(params = TARGET_ACTION_VIEW_CART_USING_CUBIC, method = RequestMethod.GET)
    public ModelAndView viewCartUsingCubic(HttpSession session, @RequestParam("id") String cardNumber) {
        return super.viewCartUsingCubic(session, cardNumber);
    }

    @RequestMapping(params = TARGET_ACTION_UPDATE_CART_TOTAL, method = RequestMethod.POST)
    public ModelAndView updateCartTotal(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                        BindingResult result) {
        return super.updateCartTotal(session, cmd, result);
    }

    @RequestMapping(params = TARGET_ACTION_ADD_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView addTravelCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                      BindingResult result) {
        return super.addTravelCard(session, cmd, result);
    }

    @RequestMapping(params = TARGET_ACTION_DELETE_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView deleteTravelCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                         @RequestParam(value = CART_ITEM_ID) int cartItemId) {
        return super.deleteTravelCard(session, cmd, cartItemId);
    }

    @RequestMapping(params = TARGET_ACTION_ADD_GOODWILL, method = RequestMethod.POST)
    public ModelAndView addGoodwill(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                    BindingResult result, Model model) {
        return super.addGoodwill(session, cmd, result, model);
    }

    @RequestMapping(params = TARGET_ACTION_DELETE_GOODWILL, method = RequestMethod.POST)
    public ModelAndView deleteGoodwill(HttpSession session, @ModelAttribute(CART) CartCmdImpl cmd,
                                       @RequestParam(value = CART_ITEM_ID) int cartItemId, Model model) {
        return super.deleteGoodwill(session, cmd, cartItemId, model);
    }

    @RequestMapping(params = TARGET_ACTION_REFUND, method = RequestMethod.POST)
    public ModelAndView refund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        return super.refund(session, cmd, result);
    }

    @RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancelRefund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                     BindingResult result) {
        return super.cancelRefund(session, cmd, result);
    }
    
    @Override
    @RequestMapping(params = TARGET_ACTION_ADD_TRADED_TICKET, method = RequestMethod.POST)
    public ModelAndView addTradedTicket(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result,
                    @RequestParam(value = CART_ITEM_ID) long cartItemId) {
        return super.addTradedTicket(session, cmd, result,cartItemId);
    }
}