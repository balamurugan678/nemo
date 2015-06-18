package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;

public class RefundScenarioHotListReasonTypeDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2208358609160416622L;

    protected  RefundScenarioEnum refundScenarioEnum;
    
    protected HotlistReasonDTO hotlistReasonDTO;

    public RefundScenarioEnum getRefundScenarioEnum() {
        return refundScenarioEnum;
    }

    public void setRefundScenarioEnum(RefundScenarioEnum refundScenarioEnum) {
        this.refundScenarioEnum = refundScenarioEnum;
    }

    public HotlistReasonDTO getHotlistReasonDTO() {
        return hotlistReasonDTO;
    }

    public void setHotlistReasonDTO(HotlistReasonDTO hotlistReasonDTO) {
        this.hotlistReasonDTO = hotlistReasonDTO;
    }
    
}
