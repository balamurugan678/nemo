package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ProductItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ProductItemDAO;
import com.novacroft.nemo.tfl.common.data_service.ProductItemDataService;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Product item data service implementation
 */
@Service(value = "productItemDataService")
@Transactional(readOnly = true)
public class ProductItemDataServiceImpl extends BaseDataServiceImpl<ProductItem, ProductItemDTO>
        implements ProductItemDataService {
    static final Logger logger = LoggerFactory.getLogger(ProductItemDataServiceImpl.class);

    public ProductItemDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(ProductItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ProductItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ProductItem getNewEntity() {
        return new ProductItem();
    }

    @Override
    public List<ProductItemDTO> findByCartIdAndCardId(Long cartId, Long cardId) {
        if (cartId != null && cardId != null) {
            ProductItem productItem = new ProductItem();
            List<ProductItem> productItems = dao.findByExample(productItem);
            return convertProductItems(productItems);
        }
        return Collections.<ProductItemDTO>emptyList();
    }

    /** 
     * Custom converter for productItem required for the one to one self referencing hibernate mapping for 
     * traded tickets which cannot be handled by current implementation of converter.
     * Current converter doesn't handle nested complex entities
     * @param items List<ProductItem>
     * @return List<ProductItemDTO>
     */
    private List<ProductItemDTO> convertProductItems(List<ProductItem> items) {
        List<ProductItemDTO> dtoItems = new ArrayList<ProductItemDTO>();
        List<ProductItemDTO> tradedItems = new ArrayList<ProductItemDTO>();
        List<ProductItemDTO> dtoItemsWithTradedTicketsFiltered = new ArrayList<ProductItemDTO>();
        if (items != null) {
            if (items instanceof List<?>) {
                if (items.size() > 0) {
                    for (ProductItem item : items) {
                        ProductItemDTO itemDTO = converter.convertEntityToDto(item);
                        if (null != item.getRelatedItem() && item.getRelatedItem().getClass().equals(ProductItem.class)) {
                            ProductItemDTO tradedDTO = converter.convertEntityToDto((ProductItem)item.getRelatedItem());
                            itemDTO.setRelatedItem(tradedDTO);
                            tradedItems.add(tradedDTO);
                        }
                        dtoItems.add(itemDTO);
                    }
                    for (ProductItemDTO dtoItem : dtoItems) {
                        Boolean addItem = true;
                        for (ProductItemDTO tradedItem : tradedItems) {
                            if (tradedItem.getId() == dtoItem.getId()) {
                                addItem = false;
                                break;
                            }
                        }
                        if (addItem) {
                            dtoItemsWithTradedTicketsFiltered.add(dtoItem);
                        }

                    }

                }
            }
        }

        return dtoItemsWithTradedTicketsFiltered;
    }

    @Override
    public List<ProductItemDTO> findByCustomerOrderId(Long customerOrderId) {
        ProductItem productItem = new ProductItem();
        productItem.setOrderId(customerOrderId);
        List<ProductItem> items = dao.findByExample(productItem);
        return convert(items);
    }

    @Override
    public List<ProductItemDTO> findAllByExample(ProductItem productItem) {
        List<ProductItem> items = dao.findByExample(productItem);
        return convert(items);
    }
}
