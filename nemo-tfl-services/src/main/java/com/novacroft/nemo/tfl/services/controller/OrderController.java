package com.novacroft.nemo.tfl.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.services.application_service.OrderItemService;
import com.novacroft.nemo.tfl.services.application_service.StationService;
import com.novacroft.nemo.tfl.services.transfer.CustomerOrder;
import com.novacroft.nemo.tfl.services.transfer.Order;
import com.novacroft.nemo.tfl.services.transfer.Station;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

/**
 * Controller for all order related services.
 */
@Controller
public class OrderController extends BaseServicesController {

    @Autowired
    protected OrderItemService orderItemService;

    @Autowired
    protected StationService stationService;

    @RequestMapping(value = "/order/customer/{customerId}/cancel/{orderId}", method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public WebServiceResult cancelOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
        return orderItemService.cancelOrder(customerId, orderId);
    }

    @RequestMapping(value = "/order/customer/{customerId}/cancel/{orderId}/complete", method = RequestMethod.POST, produces = JSON_MEDIA)
    @ResponseBody
    public WebServiceResult completeCancelOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
        return orderItemService.completeCancelOrder(customerId, orderId);
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public Order getOrderFromExternalOrderId(@PathVariable Long orderId) {
        return orderItemService.getOrderByExternalOrderId(orderId);
    }

    @RequestMapping(value = "/orders/customer/{customerId}/{dateFrom}/{dateTo}", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public CustomerOrder getCustomerOrdersAndItemsByDates(@PathVariable Long customerId, @PathVariable String dateFrom, @PathVariable String dateTo) {
        return orderItemService.getOrderItemsByCustomerIdAndDates(customerId, dateFrom, dateTo);
    }

    @RequestMapping(value = "/orders/customer/{customerId}/{cardId}/outstanding", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public Station getOutstandingOrderStation(@PathVariable Long customerId, @PathVariable Long cardId) {
        return stationService.findStationForOutstandingOrder(customerId, cardId);
    }

}
