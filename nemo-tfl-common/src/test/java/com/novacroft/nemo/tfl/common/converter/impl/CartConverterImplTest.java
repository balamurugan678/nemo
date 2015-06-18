package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CartConverterImplTest {

    private CartConverterImpl converter;

    private static final Long LONG_ID1 = new Long(1);
    private static final Long LONG_ID2 = new Long(2);

    @Before
    public void setUp() {
        converter = new CartConverterImpl();
        ReflectionTestUtils.setField(converter, "itemConverterImpl", new ItemConverterImpl());

    }

    @Test
    public void shouldBuildMapOfItems() {
        Cart entity = buildCart1();
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(entity.getItems());
        assertEquals(2, mapOfItems.size());
    }

    @Test
    public void shouldUpdateAllItemDTOToItem() {
        CartDTO dto = buildCartDTO1();
        Cart entity = buildCart1();
        Cart cart = converter.convertDtoToEntity(dto, entity);
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(cart.getItems());
        assertEquals(2, cart.getItems().size());
        assertEquals(100, mapOfItems.get(LONG_ID1).getPrice().intValue());
        assertEquals(566, mapOfItems.get(LONG_ID2).getPrice().intValue());
    }

    @Test
    public void shouldMapAllItemToItemDTO() {
        Cart entity = buildCart1();
        CartDTO dto = converter.convertEntityToDto(entity);
        Map<Long, ItemDTO> mapOfItems = buildItemDTOLookupMap(dto.getCartItems());
        assertEquals(2, dto.getCartItems().size());
        assertEquals(100, mapOfItems.get(LONG_ID1).getPrice().intValue());
        assertEquals(566, mapOfItems.get(LONG_ID2).getPrice().intValue());
    }

    @Test
    public void shouldAddItemEntity1() {
        CartDTO dto = buildCartDTO1();
        Cart entity = buildCart2();
        Cart cart = converter.convertDtoToEntity(dto, entity);
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(cart.getItems());
        assertEquals(2, cart.getItems().size());
        assertEquals(100, mapOfItems.get(LONG_ID1).getPrice().intValue());
        assertEquals(566, mapOfItems.get(LONG_ID2).getPrice().intValue());
    }

    @Test
    public void shouldDeleteItemEntity1() {
        CartDTO dto = buildCartDTO2();
        Cart entity = buildCart1();
        Cart cart = converter.convertDtoToEntity(dto, entity);
        Map<Long, Item> mapOfItems = converter.buildItemLookupMap(cart.getItems());
        assertEquals(1, cart.getItems().size());
        assertEquals(566, mapOfItems.get(LONG_ID2).getPrice().intValue());
    }

    @Test
    public void shouldDeleteAllItemEntity1() {
        CartDTO dto = new CartDTO();
        Cart entity = buildCart1();
        Cart cart = converter.convertDtoToEntity(dto, entity);
        assertEquals(0, cart.getItems().size());
    }

    private CartDTO buildCartDTO1() {
        CartDTO cart = new CartDTO();
        cart.setId(CartTestUtil.CARD_ID_1);
        cart.setCustomerId(CartTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(buildItemDTO(new Long(1), Integer.valueOf(100), AdministrationFeeItemDTO.class));
        items.add(buildItemDTO(new Long(2), Integer.valueOf(566), ShippingMethodItemDTO.class));
        cart.setCartItems(items);
        return cart;
    }

    private CartDTO buildCartDTO2() {
        CartDTO cart = new CartDTO();
        cart.setId(CartTestUtil.CARD_ID_1);
        cart.setCustomerId(CartTestUtil.CUSTOMER_ID_2);
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(buildItemDTO(new Long(2), Integer.valueOf(566), ShippingMethodItemDTO.class));
        cart.setCartItems(items);
        return cart;
    }

    private Cart buildCart1() {
        Cart cart = new Cart();
        cart.setId(CartTestUtil.CARD_ID_1);
        cart.setCustomerId(CartTestUtil.CUSTOMER_ID_2);
        Set<Item> items = new HashSet<Item>();
        items.add(buildItem(new Long(1), Integer.valueOf(100), AdministrationFeeItem.class));
        items.add(buildItem(new Long(2), Integer.valueOf(566), ShippingMethodItem.class));
        cart.setItems(items);
        return cart;
    }

    private Cart buildCart2() {
        Cart cart = new Cart();
        cart.setId(CartTestUtil.CARD_ID_1);
        cart.setCustomerId(CartTestUtil.CUSTOMER_ID_2);
        Set<Item> items = new HashSet<Item>();
        items.add(buildItem(new Long(1), Integer.valueOf(9999), AdministrationFeeItem.class));
        items.add(buildItem(new Long(2), Integer.valueOf(9999), ShippingMethodItem.class));
        cart.setItems(items);
        return cart;
    }

    private ItemDTO buildItemDTO(Long id, Integer price, Class itemDTOSubclasss) {
        try {
            ItemDTO item = (ItemDTO) itemDTOSubclasss.newInstance();
            item.setId(id);
            item.setPrice(price);
            return item;
        } catch (Exception e) {
            return null;
        }
    }

    private Item buildItem(Long id, Integer price, Class itemSubclasss) {
        try {
            Item item = (Item) itemSubclasss.newInstance();
            item.setId(id);
            item.setPrice(price);
            return item;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<Long, ItemDTO> buildItemDTOLookupMap(List<ItemDTO> items) {
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
}
