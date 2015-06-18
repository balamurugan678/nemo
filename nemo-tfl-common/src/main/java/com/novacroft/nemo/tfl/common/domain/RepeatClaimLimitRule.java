package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLAIMLIMITTHRESHOLD")
public class RepeatClaimLimitRule extends WorkflowRuleType{

    private static final long serialVersionUID = -125841705111954132L;
    private String department;
    private String scenario;
    private Integer minValueThreshold;
    private Integer maxValueThreshold;
    private Integer instanceThreshold;
    
    
  
    public RepeatClaimLimitRule(String department, String scenario, Integer minValueThreshold, Integer maxValueThreshold, Integer instanceThreshold) {
        super();
        this.department = department;
        this.scenario = scenario;
        this.minValueThreshold = minValueThreshold;
        this.maxValueThreshold = maxValueThreshold;
        this.instanceThreshold = instanceThreshold;
    }


    public RepeatClaimLimitRule() {
    }

    public Integer getMinValueThreshold() {
        return minValueThreshold;
    }


    public Integer getMaxValueThreshold() {
        return maxValueThreshold;
    }


    public void setMinValueThreshold(Integer minValueThreshold) {
        this.minValueThreshold = minValueThreshold;
    }


    public void setMaxValueThreshold(Integer maxValueThreshold) {
        this.maxValueThreshold = maxValueThreshold;
    }


    @Override
    @Id
    public Long getId() {
        return this.id;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }


    public String getScenario() {
        return scenario;
    }


    public void setScenario(String scenario) {
        this.scenario = scenario;
    }


    public Integer getInstanceThreshold() {
        return instanceThreshold;
    }


    public void setInstanceThreshold(Integer instanceThreshold) {
        this.instanceThreshold = instanceThreshold;
    }
    
    
  
    
}
