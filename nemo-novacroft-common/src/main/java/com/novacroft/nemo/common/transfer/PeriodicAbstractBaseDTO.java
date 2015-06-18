package com.novacroft.nemo.common.transfer;

import java.util.Date;

public abstract class PeriodicAbstractBaseDTO  extends AbstractBaseDTO {

	private static final long serialVersionUID = -8417434605196752217L;

    protected Date effectiveFrom;
    protected Date effectiveTo;
    protected String cubicReference;

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getCubicReference() {
        return cubicReference;
    }

    public void setCubicReference(String cubicReference) {
        this.cubicReference = cubicReference;
    }
}
