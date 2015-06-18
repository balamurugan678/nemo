package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ApplicationEvent domain class to hold the domain data.
 */
@Audited
@Entity
@Table(name = "USERS")
public class User extends CommonUser {

    /**
     *
     */
    private static final long serialVersionUID = -8583982790436199665L;

    public User() {
        super();
    }

    @Id
    @Column(name = "ID")
    @Override
    public String getId() {
        return id;
    }
}
