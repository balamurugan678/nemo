package com.novacroft.nemo.tfl.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Workqueue  domain class to hold the domain data.
 * Automatically created.
 */

@Audited
@Entity
public class WorkQueue extends CommonWorkQueue {
    private static final long serialVersionUID = 1L;

    public WorkQueue() {
        super();
    }

    @SequenceGenerator(name = "WORKQUEUE_SEQ", sequenceName = "WORKQUEUE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKQUEUE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

}
