package com.novacroft.nemo.tfl.common.transfer;

import java.sql.Date;


/**
 * TfL auto top-up performed data transfer object
 */
public class AutoTopUpPerformedSettlementDTO extends SettlementDTO {
    private static final long serialVersionUID = 5496715858643282983L;

    protected Integer autoLoadState;

    public AutoTopUpPerformedSettlementDTO() {
        
    }
    
    public AutoTopUpPerformedSettlementDTO(Long orderId, String status, java.util.Date settlementDate, Integer amount, Integer autoLoadState) {
        this.orderId = orderId;
        this.status = status;
        this.settlementDate = settlementDate;
        this.amount = amount;
        this.autoLoadState = autoLoadState;
    }

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }
}
