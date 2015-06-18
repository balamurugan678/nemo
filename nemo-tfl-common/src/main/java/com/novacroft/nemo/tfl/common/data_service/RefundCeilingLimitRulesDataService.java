package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;

public interface RefundCeilingLimitRulesDataService {

    void create(RepeatClaimLimitRuleDTO randomApprovalSampleThresholdDTO);

    List<RepeatClaimLimitRuleDTO> findAllRepeatClaimLimitRules();

}
