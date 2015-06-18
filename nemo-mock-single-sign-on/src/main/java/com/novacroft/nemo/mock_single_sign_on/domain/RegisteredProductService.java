package com.novacroft.nemo.mock_single_sign_on.domain;

import java.util.HashMap;
import java.util.Map;

public class RegisteredProductService
{

    private final Map<String,Object> map;

    private RegisteredProdServiceStatus registeredProdServiceStatus;

    private Roles roles;

    private ProductService productService;
    
    public RegisteredProductService() {
        super();
        map = new HashMap<String,Object>();
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public RegisteredProdServiceStatus getRegisteredProdServiceStatus() {
        return registeredProdServiceStatus;
    }

    public void setRegisteredProdServiceStatus(RegisteredProdServiceStatus registeredProdServiceStatus) {
        this.registeredProdServiceStatus = registeredProdServiceStatus;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    
}
