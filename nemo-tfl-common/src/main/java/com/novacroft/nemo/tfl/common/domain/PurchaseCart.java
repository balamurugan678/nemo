package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL purchase cart domain definition
 */
@Entity
@DiscriminatorValue("Purchase")
public class PurchaseCart extends Cart {
    private static final long serialVersionUID = -7780773709056155291L;

}
