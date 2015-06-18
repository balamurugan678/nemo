package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * TfL zone id description domain definition
 */

@Entity
public class ZoneIdDescription extends AbstractBaseEntity {
    protected String description;

    @Transient
    private static final long serialVersionUID = 6200449962689402878L;

    public ZoneIdDescription() {
        super();
    }

    @Id
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
