package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;

/**
 * Common domain object to hold the  Workqueue information
 * Automatically created.
 */

@Audited
@MappedSuperclass()
public class CommonWorkQueue extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;
    protected Long stage;
    protected String status;
    protected String description;

    public Long getStage() {
        return stage;
    }

    public void setStage(Long stage) {
        this.stage = stage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
