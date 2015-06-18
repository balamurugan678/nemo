package com.novacroft.nemo.mock_single_sign_on.domain;

public class AddressType
{
    private String description;

    private Long id;

    public AddressType() {

    }

    public AddressType(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}