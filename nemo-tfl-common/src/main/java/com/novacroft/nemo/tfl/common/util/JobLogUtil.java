package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.tfl.common.constant.JobStatus;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogSessionData;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.novacroft.nemo.tfl.common.constant.AgentLogSearchConstant.SESSION_ATTRIBUTE_JOBLOG_DATA;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Job Log utilities
 */
public final class JobLogUtil {
    public static final String NEW_LINE = "\r\n";
    public static final String NEW_LINE_MARK_UP = "<br/>";
    protected static final String ERROR_TEXT = "Error:";
    protected static final String ERROR_TEXT_WITH_EMPHASIS_MARK_UP = "<span class=\"log-error\">" + ERROR_TEXT + "</span>";

    public static JobLogDTO createLog(String jobName, String fileName) {
        return new JobLogDTO(jobName, fileName);
    }

    public static JobLogDTO createLog(String jobName) {
        return new JobLogDTO(jobName);
    }

    public static void logMessage(JobLogDTO jobLogDTO, String message) {
        if (isBlank(jobLogDTO.getLog())) {
            jobLogDTO.setLog(message);
        } else {
            jobLogDTO.setLog(jobLogDTO.getLog() + NEW_LINE + message);
        }
    }

    public static void logInComplete(JobLogDTO jobLogDTO) {
        setStatus(jobLogDTO, JobStatus.IN_COMPLETE);
    }

    public static void logComplete(JobLogDTO jobLogDTO) {
        setStatus(jobLogDTO, JobStatus.COMPLETE);
    }

    public static void logEnd(JobLogDTO jobLogDTO) {
        jobLogDTO.setEndedAt(new Date());
    }

    protected static void setStatus(JobLogDTO jobLogDTO, JobStatus jobStatus) {
        jobLogDTO.setStatus(jobStatus.code());
        jobLogDTO.setEndedAt(new Date());
    }

    public static void addJobLogSearchDataInSession(HttpSession session, JobLogSessionData jobLogSessionData) {
        session.setAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA, jobLogSessionData);
    }

    public static void removeJobLogSearchDataFromSession(HttpSession session) {
        session.removeAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA);
    }

    public static JobLogSessionData getJobLogSearchDataFromSession(HttpSession session) {
        return (JobLogSessionData) session.getAttribute(SESSION_ATTRIBUTE_JOBLOG_DATA);
    }

    public static String formatLogAsHtml(String log) {
        return HtmlUtils.htmlEscape(log).replaceAll(NEW_LINE, NEW_LINE_MARK_UP)
                .replaceAll(ERROR_TEXT, ERROR_TEXT_WITH_EMPHASIS_MARK_UP);
    }

    private JobLogUtil() {
    }
}
