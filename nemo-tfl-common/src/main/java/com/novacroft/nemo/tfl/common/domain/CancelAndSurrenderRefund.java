package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL cancel and surrender card refund cart domain definition
 */
@Entity
@DiscriminatorValue("CancelAndSurrenderRefund")
public class CancelAndSurrenderRefund extends Cart {
    private static final long serialVersionUID = 7000434869117267065L;
}
