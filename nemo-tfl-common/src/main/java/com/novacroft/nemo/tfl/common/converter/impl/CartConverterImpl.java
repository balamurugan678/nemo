package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Transform between cart entity and DTO.
 */
@Component(value = "cartConverter")
public class CartConverterImpl extends BaseDtoEntityConverterImpl<Cart, CartDTO> {
    static final Logger logger = LoggerFactory.getLogger(CartConverterImpl.class);

    @Autowired
    protected ItemConverterImpl itemConverterImpl;

    @Override
    protected CartDTO getNewDto() {
        return new CartDTO();
    }

    public Cart convertDtoToEntity(CartDTO dto, Cart entity) {
        entity = convertCartItems(dto, entity);
        return (Cart) Converter.convert(dto, entity);
    }

    protected Cart convertCartItems(CartDTO dto, Cart entity) {
        assert (null != dto);
        assert (null != entity);
        if (dto.getCartItems() != null) {
            Set<Item> items = entity.getItems();
            Map<Long, Item> itemEntitiesNotInCartDTO = buildItemLookupMap(entity.getItems());
            Item existingEntity = null;
            for (ItemDTO itemDTO : dto.getCartItems()) {
                existingEntity = itemEntitiesNotInCartDTO.get(itemDTO.getId());
                if (null == existingEntity) {
                    items.add(createItemEntity(itemDTO));
                } else {
                    existingEntity = updateItemEntity(itemDTO, existingEntity);
                    itemEntitiesNotInCartDTO.remove(existingEntity.getId());
                }
            }
            removeOrphanedItems(entity, itemEntitiesNotInCartDTO.values());
        }
        return entity;
    }

    private Item createItemEntity(ItemDTO itemDTO) {
        return itemConverterImpl.convertDtoToEntity(itemDTO, null);
    }

    private Item updateItemEntity(ItemDTO itemDTO, Item item) {
        return itemConverterImpl.convertDtoToEntity(itemDTO, item);
    }

    protected Map<Long, Item> buildItemLookupMap(Set<Item> entityItems) {
        Map<Long, Item> mapOfEntityItems = new HashMap<Long, Item>();
        if (null != entityItems && !entityItems.isEmpty()) {
            Iterator<Item> itemIterator = entityItems.iterator();
            Item entity = null;
            while (itemIterator.hasNext()) {
                entity = itemIterator.next();
                mapOfEntityItems.put(entity.getId(), entity);
            }
        }
        return mapOfEntityItems;
    }

    protected void removeOrphanedItems(Cart cartEntity, Collection<Item> itemsToBeRemoved) {
        cartEntity.getItems().removeAll(itemsToBeRemoved);
    }

    public CartDTO convertEntityToDto(Cart entity) {
        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        if (entity.getItems() != null) {
            for (Item item : entity.getItems()) {
                itemDTOs.add(itemConverterImpl.convertEntityToDto(item));
            }
        }
        CartDTO cartDTO = (CartDTO) Converter.convert(entity, getNewDto());
        cartDTO.setCartItems(itemDTOs);
        return cartDTO;
    }
}
