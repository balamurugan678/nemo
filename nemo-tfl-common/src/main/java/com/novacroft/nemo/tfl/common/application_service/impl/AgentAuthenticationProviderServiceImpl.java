package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.data_service.AgentWebAccountAccessDataService;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import com.novacroft.nemo.tfl.common.application_service.AgentService;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.security.TflAuthenticationProvider;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Security service implementation
 *
 * <p>
 * Service to allow users to log in and out of the application. Also allows
 * update (for logged in user) and reset (for forgotten password) of password.
 * </p>
 */
@Service(value = "agentAuthenticationProvideService")
public class AgentAuthenticationProviderServiceImpl extends TflAuthenticationProvider {

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AgentWebAccountAccessDataService agentWebAccountAccessDataService;
    @Autowired
    protected AgentService agentService;

    public Authentication authenticateAgent(Authentication authentication, HttpServletRequest request)
            throws AuthenticationException {
        String agentId = request.getParameter(PageParameter.AGENT_ID);
        String customerId = request.getParameter(PageParameter.CUSTOMER_ID);

        String token = request.getParameter(PageParameter.AGENT_TOKEN);

        AgentWebAccessDTO agentWebAccountAccess = new AgentWebAccessDTO();
        agentWebAccountAccess.setAgentId(agentId);

        List<AgentWebAccessDTO> agentWebAccountList =
                this.agentWebAccountAccessDataService.findAllAgentIdAndWebacountId(agentWebAccountAccess);
        if (agentWebAccountList != null && agentWebAccountList.size() > 0) {
            checkAgentAuthentication(agentWebAccountList, customerId, token, authentication);
        }
        return null;
    }
    
    protected Authentication checkAgentAuthentication(List<AgentWebAccessDTO>agentWebAccountList, String customerId, String token, Authentication authentication) {
        CustomerDTO customer = customerDataService.findById(Long.parseLong(customerId));
        String username = customer != null ? customer.getUsername() : "";
        AgentWebAccessDTO agentWebAccountAccessLast = agentWebAccountList.get(agentWebAccountList.size() - 1);
        if (agentWebAccountAccessLast.getToken().equals(token)) {
            agentService.deleteAll(agentWebAccountList);
            UserDetails user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            Object principalToReturn = user.getUsername();
            return createSuccessAuthentication(principalToReturn, authentication, user);
        } else {
            return null;
        }
    }
}
