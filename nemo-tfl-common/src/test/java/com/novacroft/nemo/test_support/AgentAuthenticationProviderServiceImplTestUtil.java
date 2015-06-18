package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;

/**
 * Utilities for AgentAuthenticationProviderServiceImpl tests
 */
public final class AgentAuthenticationProviderServiceImplTestUtil {

    public static Long ID_1 = 12L;
    public static String AGENT_ID_1 = "test agent 1";
    public static Long ID_2 = 24L;
    public static String AGENT_ID_2 = "test agent 2";
    public static String TOKEN_1 = PageParameter.AGENT_TOKEN;
    public static String TOKEN_2 = "test token 2";
    public static final Long CUSTOMER_ID_1 = 4L;
    public static final Long CUSTOMER_ID_2 = 5L;
    public static final String CUSTOMER_ID_3 = "12";
    public static final Long WEBACCOUNT_ID_3 = 16L;
    public static final Long WEBACCOUNT_ID_4 = 17L;
    public static final String USER_NAME = "test username ";
    public static final String USER_PASSWORD = "test password";

    public static List<AgentWebAccessDTO> getAgentIdAndWebacountIdList() {
        List<AgentWebAccessDTO> testAgentWebAccessDTOs = new ArrayList<AgentWebAccessDTO>(2);
        testAgentWebAccessDTOs.add(getAgentIdAndWebacountId1());
        testAgentWebAccessDTOs.add(getAgentIdAndWebacountId2());
        return testAgentWebAccessDTOs;
    }

    public static List<AgentWebAccessDTO> getAgentIdAndWebacountIdEmptyList() {
        List<AgentWebAccessDTO> testAgentWebAccessDTOs = new ArrayList<AgentWebAccessDTO>();
        return testAgentWebAccessDTOs;
    }

    public static AgentWebAccessDTO getAgentIdAndWebacountId1() {
        AgentWebAccessDTO agentWebAccessDTO = new AgentWebAccessDTO();
        agentWebAccessDTO.setAgentId(AGENT_ID_1);
        agentWebAccessDTO.setCustomerId(CUSTOMER_ID_1);
        agentWebAccessDTO.setId(ID_1);
        agentWebAccessDTO.setToken(TOKEN_1);
        agentWebAccessDTO.setWebaccountId(WEBACCOUNT_ID_3);
        return agentWebAccessDTO;
    }

    public static AgentWebAccessDTO getAgentIdAndWebacountId2() {
        AgentWebAccessDTO agentWebAccessDTO = new AgentWebAccessDTO();
        agentWebAccessDTO.setAgentId(AGENT_ID_2);
        agentWebAccessDTO.setCustomerId(CUSTOMER_ID_2);
        agentWebAccessDTO.setId(ID_2);
        agentWebAccessDTO.setToken(TOKEN_2);
        agentWebAccessDTO.setWebaccountId(WEBACCOUNT_ID_4);
        return agentWebAccessDTO;
    }
    
    public static User getUser1() {
        User testUser = new User(USER_NAME, USER_PASSWORD, getGrantedAuthorities());
        return testUser;
    }
    
    public static List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        return authorities;
    }
}
