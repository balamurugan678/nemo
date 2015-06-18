package com.novacroft.nemo.mock_single_sign_on.domain;

public class SystemReferences
{
    private final SystemName systemName;
    private final String refSysCustomerId;
    private final String refSysUserName;


    public SystemReferences(com.novacroft.nemo.mock_single_sign_on.domain.SystemName systemName, String refSysCustomerId, String refSysUserName) {
        super();
        this.systemName = systemName;
        this.refSysCustomerId = refSysCustomerId;
        this.refSysUserName = refSysUserName;
    }


    public SystemName getSystemName() {
        return systemName;
    }


    public String getRefSysCustomerId() {
        return refSysCustomerId;
    }


    public String getRefSysUserName() {
        return refSysUserName;
    }

    
}