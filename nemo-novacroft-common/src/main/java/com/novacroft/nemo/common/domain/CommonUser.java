package com.novacroft.nemo.common.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

/**
 * Common domain object to hold the User information
 */
@Audited
@MappedSuperclass()
public class CommonUser implements Serializable {

    private static final long serialVersionUID = -1179897057073638621L;

    protected String id;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String emailAddress;
    protected String telephone;
    protected String superUser;
    protected String templateUserId;
    protected String organization;
    protected String team;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSuperUser() {
        return superUser;
    }

    public void setSuperUser(String superUser) {
        this.superUser = superUser;
    }

    public String getTemplateUserId() {
        return templateUserId;
    }

    public void setTemplateUserId(String templateUserId) {
        this.templateUserId = templateUserId;
    }

    public String getOrganization() {
        return organization;
    }

    public String getTeam() {
        return team;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
