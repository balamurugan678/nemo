package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_SEARCH;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemDTOSerializer;
import com.novacroft.nemo.tfl.common.form_validator.ApprovalSearchValidator;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Controller
@SessionAttributes(PageCommand.APPROVALLIST)
@RequestMapping(value = { Page.APPROVALLIST, Page.APPROVALLISTSTAGE2, Page.AGENTLIST, Page.SUPERVISORLIST, Page.EXCEPTIONLIST })
public class ApprovalListSearchController extends BaseController {
    static final int GSON_EMPTY = 3;

    private static final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(WorkflowItemDTO.class, new WorkflowItemDTOSerializer());
    private static final Gson gson = gsonBuilder.create();

    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected ApprovalSearchValidator approvalSearchValidator;

    @InitBinder
    protected final void initBinder(final ServletRequestDataBinder binder) {
        final CustomDateEditor editor = new CustomDateEditor(DateUtil.createShortDateFormatter(), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(params = TARGET_ACTION_SEARCH, method = RequestMethod.POST)
    @ResponseBody
    public String getResults(ApprovalListCmdImpl search, BindingResult result, HttpServletRequest request) {
        String messages = checkSearch(search, result, request);
        if (messages.length() > GSON_EMPTY) {
            return messages;
        } else {
            return search(search);
        }
    }

    protected String search(ApprovalListCmdImpl search) {
        List<WorkflowItemDTO> results = workflowService.findBySearchCriteria(search);

        return gson.toJson(results);
    }

    @RequestMapping(value = "/approvalValid", method = RequestMethod.POST)
    @ResponseBody
    public String checkSearch(ApprovalListCmdImpl search, BindingResult result, HttpServletRequest request) {
        return checkSearchCriteria(search, result);
    }

    private String checkSearchCriteria(ApprovalListCmdImpl search, BindingResult result) {
        Map<String, String> messages = new HashMap<String, String>();

        approvalSearchValidator.validate(search, result);

        List<ObjectError> errorList = result.getAllErrors();

        for (ObjectError objectError : errorList) {
            FieldError error = (FieldError) objectError;
            messages.put(error.getField(), getContent(error.getField() + "." + error.getCode()));
        }

        return gson.toJson(messages);
    }

    @RequestMapping(value = "/approvalAllByGroup", method = RequestMethod.POST)
    @ResponseBody
    public String getAllByGroup(ApprovalListCmdImpl search, BindingResult result, HttpServletRequest request) {
        List<WorkflowItemDTO> results = workflowService.findAllByGroup(search.getFormLocation());
        return gson.toJson(results);
    }

    @RequestMapping(value = "/approvalAllByUser", method = RequestMethod.POST)
    @ResponseBody
    public String getAllByUser(@RequestParam(value = "user", required = false) String user, HttpServletRequest request) {
        List<WorkflowItemDTO> results = workflowService.findAllByUser(user);
        return gson.toJson(results);
    }
}
