package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;


public class RandomApprovalSampleThresholdRuleDTO extends WorkflowRuleDTO{
    private static final long serialVersionUID = -1016892535952962089L;
    private String department;
    private String scenario;
    private Integer minValueThreshold;
    private Integer maxValueThreshold;
    private String basis;
    private Integer percentageToApprove;
    
    private String ruleType;
    private String refundType;
    private String instanceThreshold;
    private String team;
    private String organisation;

    
    public RandomApprovalSampleThresholdRuleDTO(String department, String scenario, String basis, Integer minValueThreshold, Integer maxValueThreshold, Integer percentage) {
        super();
        this.department=department;
        this.scenario=scenario;
        this.basis =basis;
        this.minValueThreshold = minValueThreshold;
        this.maxValueThreshold = maxValueThreshold;
        this.percentageToApprove = percentage;
    }
    
    public RandomApprovalSampleThresholdRuleDTO() {
    }
    
    public RandomApprovalSampleThresholdRuleDTO(String team, String organisation) {
        this.team = team;
        this.organisation = organisation;
                
    }

   

    public RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum department, RefundScenarioEnum scenario, RefundCalculationBasis basis, Integer minValueThreshold, Integer maxValueThreshold, Integer percentage) {
        super();
        this.department = (department != null ? department.toString() : null);
        this.scenario = (scenario != null? scenario.toString(): null);
        this.setBasis(basis != null? basis.code(): null);
        this.minValueThreshold = minValueThreshold;
        this.maxValueThreshold = maxValueThreshold;
        this.percentageToApprove = percentage;
    }

    public Integer getMinValueThreshold() {
        return minValueThreshold;
    }

    public Integer getMaxValueThreshold() {
        return maxValueThreshold;
    }

    public Integer getPercentage() {
        return percentageToApprove;
    }

    public void setMinValueThreshold(Integer minValueThreshold) {
        this.minValueThreshold = minValueThreshold;
    }

    public void setMaxValueThreshold(Integer maxValueThreshold) {
        this.maxValueThreshold = maxValueThreshold;
    }

    public void setPercentage(Integer percentage) {
        this.percentageToApprove = percentage;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRefundType() {
        return refundType;
    }

    public String getInstanceThreshold() {
        return instanceThreshold;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public void setInstanceThreshold(String instanceThreshold) {
        this.instanceThreshold = instanceThreshold;
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

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public Integer getPercentageToApprove() {
        return percentageToApprove;
    }

    public void setPercentageToApprove(Integer percentageToApprove) {
        this.percentageToApprove = percentageToApprove;
    }

    public String getTeam() {
        return team;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

}
