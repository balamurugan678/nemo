package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.AGENTLOG_JOBNAMES_LIST;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.transfer.JobLogSessionData;
import com.novacroft.nemo.tfl.common.util.JobLogUtil;


/**
 * Controller class to handle the cmd for Job Details. This will retrieve job log details that match the provided Search
 * Command.
 */

@Controller
@RequestMapping(value = Page.INV_AGENTLOG_SEARCH)
public class AgentLogSearchController extends BaseController {
    @Autowired
    protected JobLogService jobLogService;
    @Autowired
    protected SelectListService selectListService;

    @InitBinder
    protected void initBinder(final ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = DateUtil.createShortDateFormatter();
        dateFormat.setLenient(false);
        final CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    @ModelAttribute
    public void populateJobNameList(Model model) {
        model.addAttribute(AGENTLOG_JOBNAMES_LIST, selectListService.getSelectList(PageSelectList.AGENTLOG_JOBNAMES_LIST));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(AgentLogSearchCmdImpl cmd, HttpSession session) {
    	JobLogSessionData logSessionData = JobLogUtil.getJobLogSearchDataFromSession(session);
    	if(null!=logSessionData) {
    		ModelAndView redirectView = new ModelAndView();
    		if(logSessionData.isSearchCriteriaNonEmptyFlag()){
    			setLogSessionValueInCommandObj(logSessionData, cmd);
	        	changeSytemDateTimeFormatWithDBDateTimeFormat(cmd);
	            redirectView.setViewName(PageView.INV_AGENTLOG_SEARCH);
	            redirectView.addObject(PageCommand.AGENTLOG_SEARCH, cmd);
	            JobLogUtil.removeJobLogSearchDataFromSession(session);
    		} 
    		return redirectView;
    	}else {
    		return new ModelAndView(PageView.INV_AGENTLOG_SEARCH, PageCommand.AGENTLOG_SEARCH, cmd);
    	}
    }


	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String getJobLogResultsByJobNameAndExecutionDates(HttpSession session, AgentLogSearchCmdImpl cmd) {
    	changeSytemDateTimeFormatWithDBDateTimeFormat(cmd);
    	setSearchCriteriaDataIntoSessionData(cmd, session);
        return new Gson().toJson(jobLogService.findJobLogSearchDetailsBetweenExecutionDatesWithJobName(cmd));
    }

	private void setSearchCriteriaDataIntoSessionData(AgentLogSearchCmdImpl cmd, HttpSession session) {
		JobLogSessionData jobLogSessionData = new JobLogSessionData();
		jobLogSessionData.setJobName(cmd.getJobName());
		jobLogSessionData.setStartedAt(cmd.getStartedAt());
		jobLogSessionData.setEndedAt(cmd.getEndedAt());
		jobLogSessionData.setSearchCriteriaNonEmptyFlag(true);
		JobLogUtil.addJobLogSearchDataInSession(session, jobLogSessionData);
	}
	
	private void changeSytemDateTimeFormatWithDBDateTimeFormat(AgentLogSearchCmdImpl cmdImpl){
		String startDate = DateUtil.parseCalenderToStringDateObj(cmdImpl.getStartedAt().toString());
		String endDate = DateUtil.parseCalenderToStringDateObj(cmdImpl.getEndedAt().toString());
		cmdImpl.setStartDate(startDate);
		cmdImpl.setEndDate(endDate);
	}

	private void setLogSessionValueInCommandObj(JobLogSessionData logSessionData, AgentLogSearchCmdImpl cmd) {
		cmd.setStartedAt(logSessionData.getStartedAt());
		cmd.setEndedAt(logSessionData.getEndedAt());
		cmd.setJobName(logSessionData.getJobName());
		cmd.setSearchCriteriaNonEmptyFlag(logSessionData.isSearchCriteriaNonEmptyFlag());
	}
}
