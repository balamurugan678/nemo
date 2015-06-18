package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterValue.ORDERDAYS;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_ORDER_HISTORY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.CollateOrdersService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.comparator.OrderItemsDayComparator;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDayDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;


@Controller
@RequestMapping(value = Page.VIEW_ORDER_HISTORY)
public class ViewOrderHistoryController extends OnlineBaseController {
    
    static final Logger logger = LoggerFactory.getLogger(ViewOrderHistoryController.class);

    @Autowired
    protected OrderService orderService;
    
    @Autowired
    protected CollateOrdersService collateOrdersService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLoadOrderItems() {
    	List<OrderItemsDTO> paidOrderItems = orderService.findOrderItemsByCustomerIdAndOrderStatus(getLoggedInUserCustomerId(), OrderStatus.PAID.code());
    	List<OrderItemsDTO> fulfilledOrderItems = orderService.findOrderItemsByCustomerIdAndOrderStatus(getLoggedInUserCustomerId(), OrderStatus.FULFILLED.code());
    	List<OrderItemsDTO> completedOrderItems = orderService.findOrderItemsByCustomerIdAndOrderStatus(getLoggedInUserCustomerId(), OrderStatus.COMPLETE.code());
    	List<OrderItemsDTO> errorOrderItems = orderService.findOrderItemsByCustomerIdAndOrderStatus(getLoggedInUserCustomerId(), OrderStatus.ERROR.code());
    	List<OrderItemsDTO> orderItems = paidOrderItems;
    	orderItems.addAll(fulfilledOrderItems);
    	orderItems.addAll(completedOrderItems);
    	orderItems.addAll(errorOrderItems);
    	setWebCreditItemName(orderItems);
    	List<OrderItemsDayDTO> orderDays = collateOrdersService.collateByDay(orderItems);
    	Collections.sort(orderDays, new OrderItemsDayComparator());
        return new ModelAndView(VIEW_ORDER_HISTORY, ORDERDAYS, orderDays);
    }

    protected void setWebCreditItemName(List<OrderItemsDTO> orderItems) {
        for (OrderItemsDTO orderItemsDTO : orderItems) {
            List<ItemDTO> itemDTOList= orderItemsDTO.getItems();
            if(itemDTOList.size()==0){
                setItemName(orderItemsDTO);
            }
        }
    }

    protected void setItemName(OrderItemsDTO orderItemsDTO) {
        ItemDTO item = new ProductItemDTO();
        item.setName(CartAttribute.WEB_CREDIT_REFUND); 
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        orderItemsDTO.setItems(items);
    }    
}
