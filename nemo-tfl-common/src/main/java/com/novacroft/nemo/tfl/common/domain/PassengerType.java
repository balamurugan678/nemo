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
public class PassengerType extends PeriodicAbstractBaseEntity {

    private static final long serialVersionUID = 5382823668993173942L;
    protected String code;
    protected String name;

    @SequenceGenerator(name = "PASSENGERTYPE_SEQ", sequenceName = "PASSENGERTYPE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSENGERTYPE_SEQ")
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
