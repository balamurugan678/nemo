package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.domain.AgentWebAccountAccess;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;

import java.util.List;

/**
 * AgentWebaccountAccess   transfer implementation.
 */

public interface AgentWebAccountAccessDataService extends BaseDataService<AgentWebAccountAccess, AgentWebAccessDTO> {
    List<AgentWebAccessDTO> findAllAgentIdAndWebacountId(AgentWebAccessDTO agentWebaccount);
}
