package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.CommonSettlement;
import com.novacroft.nemo.common.listener.hibernate.GeneratedSequenceDependence;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Settlement (payment or refund against an order).  Note: sub-classes for settlement methods, e.g. payment card, web credit,
 * etc
 */
@Entity
@Audited
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SETTLEMENTMETHOD", discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(name = "orderId", column = @Column(name = "CUSTOMERORDERID"))
@GeneratedSequenceDependence(fieldNames = {"settlementNumber", "externalId"},
        sequenceNames = {Settlement.ORS_SEQ, "EXTERNALID_SEQ"})
public class Settlement extends CommonSettlement {

    private static final long serialVersionUID = 4824487292196680600L;
    public static final String ORS_SEQ = "ORS_SEQ";

    @SequenceGenerator(name = "SETTLEMENT_SEQ", sequenceName = "SETTLEMENT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SETTLEMENT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

}
