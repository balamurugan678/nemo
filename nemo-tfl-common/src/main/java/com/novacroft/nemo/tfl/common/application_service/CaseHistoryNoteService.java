package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface CaseHistoryNoteService {
    List<CaseHistoryNote> addRejectionCaseNotes(WorkflowRejectCmd cmd, WorkflowItemDTO workflowItem);

    List<CaseHistoryNote> addChangesCaseNote(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd, String field, String oldValue,
                    String newValue, String reason);

    List<CaseHistoryNote> addReasonCaseNote(List<CaseHistoryNote> caseNotes, WorkflowItemDTO workflowItem, WorkflowEditCmd cmd);

    List<CaseHistoryNote> getCaseNotes(WorkflowItemDTO workflowItem);

    List<CaseHistoryNote> getCaseNotesExcludingHistoric(WorkflowItemDTO workflowItem);

    List<CaseHistoryNote> addRefundItemRemovedCaseNote(List<CaseHistoryNote> caseNotes, WorkflowCmd cmd, String oldRefundItem, String reason);

    List<CaseHistoryNote> addRefundItemAddedCaseNote(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd, String newRefundItem, String reason);

    List<CaseHistoryNote> addCaseNote(WorkflowItemDTO workflowItem, String note);
}
