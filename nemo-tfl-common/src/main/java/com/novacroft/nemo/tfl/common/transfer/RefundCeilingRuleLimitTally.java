package com.novacroft.nemo.tfl.common.transfer;

import java.math.BigDecimal;

public class RefundCeilingRuleLimitTally {

    public RefundCeilingRuleLimitTally(String name, Integer tally) {
        super();
        this.name = name;
        this.tally = tally;
    }
    public RefundCeilingRuleLimitTally(String name, BigDecimal bigDecimal, String department) {
        super();
        this.name = name;
        this.tally = bigDecimal.intValue();
        this.department = department;
    }
    private String name;
    private Integer tally;
    private String department;
    
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getTally() {
        return tally;
    }
    public void setTally(Integer tally) {
        this.tally = tally;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
}
