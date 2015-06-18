package com.novacroft.nemo.common.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * System parameter domain definition
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemParameter extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 1L;
    protected String code;
    protected String value;
    protected String purpose;

    public SystemParameter() {
    }

    @SequenceGenerator(name = "SYSTEMPARAMETER_SEQ", sequenceName = "SYSTEMPARAMETER_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEMPARAMETER_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
