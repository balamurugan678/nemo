package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.RefundScenarioHotListReasonType;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

@Component
public class RefundScenarioHotListReasonTypeConverterImpl extends BaseDtoEntityConverterImpl<RefundScenarioHotListReasonType, RefundScenarioHotListReasonTypeDTO> {

    @Override
    protected RefundScenarioHotListReasonTypeDTO getNewDto() {
        return new RefundScenarioHotListReasonTypeDTO();
    }
    
    @Autowired
    protected HotlistReasonConverterImpl hotListReasonConverter;

    @Override
    public RefundScenarioHotListReasonTypeDTO convertEntityToDto(RefundScenarioHotListReasonType refundScenarioHotListReasonType) {
        RefundScenarioHotListReasonTypeDTO refundScenarioHotListReasonTypeDTO = super.convertEntityToDto(refundScenarioHotListReasonType);
        refundScenarioHotListReasonTypeDTO.setHotlistReasonDTO(hotListReasonConverter.convertEntityToDto(refundScenarioHotListReasonType.getHotListReaon()));
        return refundScenarioHotListReasonTypeDTO;
    }
}
