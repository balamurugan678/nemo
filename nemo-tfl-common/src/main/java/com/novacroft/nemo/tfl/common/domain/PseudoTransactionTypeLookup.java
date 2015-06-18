package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TfL pttid lookup description domain definition
 */

@Entity
@Table(name = "PTTIDLOOKUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PseudoTransactionTypeLookup extends AbstractBaseEntity {
    protected String description;
    protected String displayDescription;

    @Id
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
