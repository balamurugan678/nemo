package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * Transfer class for hotlist reasons
 */
public class HotlistReasonDTO extends AbstractBaseDTO {
    
    private static final long serialVersionUID = -638398241419348632L;
    protected String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
