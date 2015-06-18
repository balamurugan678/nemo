package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;

public interface RandomApprovalRulesDataService {

    void create(RandomApprovalSampleThresholdRuleDTO randomApprovalSampleThresholdDTO);

    List<RandomApprovalSampleThresholdRuleDTO> findAllRandomApprovalSampleThresholds();

    List<RandomApprovalSampleThresholdRuleDTO> findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(String organization, String team);

    List<RandomApprovalSampleThresholdRuleDTO> findOrganisationAndTeamById(String id);
    


}
