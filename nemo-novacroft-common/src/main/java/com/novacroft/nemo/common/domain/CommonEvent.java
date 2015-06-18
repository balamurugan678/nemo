package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;

/**
 * Common domain object to hold the Event information
 */
@Audited
@MappedSuperclass()
public class CommonEvent extends AbstractBaseEntity {
    private static final long serialVersionUID = -3873401110421947674L;
    protected String name;
    protected String description;
    protected Integer displayOrder;
    protected Integer displayOnline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getDisplayOnline() {
        return displayOnline;
    }

    public void setDisplayOnline(Integer displayOnline) {
        this.displayOnline = displayOnline;
    }

}
