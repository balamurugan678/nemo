package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;

@Entity
@Table(name="RANDOMAPPROVALSAMPLE")
public class RandomApprovalSampleThresholdRule extends WorkflowRuleType{

    private static final long serialVersionUID = -1787586470048732874L;


    private String department;
    private String scenario;
    private String basis;
    private Integer minValueThreshold;
    private Integer maxValueThreshold;
    private Integer percentageToApprove;
    private String team;
    private String organisation;
    
    
    @Override
    @Id
    public Long getId() {
        return this.id;
    }
    
    public RandomApprovalSampleThresholdRule(String department, String scenario, String basis, Integer minValueThreshold, Integer maxValueThreshold, Integer percentageToApprove, String team,
            String organisation) {
        super();
        this.department = department;
        this.scenario = scenario;
        this.basis = basis;
        this.minValueThreshold = minValueThreshold;
        this.maxValueThreshold = maxValueThreshold;
        this.percentageToApprove = percentageToApprove;
        this.team = team;
        this.organisation = organisation;
    }
    
    public RandomApprovalSampleThresholdRule(RefundDepartmentEnum department, RefundScenarioEnum scenario, String basis, Integer minValueThreshold, Integer maxValueThreshold, Integer percentageToApprove) {
        super();
        this.department = department.name().toString();
        this.scenario = scenario.name().toString();
        this.basis = basis;
        this.minValueThreshold = minValueThreshold;
        this.maxValueThreshold = maxValueThreshold;
        this.percentageToApprove = percentageToApprove;
    }

    public String getDepartment() {
        return department;
    }

    public String getScenario() {
        return scenario;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public RandomApprovalSampleThresholdRule() {
    }

    public Integer getMinValueThreshold() {
        return minValueThreshold;
    }
    public Integer getMaxValueThreshold() {
        return maxValueThreshold;
    }
    public Integer getPercentageToApprove() {
        return percentageToApprove;
    }

    public void setMinValueThreshold(Integer minValueThreshold) {
        this.minValueThreshold = minValueThreshold;
    }
    public void setMaxValueThreshold(Integer maxValueThreshold) {
        this.maxValueThreshold = maxValueThreshold;
    }
    public void setPercentageToApprove(Integer percentageToApprove) {
        this.percentageToApprove = percentageToApprove;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}
