package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

@Entity
public class ExternalUser extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = -2286056962018510901L;

    protected String username;
    
    protected String password;
    protected String status;
    protected String salt;
    protected String role;

    @SequenceGenerator(name = "EXTERNAL_USER_SEQ", sequenceName = "EXTERNAL_USER_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXTERNAL_USER_SEQ")
    @Override
    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
