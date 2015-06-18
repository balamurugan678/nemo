package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.CommonProduct;

import javax.persistence.*;

/**
 * TfL product implementation
 */

@Entity
public class Product extends CommonProduct {

    @Transient
    private static final long serialVersionUID = 6200449962689402878L;
    
    protected Integer startZone;
    protected Integer endZone;
    
    public Product() {
        super();
    }

    public Product(Integer startZone, Integer endZone) {
        this.startZone = startZone;
        this.endZone = endZone;
    }

    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Integer getStartZone() {
        return startZone;
    }

    public Integer getEndZone() {
        return endZone;
    }

    public void setStartZone(Integer startZone) {
        this.startZone = startZone;
    }

    public void setEndZone(Integer endZone) {
        this.endZone = endZone;
    }
    


}
