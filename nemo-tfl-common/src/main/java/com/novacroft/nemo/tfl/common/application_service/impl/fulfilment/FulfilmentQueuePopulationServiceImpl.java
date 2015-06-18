package com.novacroft.nemo.tfl.common.application_service.impl.fulfilment;

import static com.novacroft.nemo.common.utils.StringUtil.isEmpty;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOAutoTopUpItemDTO;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOPayAsYouGoItemDTO;
import static com.novacroft.nemo.tfl.common.util.CartUtil.isItemDTOProductItemDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@Service(value = "fulfilmentQueuePopulationService")
public class FulfilmentQueuePopulationServiceImpl implements FulfilmentQueuePopulationService {

    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public Boolean isFirstIssue(CartDTO cartDTO) {
        return cartDTO.getCardId() != null ? isCardNumberEmpty(cartDTO) : true;
    }

    protected Boolean isCardNumberEmpty(CartDTO cartDTO) {
        return isEmpty(cardDataService.findById(cartDTO.getCardId()).getCardNumber()); 
    }

    @Override
    public Boolean isPayAsYouGoTheOnlyItemPresentInOrder(List<ItemDTO> orderItems) {
        return (isPayAsYouGoItemPresentInOrder(orderItems) && !isAutoTopUpPresentInOrder(orderItems) && !isProductItemPresentInOrder(orderItems));
    }

    protected Boolean isProductItemPresentInOrder(List<ItemDTO> orderItems) {
        for (ItemDTO orderItem : orderItems) {
            if (isItemDTOProductItemDTO(orderItem)) {
                return true;
            }
        }
        return false;
    }
    protected Boolean isPayAsYouGoItemPresentInOrder(List<ItemDTO> orderItems) {
        for (ItemDTO orderItem : orderItems) {
            if (isItemDTOPayAsYouGoItemDTO(orderItem)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Boolean isAutoTopUpPresentInOrder(List<ItemDTO> orderItems) {
        for (ItemDTO orderItem : orderItems) {
            if (isItemDTOAutoTopUpItemDTO(orderItem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isAnnualTravelCardPresentInOrder(List<ItemDTO> orderItems) {
        for (ItemDTO orderItem : orderItems) {
            if (isItemDTOProductItemDTO(orderItem)) {
                ProductItemDTO productItem = (ProductItemDTO) orderItem;
                if (Durations.ANNUAL.getDurationType().equals(productItem.getDuration())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected OrderDTO updateOrderStatus(OrderDTO order, String status) {
        order.setStatus(status);
        return orderDataService.createOrUpdate(order);
    }

    @Override
    public void determineFulfilmentQueueAndUpdateOrderStatus(boolean firstIssueOrder, boolean replacementOrder, OrderDTO order) {
        String fulfilmentOrderStatus = determineOrderStatusForFirstIssueAndAnnualCard(firstIssueOrder, replacementOrder, order);
        if(!fulfilmentOrderStatus.equalsIgnoreCase(order.getStatus())) {
            updateOrderStatus(order, fulfilmentOrderStatus);
        }
    }

    protected String determineOrderStatusForFirstIssueAndAnnualCard(boolean firstIssueOrder, boolean replacementOrder, OrderDTO order) {
        if (firstIssueOrder) {
            return determineOrderStatusWhenOrderIsAFirstIssue(replacementOrder, order);
        } else if (isAnnualTravelCardPresentInOrder(order.getOrderItems())) {
            return OrderStatus.GOLD_CARD_PENDING.code();
        }
        return order.getStatus();
    }

    protected String determineOrderStatusWhenOrderIsAFirstIssue(boolean replacementOrder, OrderDTO order) {
        if (replacementOrder) {
            return determineOrderStatusIfAutoTopUpAvailable(order);
        } else if (isPayAsYouGoTheOnlyItemPresentInOrder(order.getOrderItems())) {
            return OrderStatus.PAY_AS_YOU_GO_FULFILMENT_PENDING.code();
        } else if (isAutoTopUpPresentInOrder(order.getOrderItems())) {
            return OrderStatus.AUTO_TOP_UP_PAYG_FULFILMENT_PENDING.code();
        } else {
            return OrderStatus.FULFILMENT_PENDING.code();
        }
    }

    protected String determineOrderStatusIfAutoTopUpAvailable(OrderDTO order) {
        return isAutoTopUpPresentInOrder(order.getOrderItems()) ? OrderStatus.AUTO_TOP_UP_REPLACEMENT_FULFILMENT_PENDING.code()
                        : OrderStatus.REPLACEMENT.code();
    }
}
