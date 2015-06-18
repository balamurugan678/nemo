package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.command.CurrentActionListFileCmd;
import com.novacroft.nemo.mock_cubic.service.CurrentActionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static com.novacroft.nemo.mock_cubic.constant.Constant.DEFAULT_COMMAND_NAME;
import static com.novacroft.nemo.mock_cubic.constant.Constant.NUM_ROWS;

/**
 * Controller for creating Current Action List files
 */
@Controller
@RequestMapping(value = "CurrentActionListFile.htm")
public class CurrentActionListController extends MockCubicBaseController {

    @Autowired
    CurrentActionListService currentActionListService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCurrentActionListPage() {
        CurrentActionListFileCmd cmd = new CurrentActionListFileCmd();
        return new ModelAndView("CurrentActionListFileView", DEFAULT_COMMAND_NAME,
                this.currentActionListService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = "targetAction=addRows", method = RequestMethod.POST)
    public ModelAndView addRows(@ModelAttribute(DEFAULT_COMMAND_NAME) CurrentActionListFileCmd cmd) {
        return new ModelAndView("CurrentActionListFileView", DEFAULT_COMMAND_NAME,
                this.currentActionListService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = "targetAction=getCsv", method = RequestMethod.POST)
    public void getCsv(@ModelAttribute(DEFAULT_COMMAND_NAME) CurrentActionListFileCmd cmd, HttpServletResponse response,
                       Writer writer) {
        String csv = this.currentActionListService.serialiseToCsv(cmd.getCurrentActions());
        setContentTypeToCsvOnResponseHeader(response);
        setAttachedFileNameOnResponseHeader(response, "wca_00000_0000.dat");
        try {
            writer.write(csv);
            writer.flush();
        } catch (IOException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}
