package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.constant.CaseNoteType;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;

/**
 * Utilities for CaseHistoryNote tests
 */
public final class CaseHistoryNoteServiceImplTestUtil extends CommonCardTestUtil {

    public static final String TEST_MESSAGE_1 = "test message 1";
    public static final String TEST_AGENT_1 = "test agent 1";
    public static final String TEST_STATUS_1 = "test status 1";
    public static final String TEST_MESSAGE_2 = "test message 2";
    public static final String TEST_AGENT_2 = "test agent 2";
    public static final String TEST_STATUS_2 = "test status 2";
    public static final String TEST_REJECTION_REASON_OTHER = "Other";
    public static final String TEST_REJECTION_REASON_NOT_OTHER = "not Other";
    public static final String TEST_CASE_NOTE_REASON = "Test case note reason";
    public static final String TEST_FIELD_NAME = "Test Field Name";
    public static final String REASON_TEXT = "";
    public static final String BLANK = "BLANK";

    public static List<CaseHistoryNote> getTestCaseHistoryNotes1And2() {
        List<CaseHistoryNote> testCaseHistoryNotes = new ArrayList<CaseHistoryNote>(2);
        testCaseHistoryNotes.add(getTestCaseHistoryNote1());
        testCaseHistoryNotes.add(getTestCaseHistoryNote2());
        return testCaseHistoryNotes;
    }

    public static CaseHistoryNote getTestCaseHistoryNote1() {
        CaseHistoryNote caseHistoryNote = new CaseHistoryNote();
        caseHistoryNote.setAgent(TEST_AGENT_1);
        caseHistoryNote.setDate(DateTestUtil.get1Jan());
        caseHistoryNote.setMessage(TEST_MESSAGE_1);
        caseHistoryNote.setStatus(TEST_STATUS_1);
        caseHistoryNote.setType(CaseNoteType.AGENT);
        return caseHistoryNote;
    }

    public static CaseHistoryNote getTestCaseHistoryNote2() {
        CaseHistoryNote caseHistoryNote = new CaseHistoryNote();
        caseHistoryNote.setAgent(TEST_AGENT_2);
        caseHistoryNote.setDate(DateTestUtil.get31Jan());
        caseHistoryNote.setMessage(TEST_MESSAGE_2);
        caseHistoryNote.setStatus(TEST_STATUS_2);
        caseHistoryNote.setType(CaseNoteType.HISTORIC);
        return caseHistoryNote;
    }
    
}
