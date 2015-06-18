package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Settlement using web account credit: payment or refund against an order.
 */
@Entity
@DiscriminatorValue("WebAccountCredit")
public class WebAccountCreditSettlement extends Settlement {
}
