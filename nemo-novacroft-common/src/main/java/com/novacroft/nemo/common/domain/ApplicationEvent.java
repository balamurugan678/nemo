package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * ApplicationEvent domain class to hold the domain data.
 */
@Audited
@Entity
public class ApplicationEvent extends CommonApplicationEvent {
    private static final long serialVersionUID = 1290815651766232264L;

    public ApplicationEvent() {
        super();
    }

    @SequenceGenerator(name = "APPLICATIONEVENT_SEQ", sequenceName = "APPLICATIONEVENT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATIONEVENT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
}
