package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

public abstract class WorkflowRuleType extends AbstractBaseEntity{

    private static final long serialVersionUID = 7827159629786190422L;
    private String ruleType;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }
    
}
