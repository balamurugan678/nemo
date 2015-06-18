package com.novacroft.nemo.common.domain;

import com.novacroft.nemo.common.listener.hibernate.GeneratedSequenceDependence;
import com.novacroft.nemo.common.transfer.ConverterNullable;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Base abstract implementation for all domain classes.
 */
@Audited
@MappedSuperclass()
@GeneratedSequenceDependence(fieldNames = {"externalId"}, sequenceNames = {"EXTERNALID_SEQ"})
public abstract class AbstractBaseEntity implements BaseEntity, Serializable, ConverterNullable {
    private static final long serialVersionUID = 3812042476489200516L;

    protected Long id;
    @Column(updatable = false)
    protected String createdUserId;
    @Column(updatable = false)
    protected Date createdDateTime;
    protected String modifiedUserId;
    protected Date modifiedDateTime;
    @Transient
    protected Boolean nullable = Boolean.FALSE;
    @Column(name = "EXTERNALID", nullable = false, unique = true)
    protected Long externalId;

    @Override
    @Transient
    public Long getId() {
        return this.id;
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

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

}
