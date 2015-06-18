package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterValue.ORDERS;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_ORDER_DETAILS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.Page;


@Controller
@RequestMapping(value = Page.VIEW_ORDER_DETAILS)
public class ViewOrderDetailsController extends OnlineBaseController {
    
    @Autowired
    protected OrderService orderService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLoadOrderItems() {
        return  new ModelAndView(VIEW_ORDER_DETAILS, ORDERS, orderService.findOrderItemsByCustomerId(getLoggedInUserCustomerId()));
    }
    
}
