package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.PAYMENT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_REASONS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.WORK_FLOW_STATUS_LIST;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.ApprovalListCmd;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterValue;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

@Controller
@SessionAttributes(PageCommand.APPROVALLIST)
public class ApprovalListController {

    static final Logger logger = LoggerFactory.getLogger(ApprovalListController.class);

    private static final String CASE_NUMBER_SUFFIX = "?caseNumber=";

    private ApprovalListCmdImpl newApprovalListCmdImpl() {
        return new ApprovalListCmdImpl();
    }

    @Autowired
    protected WorkFlowService workflowService;
    
    @Autowired
    protected SelectListService selectListService;
    
    @ModelAttribute
    public void populateWorkflowStatus(Model model) {
        model.addAttribute(WORK_FLOW_STATUS_LIST, selectListService.getSelectList(PageSelectList.WORK_FLOW_STATUS_LIST));
    }
    
    @ModelAttribute
    public void populatePaymentTypes(Model model) {
        model.addAttribute(PAYMENT_TYPE, selectListService.getSelectList(PageSelectList.PAYMENT_TYPE));
    }
    
    @ModelAttribute
    public void populateRefundApprovalReasons(Model model) {
        model.addAttribute(REFUND_REASONS, selectListService.getSelectList(PageSelectList.REFUND_REASONS));
    }

    @RequestMapping(value = Page.AGENTLIST, method = RequestMethod.GET)
    public ModelAndView showAgentApprovalList(@RequestParam(value = "user", required = false) String user) {

        ApprovalListCmd cmd = newApprovalListCmdImpl();
        cmd.setFormLocation(Page.AGENTLIST);

        if (isNotBlank(user)) {
            cmd.setApprovals(workflowService.findAllByUser(user));
        } else {
            cmd.setApprovals(workflowService.findAllByGroup(AgentGroup.AGENT));
        }

        return new ModelAndView(PageView.AGENTLIST, PageCommand.APPROVALLIST, cmd);
    }

    @RequestMapping(value = Page.APPROVALLIST, method = RequestMethod.GET)
    public ModelAndView showFirstStageApprovalList(@RequestParam(value = "user", required = false) String user) {
        ApprovalListCmd cmd = newApprovalListCmdImpl();
        cmd.setFormLocation(Page.APPROVALLIST);
        cmd.setTargetAction(PageParameterValue.SEARCH);

        if (isNotBlank(user)) {
            cmd.setApprovals(workflowService.findAllByUser(user));
        } else {
            cmd.setApprovals(workflowService.findAllByGroup(AgentGroup.FIRST_STAGE_APPROVER));
        }
        ModelAndView modelAndView = new ModelAndView(PageView.APPROVALLIST, PageCommand.APPROVALLIST, cmd);
        ApprovalListCmd claimCmd = newApprovalListCmdImpl();
        claimCmd.setFormLocation(Page.APPROVALLIST);
        modelAndView.addObject("claimCmd", claimCmd);
        return modelAndView;
    }

    
    
    @RequestMapping(value = Page.SUPERVISORLIST, method = RequestMethod.GET)
    public ModelAndView showSupervisorList(@RequestParam(value = "user", required = false) String user) {

        ApprovalListCmd cmd = newApprovalListCmdImpl();
        cmd.setFormLocation(Page.SUPERVISORLIST);

        if (isNotBlank(user)) {
            cmd.setApprovals(workflowService.findAllByUser(user));
        } else {
            cmd.setApprovals(workflowService.findAllByGroup(AgentGroup.SUPERVISOR));
        }

        return new ModelAndView(PageView.SUPERVISORLIST, PageCommand.APPROVALLIST, cmd);
    }


    @RequestMapping(value = Page.APPROVALLISTSTAGE2, method = RequestMethod.GET)
    public ModelAndView showSecondStageApprovalList(@RequestParam(value = "user", required = false) String user) {
        ApprovalListCmd cmd = newApprovalListCmdImpl();
        cmd.setFormLocation(Page.APPROVALLISTSTAGE2);
        cmd.setTargetAction(PageParameterValue.SEARCH);

        if (isNotBlank(user)) {
            cmd.setApprovals(workflowService.findAllByUser(user));
        } else {
            cmd.setApprovals(workflowService.findAllByGroup(AgentGroup.SECOND_STAGE_APPROVER));
        }

        ModelAndView modelAndView = new ModelAndView(PageView.APPROVALLISTSTAGE2, PageCommand.APPROVALLIST, cmd);
        ApprovalListCmd claimCmd = newApprovalListCmdImpl();
        claimCmd.setFormLocation(Page.APPROVALLISTSTAGE2);
        modelAndView.addObject("claimCmd", claimCmd);
        return modelAndView;
    }

    @RequestMapping(value = Page.EXCEPTIONLIST, method = RequestMethod.GET)
    public ModelAndView showExceptionList(@RequestParam(value = "user", required = false) String user) {
        ApprovalListCmd cmd = newApprovalListCmdImpl();
        cmd.setFormLocation(Page.EXCEPTIONLIST);
        cmd.setTargetAction(PageParameterValue.SEARCH);

        if (isNotBlank(user)) {
            cmd.setApprovals(workflowService.findAllByUser(user));
        } else {
            cmd.setApprovals(workflowService.findAllByGroup(AgentGroup.EXCEPTIONS));
        }

        ModelAndView modelAndView = new ModelAndView(PageView.EXCEPTIONLIST, PageCommand.APPROVALLIST, cmd);
        ApprovalListCmd claimCmd = newApprovalListCmdImpl();
        claimCmd.setFormLocation(Page.EXCEPTIONLIST);
        modelAndView.addObject("claimCmd", claimCmd);
        return modelAndView;
    }

    @RequestMapping(value = { Page.APPROVALLIST, Page.APPROVALLISTSTAGE2, Page.EXCEPTIONLIST }, method = RequestMethod.POST)
    public RedirectView claim(@ModelAttribute("claimCmd") ApprovalListCmdImpl cmd) {
        workflowService.claim(cmd.getCaseNumber(), cmd.getFormLocation());
        return new RedirectView(PageUrl.VIEW_WORKFLOW_ITEM + CASE_NUMBER_SUFFIX + cmd.getCaseNumber());
    }
    
  }
