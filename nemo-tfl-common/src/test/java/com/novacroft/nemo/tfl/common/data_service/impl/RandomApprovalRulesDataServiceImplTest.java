package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.RandomApprovalRulesConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.RandomApprovalSampleThresholdDAO;
import com.novacroft.nemo.tfl.common.domain.RandomApprovalSampleThresholdRule;
import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;

public class RandomApprovalRulesDataServiceImplTest {
    private static final Long TEST_RULE_ID = 101l;
    private static final String TEST_TEAM = "test-team";
    private static final String TEST_ORGANISATION = "test-organisation";
    private static final String TEST_STRING_ID = "test-id";
    
    private RandomApprovalRulesDataServiceImpl spyService;
    private RandomApprovalSampleThresholdDAO mockDAO;
    
    @Before
    public void setUp() {
        spyService = spy(new RandomApprovalRulesDataServiceImpl());
        mockDAO = mock(RandomApprovalSampleThresholdDAO.class);
        spyService.setDao(mockDAO);
        spyService.setConverter(new RandomApprovalRulesConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(spyService.getNewEntity());
    }
    
    @Test
    public void shouldCreate() {
        doReturn(null).when(spyService).createOrUpdate(any(RandomApprovalSampleThresholdRuleDTO.class));
        
        RandomApprovalSampleThresholdRuleDTO testDTO = new RandomApprovalSampleThresholdRuleDTO();
        spyService.create(testDTO);
        verify(spyService).createOrUpdate(testDTO);
    }
    
    @Test
    public void shouldindAllRandomApprovalSampleThresholds() {
        RandomApprovalSampleThresholdRule testRule = new RandomApprovalSampleThresholdRule();
        testRule.setId(TEST_RULE_ID);
        when(mockDAO.findAll()).thenReturn(Arrays.asList(testRule));
        List<RandomApprovalSampleThresholdRuleDTO> actualList = spyService.findAllRandomApprovalSampleThresholds();
        assertEquals(1, actualList.size());
        assertEquals(TEST_RULE_ID, actualList.get(0).getId());
    }
    
    @Test
    public void shouldFindAllRandomApprovalSampleThresholdsByOrganisationAndTeam() {
        RandomApprovalSampleThresholdRule testRule = new RandomApprovalSampleThresholdRule();
        testRule.setTeam(TEST_TEAM);
        testRule.setOrganisation(TEST_ORGANISATION);
        
        when(mockDAO.findByExample(any(RandomApprovalSampleThresholdRule.class)))
                .thenReturn(Arrays.asList(testRule));
        List<RandomApprovalSampleThresholdRuleDTO> actualList = 
                spyService.findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(TEST_ORGANISATION, TEST_TEAM);
        assertEquals(1, actualList.size());
        assertEquals(TEST_TEAM, actualList.get(0).getTeam());
        assertEquals(TEST_ORGANISATION, actualList.get(0).getOrganisation());
    }
    
    @Test
    public void shouldFindOrganisationAndTeamById() {
        List<Object[]> mockReturnList = new ArrayList<>();
        mockReturnList.add(new Object[] {TEST_TEAM, TEST_ORGANISATION});
        
        Query mockQuery = mock(Query.class);
        when(mockQuery.setParameter(anyInt(), anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(mockReturnList);
        
        when(mockDAO.createSQLQuery(anyString())).thenReturn(mockQuery);
        List<RandomApprovalSampleThresholdRuleDTO> actualList = spyService.findOrganisationAndTeamById(TEST_STRING_ID);
        assertEquals(1, actualList.size());
        assertEquals(TEST_TEAM, actualList.get(0).getTeam());
        assertEquals(TEST_ORGANISATION, actualList.get(0).getOrganisation());
    }
}
