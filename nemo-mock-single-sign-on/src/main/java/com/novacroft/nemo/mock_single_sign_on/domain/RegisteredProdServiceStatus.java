package com.novacroft.nemo.mock_single_sign_on.domain;

public class RegisteredProdServiceStatus
{

    private String status;

    private Long id;
    
    public RegisteredProdServiceStatus(Long id, String status) {
        this.id=id;
        this.status=status;
    }

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


   
   
}