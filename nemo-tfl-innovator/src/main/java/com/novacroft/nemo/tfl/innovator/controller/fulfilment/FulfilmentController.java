package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_FULFILMENT_PENDING_QUEUE;

import javax.servlet.http.HttpSession;

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
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentService;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.innovator.controller.BaseController;

@Controller
@RequestMapping(value = Page.FULFILMENT_HOME)
@SessionAttributes(PageCommand.FULFILMENT)
public class FulfilmentController extends BaseController {
    
    @Autowired
    protected FulfilmentRetrievalService fulfilmentRetrievalService;
    @Autowired
    protected FulfilmentService fulfilmentService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showFulfilmentQueues() {
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setAutoTopUpPendingQueueCount(fulfilmentRetrievalService.getNumberOfOrdersInQueue(OrderStatus.AUTO_TOP_UP_PAYG_FULFILMENT_PENDING));
        cmd.setAutoTopUpReplacementCardPendingQueueCount(fulfilmentRetrievalService
                        .getNumberOfOrdersInQueue(OrderStatus.AUTO_TOP_UP_REPLACEMENT_FULFILMENT_PENDING));
        cmd.setFulfilmentPendingQueueCount(fulfilmentRetrievalService.getNumberOfOrdersInQueue(OrderStatus.FULFILMENT_PENDING));
        cmd.setGoldCardPendingQueueCount(fulfilmentRetrievalService.getNumberOfOrdersInQueue(OrderStatus.GOLD_CARD_PENDING));
        cmd.setPayAsYouGoPendingQueueCount(fulfilmentRetrievalService.getNumberOfOrdersInQueue(OrderStatus.PAY_AS_YOU_GO_FULFILMENT_PENDING));
        cmd.setReplacementCardPendingQueueCount(fulfilmentRetrievalService.getNumberOfOrdersInQueue(OrderStatus.REPLACEMENT));
        return new ModelAndView(PageView.FULFILMENT_HOME, PageCommand.FULFILMENT, cmd);
    }
    
    @RequestMapping(params = TARGET_ACTION_FULFILMENT_PENDING_QUEUE, method = RequestMethod.POST)
    public ModelAndView fulfilmentPendingOrder(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd fulfilmentCmd, Model model, HttpSession session, SessionStatus status) {
        OrderDTO fulfilPendingOrder = fulfilmentRetrievalService.getFirstOrderPendingFulfilmentFromQueue(OrderStatus.FULFILMENT_PENDING);
        fulfilmentCmd.setOrder(fulfilPendingOrder);
        model.addAttribute(PageCommand.FULFILMENT, fulfilmentCmd);
        return new ModelAndView(new RedirectView(PageUrl.FULFIL_ORDER), PageCommand.FULFILMENT, fulfilmentCmd);
    }

}
