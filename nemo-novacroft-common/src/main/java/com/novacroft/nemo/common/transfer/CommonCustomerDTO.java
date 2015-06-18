package com.novacroft.nemo.common.transfer;

/**
 * customer transfer common definition
 */
public class CommonCustomerDTO extends AbstractBaseDTO {
    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected Long addressId;
    protected String password;
    protected String salt;
    protected String emailAddress;
    protected String username;
    protected String firstNameMetaphone;
    protected String lastNameMetaphone;

    public CommonCustomerDTO() {
        super();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String slt) {
        this.salt = slt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstNameMetaphone() {
        return firstNameMetaphone;
    }

    public void setFirstNameMetaphone(String firstNameMetaphone) {
        this.firstNameMetaphone = firstNameMetaphone;
    }

    public String getLastNameMetaphone() {
        return lastNameMetaphone;
    }

    public void setLastNameMetaphone(String lastNameMetaphone) {
        this.lastNameMetaphone = lastNameMetaphone;
    }
}
