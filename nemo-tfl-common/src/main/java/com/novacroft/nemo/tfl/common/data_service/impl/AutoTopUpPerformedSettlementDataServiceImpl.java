package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AutoTopUpPerformedSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoTopUpPerformedSettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpPerformedSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpPerformedSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpPerformedSettlementDTO;

/**
 * Auto Top-Up Performed Settlement data service implementation
 */
@Service("autoTopUpPerformedSettlementDataService")
@Transactional(readOnly = true)
public class AutoTopUpPerformedSettlementDataServiceImpl extends BaseDataServiceImpl<AutoTopUpPerformedSettlement, AutoTopUpPerformedSettlementDTO>
        implements AutoTopUpPerformedSettlementDataService {
    
    @Override
    public List<AutoTopUpPerformedSettlementDTO> findByOrderId(Long orderId) {
        final String hsql = "from AutoTopUpPerformedSettlement s where s.orderId = ?";
        return convert(dao.findByQuery(hsql, orderId));
    }

    @Override
    public AutoTopUpPerformedSettlement getNewEntity() {
        return new AutoTopUpPerformedSettlement();
    }

    @Autowired
    public void setDao(AutoTopUpPerformedSettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AutoTopUpPerformedSettlementConverterImpl converter) {
        this.converter = converter;
    }
}
