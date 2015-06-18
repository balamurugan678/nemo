package com.novacroft.nemo.mock_single_sign_on.domain;

import java.util.ArrayList;
import java.util.List;


public class RegisteredProductServices
{

    private String lastLoggedIn;
    private ProductService productService;
    private RegisteredProdServiceStatus registeredProdServiceStatus;
    private final List<Role> roles;


    public RegisteredProductServices(String lastLoggedIn, com.novacroft.nemo.mock_single_sign_on.domain.ProductService productService,
            com.novacroft.nemo.mock_single_sign_on.domain.RegisteredProdServiceStatus registeredProdServiceStatus) {
        super();
        this.lastLoggedIn = lastLoggedIn;
        this.productService = productService;
        this.registeredProdServiceStatus = registeredProdServiceStatus;
        roles = new ArrayList<Role>();
    }


    public String getLastLoggedIn() {
        return lastLoggedIn;
    }


    public void setLastLoggedIn(String lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }


    public ProductService getProductService() {
        return productService;
    }


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    public RegisteredProdServiceStatus getRegisteredProdServiceStatus() {
        return registeredProdServiceStatus;
    }


    public void setRegisteredProdServiceStatus(RegisteredProdServiceStatus registeredProdServiceStatus) {
        this.registeredProdServiceStatus = registeredProdServiceStatus;
    }


    public List<Role> getRoles() {
        return roles;
    }

   
}