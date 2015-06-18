package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.converter.impl.CustomerPreferencesConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CustomerPreferencesDAO;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.domain.CustomerPreferences;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TfL customer preferences data service implementation.
 */
@Service(value = "customerPreferencesDataService")
@Transactional(readOnly = true)
public class CustomerPreferencesDataServiceImpl extends BaseDataServiceImpl<CustomerPreferences, CustomerPreferencesDTO>
        implements CustomerPreferencesDataService {
    static final Logger logger = LoggerFactory.getLogger(CustomerPreferencesDataServiceImpl.class);

    @Override
    public CustomerPreferences getNewEntity() {
        return new CustomerPreferences();
    }

    @Override
    @SuppressWarnings("unchecked")
    public CustomerPreferencesDTO findByCustomerId(Long customerId) {
        final String hsql = "select cp from CustomerPreferences cp where cp.customerId = ?";
        List<CustomerPreferences> results = dao.findByQuery(hsql, customerId);
        if (results.size() > 1) {
            String msg = String.format(PrivateError.MORE_THAN_ONE_RECORD_FOR_CUSTOMER.message(), customerId);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        }
        return null;
    }

    @Autowired
    public void setDao(CustomerPreferencesDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CustomerPreferencesConverterImpl converter) {
        this.converter = converter;
    }
}
