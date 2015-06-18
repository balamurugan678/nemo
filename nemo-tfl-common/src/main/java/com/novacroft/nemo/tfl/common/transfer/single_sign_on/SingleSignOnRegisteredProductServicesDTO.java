package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

import java.util.List;

public class SingleSignOnRegisteredProductServicesDTO {
    private String lastLoggedIn;
    private SingleSignOnProductServiceDTO productService;
    private SingleSignOnRegisteredProdServiceStatusDTO registeredProdServiceStatus;
    private List<SingleSignOnRoleDTO> roles;
    
    public String getLastLoggedIn() {
        return lastLoggedIn;
    }
    
    public void setLastLoggedIn(String lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public SingleSignOnProductServiceDTO getProductService() {
        return productService;
    }

    public void setProductService(SingleSignOnProductServiceDTO productService) {
        this.productService = productService;
    }

    public SingleSignOnRegisteredProdServiceStatusDTO getRegisteredProdServiceStatus() {
        return registeredProdServiceStatus;
    }

    public void setRegisteredProdServiceStatus(SingleSignOnRegisteredProdServiceStatusDTO registeredProdServiceStatus) {
        this.registeredProdServiceStatus = registeredProdServiceStatus;
    }

    public List<SingleSignOnRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<SingleSignOnRoleDTO> roles) {
        this.roles = roles;
    }
    
}
