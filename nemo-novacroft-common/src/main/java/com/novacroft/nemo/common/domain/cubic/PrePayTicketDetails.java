package com.novacroft.nemo.common.domain.cubic;

import java.util.List;


/**
 * Card Pre pay ticket details
 */
public class PrePayTicketDetails {
    protected List<PrePayTicketSlot> pptSlots;

    public List<PrePayTicketSlot> getPptSlots() {
        return pptSlots;
    }

    public void setPptSlots(final List<PrePayTicketSlot> pptSlots) {
        this.pptSlots = pptSlots;
    }
}
