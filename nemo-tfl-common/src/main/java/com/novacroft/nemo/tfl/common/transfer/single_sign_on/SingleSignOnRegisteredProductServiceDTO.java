package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

import java.util.Map;

public class SingleSignOnRegisteredProductServiceDTO {
    private Map<String,Object> map;
    private SingleSignOnRegisteredProdServiceStatusDTO registeredProdServiceStatus;
    private SingleSignOnRolesDTO roles;
    private SingleSignOnProductServiceDTO productService;
    
    public Map<String,Object> getMap() {
        return map;
    }
    
    public void setMap(Map<String,Object> map) {
        this.map = map;
    }

    public SingleSignOnRegisteredProdServiceStatusDTO getRegisteredProdServiceStatus() {
        return registeredProdServiceStatus;
    }

    public void setRegisteredProdServiceStatus(SingleSignOnRegisteredProdServiceStatusDTO registeredProdServiceStatus) {
        this.registeredProdServiceStatus = registeredProdServiceStatus;
    }

    public SingleSignOnRolesDTO getRoles() {
        return roles;
    }

    public void setRoles(SingleSignOnRolesDTO roles) {
        this.roles = roles;
    }

    public SingleSignOnProductServiceDTO getProductService() {
        return productService;
    }

    public void setProductService(SingleSignOnProductServiceDTO productService) {
        this.productService = productService;
    }
    
}
