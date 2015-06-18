package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentRetrievalService;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.innovator.controller.BaseController;

@Controller
@RequestMapping(value = Page.FULFIL_ORDER_CONFIRMATION)
@SessionAttributes(PageCommand.FULFILMENT)
public class FulfilOrderConfirmationController extends BaseController {

    @Autowired
    protected FulfilmentRetrievalService fulfilmentRetrievalService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadConfirmation(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd cmd, HttpSession session) {
        cmd.setCurrentQueue(OrderStatus.FULFILMENT_PENDING.code());
        cmd.setCurrentQueueCount(fulfilmentRetrievalService.getNumberOfOrdersInQueue(OrderStatus.find(cmd.getCurrentQueue())));
        return new ModelAndView(PageView.FULFIL_ORDER_CONFIRMATION, PageCommand.FULFILMENT, cmd);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView retrieveNextInQeue(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd fulfilmentCmd, Model model, HttpSession session, SessionStatus status) {
        OrderDTO fulfilPendingOrder = fulfilmentRetrievalService.getFirstOrderPendingFulfilmentFromQueue(OrderStatus.find(fulfilmentCmd.getCurrentQueue()));
        fulfilmentCmd.setOrder(fulfilPendingOrder);
        fulfilmentCmd.setFulfiledOysterCardNumber(StringUtils.EMPTY);
        return new ModelAndView(new RedirectView(PageUrl.FULFIL_ORDER), PageCommand.FULFILMENT, fulfilmentCmd);
    }
}
