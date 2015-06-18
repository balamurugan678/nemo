package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.command.AutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.constant.Constant;
import com.novacroft.nemo.mock_cubic.service.AutoLoadChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static com.novacroft.nemo.mock_cubic.constant.Constant.*;

/**
 * Controller for creating Ad Hoc Distribution files
 */
@Controller
@RequestMapping(value = Constant.AUTO_LOAD_CHANGE_URL)
public class AutoLoadChangeController extends MockCubicBaseController {

    @Autowired
    AutoLoadChangeService autoLoadChangeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
        AutoLoadChangeCmd cmd = new AutoLoadChangeCmd();
        return new ModelAndView(AUTO_LOAD_CHANGE_VIEW, DEFAULT_COMMAND_NAME,
                this.autoLoadChangeService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = TARGET_ACTION_ADD_ROWS, method = RequestMethod.POST)
    public ModelAndView addRows(@ModelAttribute(DEFAULT_COMMAND_NAME) AutoLoadChangeCmd cmd) {
        return new ModelAndView(AUTO_LOAD_CHANGE_VIEW, DEFAULT_COMMAND_NAME,
                this.autoLoadChangeService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = TARGET_ACTION_GENERATE_ROWS, method = RequestMethod.POST)
    public ModelAndView generateRows(@ModelAttribute(DEFAULT_COMMAND_NAME) AutoLoadChangeCmd cmd) {
        return new ModelAndView(AUTO_LOAD_CHANGE_VIEW, DEFAULT_COMMAND_NAME, this.autoLoadChangeService.generateRecords(cmd));
    }

    @RequestMapping(params = TARGET_ACTION_GET_CSV, method = RequestMethod.POST)
    public void getCsv(@ModelAttribute(DEFAULT_COMMAND_NAME) AutoLoadChangeCmd cmd, HttpServletResponse response,
                       Writer writer) {
        String csv = this.autoLoadChangeService.serialiseToCsv(cmd.getAutoLoadChanges());
        setContentTypeToCsvOnResponseHeader(response);
        setAttachedFileNameOnResponseHeader(response, "wac_00000_0000.dat");
        try {
            writer.write(csv);
            writer.flush();
        } catch (IOException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}
