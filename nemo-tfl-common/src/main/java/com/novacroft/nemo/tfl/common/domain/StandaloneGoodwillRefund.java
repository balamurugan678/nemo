package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL standalone goodwill refund cart domain definition
 */
@Entity
@DiscriminatorValue("StandaloneGoodwillRefund")
public class StandaloneGoodwillRefund extends Cart {
    private static final long serialVersionUID = -6646945007451058314L;
}
