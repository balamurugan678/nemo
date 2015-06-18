package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.domain.RefundScenarioHotListReasonType;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

public interface RefundScenarioHotListReasonTypeDataService extends BaseDataService<RefundScenarioHotListReasonType, RefundScenarioHotListReasonTypeDTO> {

    RefundScenarioHotListReasonTypeDTO findByRefundScenario(RefundScenarioEnum refundScenarioEnum);
    
}
