package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Audited
@MappedSuperclass()
public class CommonCallType extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 4807633484384442951L;
    protected String description;
    protected Integer active;

    public CommonCallType() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
