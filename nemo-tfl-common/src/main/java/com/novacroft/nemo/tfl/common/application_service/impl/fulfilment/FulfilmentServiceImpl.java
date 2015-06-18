package com.novacroft.nemo.tfl.common.application_service.impl.fulfilment;


import static com.novacroft.nemo.common.utils.StringUtil.COMMA;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.common.utils.StringUtil.WHITE_SPACE;
import static com.novacroft.nemo.tfl.common.constant.FulfilmentConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.FulfilmentConstants.WEB_CREDIT;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOCardShippingMethodItemDTO;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOPayAsYouGoItemDTO;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOProductItemDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentService;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

@Service(value = "fulfilmentService")
public class FulfilmentServiceImpl implements FulfilmentService {

	@Autowired
    protected OrderDataService orderDataService;
	@Autowired
    protected CardDataService cardDataService;
	@Autowired
    protected CustomerDataService customerDataService;
	@Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;
    @Autowired
    protected ShippingMethodDataService shippingMethodDataService;
    @Autowired
    protected PaymentCardSettlementDataService cardSettlementDataService;
    @Autowired
    protected WebCreditSettlementDataService webCreditSettlementDataService;
    @Autowired
    protected AddressDataService addressDataService;
    
    @Override
    public void updateOrderStatus(OrderDTO order, String orderStatus) {
        order.setStatus(orderStatus);
        orderDataService.createOrUpdate(order);
    }

    @Override
    public void associateFulfiledOysterCardNumberWithTheOrder(OrderDTO order, String fulfilledOysterNumber) {
        CardDTO card = cardDataService.findById(order.getCardId());
        card.setCardNumber(fulfilledOysterNumber);
        cardDataService.createOrUpdate(card);
    }
    
    @Override
    public FulfilmentCmd loadOrderDetails(FulfilmentCmd cmd) {
        OrderDTO order = getOrderFromOrderNumber(cmd.getOrder().getOrderNumber());
        FulfilmentCmd fulfilmentCmd = new FulfilmentCmd();
        fulfilmentCmd.setOrder(order);
        fulfilmentCmd.setCustomer(getCustomerByCustomerId(order.getCustomerId()));
        fulfilmentCmd.setCard(getCardByCardId(order.getCardId()));
        fulfilmentCmd.setCustomerAddress(getCustomerAddress(fulfilmentCmd.getCustomer().getAddressId()));
        populateProductNameForTickets(order);
        populateShippingMethodForTickets(fulfilmentCmd);
        fulfilmentCmd.setPaymentMethod(populatePaymentMethodForTickets(fulfilmentCmd));
        fulfilmentCmd.setSubTotal(getSubTotal(order.getOrderItems()));
        fulfilmentCmd.setWebCreditDiscount(getAmountPaidUsingWebCredit(order));
        fulfilmentCmd.setTotalPaid(getAmountPaidUsingPaymentCard(order));
        updateOrderStatus(fulfilmentCmd.getOrder(), OrderStatus.SELECTED_FOR_FULFILMENT.code());
        return fulfilmentCmd;
    }
    
    @Override
    public OrderDTO getOrderFromOrderNumber(Long orderNumber) {
        return orderDataService.findByOrderNumber(orderNumber);
    }
    
    protected Integer getSubTotal(List<ItemDTO> orderItems) {
        int amount = 0;
        for (ItemDTO item : orderItems) {
            if ((isItemDTOPayAsYouGoItemDTO(item) || isItemDTOProductItemDTO(item)) && null != item.getPrice()) {
                amount += item.getPrice();
            }
        }
        return amount;
    }
    
    protected AddressDTO getCustomerAddress(Long addressId) {
        return addressDataService.findById(addressId);
    }
    protected CustomerDTO getCustomerByCustomerId(Long customerId) {
        return customerDataService.findByCustomerId(customerId);
    }
    
    protected CardDTO getCardByCardId(Long cardId) {
        return cardDataService.findById(cardId);
    }
    
    protected void populateProductNameForTickets(OrderDTO order) {
        for(ItemDTO orderItem : order.getOrderItems()) {
            if(isItemDTOProductItemDTO(orderItem)) {
                orderItem.setName(findDescriptionAndPopulateProductName((ProductItemDTO) orderItem));
            }
        }
    }
    
    protected String findDescriptionAndPopulateProductName(ProductItemDTO productItem) {
        PrePaidTicketDTO prePaidTicket = prePaidTicketDataService.findById(productItem.getProductId()); 
        return prePaidTicket != null? prePaidTicket.getDescription() : null;
    }
    
    protected void populateShippingMethodForTickets(FulfilmentCmd cmd) {
        for(ItemDTO orderItem : cmd.getOrder().getOrderItems()) {
            if(isItemDTOCardShippingMethodItemDTO(orderItem)) {
                cmd.setShippingMethod(findShippingMethod((ShippingMethodItemDTO) orderItem));
                cmd.setShippingCost(((ShippingMethodItemDTO) orderItem).getPrice());
            }
        }
    }
    
    protected String findShippingMethod(ShippingMethodItemDTO orderItem) {
        ShippingMethodDTO shippingMethod = shippingMethodDataService.findById(orderItem.getShippingMethodId());
        return shippingMethod != null? shippingMethod.getName() : null;
    }
    
    protected String populatePaymentMethodForTickets(FulfilmentCmd cmd) {
        return findSettlementsAndPopulatePaymentMethod(cmd);
    }
    
    protected String findSettlementsAndPopulatePaymentMethod(FulfilmentCmd cmd) {
        Boolean isPaidUsingWebCredit = isPaidUsingWebCredit(cmd);
        Boolean isPaidWithPaymentCard = isPaidWithPaymentCard(cmd);
        if (isPaidUsingWebCredit && isPaidWithPaymentCard) {
            return WEB_CREDIT + COMMA + WHITE_SPACE + PAYMENT_CARD;
        } else if (isPaidUsingWebCredit) {
            return WEB_CREDIT;
        } else if (isPaidWithPaymentCard) {
            return PAYMENT_CARD;
        }
        return EMPTY_STRING;
    }
    
    protected Boolean isPaidUsingWebCredit(FulfilmentCmd fulfilmentCmd) {
        return !CollectionUtils.isEmpty(webCreditSettlementDataService.findByOrderId(fulfilmentCmd.getOrder().getId()));
    }
    
    protected Boolean isPaidWithPaymentCard(FulfilmentCmd fulfilmentCmd){
        return !CollectionUtils.isEmpty(cardSettlementDataService.findByOrderId(fulfilmentCmd.getOrder().getId()));
    }
    
    protected List<WebCreditSettlementDTO> getWebCreditSettlementsForOrder(OrderDTO order) {
        return webCreditSettlementDataService.findByOrderId(order.getId());
    }
    
    protected List<PaymentCardSettlementDTO> getPaymentCardSettlementsForOrder(OrderDTO order) {
        return cardSettlementDataService.findByOrderId(order.getId());
    }
    
    public Integer getAmountPaidUsingWebCredit(OrderDTO order){
        Integer amountPaid = 0;
        for(WebCreditSettlementDTO webCreditSettlement: getWebCreditSettlementsForOrder(order)) {
            amountPaid += webCreditSettlement.getAmount();
        }
        return amountPaid;
    }
    
    public Integer getAmountPaidUsingPaymentCard(OrderDTO order) {
        Integer amountPaid = 0;
        for(PaymentCardSettlementDTO paymentCardSettlement: getPaymentCardSettlementsForOrder(order)) {
            amountPaid += paymentCardSettlement.getAmount();
        }
        return amountPaid;
    }
}
