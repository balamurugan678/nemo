package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

public class SingleSignOnResponseDTO {
    private SingleSignOnUserDTO user;
    private String errorMessage;
    private Boolean isValid;
    
    public SingleSignOnUserDTO getUser() {
        return user;
    }
    
    public void setUser(SingleSignOnUserDTO user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    
}
