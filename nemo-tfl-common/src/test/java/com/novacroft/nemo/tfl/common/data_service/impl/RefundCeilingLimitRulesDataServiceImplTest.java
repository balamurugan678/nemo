package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.RepeatClaimLimitRuleConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.RepeatClaimLimitRuleDAO;
import com.novacroft.nemo.tfl.common.domain.RepeatClaimLimitRule;
import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;

public class RefundCeilingLimitRulesDataServiceImplTest {
    private static final Long TEST_RULE_ID = 101l;
    
    private RefundCeilingLimitRulesDataServiceImpl spyService;
    private RepeatClaimLimitRuleDAO mockDAO;
    
    @Before
    public void setUp() {
        spyService = spy(new RefundCeilingLimitRulesDataServiceImpl());
        mockDAO = mock(RepeatClaimLimitRuleDAO.class);
        spyService.setDao(mockDAO);
        spyService.setConverter(new RepeatClaimLimitRuleConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(spyService.getNewEntity());
    }
    
    @Test
    public void shouldCreate() {
        doReturn(null).when(spyService).createOrUpdate(any(RepeatClaimLimitRuleDTO.class));
        
        RepeatClaimLimitRuleDTO testDTO = new RepeatClaimLimitRuleDTO();
        spyService.create(testDTO);
        verify(spyService).createOrUpdate(testDTO);
    }
    
    @Test
    public void shouldFindAllRepeatClaimLimitRules() {
        RepeatClaimLimitRule testRule = new RepeatClaimLimitRule();
        testRule.setId(TEST_RULE_ID);
        when(mockDAO.findAll()).thenReturn(Arrays.asList(testRule));
        List<RepeatClaimLimitRuleDTO> actualList = spyService.findAllRepeatClaimLimitRules();
        assertEquals(1, actualList.size());
        assertEquals(TEST_RULE_ID, actualList.get(0).getId());
    }
}
