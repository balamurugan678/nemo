package com.novacroft.nemo.mock_single_sign_on.domain;

public class ProductService
{

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getSiteName() {
        return siteName;
    }

    public ProductService(Long id, String token, String siteName) {
        super();
        this.id = id;
        this.token = token;
        this.siteName = siteName;
    }
    
    private final Long id;
    
    private final String token;
    
    private final String siteName;
    

}