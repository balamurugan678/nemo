package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

import java.util.List;

public class SingleSignOnUserAccountDTO {
    private String userName;
    private SingleSignOnAccountStatusDTO accountStatus;
    private List<SingleSignOnRegisteredProductServicesDTO> registeredProductServices;
    private String notes;
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SingleSignOnAccountStatusDTO getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(SingleSignOnAccountStatusDTO accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<SingleSignOnRegisteredProductServicesDTO> getRegisteredProductServices() {
        return registeredProductServices;
    }

    public void setRegisteredProductServices(List<SingleSignOnRegisteredProductServicesDTO> registeredProductServices) {
        this.registeredProductServices = registeredProductServices;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
