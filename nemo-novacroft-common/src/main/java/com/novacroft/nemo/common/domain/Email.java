package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Email domain class to hold the domain data. Automatically created.
 */

@Audited
public class Email extends CommonEmail {
    private static final long serialVersionUID = -8690021870264129183L;

    public Email() {
        super();
    }

    @SequenceGenerator(name = "EMAIL_SEQ", sequenceName = "EMAIL_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
}
