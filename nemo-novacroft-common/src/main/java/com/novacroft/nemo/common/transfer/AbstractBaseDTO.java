package com.novacroft.nemo.common.transfer;

import java.io.Serializable;
import java.util.Date;

/**
 * Base abstract implementation for all data transfer classes.
 */
public class AbstractBaseDTO implements BaseDTO, ConverterNullable, Serializable {
    private static final long serialVersionUID = -7358946561297289953L;
    
    protected Long id;
    protected String createdUserId;
    protected Date createdDateTime;
    protected String modifiedUserId;
    protected Date modifiedDateTime;
    protected Boolean nullable = Boolean.FALSE;
    protected Long externalId;
    
    @Override
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

    @Override
    public Boolean isNullable() {
        return nullable;
    }

    @Override
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
