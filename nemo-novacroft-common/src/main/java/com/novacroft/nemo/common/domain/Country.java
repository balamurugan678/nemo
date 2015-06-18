package com.novacroft.nemo.common.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * ISO 3166-1 country names and two letter codes
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Country extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 1453646062439767490L;
    protected String name;
    protected String code;
    protected String isoNumericCode;

    @SequenceGenerator(name = "COUNTRY_SEQ", sequenceName = "COUNTRY_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_SEQ")
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getIsoNumericCode() {
        return isoNumericCode;
    }
    
    public void setIsoNumericCode(String isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }
}
