package com.novacroft.nemo.mock_single_sign_on.domain;

public class SystemName
{
    private Long id;
    private String description;


    public SystemName(Long id, String description) {
        super();
        this.id = id;
        this.description = description;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

   
}