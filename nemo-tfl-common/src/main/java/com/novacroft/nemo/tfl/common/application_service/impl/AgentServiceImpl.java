package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.AgentWebAccountAccessDataService;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import com.novacroft.nemo.tfl.common.application_service.AgentService;

/**
 * Agent service implementation
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {

    @Autowired
    protected AgentWebAccountAccessDataService agentWebAccountAccessDataService;

    /**
     * Insert the agentid, webaccountid and the token allowed to give access
     */
    @Override
    @Transactional
    public String allowAgent(String agentId, long webaccountId) throws Exception {
        AgentWebAccessDTO agentWebAccountAccess = new AgentWebAccessDTO();
        agentWebAccountAccess.setAgentId(agentId);
        agentWebAccountAccess.setWebaccountId(webaccountId);
        List<AgentWebAccessDTO> agentWebaccountList =
                this.agentWebAccountAccessDataService.findAllAgentIdAndWebacountId(agentWebAccountAccess);
        if (agentWebaccountList != null && agentWebaccountList.size() > 0) {
            deleteAll(agentWebaccountList);
        }
        agentWebAccountAccess.setToken(generateToken());
        this.agentWebAccountAccessDataService.createOrUpdate(agentWebAccountAccess);
        return agentWebAccountAccess.getToken();
    }

    @Override
    @Transactional
    public void deleteAll(List<AgentWebAccessDTO> agentWebAccountList) {
        AgentWebAccessDTO agentWebAccountAccess = null;
        for (Iterator<AgentWebAccessDTO> it = agentWebAccountList.iterator();
             it.hasNext(); ) {
            agentWebAccountAccess = it.next();
            this.agentWebAccountAccessDataService.delete(agentWebAccountAccess);
        }
    }

    /**
     * Generate an random Token as one time token
     */
    public String generateToken() {
        return RandomStringUtils.randomAlphanumeric(20);

    }

    @Override
    public String getAgentId() {
        // TODO Auto-generated method stub
        return null;
    }

}
