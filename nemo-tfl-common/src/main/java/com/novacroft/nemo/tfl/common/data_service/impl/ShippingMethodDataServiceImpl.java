package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ShippingMethodConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ShippingMethodDAO;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.domain.ShippingMethod;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ShippingMethod data service implementation
 */
@Service(value = "shippingMethodDataService")
@Transactional(readOnly = true)
public class ShippingMethodDataServiceImpl extends BaseDataServiceImpl<ShippingMethod, ShippingMethodDTO>
        implements ShippingMethodDataService {
    static final Logger logger = LoggerFactory.getLogger(ShippingMethodDataServiceImpl.class);

    public ShippingMethodDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(ShippingMethodDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ShippingMethodConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public ShippingMethod getNewEntity() {
        return new ShippingMethod();
    }

    @Override
    public ShippingMethodDTO findByShippingMethodName(String name, boolean exact) {
        if (name != null) {
            final String hsql = "from ShippingMethod sm where sm.name " + (exact ? "=" : "like") + " ?";
            ShippingMethod shippingMethod = dao.findByQueryUniqueResult(hsql, addLike(name, exact));
            if (shippingMethod != null) {
                return converter.convertEntityToDto(shippingMethod);
            }
        }
        return null;
    }

}
