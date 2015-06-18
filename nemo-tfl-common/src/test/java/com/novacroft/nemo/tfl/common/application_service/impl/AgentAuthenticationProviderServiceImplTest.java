package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.common.data_service.AgentWebAccountAccessDataService;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;

import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.getUser1;
import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.CUSTOMER_ID_3;
import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.TOKEN_1;
import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.TOKEN_2;
import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.getAgentIdAndWebacountIdList;
import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.getAgentIdAndWebacountIdEmptyList;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 *  AgentAuthenticationProviderServiceImpl unit tests
 */
public class AgentAuthenticationProviderServiceImplTest {

    private AgentAuthenticationProviderServiceImpl mockServiceImpl;
    private AgentWebAccountAccessDataService mockAgentWebAccountAccessDataService;
    private AgentServiceImpl mockAgentService;
    private CustomerDataService mockCustomerDataService;
    private Authentication mockAuthentication;
    private HttpServletRequest mockHttpServletRequest;
    private List<AgentWebAccessDTO> mockAgentWebAccountList;

    @Before
    public void setup() {
        mockServiceImpl = mock(AgentAuthenticationProviderServiceImpl.class);
        mockAgentService = mock(AgentServiceImpl.class);
        mockAgentWebAccountAccessDataService = mock(AgentWebAccountAccessDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        
        mockServiceImpl.agentService = mockAgentService;
        mockServiceImpl.agentWebAccountAccessDataService = mockAgentWebAccountAccessDataService;
        mockServiceImpl.customerDataService = mockCustomerDataService;

        mockAuthentication = mock(Authentication.class);
        mockHttpServletRequest = mock(HttpServletRequest.class);
        
        mockAgentWebAccountList = mock(ArrayList.class);
    }

    @Test
    public void shouldNotAuthenticateAgentWhenAgentWebAccountListIsNull() throws Exception {
        doCallRealMethod().when(mockServiceImpl).authenticateAgent(mockAuthentication, mockHttpServletRequest);
        when(mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class))).thenReturn(null);
        mockServiceImpl.authenticateAgent(mockAuthentication, mockHttpServletRequest);
        assertNull(mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class)));
    }

    @Test
    public void shouldNotAuthenticateAgentWhenAgentWebAccountListIsEmpty() throws Exception {
        doCallRealMethod().when(mockServiceImpl).authenticateAgent(mockAuthentication, mockHttpServletRequest);
        when(mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class))).thenReturn(getAgentIdAndWebacountIdEmptyList());
        mockServiceImpl.authenticateAgent(mockAuthentication, mockHttpServletRequest);
        assertTrue(mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class)).isEmpty());
    }

    @Test
    public void shouldAuthenticateAgent() throws Exception {
        doCallRealMethod().when(mockServiceImpl).authenticateAgent(mockAuthentication, mockHttpServletRequest);
        when(mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class))).thenReturn(getAgentIdAndWebacountIdList());
        mockServiceImpl.authenticateAgent(mockAuthentication, mockHttpServletRequest);
        assertTrue(mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class)) != null && mockAgentWebAccountAccessDataService.findAllAgentIdAndWebacountId(any(AgentWebAccessDTO.class)).size() > 0);
        verify(mockServiceImpl, atLeastOnce()).checkAgentAuthentication(anyList(), anyString(), anyString(), any(Authentication.class));
    }

    @Test
    public void shouldCreateSuccessAuthenticationIfTokenDoesMatch() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        doCallRealMethod().when(mockServiceImpl).checkAgentAuthentication(anyList(), anyString(), anyString(), any(Authentication.class));
        when(mockCustomerDataService.findById(anyLong())).thenReturn(getTestCustomerDTO1());
        when(mockAgentWebAccountList.get(anyInt())).thenReturn(getAgentIdAndWebacountIdList().get(0));
        doNothing().when(mockAgentService).deleteAll(mockAgentWebAccountList);
        when(mockServiceImpl.retrieveUser(anyString(), any(UsernamePasswordAuthenticationToken.class))).thenReturn(getUser1());
        mockServiceImpl.checkAgentAuthentication(mockAgentWebAccountList, CUSTOMER_ID_3, TOKEN_1, authentication);
        assertNotNull(mockCustomerDataService.findById(anyLong()));
        assertTrue(mockAgentWebAccountList.get(0).getToken().equals(PageParameter.AGENT_TOKEN));
        verify(mockAgentService, atLeastOnce()).deleteAll(mockAgentWebAccountList);        
        verify(mockServiceImpl, atLeastOnce()).retrieveUser(anyString(), any(UsernamePasswordAuthenticationToken.class));        
    }

    @Test
    public void shouldNotCreateSuccessAuthenticationIfTokenDoesNotMatch() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        doCallRealMethod().when(mockServiceImpl).checkAgentAuthentication(anyList(), anyString(), anyString(), any(Authentication.class));
        when(mockCustomerDataService.findById(anyLong())).thenReturn(getTestCustomerDTO1());
        when(mockAgentWebAccountList.get(anyInt())).thenReturn(getAgentIdAndWebacountIdList().get(0));
        mockServiceImpl.checkAgentAuthentication(mockAgentWebAccountList, CUSTOMER_ID_3, TOKEN_2, authentication);
        assertNotNull(mockCustomerDataService.findById(anyLong()));
        assertFalse(mockAgentWebAccountList.get(0).getToken().equals(TOKEN_2));
        verify(mockAgentService, never()).deleteAll(mockAgentWebAccountList);        
    }
    
}
