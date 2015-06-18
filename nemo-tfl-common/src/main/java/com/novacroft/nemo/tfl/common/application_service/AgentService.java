package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;

import java.util.List;

/**
 * Agent service to allow login to webaccount service specification.
 */
public interface AgentService {

    String allowAgent(String agentId, long webaccountId) throws Exception;

    String getAgentId();

    void deleteAll(List<AgentWebAccessDTO> agentWebAccountList);

}
