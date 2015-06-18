package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

/**
 * TfL administration fee domain definition
 */

@Entity
public class AdministrationFee extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 6200449962689402878L;

    protected String type;
    protected Integer price;
    protected String description;

    public AdministrationFee() {
        super();
    }

    public AdministrationFee(String type, Integer price) {
        this.type = type;
        this.price = price;
    }

    @SequenceGenerator(name = "ADMINISTRATIONFEE_SEQ", sequenceName = "ADMINISTRATIONFEE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMINISTRATIONFEE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
