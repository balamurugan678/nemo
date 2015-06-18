package com.novacroft.nemo.mock_single_sign_on.domain;

public class AssociatedSystemReference
{
    private String refSysCustomerId;

    private String refSysUserName;

    private SystemName systemName;

    public String getRefSysCustomerId() {
        return refSysCustomerId;
    }

    public void setRefSysCustomerId(String refSysCustomerId) {
        this.refSysCustomerId = refSysCustomerId;
    }

    public String getRefSysUserName() {
        return refSysUserName;
    }

    public void setRefSysUserName(String refSysUserName) {
        this.refSysUserName = refSysUserName;
    }

    public SystemName getSystemName() {
        return systemName;
    }

    public void setSystemName(SystemName systemName) {
        this.systemName = systemName;
    }

   
}