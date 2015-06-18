package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AutoTopUpHistoryItemConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ItemDAO;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Item data service implementation
 */
@Deprecated
@Service(value = "itemDataService")
@Transactional(readOnly = true)
public class ItemDataServiceImpl extends BaseDataServiceImpl<Item, ItemDTO> implements ItemDataService {
    static final Logger logger = LoggerFactory.getLogger(ItemDataServiceImpl.class);

    protected static final String DTO = "DTO";
    protected static final String DOMAIN = "domain";
    protected static final String TRANSFER = "transfer";
    protected static final String BLANK = "";

    public ItemDataServiceImpl() {
        super();
    }
    
    @Autowired
    protected AutoTopUpHistoryItemConverterImpl autoTopUpHistoryItemConverter;

    @Autowired
    public void setDao(ItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Item getNewEntity() {
        return new Item();
    }

    @Override
    public List<ItemDTO> findAllByExample(Item example) {
        List<Item> items = dao.findByExample(example);
        return convert(items);
    }

    @Override
    public List<ItemDTO> findByOrderId(Long orderId) {
        Item item = new Item();
        item.setOrderId(orderId);
        List<Item> items = dao.findByExampleWithOrderBy(item, org.hibernate.criterion.Order.desc("id"));
        return convert(items);
    }

    @Override
    @Transactional
    public ItemDTO createOrUpdate(ItemDTO itemDTO) {
        Item item = null;
        if (itemDTO.getId() != null) {
            item = dao.findById(itemDTO.getId());
            item.setModifiedDateTime(new Date());
            item.setModifiedUserId(this.nemoUserContext.getUserName());
        } else {
            item = getNewEntity(itemDTO);
            item.setCreatedDateTime(new Date());
            item.setCreatedUserId(this.nemoUserContext.getUserName());
        }
        item = converter.convertDtoToEntity(itemDTO, item);
        item = dao.createOrUpdate(item);
        return converter.convertEntityToDto(item);
    }

    @Override
    public ItemDTO setDateTime(ItemDTO itemDTO) {
        if (itemDTO.getId() != null) {
            itemDTO.setModifiedDateTime(new Date());
        } else {
            itemDTO.setCreatedDateTime(new Date());
        }
        return itemDTO;
    }

    @Override
    public ItemDTO setUserId(ItemDTO itemDTO) {
        if (itemDTO.getId() != null) {
            itemDTO.setModifiedUserId(this.nemoUserContext.getUserName());
        } else {
            itemDTO.setCreatedUserId(this.nemoUserContext.getUserName());
        }
        return itemDTO;
    }

    public Item getNewEntity(ItemDTO itemDTO) {
        Class<? extends ItemDTO> itemDTOClass = itemDTO.getClass();
        String itemDTOClassName = itemDTOClass.getName();
        String itemClassName = itemDTOClassName.replace(DTO, BLANK);
        itemClassName = itemClassName.replace(TRANSFER, DOMAIN);
        try {
            Class<?> itemClass = Class.forName(itemClassName);
            return (Item) itemClass.newInstance();
        } catch (ReflectiveOperationException roe) {
            return new Item();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AutoTopUpHistoryItemDTO> findAllAutoTopUpsForCard(Long cardId) {
        String sql = "SELECT i.autoTopUpActivity, c.stationId, c.orderDate, a.autotopupAmount, s.settlementdate, s.status " +
                     "FROM CustomerOrder c, Item i, AutoTopUp a, Settlement s " + 
                     "WHERE c.id = i.customerOrderId " +
                     "AND i.autoTopUpId = a.id " +
                     "AND i.customerOrderId = s.customerorderid " +
                     "AND s.autoloadstate IS NOT NULL " +
                     "AND c.cardId = :cardId " +
                     "ORDER BY c.orderDate";
        Query query = dao.createSQLQuery(sql);
        query.setParameter("cardId", cardId);
        List<Object[]> rows = query.list();
        return autoTopUpHistoryItemConverter.convertEntitiesToDTOs(rows);        
    }
    
}
