package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.PeriodicAbstractBaseDTO;

public class PriceDTO extends PeriodicAbstractBaseDTO {

	private static final long serialVersionUID = -1705809668613250464L;
    protected Integer priceInPence;

    public PriceDTO() {

    }

    public PriceDTO(Long id, Integer priceInPence, Date effectiveFrom, Date effectiveTo, String cubicReference) {
        this.id = id;
        this.priceInPence = priceInPence;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.cubicReference = cubicReference;
    }

    public Integer getpriceInPence() {
        return priceInPence;
    }

    public void setpriceInPence(Integer priceInPence) {
        this.priceInPence = priceInPence;
    }
}
