package com.novacroft.nemo.tfl.common.util;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.UnattachedCardDetailsComparator;


public class JsonResponse  {

    private Boolean hotlisted;
    private List<UnattachedCardDetailsComparator> comparison;
    private String description;
    
    public Boolean getHotlisted() {
        return hotlisted;
    }
    public void setHotlisted(Boolean hotlisted) {
        this.hotlisted = hotlisted;
    }
    public List<UnattachedCardDetailsComparator> getComparison() {
        return comparison;
    }
    public void setComparison(List<UnattachedCardDetailsComparator> comparison) {
        this.comparison = comparison;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
