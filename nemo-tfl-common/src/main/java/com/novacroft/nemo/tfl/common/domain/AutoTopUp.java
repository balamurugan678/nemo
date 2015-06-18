package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

/**
 * TfL auto top-up domain definition
 */
@Entity
public class AutoTopUp extends AbstractBaseEntity {
    private static final long serialVersionUID = -4737628704444041743L;

    protected Integer autoTopUpAmount;

    @SequenceGenerator(name = "AutoTopUp_SEQ", sequenceName = "AutoTopUp_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AutoTopUp_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

}
