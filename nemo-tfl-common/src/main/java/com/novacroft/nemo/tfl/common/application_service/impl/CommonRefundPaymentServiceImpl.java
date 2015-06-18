package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

@Service("commonRefundPaymentService")
public class CommonRefundPaymentServiceImpl implements CommonRefundPaymentService {
    static final Logger logger = LoggerFactory.getLogger(CommonRefundPaymentServiceImpl.class);

    @Autowired
    protected OrderDataService orderDataService;

    @Autowired
    protected CartService cartService;

    @Autowired
    protected ApplicationEventService applicationEventService;

    @Autowired
    protected AddressDataService addressDataService;
    
    @Autowired
    protected CardService cardService;

    @Override
    public void updateToPayAmount(CartCmdImpl cmd) {
        cmd.setToPayAmount(cmd.getCartDTO().getCartRefundTotal());
    }

    @Override
    public CartCmdImpl createOrderFromCart(CartCmdImpl cmd) {
        OrderDTO orderDTO = createOrderFromCart(cmd.getCartDTO());
        cmd.getCartDTO().setOrder(orderDTO);
        return cmd;
    }

    private OrderDTO createOrderFromCart(CartDTO cartDTO) {
        if (isCartCustomerBased(cartDTO)) {
            return createOrderDTOFromCustomerIdOfCartDTO(cartDTO);
        } else if (isCartCardBased(cartDTO)) {
            return createOrderDTOFromCardIdOfCartDTO(cartDTO);
        } else {
            return createOrderDTOFromCartDTO(cartDTO);
        }
    }

    private boolean isCartCustomerBased(CartDTO cartDTO) {
        return (cartDTO.getCustomerId() != null && cartDTO.getCustomerId() > 0);
    }

    private OrderDTO createOrderDTOFromCustomerIdOfCartDTO(CartDTO cartDTO) {
        OrderDTO orderDTO = getNewOrder(cartDTO);
        orderDTO.setCustomerId(cartDTO.getCustomerId());
        return this.orderDataService.create(orderDTO);
    }

    private OrderDTO getNewOrder(CartDTO cartDTO) {
        return new OrderDTO(cartDTO.getCustomerId(), cartDTO.getApprovalId(), new Date(), -cartDTO.getCartRefundTotal(), OrderStatus.NEW.code(), cartDTO.getDateOfRefund());
    }

    private boolean isCartCardBased(CartDTO cartDTO) {
        return (cartDTO.getCardId() != null && cartDTO.getCardId() > 0);
    }

    private OrderDTO createOrderDTOFromCardIdOfCartDTO(CartDTO cartDTO) {
        OrderDTO orderDTO = getNewOrder(cartDTO);
        orderDTO.setCardId(cartDTO.getCardId());
        if (orderDTO.getCustomerId() == null) {
            CardDTO cardDTO = cardService.getCardDTOById(cartDTO.getCardId());
            orderDTO.setCustomerId(cardDTO == null ? null : cardDTO.getCustomerId());
        }
        
        return this.orderDataService.create(orderDTO);
    }

    private OrderDTO createOrderDTOFromCartDTO(CartDTO cartDTO) {
        OrderDTO orderDTO = getNewOrder(cartDTO);
        return this.orderDataService.create(orderDTO);
    }

    @Override
    public void updateOrderStatusToPaid(CartCmdImpl cmd) {
        OrderDTO orderDTO = this.orderDataService.findById(cmd.getCartDTO().getOrder().getId());
        cmd.getCartDTO().setOrder(updateOrderStatus(orderDTO, OrderStatus.PAID.code()));
    }

    private OrderDTO updateOrderStatus(OrderDTO orderDTO, String newStatus) {
        orderDTO.setStatus(newStatus);
        return this.orderDataService.createOrUpdate(orderDTO);
    }

    @Override
    public void updateCartItemsWithOrderId(CartCmdImpl cmd) {
        updateCartItemsToOrder(cmd.getCartDTO(), cmd.getCartDTO().getOrder());
    }

    private OrderDTO updateCartItemsToOrder(CartDTO cartDTO, OrderDTO orderDTO) {
        if (OrderStatus.PAID.code().equals(orderDTO.getStatus())) {
            moveCartItemsToOrder(cartDTO, orderDTO);
        }
        orderDTO = updateOrderRecord(orderDTO);
        return orderDTO;
    }

    private OrderDTO moveCartItemsToOrder(CartDTO cartDTO, OrderDTO orderDTO) {
        List<ItemDTO> orderItems = new ArrayList<ItemDTO>();
        for (ItemDTO cartItemDTO : cartDTO.getCartItems()) {
            cartItemDTO.setId(null);
            cartItemDTO.setExternalId(null);
            cartItemDTO.setNullable(true);
            cartItemDTO.setCartId(null);
            cartItemDTO.setOrderId(orderDTO.getId());
            orderItems.add(cartItemDTO);
        }
        orderDTO.setOrderItems(orderItems);
        return orderDTO;
    }

    private OrderDTO updateOrderRecord(OrderDTO orderDTO) {
        return this.orderDataService.createOrUpdate(orderDTO);
    }

    @Override
    public void createEvent(CartCmdImpl cmd) {
        this.applicationEventService.create(cmd.getCartDTO().getCustomerId(), EventName.PAYMENT_RESOLVED, buildAdditionalInformation(cmd.getCartDTO()));
    }

    private String buildAdditionalInformation(CartDTO cartDTO) {
        InformationBuilder additionalInformation = new InformationBuilder().append("Order Number [%s]", cartDTO.getOrder().getOrderNumber());
        return additionalInformation.toString();
    }

    @Override
    public AddressDTO overwriteOrCreateNewAddress(CartCmdImpl cmd) {
        return cmd.getOverwriteAddress() ? overwriteAddress(cmd.getPayeeAddress()) : createNewAddress(cmd.getPayeeAddress());
    }

    private AddressDTO overwriteAddress(AddressDTO payeeAddress) {
        return addressDataService.createOrUpdate(payeeAddress);
    }

    private AddressDTO createNewAddress(AddressDTO payeeAddress) {
        AddressDTO addressDTO = new AddressDTO(payeeAddress.getHouseNameNumber(), payeeAddress.getStreet(), payeeAddress.getTown(), payeeAddress.getPostcode(), payeeAddress.getCountry(), payeeAddress.getCounty());
        addressDTO = addressDataService.createOrUpdate(addressDTO);
        return addressDTO;
    }
}
