package com.novacroft.nemo.common.transfer;

/**
 * ApplicationEvent transfer common definition
 */

public class CommonUserDTO {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String emailAddress;
    protected String telephone;
    protected Boolean superUser;
    protected String templateUserId;

    public CommonUserDTO() {

    }

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

    public Boolean getSuperUser() {
        return superUser;
    }

    public void setSuperUser(Boolean superUser) {
        this.superUser = superUser;
    }

    public String getTemplateUserId() {
        return templateUserId;
    }

    public void setTemplateUserId(String templateUserId) {
        this.templateUserId = templateUserId;
    }
}
