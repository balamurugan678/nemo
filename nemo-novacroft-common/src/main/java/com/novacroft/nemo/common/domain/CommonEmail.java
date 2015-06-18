package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;

/**
 * Common domain object to hold the  Email information
 * Automatically created.
 */

@Audited
@MappedSuperclass
public class CommonEmail extends AbstractBaseEntity {
    private static final long serialVersionUID = -2889392595523640783L;
    protected Long id;
    protected String code;
    protected String description;
    protected String defaultto;
    protected String cc;
    protected String bcc;
    protected String fromaddress;
    protected String subject;
    protected String contentcode;
    protected Long html;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultto() {
        return defaultto;
    }

    public void setDefaultto(String defaultto) {
        this.defaultto = defaultto;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getFromaddress() {
        return fromaddress;
    }

    public void setFromaddress(String fromaddress) {
        this.fromaddress = fromaddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentcode() {
        return contentcode;
    }

    public void setContentcode(String contentcode) {
        this.contentcode = contentcode;
    }

    public Long getHtml() {
        return html;
    }

    public void setHtml(Long html) {
        this.html = html;
    }
}
