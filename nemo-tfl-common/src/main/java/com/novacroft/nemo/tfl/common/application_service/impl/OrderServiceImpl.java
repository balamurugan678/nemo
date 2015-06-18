package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.StringUtil.formatPenceToCurrency;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.common.action.ItemDTOActionDelegate;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

@Service(value = "orderService")
public class OrderServiceImpl extends BaseService implements OrderService {

    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected ItemDataService itemDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected PaymentCardSettlementDataService cardSettlementDataService;
    @Autowired
    protected WebCreditSettlementDataService webCreditSettlementDataService;
    @Autowired
    protected AdHocLoadSettlementDataService adhocLoadSettlementDataService;
    @Autowired
    protected ItemDTOActionDelegate itemDTOActionDelegate;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public List<OrderItemsDTO> findOrderItemsByCustomerId(Long customerId) {
        CustomerDTO customerDTO = customerDataService.findById(customerId);
        List<OrderDTO> orders = orderDataService.findByCustomerId(customerDTO.getId());
        List<OrderItemsDTO> orderItemsList = new ArrayList<OrderItemsDTO>();
        for (OrderDTO order : orders) {
            order.setFormattedTotalAmount(formatPenceToCurrency(order.getTotalAmount()));
            OrderItemsDTO orderItems = new OrderItemsDTO(order, populateOrderItemsWithDesc(order.getOrderItems()),
                            populateOrderSettlement(order.getId()), populateOrderCardSettlement(order.getId()),
                            populateAdhocLoadSettlement(order.getId()));
            orderItemsList.add(orderItems);
        }
        return orderItemsList;
    }
    
    @Override
    public List<OrderItemsDTO> findOrderItemsByCustomerIdAndOrderStatus(Long customerId, String orderStatus) {
        CustomerDTO customerDTO = customerDataService.findById(customerId);
        List<OrderDTO> orders = orderDataService.findByCustomerId(customerDTO.getId());
        List<OrderItemsDTO> orderItemsList = new ArrayList<OrderItemsDTO>();
        for (OrderDTO order : orders) {
            if(orderStatus.equals(order.getStatus())){
                order.setFormattedTotalAmount(formatPenceToCurrency(order.getTotalAmount()));
                OrderItemsDTO orderItems = new OrderItemsDTO(order, populateOrderItemsWithDesc(order.getOrderItems()),
                                populateOrderSettlement(order.getId()), populateOrderCardSettlement(order.getId()),
                                populateAdhocLoadSettlement(order.getId()));
                orderItemsList.add(orderItems);
            } 
        }
        return orderItemsList;
    }

    @Override
    public List<OrderDTO> findOrdersByCustomerId(Long customerId) {
        CustomerDTO customerDTO = customerDataService.findById(customerId);
        List<OrderDTO> orders = orderDataService.findByCustomerId(customerDTO.getId());
        for (OrderDTO order : orders) {
            order.setFormattedTotalAmount(formatPenceToCurrency(order.getTotalAmount()));
            order.setOrderItems(populateOrderItemsWithDesc(order.getOrderItems()));
        }
        return orders;
    }

    @Override
    public List<OrderDTO> findOrdersByCustomerIdAndDates(Long customerId, String startDate, String endDate) {
        CustomerDTO customerDTO = customerDataService.findByExternalId(customerId);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateConstant.YYYYMMDD);
        DateTime startDateTime = formatter.parseDateTime(startDate);
        DateTime endDateTime = formatter.parseDateTime(endDate).plusDays(DateConstant.OFFSETDAY);
        List<OrderDTO> orders = orderDataService.findByCustomerIdAndDuration(customerDTO.getId(), startDateTime, endDateTime);
        for (OrderDTO order : orders) {
            order.setFormattedTotalAmount(formatPenceToCurrency(order.getTotalAmount()));
            order.setOrderItems(populateOrderItemsWithDesc(order.getOrderItems()));
        }
        return orders;
    }

    protected List<ItemDTO> populateOrderItemsWithDesc(List<ItemDTO> orderItems) {
        return itemDTOActionDelegate.postProcessItems(orderItems, false);
    }

    protected WebCreditSettlementDTO populateOrderSettlement(Long orderId) {
        List<WebCreditSettlementDTO> settlements = webCreditSettlementDataService.findByOrderId(orderId);
        for (WebCreditSettlementDTO settlement : settlements) {
            if (settlement instanceof WebCreditSettlementDTO) {
                return settlement;
            }
        }
        return null;
    }

    protected AdHocLoadSettlementDTO populateAdhocLoadSettlement(Long orderId) {
        List<AdHocLoadSettlementDTO> settlements = adhocLoadSettlementDataService.findByOrderId(orderId);
        if(null != settlements){
        	for (AdHocLoadSettlementDTO settlement : settlements) {
        		if (settlement instanceof AdHocLoadSettlementDTO) {
        			return settlement;
        		}
        	}
        }
        return null;
    }
    
    protected PaymentCardSettlementDTO populateOrderCardSettlement(Long orderId) {
        List<PaymentCardSettlementDTO> settlements = cardSettlementDataService.findByOrderId(orderId);
        for (PaymentCardSettlementDTO settlement : settlements) {
            if (settlement instanceof PaymentCardSettlementDTO) {
                return settlement;
            }
        }
        return null;
    }

    @Override
    public OrderDTO findOrderByNumber(Long orderNumber) {
        OrderDTO orderDTO = orderDataService.findByOrderNumber(orderNumber);
        if (orderDTO != null) {
            orderDTO.setOrderItems(populateOrderItemsWithDesc(orderDTO.getOrderItems()));
        }
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId) {
        OrderDTO orderDTO = orderDataService.findById(orderId);
        AdHocLoadSettlementDTO adHocLoadSettlementDTO = populateAdhocLoadSettlement(orderId);
        PaymentCardSettlementDTO paymentCardSettlementDTO = populateOrderCardSettlement(orderId);
        WebCreditSettlementDTO webCreditSettlementDTO = populateOrderSettlement(orderId);
        updateOrderByAdHocLoadSettlementStatus(orderDTO, adHocLoadSettlementDTO);
        if (paymentCardSettlementDTO != null && OrderStatus.COMPLETE.code().equals(orderDTO.getStatus())) {
            updateOrderByPaymentCardSettlementStatus(orderDTO, paymentCardSettlementDTO);
        }

        if (webCreditSettlementDTO != null && OrderStatus.COMPLETE.code().equals(orderDTO.getStatus())) {
            updateOrderByWebCreditSettlementStatus(orderDTO, webCreditSettlementDTO);
        }
        orderDataService.createOrUpdate(orderDTO);
        return orderDTO;
    }

    protected OrderDTO updateOrderByPaymentCardSettlementStatus(OrderDTO order, PaymentCardSettlementDTO paymentCardSettlementDTO) {
        if (SettlementStatus.INCOMPLETE.code().equals(paymentCardSettlementDTO.getStatus())
                        || SettlementStatus.REQUESTED.code().equals(paymentCardSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.ERROR.code());
        } else if (SettlementStatus.CANCELLED.code().equals(paymentCardSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.CANCELLED.code());
        } else if (SettlementStatus.ACCEPTED.code().equals(paymentCardSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.COMPLETE.code());
        }
        return order;
    }

    protected OrderDTO updateOrderByAdHocLoadSettlementStatus(OrderDTO order, AdHocLoadSettlementDTO adHocLoadSettlementDTO) {
        if (SettlementStatus.FAILED.code().equals(adHocLoadSettlementDTO.getStatus())
                        || SettlementStatus.PICK_UP_EXPIRED.code().equals(adHocLoadSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.ERROR.code());
        } else if (SettlementStatus.CANCELLED.code().equals(adHocLoadSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.CANCELLED.code());
        } else if (SettlementStatus.PICKED_UP.code().equals(adHocLoadSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.COMPLETE.code());
        }
        return order;
    }

    protected OrderDTO updateOrderByWebCreditSettlementStatus(OrderDTO order, WebCreditSettlementDTO webCreditSettlementDTO) {
        if (SettlementStatus.INCOMPLETE.code().equals(webCreditSettlementDTO.getStatus())
                        || SettlementStatus.REQUESTED.code().equals(webCreditSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.ERROR.code());
        } else if (SettlementStatus.COMPLETE.code().equals(webCreditSettlementDTO.getStatus())) {
            order.setStatus(OrderStatus.COMPLETE.code());
        }
        return order;
    }
}
