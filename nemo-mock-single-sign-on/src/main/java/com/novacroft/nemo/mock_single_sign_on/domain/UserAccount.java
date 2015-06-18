package com.novacroft.nemo.mock_single_sign_on.domain;

import java.util.ArrayList;
import java.util.List;

public class UserAccount
{

    private String userName;
    private AccountStatus accountStatus;
    private List<RegisteredProductServices> registeredProductServices;
    private String notes;

    public UserAccount(String userName, com.novacroft.nemo.mock_single_sign_on.domain.AccountStatus accountStatus, String notes) {
        super();
        this.userName = userName;
        this.accountStatus = accountStatus;
        this.registeredProductServices = new ArrayList<RegisteredProductServices>();
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<RegisteredProductServices> getRegisteredProductServices() {
        return registeredProductServices;
    }

    public void setRegisteredProductServices(List<RegisteredProductServices> registeredProductServices) {
        this.registeredProductServices = registeredProductServices;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

   
}