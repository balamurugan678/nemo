package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

/**
 * TfL shippingMethodItem single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("ShippingMethod")
public class ShippingMethodItem extends Item {
	private static final long serialVersionUID = -6712656857354383248L;
	protected Long shippingMethodId;
	protected ShippingMethodItem relatedItem;
	
	public Long getShippingMethodId() {
		return shippingMethodId;
	}
	public void setShippingMethodId(Long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
	}
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE} )
    @JoinColumn(name = "RELATEDITEMID" ) 
    public ShippingMethodItem getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(ShippingMethodItem relatedItem) {
        this.relatedItem = relatedItem;
    }
	
}
