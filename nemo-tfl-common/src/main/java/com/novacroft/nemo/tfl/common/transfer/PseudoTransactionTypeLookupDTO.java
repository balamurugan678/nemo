package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class PseudoTransactionTypeLookupDTO extends AbstractBaseDTO {

    protected String description;
    protected String displayDescription;

    public PseudoTransactionTypeLookupDTO() {
    }

    public PseudoTransactionTypeLookupDTO(String description, String displayDescription) {
        this.description = description;
        this.displayDescription = displayDescription;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
