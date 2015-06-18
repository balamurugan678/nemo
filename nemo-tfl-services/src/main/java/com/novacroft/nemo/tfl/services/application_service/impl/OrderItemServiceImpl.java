package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.YYYYMMDD;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CancelOrderService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.ContentCodeSuffix;
import com.novacroft.nemo.tfl.common.constant.Refund;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.SettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.services.application_service.OrderItemService;
import com.novacroft.nemo.tfl.services.constant.CommandAttribute;
import com.novacroft.nemo.tfl.services.converter.ItemConverter;
import com.novacroft.nemo.tfl.services.converter.SettlementConverter;
import com.novacroft.nemo.tfl.services.converter.WebsServiceResultConverter;
import com.novacroft.nemo.tfl.services.transfer.CustomerOrder;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.Order;
import com.novacroft.nemo.tfl.services.transfer.Settlement;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;
import com.novacroft.nemo.tfl.services.util.ErrorUtil;

/**
 * Application service for http request returning a json response of all orders for a given customer id.
 */
@Service("orderItemService")
public class OrderItemServiceImpl extends BaseService implements OrderItemService {

    @Autowired
    protected OrderService orderService;
    @Autowired
    protected ItemConverter itemDTOConverter;
    @Autowired
    protected CancelOrderService cancelOrderService;
    @Autowired
    protected WebsServiceResultConverter webServiceResultConverter;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected SettlementDataService settlementDataService;
    @Autowired
    protected SettlementConverter settlementConverter;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;

    public CustomerOrder getOrderItemsByCustomerIdAndDates(Long customerId, String dateFrom, String dateTo) {
        CustomerOrder customerOrders = new CustomerOrder();
        customerOrders.setOrders(new ArrayList<Order>());
        if (!isDateValid(dateFrom) || !isDateValid(dateTo)) {
            customerOrders.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), null, CommandAttribute.FIELD_DATE_OBJECT, getContent(ContentCode.INVALID_WEB_SERVICE_DATE_PATTERN.codeStem().concat(Refund.DOT).concat(ContentCodeSuffix.ERROR.code()))));
        } else if (!DateUtil.isBefore(dateFrom, dateTo, DateConstant.YYYYMMDD)) {
            customerOrders.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), null, CommandAttribute.FIELD_DATE_OBJECT, getContent(ContentCode.GREATER_THAN_START_DATE.codeStem().concat(Refund.DOT).concat(ContentCodeSuffix.ERROR.code()))));
        } else {
            List<OrderDTO> orderDTOs = orderService.findOrdersByCustomerIdAndDates(customerId, dateFrom, dateTo);
            customerOrders.setOrders(getOrders(orderDTOs));
        }
        return customerOrders;
    }
    
    protected List<Order> getOrders(List<OrderDTO> orderDTOs) {
        List<Order> orders = new ArrayList<Order>();
        for (OrderDTO orderDTO : orderDTOs) {
            Order order = getOrderFromOrderDTO(orderDTO);
            List<Item> items = itemDTOConverter.convert(orderDTO.getOrderItems(), orderDTO.getOrderDate());
            order.setItems(items);
            orders.add(order);
        }
        return orders;
    }
    
    protected List<Settlement> getSettlementsFromOrderId(Long internalOrderId){
        List<SettlementDTO> settlementDTOs = settlementDataService.findPolymorphicChildTypeSettlementByOrderId(internalOrderId);
        if(CollectionUtils.isNotEmpty(settlementDTOs)){
            return settlementConverter.convertDTOs(settlementDTOs);
        }
        
        return new ArrayList<Settlement>();
    }

    @Override
    public Order getOrderByExternalOrderId(Long externalOrderId) {
        Order order = new Order();
        OrderDTO orderDTO = orderDataService.findByExternalId(externalOrderId);
        if (orderDTO != null) {
            order = getOrderFromOrderDTO(orderDTO);
            order.setItems(itemDTOConverter.convert(orderDTO.getOrderItems(),order.getOrderDate()));
        } else {
            order.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), null, CommandAttribute.FIELD_ORDER_OBJECT, getContent(ContentCode.ORDER_NOT_FOUND.codeStem())));
        }
        return order;
    }

    protected boolean isDateValid(String inputDate) {
        DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inputDate);
            return true;
        } catch(ParseException e) {
            return false;
        }
    }
    
    protected Order getOrderFromOrderDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getExternalId());
        order.setCustomerId(getExternalCustomerId(orderDTO.getCustomerId()));
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setFormattedOrderDate(DateUtil.formatDate(orderDTO.getOrderDate()));
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
        order.setStationId(orderDTO.getStationId());
        order.setFormattedTotalAmount(orderDTO.getFormattedTotalAmount());
        order.setApprovalId(orderDTO.getApprovalId());
        order.setCardId(orderDTO.getCardId() != null? getExternalCardId(orderDTO.getCardId()): null);
        order.setRefundDate(orderDTO.getRefundDate());
        order.setFormattedRefundDate(DateUtil.formatDate(orderDTO.getRefundDate()));
        
        List<Settlement> settlements = getSettlementsFromOrderId(orderDTO.getId());
        order.setSettlements(settlements);
        return order;
    }
    
    private Long getExternalCustomerId(Long internalCustomerId) {
        CustomerDTO customerDTO = customerDataService.findById(internalCustomerId);
        return customerDTO != null ? customerDTO.getExternalId() : null;
    }
    
    protected Long getExternalCardId(Long internalCardId){
        CardDTO cardDTO = cardDataService.findById(internalCardId);
        return cardDTO != null ? cardDTO.getExternalId() : null;
    }

    @Override
    public WebServiceResult cancelOrder(Long customerId, Long externalOrderId) {
        CancelOrderResultDTO cancelOrderResultDTO = cancelOrderService.cancelOrderWithExternalOrderIdAndCustomerId(customerId, externalOrderId);
        return webServiceResultConverter.convertCancelOrderResultDTOToWebServiceResult(cancelOrderResultDTO);
    }

    @Override
    public WebServiceResult completeCancelOrder(Long customerId, Long externalOrderId) {
        CancelOrderResultDTO completeCancelOrderDTO = cancelOrderService.completeCancelOrderWithExternalOrderIdAndCustomerId(customerId,
                        externalOrderId);

        return webServiceResultConverter.convertCancelOrderResultDTOToWebServiceResult(completeCancelOrderDTO);
    }
    
}
