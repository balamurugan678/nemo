package com.novacroft.nemo.common.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

@Audited
@MappedSuperclass()
public abstract class PeriodicAbstractBaseEntity extends AbstractBaseEntity {

    private static final long serialVersionUID = -5438736301426142542L;
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
