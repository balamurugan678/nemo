package com.novacroft.nemo.tfl.services.transfer;

/**
 * Station
 */
public class Station extends AbstractBase {
    protected Long id;
    protected String name;
    protected String status;

    public Station() {
    }

    public Station(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Station(ErrorResult errors){
        this.errors = errors;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
