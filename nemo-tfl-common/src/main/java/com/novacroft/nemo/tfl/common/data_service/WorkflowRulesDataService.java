package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.domain.RandomApprovalSampleThresholdRule;
import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;
import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;

public interface WorkflowRulesDataService {

    void create(RandomApprovalSampleThresholdRuleDTO randomApprovalSampleThresholdDTO);

    List<RandomApprovalSampleThresholdRuleDTO> findByExample(RandomApprovalSampleThresholdRule exampleRandomApprovalSampleThreshold);

    List<RandomApprovalSampleThresholdRuleDTO> findAllRandomApprovalSampleThresholds();
    
    List<RepeatClaimLimitRuleDTO> findAllRepeatClaimLimitRules();


}
