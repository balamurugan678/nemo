package com.novacroft.nemo.common.domain.cubic;

import java.util.List;


/**
 * Card pending items details
 */
public class PendingItems {
    protected List<PrePayValue> ppvs;
    protected List<PrePayTicket> ppts;
    
    
    public List<PrePayValue> getPpvs() {
		return ppvs;
	}
	public void setPpvs(List<PrePayValue> ppvs) {
		this.ppvs = ppvs;
	}
	public List<PrePayTicket> getPpts() {
        return ppts;
    }
    public void setPpts(List<PrePayTicket> ppts) {
        this.ppts = ppts;
    }
    
}
