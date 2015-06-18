package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AutoTopUpConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoTopUpDAO;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUp;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Auto top-up data service implementation
 */
@Service(value = "autoTopUpDataService")
@Transactional(readOnly = true)
public class AutoTopUpDataServiceImpl extends BaseDataServiceImpl<AutoTopUp, AutoTopUpDTO> implements AutoTopUpDataService {
    static final Logger logger = LoggerFactory.getLogger(AutoTopUpDataServiceImpl.class);

    public AutoTopUpDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(AutoTopUpDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AutoTopUpConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public AutoTopUp getNewEntity() {
        return new AutoTopUp();
    }

    @Override
    public AutoTopUpDTO findByAutoTopUpAmount(Integer autoTopUpAmount) {
        if (autoTopUpAmount != null) {
            AutoTopUp autoTopUp = new AutoTopUp();
            autoTopUp.setAutoTopUpAmount(autoTopUpAmount);
            autoTopUp = dao.findByExampleUniqueResult(autoTopUp);
            if (autoTopUp != null) {
                return this.converter.convertEntityToDto(autoTopUp);
            }
        }
        return null;
    }

}
