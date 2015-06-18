package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.ItemTestUtil.ITEM_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

/**
 * Utilities for Order tests
 */
public class OrderTestUtil {

    public static final Long ORDER_ID = 1L;
    public static final Long ORDER_ID_2 = 2L;
    public static final Long CARD_ID_1 = 10456789345L;
    public static final Long CUSTOMER_ID_2 = 23L;
    public static final String CREATED_USER_ID = "unit-test";
    public static final Date CREATED_DATE_TIME = new Date();
    public static final String MODIFIED_USER_ID = null;
    public static final Date MODIFIED_DATE_TIME = null;
    public static final Long STATION_ID = 340L;
    public static final Long ORDER_NUMBER = 999990L;
    public static final Date ORDER_DATE = new Date();
    public static final Integer TOTAL_AMOUNT = 2000;
    public static final Long PAYMENT_CARD_ID_2 = 2L;
    public static final Integer PAYMENT_CARD_AMOUNT = 0;
    public static final Integer PAYMENT_CARD_AMOUNT_100 = 100;
    public static final Integer WEB_CREDIT_AMOUNT = 0;
    public static final String STATUS = "New";
    public static final String STATUS_PAID = "Paid";
    public static final Long ITEM_CARD_ID = 123456789L;
    public static final Long ITEM_CART_ID = 1234L;
    public static final Long ITEM_ORDER_ID = 123L;
    public static final Long ITEM_ORDER_ID_2 = 124L;
    public static final Integer ITEM_PRICE = 100;
    public static final Date DATEOFREFUND = get1Jan();
    public static final Integer ITEM_PRICE_2 = 200;
    public static final Long APPROVAL_ID = 12L;
    public static final Integer ITEM_PRICE_100 = 100;
    public static final Integer ITEM_PRICE_566 = 566;
    public static final Integer ITEM_PRICE_9999 = 9999;
    
    public static final Long EXTERNAL_ID = 1L;
    
    public static final Date PRODUCT_ORDER_DATE = new Date();
    
    public static Order getTestOrder1() {
        return getTestOrder(ORDER_ID, CREATED_USER_ID, CREATED_DATE_TIME, MODIFIED_USER_ID, MODIFIED_DATE_TIME, ORDER_NUMBER, ORDER_DATE, TOTAL_AMOUNT, STATUS, CUSTOMER_ID_1, STATION_ID, DATEOFREFUND);
    }

    public static OrderDTO getTestOrderDTO1() {
        return getTestOrderDTO(ORDER_ID, CREATED_USER_ID, CREATED_DATE_TIME, MODIFIED_USER_ID, MODIFIED_DATE_TIME, ORDER_NUMBER, ORDER_DATE,
                        TOTAL_AMOUNT, STATUS, CUSTOMER_ID_1, STATION_ID, APPROVAL_ID);
    }

    public static OrderDTO getTestOrderDTO2() {
        return getTestOrderDTO(ORDER_ID, CREATED_USER_ID, CREATED_DATE_TIME, MODIFIED_USER_ID, MODIFIED_DATE_TIME, ORDER_NUMBER, ORDER_DATE,
                        TOTAL_AMOUNT, STATUS_PAID, CUSTOMER_ID_1, STATION_ID, APPROVAL_ID);
    }

    public static Order getTestOrder(Long orderId, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime, Long orderNumber, Date orderDate, Integer totalAmount, String status, Long customerId, Long stationId, Date dateOfRefund) {
        return new Order(orderId, createdUserId, createdDateTime, modifiedUserId, modifiedDateTime, orderNumber, orderDate, totalAmount, status, customerId, stationId, null, dateOfRefund);
    }

    public static OrderDTO getTestOrderDTOWithExternald() {
        OrderDTO orderDTO = getTestOrderDTO(ORDER_ID, CREATED_USER_ID, CREATED_DATE_TIME, MODIFIED_USER_ID, MODIFIED_DATE_TIME, ORDER_NUMBER,
                        ORDER_DATE, TOTAL_AMOUNT, STATUS, CUSTOMER_ID_1, STATION_ID, APPROVAL_ID);
        orderDTO.setExternalId(EXTERNAL_ID);
        return orderDTO;
    }

    public static OrderDTO getTestOrderDTO(Long orderId, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime,
                    Long orderNumber, Date orderDate, Integer totalAmount, String status, Long webAccountId, Long stationId, Long approvalId) {
        OrderDTO dto = new OrderDTO(orderId, createdUserId, createdDateTime, modifiedUserId, modifiedDateTime, orderNumber, orderDate, totalAmount, status,
                        webAccountId, stationId, approvalId);
        dto.setExternalId(orderId);
        return dto;
    }

    public static List<OrderDTO> getOrders() {
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        orders.add(getTestOrderDTO1());
        return orders;
    }
    
    public static List<OrderDTO> getOrdersWithPaid() {
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        orders.add(getTestOrderDTO2());
        return orders;
    }

    public static List<OrderItemsDTO> getOrderItems() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderItemsDTO orderItem = new OrderItemsDTO(getTestOrderDTO1(), getListOfItems(), null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }

    public static List<ItemDTO> getListOfItems() {
        ItemDTO item = getItemDTO1();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        return items;
    }

    public static List<OrderItemsDTO> getOrderItemsWebCredit() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderItemsDTO orderItem = new OrderItemsDTO(getTestOrderDTO1(), getListOfItemsWebCredit(), null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }

    public static List<ItemDTO> getListOfItemsWebCredit() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        return items;
    }
    
    
    public static ItemDTO getItemDTO1() {
        return createItemDTO(ITEM_CARD_ID, ITEM_CART_ID, ITEM_ORDER_ID, ITEM_PRICE);
    }

    public static ItemDTO getItemDTO2() {
        return createItemDTO(ITEM_CARD_ID, ITEM_CART_ID, ITEM_ORDER_ID_2, ITEM_PRICE_2);
    }

    public static ItemDTO createItemDTO(Long cardId, Long cartId, Long orderId, Integer price) {
        ItemDTO dto = new ProductItemDTO();
        dto.setOrderId(orderId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static Order getOrderEntityWithAdminFeeAndShippingItem() {
        Order order = new Order();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        Set<Item> items = new HashSet<Item>();
        items.add(createItemEntity(ORDER_ID, ITEM_PRICE_100, AdministrationFeeItem.class));
        items.add(createItemEntity(ORDER_ID_2, ITEM_PRICE_566, ShippingMethodItem.class));
        order.setItems(items);
        return order;
    }

    public static Order getOrderEntityWithExternalId() {
        Order order = new Order();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        Set<Item> items = new HashSet<Item>();
        items.add(createItemEntity(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItem.class));
        items.add(createItemEntity(ORDER_ID_2, ITEM_PRICE_566, ShippingMethodItem.class));
        order.setItems(items);
        order.setExternalId(EXTERNAL_ID);
        return order;
    }

    public static Order getOrderEntityWithAdminFeeAndShippingItem2() {
        Order order = new Order();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        Set<Item> items = new HashSet<Item>();
        items.add(createItemEntity(ORDER_ID, ITEM_PRICE_9999, AdministrationFeeItem.class));
        items.add(createItemEntity(ORDER_ID_2, ITEM_PRICE_9999, ShippingMethodItem.class));
        order.setItems(items);
        return order;
    }

    public static OrderDTO getOrderDTOWithAdminFeeAndShippingItem() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(createItemDTO(ORDER_ID, ITEM_PRICE_100, AdministrationFeeItemDTO.class));
        items.add(createItemDTO(ORDER_ID_2, ITEM_PRICE_566, ShippingMethodItemDTO.class));
        order.setOrderItems(items);
        return order;
    }

    public static OrderDTO getOrderDTOWithItems() {

        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO item = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        item.setCardId(CardTestUtil.CARD_ID);
        items.add(item);
        items.add(createItemDTO(ORDER_ID_2, ITEM_PRICE_566, ShippingMethodItemDTO.class));
        order.setOrderItems(items);
        return order;
    }

    public static OrderDTO getOrderDTOWithDifferentCardIdOnItems() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(ORDER_ID);
        orderDTO.setCustomerId(CUSTOMER_ID_1);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO paygDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(paygDTO);
        paygDTO = createItemDTO(ORDER_ID, ITEM_PRICE, PayAsYouGoItemDTO.class);
        paygDTO.setCardId(CardTestUtil.CARD_ID);
        items.add(paygDTO);
        ItemDTO productItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, ProductItemDTO.class);
        productItemDTO.setCardId(CardTestUtil.CARD_ID_2);

        items.add(paygDTO);
        items.add(productItemDTO);

        orderDTO.setOrderItems(items);
        return orderDTO;
    }

    public static OrderDTO getOrderDTOWithShippingItem() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(createItemDTO(ORDER_ID_2, ITEM_PRICE_566, ShippingMethodItemDTO.class));
        order.setOrderItems(items);
        return order;
    }

    public static OrderDTO getOrderDTOWithoutItems() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        order.setOrderItems(items);
        return order;
    }

    public static List<OrderItemsDTO> getOrderDTOWithNewItemsManageATU() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderDTO order = new OrderDTO();
        order.setStatus(OrderStatus.NEW.code());
        ItemDTO item = new AutoTopUpConfigurationItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        OrderItemsDTO orderItem = new OrderItemsDTO(order, items, null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }
    
    public static List<OrderItemsDTO> getOrderDTOWithPaidItemsManageATU() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderDTO order = new OrderDTO();
        order.setStatus(OrderStatus.PAID.code());
        ItemDTO item = new AutoTopUpConfigurationItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        OrderItemsDTO orderItem = new OrderItemsDTO(order, items, null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }
    
    public static List<OrderItemsDTO> getOrderDTOWithCompleteItemsManageATU() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderDTO order = new OrderDTO();
        order.setStatus(OrderStatus.COMPLETE.code());
        ItemDTO item = new AutoTopUpConfigurationItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        OrderItemsDTO orderItem = new OrderItemsDTO(order, items, null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }

    public static List<OrderItemsDTO> getOrderDTOWithErrorItemsManageATU() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderDTO order = new OrderDTO();
        order.setStatus(OrderStatus.ERROR.code());
        ItemDTO item = new PayAsYouGoItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        OrderItemsDTO orderItem = new OrderItemsDTO(order, items, null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }

    public static List<OrderItemsDTO> getOrderDTOWithCancelledItemsManageATU() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderDTO order = new OrderDTO();
        order.setStatus(OrderStatus.CANCELLED.code());
        ItemDTO item = new AutoTopUpConfigurationItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        OrderItemsDTO orderItem = new OrderItemsDTO(order, items, null, null, null);
        orderItems.add(orderItem);
        return orderItems;
    }

    public static ItemDTO createItemDTO(Long id, Integer price, Class itemDTOSubclasss) {
        try {
            ItemDTO item = (ItemDTO) itemDTOSubclasss.newInstance();
            item.setId(id);
            item.setPrice(price);
            return item;
        } catch (Exception e) {
            return null;
        }
    }

    public static Item createItemEntity(Long id, Integer price, Class itemSubclasss) {
        try {
            Item item = (Item) itemSubclasss.newInstance();
            item.setId(id);
            item.setPrice(price);
            return item;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<Long, ItemDTO> createItemDTOLookupMap(List<ItemDTO> items) {
        Map<Long, ItemDTO> mapOfItems = new HashMap<Long, ItemDTO>();
        if (null != items && !items.isEmpty()) {
            Iterator<ItemDTO> itemIterator = items.iterator();
            ItemDTO item = null;
            while (itemIterator.hasNext()) {
                item = itemIterator.next();
                mapOfItems.put(item.getId(), item);
            }
        }
        return mapOfItems;
    }

    public static ItemDTO getNewProductItemDTO() {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setCreatedDateTime(new Date());
        productItemDTO.setId(ITEM_ID);
        return productItemDTO;
    }

    public static OrderDTO getProductItemDTOTypeOfPreviouslyTradedTicket() {
        OrderDTO orderDTO = new OrderDTO();
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();

        ProductItemDTO productItemDTO = (ProductItemDTO) getNewProductItemDTO();
        ProductItemDTO productItemTradedTicket = new ProductItemDTO();
        productItemTradedTicket.setTicketUnused(Boolean.TRUE);
        productItemTradedTicket.setTradedDate(DateTestUtil.getApr03());
        productItemDTO.setRelatedItem(productItemTradedTicket);
        productItemDTO.setTradedDate(DateTestUtil.getApr03());
        itemDTOList.add(productItemDTO);
        orderDTO.setOrderItems(itemDTOList);
        return orderDTO;

    }

    public static List<ItemDTO> getListOfItemsWithPayAsYouGoItemOnly() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO payAsYouGoItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(payAsYouGoItemDTO);
        return items;
    }
    
    public static List<ItemDTO> getListOfItemsIncludingPayAsYouGoItem() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO payAsYouGoItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(payAsYouGoItemDTO);
        ItemDTO administrationFeeItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, AdministrationFeeItemDTO.class);
        items.add(administrationFeeItemDTO);
        return items;
    }
    
    public static List<ItemDTO> getListOfItemsWithNoPayAsYouGoItem() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO administrationFeeItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, AdministrationFeeItemDTO.class);
        items.add(administrationFeeItemDTO);
        return items;
    }
    
    public static List<ItemDTO> getListOfItemsWithAutoTopUpItem() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO payAsYouGoItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(payAsYouGoItemDTO);
        ItemDTO autoTopUpItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, AutoTopUpConfigurationItemDTO.class);
        items.add(autoTopUpItem);
        return items;
    }
    
    public static List<ItemDTO> getListOfItemsWithNoAutoTopUpItem() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO payAsYouGoItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(payAsYouGoItemDTO);
        ItemDTO administrationFeeItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, AdministrationFeeItemDTO.class);
        items.add(administrationFeeItemDTO);
        return items;
    }
    
    public static List<ItemDTO> getListOfItemsWithAnnualTravelCard(){
    	List<ItemDTO> items = new ArrayList<ItemDTO>();
    	ProductItemDTO productItemDTO = (ProductItemDTO) createItemDTO(ORDER_ID, ITEM_PRICE_100, ProductItemDTO.class);
        productItemDTO.setCardId(CardTestUtil.CARD_ID_1);
        productItemDTO.setDuration(Durations.ANNUAL.getDurationType());
        items.add(productItemDTO);
    	return items;
    }
    
    public static List<ItemDTO> getListOfItemsWithNoAnnualTravelCard(){
    	List<ItemDTO> items = new ArrayList<ItemDTO>();
    	ProductItemDTO productItemDTO = (ProductItemDTO) createItemDTO(ORDER_ID, ITEM_PRICE_100, ProductItemDTO.class);
        productItemDTO.setCardId(CardTestUtil.CARD_ID_1);
        productItemDTO.setDuration(Durations.MONTH.getDurationType());
        items.add(productItemDTO);
    	return items;
    }
    
    public static OrderDTO getOrderDTOWithAnnualTravelCard() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = getListOfItemsWithAnnualTravelCard();
        order.setOrderItems(items);
        return order;
    }
    
    public static OrderDTO getOrderDTOWithPayAsYouGoOnly() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = getListOfItemsWithPayAsYouGoItemOnly();
        order.setOrderItems(items);
        return order;
    }
    
    public static OrderDTO getOrderDTOWithAutoTopUp() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = getListOfItemsWithAutoTopUpItem();
        order.setOrderItems(items);
        return order;
    }
    
    public static OrderDTO getOrderDTOWithNoAutoTopUp() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = getListOfItemsWithNoAutoTopUpItem();
        order.setOrderItems(items);
        return order;
    }
    
    public static OrderDTO getOrderDTOInPaidStatus() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        order.setStatus(OrderStatus.PAID.code());
        List<ItemDTO> items = getListOfItemsWithNoAutoTopUpItem();
        order.setOrderItems(items);
        return order;
    }
    
    public static OrderDTO getOrderDTOForFulfilmentPendingQueue() {
        OrderDTO order = new OrderDTO();
        order.setId(OrderTestUtil.CARD_ID_1);
        order.setExternalId(ORDER_ID);
        order.setCustomerId(OrderTestUtil.CUSTOMER_ID_2);
        order.setStatus(OrderStatus.PAID.code());
        List<ItemDTO> items = getListOfItemsWithProductItem();
        order.setOrderItems(items);
        return order;
    }
    
    public static List<ItemDTO> getListOfItemsWithAutoTopUpItemAndProductItem() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO payAsYouGoItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(payAsYouGoItemDTO);
        ItemDTO autoTopUpItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, AutoTopUpConfigurationItemDTO.class);
        items.add(autoTopUpItem);
        ItemDTO productItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, ProductItemDTO.class);
        items.add(productItem);
        ItemDTO shippingMethodItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, ShippingMethodItemDTO.class);
        items.add(shippingMethodItem);
        ItemDTO cardRefundableDepositItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, CardRefundableDepositItemDTO.class);
        items.add(cardRefundableDepositItem);
        return items;
    }
    
    public static List<ItemDTO> getListOfItemsWithProductItem() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ItemDTO payAsYouGoItemDTO = createItemDTO(ORDER_ID, ITEM_PRICE_100, PayAsYouGoItemDTO.class);
        items.add(payAsYouGoItemDTO);
        ItemDTO productItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, ProductItemDTO.class);
        items.add(productItem);
        ItemDTO shippingMethodItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, ShippingMethodItemDTO.class);
        items.add(shippingMethodItem);
        ItemDTO cardRefundableDepositItem = createItemDTO(ORDER_ID, ITEM_PRICE_100, CardRefundableDepositItemDTO.class);
        items.add(cardRefundableDepositItem);
        return items;
    }
    
}
