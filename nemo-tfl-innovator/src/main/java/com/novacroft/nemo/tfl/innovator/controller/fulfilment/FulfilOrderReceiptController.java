package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.innovator.controller.BaseController;

@Controller
@RequestMapping(value = Page.FULFIL_ORDER_RECEIPT)
@SessionAttributes(PageCommand.FULFILMENT)
public class FulfilOrderReceiptController extends BaseController {
      
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPrintableReceipt(@ModelAttribute(PageCommand.FULFILMENT) FulfilmentCmd fulfilmentCmd, HttpSession session) {
        return new ModelAndView(PageView.FULFIL_ORDER_RECEIPT, PageCommand.FULFILMENT, fulfilmentCmd);
    } 
}

