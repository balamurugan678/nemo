package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.RepeatClaimLimitRule;
import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;

/**
 * Transform between random approval rules threshold entity and DTO.
 */
@Component(value = "repeatClaimLimitRuleConverter")
public class RepeatClaimLimitRuleConverterImpl extends BaseDtoEntityConverterImpl<RepeatClaimLimitRule, RepeatClaimLimitRuleDTO> {
    @Override
    protected RepeatClaimLimitRuleDTO getNewDto() {
        return new RepeatClaimLimitRuleDTO();
    }
}
