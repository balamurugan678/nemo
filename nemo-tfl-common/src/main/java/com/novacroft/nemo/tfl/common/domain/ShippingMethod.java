package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

/**
 * TfL shipping method domain definition
 */
@Entity
public class ShippingMethod extends AbstractBaseEntity {
    private static final long serialVersionUID = -4737628704444041743L;

    protected String name;
    protected Integer price;

    @SequenceGenerator(name = "SHIPPINGMETHOD_SEQ", sequenceName = "SHIPPINGMETHOD_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPPINGMETHOD_SEQ")
    @Column(name = "ID")
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
