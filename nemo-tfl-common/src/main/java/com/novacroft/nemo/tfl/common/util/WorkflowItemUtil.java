package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithHtmlCurrencySymbol;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInDaysHoursMinutesAsString;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInSecondsAsLong;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.joda.time.DateTime;
import org.joda.time.Period;

import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public final class WorkflowItemUtil {

    private WorkflowItemUtil() {

    }

    public static String getTimeOnQueueAsString(WorkflowItemDTO workflowItem) {
        DateTime created = workflowItem.getTaskCreatedTime();
        DateTime now = new DateTime();

        return getDateDiffInDaysHoursMinutesAsString(created, now);
    }

    public static Period getTimeOnQueueAsPeriod(WorkflowItemDTO workflowItem) {
        DateTime created = workflowItem.getTaskCreatedTime();
        DateTime now = new DateTime();

        return new Period(created, now);
    }

    public static Long calculateTimeOnQueueAsLong(WorkflowItemDTO workflowItem) {
        return getDateDiffInSecondsAsLong(workflowItem.getTaskCreatedTime(), new DateTime());
    }

    public static String getApprovalReasonsAsString(List<String> approvalReasons) {
        StringBuilder reasons = new StringBuilder();

        if (approvalReasons != null) {
            for (Iterator<String> iterator = approvalReasons.iterator(); iterator.hasNext();) {
                reasons.append(iterator.next());
                if (iterator.hasNext()) {
                    reasons.append("<br />");
                }
            }
        }

        return reasons.toString();
    }
    
    public static List<String> setApprovalReasonsUpperCase(List<String> approvalReasons) {
    	List<String> reasonList = new ArrayList<String>();

    	return (approvalReasons != null) ? addToReasonList(reasonList, approvalReasons) : reasonList;
    }

    protected static List<String> addToReasonList(List<String> reasonList, List<String> approvalReasons) {
    	
    	for(String reason : approvalReasons) {
    		reasonList.add(reason.toUpperCase());
    	}
    	
		return reasonList;
	}

	public static String getAmountAsPoundsAndPenceAsString(Long amount) {
        return formatPenceWithHtmlCurrencySymbol(amount.intValue());
    }

    public static List<CaseHistoryNote> getCaseNotesIncludingHistory(List<CaseHistoryNote> notes,
                    String processInstanceId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = (processEngine != null) ? processEngine.getHistoryService() : null;

        List<CaseHistoryNote> caseNotesList = new ArrayList<CaseHistoryNote>();

        if (notes != null) {
            caseNotesList.addAll(notes);
        }

        if (historyService != null) {
            List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)
                            .includeTaskLocalVariables().orderByHistoricTaskInstanceDuration().desc().list();

            for (HistoricTaskInstance task : tasks) {
                if (task.getClaimTime() != null) {
                    CaseHistoryNote caseNote = new CaseHistoryNote(task);
                    if (notes == null || !notes.contains(caseNote)) {
                        caseNotesList.add(caseNote);
                    }
                }
            }
        }

        return caseNotesList;
    }
}
