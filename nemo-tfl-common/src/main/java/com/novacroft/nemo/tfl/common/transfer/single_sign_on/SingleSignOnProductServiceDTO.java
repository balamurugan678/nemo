package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

public class SingleSignOnProductServiceDTO {
    private Long id;
    private String token;
    private String siteName;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    
}
