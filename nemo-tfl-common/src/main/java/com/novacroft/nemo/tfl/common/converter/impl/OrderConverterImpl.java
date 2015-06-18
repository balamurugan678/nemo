package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Order / OrderDTO translator
 */
@Component(value = "orderConverter")
public class OrderConverterImpl extends BaseDtoEntityConverterImpl<Order, OrderDTO> {

    @Autowired
    protected ItemConverterImpl itemConverterImpl;
    
    @Override
    protected OrderDTO getNewDto() {
        return new OrderDTO();
    }

    public Order convertDtoToEntity(OrderDTO dto, Order entity) {
        entity = convertOrderItems(dto, entity);
        return (Order) Converter.convert(dto, entity);
    }

    protected Order convertOrderItems(OrderDTO dto, Order entity) {
        if (dto.getOrderItems() != null) {
            Set<Item> items = entity.getItems();
            Map<Long, Item> itemEntitiesNotInOrderDTO = buildItemLookupMap(entity.getItems());
            Item existingEntity = null;
            for (ItemDTO itemDTO : dto.getOrderItems()) {
                existingEntity = itemEntitiesNotInOrderDTO.get(itemDTO.getId());
                if (null == existingEntity) {
                    items.add(createItemEntity(itemDTO));
                } else {
                    existingEntity = updateItemEntity(itemDTO, existingEntity);
                    itemEntitiesNotInOrderDTO.remove(existingEntity.getId());
                }
            }
            removeOrphanedItems(entity, itemEntitiesNotInOrderDTO.values());
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

    protected void removeOrphanedItems(Order orderEntity, Collection<Item> itemsToBeRemoved) {
        orderEntity.getItems().removeAll(itemsToBeRemoved);

    }

    public OrderDTO convertEntityToDto(Order entity) {
    List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
    if (entity.getItems() != null) {
        for (Item item : entity.getItems()) {
        itemDTOs.add(itemConverterImpl.convertEntityToDto(item));
        }
    }
    OrderDTO orderDTO = (OrderDTO) Converter.convert(entity, getNewDto());
    orderDTO.setOrderItems(itemDTOs);
    return orderDTO; 
    }
}
