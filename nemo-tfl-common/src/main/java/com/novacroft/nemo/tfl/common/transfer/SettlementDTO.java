package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.transfer.CommonSettlementDTO;

/**
 * Settlement attributes.  A settlement is a payment or refund against an order.
 */
public class SettlementDTO extends CommonSettlementDTO {

    private static final long serialVersionUID = -1313341388510970022L;

    public SettlementDTO() {
        super();
    }

    public SettlementDTO(Long orderId, String status, Date settlementDate, Integer amount) {
        super(orderId, status, settlementDate, amount);
    }

    public SettlementDTO(Long orderId, String status, Date settlementDate, Integer amount, DateTime createdDateTime, DateTime modifiedDateTime) {
        super(orderId, status, settlementDate, amount);
        this.setCreatedDateTime(createdDateTime.toDate());
        this.setModifiedDateTime(modifiedDateTime.toDate());
    }
}
