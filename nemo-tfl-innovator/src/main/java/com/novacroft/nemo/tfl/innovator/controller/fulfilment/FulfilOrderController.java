package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentService;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.innovator.controller.BaseController;

@Controller
@RequestMapping(value = Page.FULFIL_ORDER)
@SessionAttributes(PageCommand.FULFILMENT)
public class FulfilOrderController extends BaseController {
    
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected OysterCardValidator oysterCardValidator;
    @Autowired
    protected FulfilmentService fulfilmentService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadOrderForFulfilment(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd fulfilmentCmd, HttpSession session) {
        return new ModelAndView(PageView.FULFIL_ORDER, PageCommand.FULFILMENT, fulfilmentService.loadOrderDetails(fulfilmentCmd));
    } 
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_FULFIL_ORDER_CONFIRM, method = RequestMethod.POST)
    public ModelAndView fulfilOrder(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd fulfilmentCmd, HttpSession session,BindingResult result) {
        oysterCardValidator.validate(fulfilmentCmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.FULFIL_ORDER, PageCommand.FULFILMENT, fulfilmentCmd);
        } else {
            OrderDTO order = fulfilmentService.getOrderFromOrderNumber(fulfilmentCmd.getOrder().getOrderNumber());
            fulfilmentService.associateFulfiledOysterCardNumberWithTheOrder(order, fulfilmentCmd.getFulfiledOysterCardNumber());
            fulfilmentCmd.setCurrentQueue(order.getStatus());
            fulfilmentService.updateOrderStatus(order, OrderStatus.FULFILLED.code());
            ModelAndView redirectToSummaryPage = new ModelAndView(new RedirectView(PageUrl.FULFIL_ORDER_CONFIRMATION), PageCommand.FULFILMENT,
                            fulfilmentCmd);
            return redirectToSummaryPage;
        }
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd fulfilmentCmd, HttpSession session) {
        ModelAndView redirectToHomePage = new ModelAndView(new RedirectView(PageUrl.FULFILMENT_HOME), PageCommand.FULFILMENT,
                        fulfilmentCmd);
        return redirectToHomePage;
    }
}
