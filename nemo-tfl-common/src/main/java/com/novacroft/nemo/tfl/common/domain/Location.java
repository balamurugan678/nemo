package com.novacroft.nemo.tfl.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import com.novacroft.nemo.common.domain.BaseEntity;
import com.novacroft.nemo.common.transfer.ConverterNullable;

/**
 * TfL Locations, ie stations (NLC Lookup)
 */
@Entity
@Table(name = "LOCATION_V")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Location implements BaseEntity, Serializable, ConverterNullable {
    private static final long serialVersionUID = -638398241419348632L;
    protected String name;
    protected String status;

    protected Long id;
    @Column(updatable = false)
    protected String createdUserId;
    @Column(updatable = false)
    protected Date createdDateTime;
    protected String modifiedUserId;
    protected Date modifiedDateTime;
    @Transient
    protected Boolean nullable = Boolean.FALSE;

    public Location() {
    }
    
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Date getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Date modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public void setCreated() {
        this.createdUserId = "SYS";
        this.createdDateTime = new Date();
    }
    
    @Transient
    public Boolean isNullable() {
        return nullable;
    }
    @Transient
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    @Id
    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
