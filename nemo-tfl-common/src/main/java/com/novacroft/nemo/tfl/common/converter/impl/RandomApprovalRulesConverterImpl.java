package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.RandomApprovalSampleThresholdRule;
import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;

/**
 * Transform between random approval rules threshold entity and DTO.
 */
@Component(value = "randomApprovalRulesConverter")
public class RandomApprovalRulesConverterImpl extends BaseDtoEntityConverterImpl<RandomApprovalSampleThresholdRule, RandomApprovalSampleThresholdRuleDTO> {
    @Override
    protected RandomApprovalSampleThresholdRuleDTO getNewDto() {

        return new RandomApprovalSampleThresholdRuleDTO();
    }
}
