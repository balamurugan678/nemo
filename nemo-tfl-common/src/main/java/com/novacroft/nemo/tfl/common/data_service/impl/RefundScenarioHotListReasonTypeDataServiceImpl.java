package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.RefundScenarioHotListReasonTypeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.RefundScenarioHotListReasonTypeDAO;
import com.novacroft.nemo.tfl.common.data_service.RefundScenarioHotListReasonTypeDataService;
import com.novacroft.nemo.tfl.common.domain.RefundScenarioHotListReasonType;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

@Service
public class RefundScenarioHotListReasonTypeDataServiceImpl extends
                BaseDataServiceImpl<RefundScenarioHotListReasonType, RefundScenarioHotListReasonTypeDTO> implements
                RefundScenarioHotListReasonTypeDataService {

    @Override
    public RefundScenarioHotListReasonType getNewEntity() {

        return new RefundScenarioHotListReasonType();
    }

    @Autowired
    public void setDao(RefundScenarioHotListReasonTypeDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(RefundScenarioHotListReasonTypeConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    @Transactional
    public RefundScenarioHotListReasonTypeDTO findByRefundScenario(RefundScenarioEnum refundScenarioEnum) {
        if (null != refundScenarioEnum) {
            RefundScenarioHotListReasonType refundScenarioHotListReasonType = new RefundScenarioHotListReasonType();
            refundScenarioHotListReasonType.setRefundScenarioEnum(refundScenarioEnum);
            final List<RefundScenarioHotListReasonType> refundScenarioHotListReasonTypeList =  this.dao.findByExample(refundScenarioHotListReasonType);
            if (!refundScenarioHotListReasonTypeList.isEmpty()) {
                return this.converter.convertEntityToDto(this.dao.findByExample(refundScenarioHotListReasonType).get(0));
            }
        }
        return null;
    }

    
}
