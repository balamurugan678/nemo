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
public class Duration extends PeriodicAbstractBaseEntity {

    private static final long serialVersionUID = -1968087078367288900L;
    protected Integer duration;
    protected String unit;
    protected String code;
    protected String name;

    @SequenceGenerator(name = "DURATION_SEQ", sequenceName = "DURATION_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DURATION_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
