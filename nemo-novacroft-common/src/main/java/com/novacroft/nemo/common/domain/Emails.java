package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Emails domain class to hold the domain data. Automatically created.
 */

@Audited
public class Emails extends CommonEmails {
    private static final long serialVersionUID = 1L;

    public Emails() {
        super();
    }

    @SequenceGenerator(name = "EMAILS_SEQ", sequenceName = "EMAILS_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAILS_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
}
