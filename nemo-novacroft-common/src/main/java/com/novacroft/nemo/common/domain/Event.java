package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Event domain class to hold the domain data.
 */
@Audited
@Entity
public class Event extends CommonEvent {
    private static final long serialVersionUID = 5878877210966087581L;

    public Event() {
        super();
    }

    @SequenceGenerator(name = "EVENT_SEQ", sequenceName = "EVENT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
}
