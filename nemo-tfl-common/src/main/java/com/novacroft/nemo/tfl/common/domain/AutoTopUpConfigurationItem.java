package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

/**
 * TfL auto top-up item single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("AutoTopUpConfiguration")
public class AutoTopUpConfigurationItem extends Item {
    private static final long serialVersionUID = -6712656857354383248L;

    protected Long autoTopUpId;
    protected Integer autoTopUpAmount;
    protected AutoTopUpConfigurationItem relatedItem;
    protected String autoTopUpActivity;

    public Long getAutoTopUpId() {
        return autoTopUpId;
    }

    public void setAutoTopUpId(Long autoTopUpId) {
        this.autoTopUpId = autoTopUpId;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cascade({ org.hibernate.annotations.CascadeType.DELETE })
    @JoinColumn(name = "RELATEDITEMID")
    public AutoTopUpConfigurationItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(AutoTopUpConfigurationItem relatedItem) {
        this.relatedItem = relatedItem;
    }

    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }

}
