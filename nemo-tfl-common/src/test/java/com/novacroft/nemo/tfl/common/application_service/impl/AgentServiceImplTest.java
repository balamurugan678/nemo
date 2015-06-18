package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.data_service.AgentWebAccountAccessDataService;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.CustomerTestUtil.WEBACCOUNT_ID;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * AgentServiceImpl unit tests
 */
public class AgentServiceImplTest {

    private AgentWebAccountAccessDataService agentWebAccountAccessDataService;
    private AgentServiceImpl agentService;

    @Before
    public void setup() {
        agentService = new AgentServiceImpl();
        agentWebAccountAccessDataService = mock(AgentWebAccountAccessDataService.class);
        agentService.agentWebAccountAccessDataService = agentWebAccountAccessDataService;
    }

    /**
     * Test to create a token if the agent, webaccount is present by deleting and generating a new one
     */
    @Test
    public void checkAllowAgentAlreadyPresent() throws Exception {
        String agentId = "SK11";
        AgentWebAccessDTO agentWebaccountAccess = new AgentWebAccessDTO();
        agentWebaccountAccess.setAgentId(agentId);
        List<AgentWebAccessDTO> agentWebaccountList = new ArrayList<AgentWebAccessDTO>();
        agentWebaccountList.add(agentWebaccountAccess);
        when(agentWebAccountAccessDataService.findAllAgentIdAndWebacountId((AgentWebAccessDTO) anyObject()))
                .thenReturn(agentWebaccountList);
        String actualValue = (agentService).allowAgent(agentId, WEBACCOUNT_ID);
        assertNotNull(actualValue);
    }

    /**
     * Test to create a token if the agent, webaccount is not present by generating a new one
     */
    @Test
    public void checkAllowAgentNotPresent() throws Exception {
        String agentId = "SK11";
        AgentWebAccessDTO agentWebaccountAccess = new AgentWebAccessDTO();
        agentWebaccountAccess.setAgentId(agentId);
        when(agentWebAccountAccessDataService.findAllAgentIdAndWebacountId((AgentWebAccessDTO) anyObject()))
                .thenReturn(null);
        String actualValue = (agentService).allowAgent(agentId, WEBACCOUNT_ID);
        assertNotNull(actualValue);
    }

    /**
     * Test to delete the details of agent, webaccount
     */
    @Ignore
    public void checkDeleteExistingLogin() throws Exception {
        String agentId = "SK11";
        AgentWebAccessDTO agentWebAccountAccess = new AgentWebAccessDTO();
        agentWebAccountAccess.setAgentId(agentId);
        when(agentWebAccountAccessDataService.findAllAgentIdAndWebacountId(agentWebAccountAccess)).thenReturn(null);
        String actualValue = (agentService).allowAgent(agentId, WEBACCOUNT_ID);
        assertNotNull(actualValue);
    }

}
