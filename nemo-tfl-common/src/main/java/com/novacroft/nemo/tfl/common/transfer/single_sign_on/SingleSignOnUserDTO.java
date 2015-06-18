package com.novacroft.nemo.tfl.common.transfer.single_sign_on;


public class SingleSignOnUserDTO {
    private SingleSignOnUserDTO user;
    private SingleSignOnCustomerDTO customer;
    private SingleSignOnUserAccountDTO userAccount;
    
    public SingleSignOnUserDTO getUser() {
        return user;
    }
    
    public void setUser(SingleSignOnUserDTO user) {
        this.user = user;
    }

    public SingleSignOnCustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(SingleSignOnCustomerDTO customer) {
        this.customer = customer;
    }

    public SingleSignOnUserAccountDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(SingleSignOnUserAccountDTO userAccount) {
        this.userAccount = userAccount;
    }
}
