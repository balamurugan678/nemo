package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.common.utils.StringUtil.formatPenceToCurrency;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CUSTOMER_ID;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_ORDER;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.controller.purchase.BasePurchaseController;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * InNovator order controller.
 */
@Controller
@RequestMapping(value = Page.INV_ORDER)
public class OrderController extends BasePurchaseController {
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected CustomerDataService customerDataService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadOrderByCustomerId(@RequestParam(value = CUSTOMER_ID, required = false) Long customerId) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER);
        CustomerDTO customer = this.customerDataService.findById(customerId);
        List<OrderDTO> orders = this.orderService.findOrdersByCustomerId(customer.getId());
        for (OrderDTO order : orders) {
            order.setFormattedTotalAmount(formatPenceToCurrency(order.getTotalAmount()));
        }
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("customerId", customerId);
        return modelAndView;
    }

    @RequestMapping(value = "/LoadItems", method = RequestMethod.POST)
    @ResponseBody
    public String checkSearch(HttpServletRequest request) {
        Long orderNumber = ServletRequestUtils.getLongParameter(request, "orderNumber", 0);
        OrderDTO orderDTO = orderService.findOrderByNumber(orderNumber);
        return new Gson().toJson(orderDTO.getOrderItems());
    }
}
