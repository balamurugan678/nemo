package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.AGENTLOG_JOBNAMES_LIST;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.AGENTLOG_SEARCH;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_AGENTLOG_DETAILS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.controller.BaseController;

/**
 * Controller class to handle the search for Job Details. This will retrieve job log details that match the provided Search
 * Command.
 */

@Controller
@RequestMapping(value = Page.INV_AGENTLOG_DETAILS)
public class AgentLogDetailsController extends BaseController {
    @Autowired
    protected JobLogService jobLogService;
    @Autowired
    protected SelectListService selectListService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadJobLogDetails(AgentLogSearchCmdImpl agentLogSearchCmd, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(INV_AGENTLOG_DETAILS);
        if (agentLogSearchCmd.getId() != null) {
            try {
                jobLogService.getAgentLogDetailsByJobId(agentLogSearchCmd, agentLogSearchCmd.getId());
            } catch (NullPointerException e) {
                modelAndView.addObject("error", "jobid.parameter.error");
                modelAndView.addObject(AGENTLOG_SEARCH, agentLogSearchCmd);
                return modelAndView;
            }
        }
        modelAndView.addObject(AGENTLOG_SEARCH, agentLogSearchCmd);
        request.setAttribute(ID, agentLogSearchCmd.getId());
        return modelAndView;
    }
    
    @RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(HttpSession session, AgentLogSearchCmdImpl agentLogSearchCmd,  BindingResult result) {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(AGENTLOG_JOBNAMES_LIST, selectListService.getSelectList(PageSelectList.AGENTLOG_JOBNAMES_LIST));
        modelAndView.addObject(PageCommand.AGENTLOG_SEARCH, agentLogSearchCmd);
        modelAndView.setView(new RedirectView(PageUrl.INV_AGENTLOG_SEARCH));
        return modelAndView;
          	
    }
}
