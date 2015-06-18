package com.novacroft.nemo.mock_cubic.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
* OysterCardPrepayValueDTO transfer implementation.
* 
*/

public class OysterCardPrepayValueDTO extends AbstractBaseDTO {
    
    protected String prestigeId;
    protected Long currency;
    protected Long balance;

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
