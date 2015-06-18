package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.getResult;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.AGENT;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.STATUS;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_AGENT_1;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_CASE_NOTE_REASON;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_REJECTION_REASON_NOT_OTHER;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_REJECTION_REASON_OTHER;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_STATUS_1;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_FIELD_NAME;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.TEST_MESSAGE_1;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.REASON_TEXT;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.BLANK;
import static com.novacroft.nemo.test_support.CaseHistoryNoteServiceImplTestUtil.getTestCaseHistoryNotes1And2;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

/**
 * Unit tests for CaseHistoryNoteService
 */
public class CaseHistoryNoteServiceImplTest {

    private CaseHistoryNoteServiceImpl mockService;
    private ContentDataService mockContentDataService;
    private WorkflowRejectCmd mockWorkflowRejectCmd;
    private WorkflowCmd mockWorkflowCmd;
    private WorkflowItemDTO mockWorkflowItemDTO;
    private WorkflowEditCmd mockWorkflowEditCmd;
    private List<CaseHistoryNote> mockCaseNotes;
    private ContentDTO mockContentDTO;
    
    @Before
    public void setUp() {
        mockService = mock(CaseHistoryNoteServiceImpl.class);        
        mockContentDataService = mock(ContentDataService.class);
        mockService.content = mockContentDataService;
        mockWorkflowRejectCmd = mock(WorkflowRejectCmd.class);
        mockWorkflowCmd = mock(WorkflowCmd.class);
        mockWorkflowItemDTO = mock(WorkflowItemDTO.class);
        mockWorkflowEditCmd = mock(WorkflowEditCmd.class);
        mockCaseNotes = mock(List.class);
        mockContentDTO = mock(ContentDTO.class);
    }

    @Test
    public void shouldInitialise() {
        when(mockContentDataService.findByLocaleAndCode(anyString(), anyString())).thenReturn(mockContentDTO);
        doNothing().when(mockService).initialize();
        doCallRealMethod().when(mockService).initialize();                
        mockService.initialize();
        verify(mockContentDataService, atLeast(4)).findByLocaleAndCode(anyString(), anyString());
    }

    @Test
    public void shouldAddRejectionCaseNotesIfRejectionReasonIsOther() {
        doNothing().when(mockService).initialize();
        when(mockService.getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class))).thenReturn(getTestCaseHistoryNotes1And2());
        when(mockWorkflowRejectCmd.getRejectReason()).thenReturn(TEST_REJECTION_REASON_OTHER);
        doCallRealMethod().when(mockService).addRejectionCaseNotes(any(WorkflowRejectCmd.class), any(WorkflowItemDTO.class));                
        mockService.addRejectionCaseNotes(mockWorkflowRejectCmd, mockWorkflowItemDTO);
        verify(mockService, atLeastOnce()).initialize();
        verify(mockService, atLeastOnce()).getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class));
        verify(mockWorkflowRejectCmd, atLeastOnce()).getRejectReason();
    }

    @Test
    public void shouldAddRejectionCaseNotesIfRejectionReasonIsNotOther() {
        doNothing().when(mockService).initialize();
        when(mockService.getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class))).thenReturn(getTestCaseHistoryNotes1And2());
        when(mockWorkflowRejectCmd.getRejectReason()).thenReturn(TEST_REJECTION_REASON_NOT_OTHER);
        doCallRealMethod().when(mockService).addRejectionCaseNotes(any(WorkflowRejectCmd.class), any(WorkflowItemDTO.class));                
        mockService.addRejectionCaseNotes(mockWorkflowRejectCmd, mockWorkflowItemDTO);
        verify(mockService, atLeastOnce()).initialize();
        verify(mockService, atLeastOnce()).getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class));
        verify(mockWorkflowRejectCmd, atLeastOnce()).getRejectReason();
    }

    @Test
    public void shouldAddChangesCaseNote() {
        doNothing().when(mockService).initialize();
        when(mockWorkflowCmd.getWorkflowItem()).thenReturn(getResult());
        when(mockWorkflowItemDTO.getAgent()).thenReturn(TEST_AGENT_1);
        when(mockWorkflowItemDTO.getStatus()).thenReturn(TEST_STATUS_1);        
        doCallRealMethod().when(mockService).addChangesCaseNote(anyList(), any(WorkflowCmd.class), anyString(), anyString(), anyString(), anyString());        
        mockService.addChangesCaseNote(getTestCaseHistoryNotes1And2(), mockWorkflowCmd, "", "", "", "");
        verify(mockService, atLeastOnce()).initialize();
        verify(mockWorkflowCmd, atLeastOnce()).getWorkflowItem();
    }

    @Test
    public void getCaseNotesWhenNotNull() {
        when(mockWorkflowItemDTO.getCaseNotes()).thenReturn(getTestCaseHistoryNotes1And2());        
        doCallRealMethod().when(mockService).getCaseNotes(any(WorkflowItemDTO.class));                
        mockService.getCaseNotes(mockWorkflowItemDTO);
        verify(mockService, atLeastOnce()).getCaseNotes(any(WorkflowItemDTO.class));
    }

    @Test
    public void getCaseNotesWhenNull() {
        when(mockWorkflowItemDTO.getCaseNotes()).thenReturn(null);
        doCallRealMethod().when(mockService).getCaseNotes(any(WorkflowItemDTO.class));                
        mockService.getCaseNotes(mockWorkflowItemDTO);
        verify(mockService, atLeastOnce()).getCaseNotes(any(WorkflowItemDTO.class));
    }

    @Test
    public void getCaseNotesExcludingHistoricWhenNotNull() {
        when(mockWorkflowItemDTO.getCaseNotes()).thenReturn(getTestCaseHistoryNotes1And2());
        doCallRealMethod().when(mockService).getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class));
        mockService.getCaseNotesExcludingHistoric(mockWorkflowItemDTO);
        verify(mockService, atLeastOnce()).getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class));
    }

    @Test
    public void getCaseNotesExcludingHistoricWhenNull() {
        when(mockWorkflowItemDTO.getCaseNotes()).thenReturn(null);
        doCallRealMethod().when(mockService).getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class));
        mockService.getCaseNotesExcludingHistoric(mockWorkflowItemDTO);
        verify(mockService, atLeastOnce()).getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class));
    }

    @Test
    public void shouldAddReasonCaseNote() {
        doNothing().when(mockService).initialize();
        when(mockWorkflowEditCmd.getReason()).thenReturn(TEST_CASE_NOTE_REASON);
        when(mockWorkflowItemDTO.getAgent()).thenReturn(TEST_AGENT_1);
        when(mockWorkflowItemDTO.getStatus()).thenReturn(TEST_STATUS_1);  
        doCallRealMethod().when(mockService).addReasonCaseNote(anyList(), any(WorkflowItemDTO.class), any(WorkflowEditCmd.class));        
        mockService.addReasonCaseNote(getTestCaseHistoryNotes1And2(), mockWorkflowItemDTO, mockWorkflowEditCmd);
        verify(mockService, atLeastOnce()).initialize();
        verify(mockWorkflowEditCmd, atLeastOnce()).getReason();
        verify(mockWorkflowItemDTO, atLeastOnce()).getAgent();
        verify(mockWorkflowItemDTO, atLeastOnce()).getStatus();
    }

    @Test
    public void shouldAddRefundItemAddedCaseNote() {
        doNothing().when(mockService).initialize();
        when(mockWorkflowCmd.getWorkflowItem()).thenReturn(getResult());
        when(mockWorkflowItemDTO.getAgent()).thenReturn(TEST_AGENT_1);
        when(mockWorkflowItemDTO.getStatus()).thenReturn(TEST_STATUS_1);  
        doCallRealMethod().when(mockService).addRefundItemAddedCaseNote(anyList(), any(WorkflowCmd.class), anyString(), anyString());        
        mockService.addRefundItemAddedCaseNote(getTestCaseHistoryNotes1And2(), mockWorkflowCmd, "", "");
        verify(mockService, atLeastOnce()).initialize();
        verify(mockWorkflowCmd, atLeastOnce()).getWorkflowItem();
    }

    @Test
    public void shouldRefundItemRemovedCaseNote() {
        doNothing().when(mockService).initialize();
        when(mockWorkflowCmd.getWorkflowItem()).thenReturn(getResult());
        when(mockWorkflowItemDTO.getAgent()).thenReturn(TEST_AGENT_1);
        when(mockWorkflowItemDTO.getStatus()).thenReturn(TEST_STATUS_1);  
        doCallRealMethod().when(mockService).addRefundItemRemovedCaseNote(anyList(), any(WorkflowCmd.class), anyString(), anyString());        
        mockService.addRefundItemRemovedCaseNote(getTestCaseHistoryNotes1And2(), mockWorkflowCmd, "", "");
        verify(mockService, atLeastOnce()).initialize();
        verify(mockWorkflowCmd, atLeastOnce()).getWorkflowItem();
    }

    @Test
    public void shouldAddCaseNoteWhenNoteNotEmpty() {
        when(mockService.getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class))).thenReturn(getTestCaseHistoryNotes1And2());
        doNothing().when(mockService).initialize();
        when(mockWorkflowItemDTO.getAgent()).thenReturn(TEST_AGENT_1);
        when(mockWorkflowItemDTO.getStatus()).thenReturn(TEST_STATUS_1);  
        doCallRealMethod().when(mockService).addCaseNote(mockWorkflowItemDTO, TEST_CASE_NOTE_REASON);        
        mockService.addCaseNote(mockWorkflowItemDTO, TEST_CASE_NOTE_REASON);
        verify(mockService, atLeastOnce()).initialize();
    }

    @Test
    public void shouldNotAddCaseNoteWhenNoteEmpty() {
        when(mockService.getCaseNotesExcludingHistoric(any(WorkflowItemDTO.class))).thenReturn(getTestCaseHistoryNotes1And2());
        doCallRealMethod().when(mockService).addCaseNote(mockWorkflowItemDTO, StringUtil.EMPTY_STRING);        
        mockService.addCaseNote(mockWorkflowItemDTO, StringUtil.EMPTY_STRING);
        verify(mockService, never()).initialize();
    }
    
    @Test
    public void shouldAddChangesCaseNoteWithBlankOldValue() {
        doNothing().when(mockService).initialize();
        when(mockWorkflowCmd.getWorkflowItem()).thenReturn(getResult());
        when(mockWorkflowItemDTO.getAgent()).thenReturn(AGENT);
        when(mockWorkflowItemDTO.getStatus()).thenReturn(STATUS);
        doCallRealMethod().when(mockService).addChangesCaseNote(anyList(), any(WorkflowCmd.class), anyString(), anyString(), anyString(), anyString());        
        
        String message = "Changed " + TEST_FIELD_NAME + " from " + BLANK + " to " + TEST_MESSAGE_1 + REASON_TEXT + TEST_CASE_NOTE_REASON;
        
        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItemDTO.getAgent(), mockWorkflowItemDTO.getStatus());
        
        List<CaseHistoryNote> caseNotes = mockService.addChangesCaseNote(new ArrayList<CaseHistoryNote>(), mockWorkflowCmd, TEST_FIELD_NAME, null, TEST_MESSAGE_1, TEST_CASE_NOTE_REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0) );
        verify(mockService, atLeastOnce()).initialize();
        verify(mockWorkflowCmd, atLeastOnce()).getWorkflowItem();
    }
    
    private void checkCaseNotes(CaseHistoryNote expectedCaseNote, CaseHistoryNote caseNote) {
        assertEquals(expectedCaseNote.getAgent(), caseNote.getAgent());
        assertEquals(expectedCaseNote.getMessage(), caseNote.getMessage());
        assertEquals(expectedCaseNote.getStatus(), caseNote.getStatus());
    }
}
