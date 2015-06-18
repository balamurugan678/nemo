package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.mock_cubic.command.EditAutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.service.EditAutoLoadChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.novacroft.nemo.tfl.common.constant.PageUrl.URL_SUFFIX;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_SUFFIX;

/**
 * Controller for mock auto load change set up
 */
@Controller
public class EditAutoLoadChangeController {
    private static final Logger logger = LoggerFactory.getLogger(EditAutoLoadChangeController.class);

    protected static final String EDIT_PAGE = "EditAutoLoadChange";
    protected static final String EDIT_VIEW = EDIT_PAGE + VIEW_SUFFIX;
    public static final String EDIT_URL = EDIT_PAGE + URL_SUFFIX;

    protected static final String LIST_PAGE = "ListAutoLoadChange";
    protected static final String LIST_VIEW = LIST_PAGE + VIEW_SUFFIX;
    public static final String LIST_URL = LIST_PAGE + URL_SUFFIX;

    public static final String COMMAND_NAME = "Cmd";

    @Autowired
    protected EditAutoLoadChangeService editAutoLoadChangeService;

    @RequestMapping(value = LIST_URL, method = RequestMethod.GET)
    public ModelAndView showListPage() {
        return new ModelAndView(LIST_VIEW, COMMAND_NAME, this.editAutoLoadChangeService.getListOfAutoLoadChangeResponses());
    }

    @RequestMapping(value = EDIT_URL, method = RequestMethod.GET)
    public ModelAndView showEditPage(@RequestParam(required = false, value = "id") Long cubicCardResponseId) {
        if (cubicCardResponseId == null) {
            return new ModelAndView(EDIT_VIEW, COMMAND_NAME, new EditAutoLoadChangeCmd());
        }
        return new ModelAndView(EDIT_VIEW, COMMAND_NAME, this.editAutoLoadChangeService.getSavedResponse(cubicCardResponseId));
    }

    @RequestMapping(value = EDIT_URL, method = RequestMethod.POST)
    public ModelAndView saveEditPage(@ModelAttribute(COMMAND_NAME) EditAutoLoadChangeCmd cmd) {
        this.editAutoLoadChangeService.saveResponse(cmd);
        return new ModelAndView(EDIT_VIEW, COMMAND_NAME, cmd);
    }
}
