package com.novacroft.nemo.mock_single_sign_on.domain;

public class Title
{
    private String description;

    private Integer id;

    public Title() {

    }

    public Title(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   
}
    