package com.novacroft.nemo.mock_cubic.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;


/**
* OysterCardHotListReasons data transfer class.
*/

public class OysterCardHotListReasonsDTO extends AbstractBaseDTO {
    
    protected String prestigeId;
    protected Long hotListReasonCode;

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Long getHotListReasonCode() {
        return hotListReasonCode;
    }

    public void setHotListReasonCode(Long hotListReasonCode) {
        this.hotListReasonCode = hotListReasonCode;
    }        
}
