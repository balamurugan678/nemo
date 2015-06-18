package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

public class SingleSignOnRegisteredProdServiceStatusDTO {
    private String status;
    private Long id;
    
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
