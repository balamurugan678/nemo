package com.novacroft.nemo.mock_single_sign_on.domain;

public class AccountStatus
{
    private String status;

    public AccountStatus(String string) {
        status = string;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
