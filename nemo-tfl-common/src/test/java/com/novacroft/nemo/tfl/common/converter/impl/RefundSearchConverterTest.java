package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTOWithoutCardNumber;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FORMATED_NAME_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.APPROVAL_ID;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATUS;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.AGENT_NAME;
import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.CASENUMBER;
import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.getWorkflowItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class RefundSearchConverterTest {
    private RefundSearchConverter converter;
    private WorkFlowService mockWorkflowService;
    private CardDataService mockCardDataService;
    
    @Before
    public void init() {
        converter = new RefundSearchConverter();
        mockWorkflowService = mock(WorkFlowService.class);
        mockCardDataService = mock(CardDataService.class);
        
        converter.workflowService = mockWorkflowService;
        converter.cardDataService = mockCardDataService;
        
    }

    @Test
    public void shouldConvertFromWorkflowItem() {
        WorkflowItemDTO workflowItem = getWorkflowItem();
        workflowItem.getRefundDetails().setCardNumber(OYSTER_NUMBER_1);        
        RefundSearchResultDTO actualResult = converter.convertFromWorkflowItem(workflowItem);
        
        assertNotNull(actualResult);
        assertEquals(CASENUMBER, actualResult.getCaseNumber());
        assertEquals(AGENT_NAME, actualResult.getAgent());
        assertEquals(FORMATED_NAME_1, actualResult.getCustomerName());
        assertEquals(OYSTER_NUMBER_1, actualResult.getCardNumber());
        assertEquals(PaymentType.WEB_ACCOUNT_CREDIT.code(), actualResult.getPaymentMethod());
    }
    
    @Test
    public void shouldConverterFromWorkflowitemWithNoCardId() {
        WorkflowItemDTO workflowItem = getWorkflowItem();
        workflowItem.getRefundDetails().setCardId(null);
        RefundSearchResultDTO actualResult = converter.convertFromWorkflowItem(workflowItem);
        
        verify(mockCardDataService, never()).findById(anyLong());
        assertNotNull(actualResult);
        assertEquals(CASENUMBER, actualResult.getCaseNumber());
        assertEquals(AGENT_NAME, actualResult.getAgent());
        assertEquals(FORMATED_NAME_1, actualResult.getCustomerName());
        assertEquals(PaymentType.WEB_ACCOUNT_CREDIT.code(), actualResult.getPaymentMethod());
    }
    
    @Test
    public void shouldConvertFromOrder() {
        when(mockWorkflowService.getHistoricWorkflowItem(anyString())).thenReturn(getWorkflowItem());
        
        RefundSearchResultDTO actualResult = converter.convertFromOrder(getTestOrderDTO1());
        
        verify(mockWorkflowService).getHistoricWorkflowItem(APPROVAL_ID.toString());
        assertEquals(STATUS, actualResult.getStatus());
    }
    
    @Test
    public void shouldConvertFromWorkflowItemWithoutCardNumber() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTOWithoutCardNumber());
        RefundSearchResultDTO actualResult = converter.convertFromWorkflowItem(getWorkflowItem());
        assertNotNull(actualResult);
        assertEquals(StringUtil.EMPTY_STRING, actualResult.getCardNumber());
    }
    
    @Test
    public void shouldConvertFromWorkflowItemWithoutCard() {
        when(mockCardDataService.findById(anyLong())).thenReturn(null);
        RefundSearchResultDTO actualResult = converter.convertFromWorkflowItem(getWorkflowItem());
        assertNotNull(actualResult);
        assertEquals(StringUtil.EMPTY_STRING, actualResult.getCardNumber());
    }
}
