package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.CaseHistoryNoteService;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.CaseNoteType;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service("caseHistoryService")
public class CaseHistoryNoteServiceImpl implements CaseHistoryNoteService {
    @Autowired
    protected ContentDataService content;

    private static final String REASON_KEY = "reasonLabel";
    private static final String REASON_FOR_CHANGE_KEY = "reasonForChangeLabel";
    private static final String ADDED_REFUND_ITEM_KEY = "addedRefundItem";
    private static final String REMOVED_REFUND_ITEM_KEY = "removedRefundItem";
    private static String REASON_TEXT = "";
    private static String REASON_FOR_CHANGE_TEXT = "";
    private static String ADDED_REFUND_ITEM_TEXT = "";
    private static String REMOVED_REFUND_ITEM_TEXT = "";
    private static String BLANK = "BLANK";

    protected void initialize() {
        REASON_TEXT = content.findByLocaleAndCode(REASON_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        REASON_FOR_CHANGE_TEXT = content.findByLocaleAndCode(REASON_FOR_CHANGE_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        ADDED_REFUND_ITEM_TEXT = content.findByLocaleAndCode(ADDED_REFUND_ITEM_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        REMOVED_REFUND_ITEM_TEXT = content.findByLocaleAndCode(REMOVED_REFUND_ITEM_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
    }

    @Override
    public List<CaseHistoryNote> addRejectionCaseNotes(WorkflowRejectCmd cmd, WorkflowItemDTO workflowItem) {
        initialize();
        List<CaseHistoryNote> caseNotes = getCaseNotesExcludingHistoric(workflowItem);
        String reason = cmd.getRejectReason();

        CaseHistoryNote caseHistoryNote;

        if (reason.equalsIgnoreCase("Other")) {
            caseHistoryNote = new CaseHistoryNote(cmd.getRejectFreeText(), workflowItem.getAgent(), workflowItem.getStatus());
        } else {
            caseHistoryNote = new CaseHistoryNote(cmd.getRejectReason(), workflowItem.getAgent(), workflowItem.getStatus());
        }

        caseNotes.add(caseHistoryNote);
        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> addChangesCaseNote(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd, String field, String oldValue,
                    String newValue, String reason) {
        initialize();
        WorkflowItemDTO workflowItem = workflowCmd.getWorkflowItem();

        oldValue = StringUtil.isBlank(oldValue)? BLANK : oldValue; 
        String message = "Changed " + field + " from " + oldValue + " to " + newValue + REASON_TEXT + reason;

        caseNotes.add(new CaseHistoryNote(message, workflowItem.getAgent(), workflowItem.getStatus()));
        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> addReasonCaseNote(List<CaseHistoryNote> caseNotes, WorkflowItemDTO workflowItem, WorkflowEditCmd cmd) {
        initialize();
        caseNotes.add(new CaseHistoryNote(REASON_FOR_CHANGE_TEXT + cmd.getReason(), workflowItem.getAgent(), workflowItem.getStatus()));
        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> getCaseNotes(WorkflowItemDTO workflowItem) {
        List<CaseHistoryNote> caseNotes = workflowItem.getCaseNotes();

        if (caseNotes == null) {
            caseNotes = new ArrayList<CaseHistoryNote>();
        }

        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> getCaseNotesExcludingHistoric(WorkflowItemDTO workflowItem) {
        List<CaseHistoryNote> caseNotes = workflowItem.getCaseNotes();

        if (caseNotes == null) {
            return new ArrayList<CaseHistoryNote>();
        }

        for (Iterator<CaseHistoryNote> iterator = caseNotes.iterator(); iterator.hasNext();) {
            CaseHistoryNote caseNote = iterator.next();
            if (CaseNoteType.HISTORIC.equals(caseNote.getType())) {
                iterator.remove();
            }
        }

        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> addRefundItemAddedCaseNote(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd, String newRefundItem,
                    String reason) {
        initialize();
        WorkflowItemDTO workflowItem = workflowCmd.getWorkflowItem();

        String message = ADDED_REFUND_ITEM_TEXT + newRefundItem + REASON_TEXT + reason;

        caseNotes.add(new CaseHistoryNote(message, workflowItem.getAgent(), workflowItem.getStatus()));
        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> addRefundItemRemovedCaseNote(List<CaseHistoryNote> caseNotes, WorkflowCmd cmd, String oldRefundItem,
                    String reason) {
        initialize();
        WorkflowItemDTO workflowItem = cmd.getWorkflowItem();

        String message = REMOVED_REFUND_ITEM_TEXT + oldRefundItem + REASON_TEXT + reason;

        caseNotes.add(new CaseHistoryNote(message, workflowItem.getAgent(), workflowItem.getStatus()));
        return caseNotes;
    }

    @Override
    public List<CaseHistoryNote> addCaseNote(WorkflowItemDTO workflowItem, String note) {
        List<CaseHistoryNote> caseNotes = getCaseNotesExcludingHistoric(workflowItem);
        
        if (StringUtils.isNotEmpty(note)){
            initialize();
            CaseHistoryNote caseHistoryNote = new CaseHistoryNote(note, workflowItem.getAgent(), workflowItem.getStatus());
            caseNotes.add(caseHistoryNote);
        }
        return caseNotes;
    }
}
