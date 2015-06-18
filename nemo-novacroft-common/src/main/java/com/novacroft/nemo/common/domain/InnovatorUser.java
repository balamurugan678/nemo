package com.novacroft.nemo.common.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * Representation of InNovator User object.
 */
public class InnovatorUser implements Serializable {
    private static final long serialVersionUID = -2376679627734435919L;
    private String id;
    private String organisationId;
    private String forename;
    private String surname;
    private String password;
    private String emailAddress;
    private String telephone;
    private Date startDate;
    private Date endDate;
    private String styleId;
    private boolean superUser;
    private boolean magnaCartaEditor;
    private boolean disabled;
    private Date passwordChangedDate;
    private int passwordLife;
    private int failedLoginCnt;
    private Date lastLogonDate;
    private boolean templateUser;
    private String templateUserId;
    private boolean auditLog;
    private Date createdDate;
    private String organisationName;
    private String licenceTypeId;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrganisationId() {
        return organisationId;
    }
    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }
    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getStyleId() {
        return styleId;
    }
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }
    public boolean isSuperUser() {
        return superUser;
    }
    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }
    public boolean isMagnaCartaEditor() {
        return magnaCartaEditor;
    }
    public void setMagnaCartaEditor(boolean magnaCartaEditor) {
        this.magnaCartaEditor = magnaCartaEditor;
    }
    public boolean isDisabled() {
        return disabled;
    }
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }
    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }
    public int getPasswordLife() {
        return passwordLife;
    }
    public void setPasswordLife(int passwordLife) {
        this.passwordLife = passwordLife;
    }
    public int getFailedLoginCnt() {
        return failedLoginCnt;
    }
    public void setFailedLoginCnt(int failedLoginCnt) {
        this.failedLoginCnt = failedLoginCnt;
    }
    public Date getLastLogonDate() {
        return lastLogonDate;
    }
    public void setLastLogonDate(Date lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }
    public boolean isTemplateUser() {
        return templateUser;
    }
    public void setTemplateUser(boolean templateUser) {
        this.templateUser = templateUser;
    }
    public String getTemplateUserId() {
        return templateUserId;
    }
    public void setTemplateUserId(String templateUserId) {
        this.templateUserId = templateUserId;
    }
    public boolean isAuditLog() {
        return auditLog;
    }
    public void setAuditLog(boolean auditLog) {
        this.auditLog = auditLog;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getOrganisationName() {
        return organisationName;
    }
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
    public String getLicenceTypeId() {
        return licenceTypeId;
    }
    public void setLicenceTypeId(String licenceTypeId) {
        this.licenceTypeId = licenceTypeId;
    }
    
    
    
}
