package com.novacroft.nemo.tfl.common.converter.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

public class OrderConverterImplTest {

    private OrderConverterImpl converter;

    @Before
    public void setUp() {
        converter = new OrderConverterImpl();
        ReflectionTestUtils.setField(converter, "itemConverterImpl", new ItemConverterImpl());

    }

    @Test
    public void shouldBuildMapOfItems() {
        Order entity = OrderTestUtil.getOrderEntityWithAdminFeeAndShippingItem();
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(entity.getItems());
        assertEquals(2, mapOfItems.size());
    }

    @Test
    public void shouldUpdateAllItemDTOToItem() {
        OrderDTO dto = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        Order entity = OrderTestUtil.getOrderEntityWithAdminFeeAndShippingItem();
        Order order = converter.convertDtoToEntity(dto, entity);
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(order.getItems());
        assertEquals(2, order.getItems().size());
        assertEquals(OrderTestUtil.ITEM_PRICE_100.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID).getPrice().intValue());
        assertEquals(OrderTestUtil.ITEM_PRICE_566.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID_2).getPrice().intValue());
    }

    @Test
    public void shouldMapAllItemToItemDTO() {
        Order entity = OrderTestUtil.getOrderEntityWithAdminFeeAndShippingItem();
        OrderDTO dto = converter.convertEntityToDto(entity);
        Map<Long, ItemDTO> mapOfItems = OrderTestUtil.createItemDTOLookupMap(dto.getOrderItems());
        assertEquals(2, dto.getOrderItems().size());
        assertEquals(OrderTestUtil.ITEM_PRICE_100.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID).getPrice().intValue());
        assertEquals(OrderTestUtil.ITEM_PRICE_566.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID_2).getPrice().intValue());
    }

    @Test
    public void shouldAddItemEntity1() {
        OrderDTO dto = OrderTestUtil.getOrderDTOWithAdminFeeAndShippingItem();
        Order entity = OrderTestUtil.getOrderEntityWithAdminFeeAndShippingItem2();
        Order cart = converter.convertDtoToEntity(dto, entity);
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(cart.getItems());
        assertEquals(2, cart.getItems().size());
        assertEquals(OrderTestUtil.ITEM_PRICE_100.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID).getPrice().intValue());
        assertEquals(OrderTestUtil.ITEM_PRICE_566.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID_2).getPrice().intValue());
    }

    @Test
    public void shouldDeleteItemEntity1() {
        OrderDTO dto = OrderTestUtil.getOrderDTOWithShippingItem();
        Order entity = OrderTestUtil.getOrderEntityWithAdminFeeAndShippingItem();
        Order cart = converter.convertDtoToEntity(dto, entity);
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(cart.getItems());
        assertEquals(1, cart.getItems().size());
        assertEquals(OrderTestUtil.ITEM_PRICE_566.intValue(), mapOfItems.get(OrderTestUtil.ORDER_ID_2).getPrice().intValue());
    }

    @Test
    public void shouldDeleteAllItemEntity1() {
        OrderDTO dto = OrderTestUtil.getOrderDTOWithoutItems();
        Order entity = OrderTestUtil.getOrderEntityWithAdminFeeAndShippingItem();
        Order order = converter.convertDtoToEntity(dto, entity);
        assertEquals(0, order.getItems().size());
    }


}
