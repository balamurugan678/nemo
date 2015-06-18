package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ShippingMethodItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ShippingMethodItemDAO;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodItemDataService;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Shipping method item data service implementation
 */
@Service(value = "shippingMethodItemDataService")
@Transactional(readOnly = true)
public class ShippingMethodItemDataServiceImpl extends BaseDataServiceImpl<ShippingMethodItem, ShippingMethodItemDTO>
        implements ShippingMethodItemDataService {
    static final Logger logger = LoggerFactory.getLogger(ShippingMethodItemDataServiceImpl.class);

    public ShippingMethodItemDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(ShippingMethodItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ShippingMethodItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ShippingMethodItem getNewEntity() {
        return new ShippingMethodItem();
    }

    @Override
    public ShippingMethodItemDTO findByCartIdAndCardId(Long cartId, Long cardId) {
        if (cartId != null && cardId != null) {
            ShippingMethodItem shippingMethodItem = new ShippingMethodItem();
            shippingMethodItem.setCardId(cardId);
            shippingMethodItem = dao.findByExampleUniqueResult(shippingMethodItem);
            if (shippingMethodItem != null) {
                return this.converter.convertEntityToDto(shippingMethodItem);
            }
        }
        return null;
    }

    @Override
    public List<ShippingMethodItemDTO> findByOrderNumber(Long customerOrderId) {
        ShippingMethodItem shippingMethodItem = new ShippingMethodItem();
        shippingMethodItem.setOrderId(customerOrderId);
        List<ShippingMethodItem> items = dao.findByExample(shippingMethodItem);
        return convert(items);
    }

}
