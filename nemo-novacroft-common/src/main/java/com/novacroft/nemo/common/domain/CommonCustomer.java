package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Common customer attributes that will be inherited by all implementations.
 */
@Audited
@MappedSuperclass()
public abstract class CommonCustomer extends AbstractBaseEntity {
    private static final long serialVersionUID = 7001524272435183149L;

    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected Long addressId;
    protected String username;
    protected String emailAddress;
    protected String firstNameMetaphone;
    protected String lastNameMetaphone;

    public CommonCustomer() {
        setCreated();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "first_name_metaphone")
    public String getFirstNameMetaphone() {
        return firstNameMetaphone;
    }

    public void setFirstNameMetaphone(String firstNameMetaphone) {
        this.firstNameMetaphone = firstNameMetaphone;
    }

    @Column(name = "last_name_metaphone")
    public String getLastNameMetaphone() {
        return lastNameMetaphone;
    }

    public void setLastNameMetaphone(String lastNameMetaphone) {
        this.lastNameMetaphone = lastNameMetaphone;
    }
}
