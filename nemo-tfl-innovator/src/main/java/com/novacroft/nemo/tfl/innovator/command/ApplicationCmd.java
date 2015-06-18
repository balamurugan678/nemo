package com.novacroft.nemo.tfl.innovator.command;

import com.novacroft.nemo.common.command.impl.CommonPersonalDetailsCmd;

import java.util.Date;

/**
 * Command object to hold the Customer information to display on the Innovator screen.
 */
public class ApplicationCmd extends CommonPersonalDetailsCmd {

    protected String securityOption;
    protected String securityPassword;
    protected Date dateOfBirth;
    protected Integer notflserviceinfo;
    protected Integer optouttoc;

    public String getSecurityOption() {
        return securityOption;
    }

    public void setSecurityOption(String securityOption) {
        this.securityOption = securityOption;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getNotflserviceinfo() {
        return notflserviceinfo;
    }

    public void setNotflserviceinfo(Integer notflserviceinfo) {
        this.notflserviceinfo = notflserviceinfo;
    }

    public Integer getOptouttoc() {
        return optouttoc;
    }

    public void setOptouttoc(Integer optouttoc) {
        this.optouttoc = optouttoc;
    }

}
