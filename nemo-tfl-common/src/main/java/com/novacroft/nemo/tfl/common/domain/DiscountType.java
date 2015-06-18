package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.PeriodicAbstractBaseEntity;

@Audited
@Entity
public class DiscountType extends PeriodicAbstractBaseEntity {

    private static final long serialVersionUID = -1136630335917097848L;
    protected String code;
    protected String name;

    @SequenceGenerator(name = "DISCOUNTTYPE_SEQ", sequenceName = "DISCOUNTTYPE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCOUNTTYPE_SEQ")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
