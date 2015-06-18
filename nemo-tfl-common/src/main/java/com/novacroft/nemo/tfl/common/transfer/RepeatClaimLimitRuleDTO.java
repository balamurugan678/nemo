package com.novacroft.nemo.tfl.common.transfer;

import javax.persistence.Id;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class RepeatClaimLimitRuleDTO extends AbstractBaseDTO{
    private static final long serialVersionUID = -7846096889985877943L;
    

    private String department;
    private String scenario;
    private Integer minValueThreshold;
    private Integer maxValueThreshold;
    private Integer instanceThreshold;
    private String ruleType;


    

    public RepeatClaimLimitRuleDTO(String department, String scenario, Integer minValueThreshold, Integer maxValueThreshold, Integer claimLimit) {
        super();
        this.department= department;
        this.scenario= scenario;
        this.minValueThreshold = minValueThreshold;
        this.maxValueThreshold = maxValueThreshold;
        this.instanceThreshold = claimLimit;
    }
    
    


    public RepeatClaimLimitRuleDTO() {
    }


  

    public Integer getMinValueThreshold() {
        return minValueThreshold;
    }


    public Integer getMaxValueThreshold() {
        return maxValueThreshold;
    }


    public Integer getClaimLimit() {
        return instanceThreshold;
    }


   


    public void setMinValueThreshold(Integer minValueThreshold) {
        this.minValueThreshold = minValueThreshold;
    }


    public void setMaxValueThreshold(Integer maxValueThreshold) {
        this.maxValueThreshold = maxValueThreshold;
    }


    public void setClaimLimit(Integer claimLimit) {
        this.instanceThreshold = claimLimit;
    }

    public Integer getInstanceThreshold() {
        return instanceThreshold;
    }


    public void setInstanceThreshold(Integer instanceThreshold) {
        this.instanceThreshold = instanceThreshold;
    }
    
    @Override
    @Id
    public Long getId() {
        return this.id;
    }
    
    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
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
    
  
    
}
