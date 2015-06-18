package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.command.AutoLoadPerformedCmd;
import com.novacroft.nemo.mock_cubic.constant.Constant;
import com.novacroft.nemo.mock_cubic.service.AutoLoadPerformedService;
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
 * Controller for creating CUBIC Auto Loads Performed Files
 */
@Controller
@RequestMapping(value = Constant.AUTO_LOAD_PERFORMED_URL)
public class AutoLoadPerformedController extends MockCubicBaseController {
    @Autowired
    protected AutoLoadPerformedService autoLoadPerformedService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
        AutoLoadPerformedCmd cmd = new AutoLoadPerformedCmd();
        return new ModelAndView(AUTO_LOAD_PERFORMED_VIEW, DEFAULT_COMMAND_NAME,
                this.autoLoadPerformedService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = TARGET_ACTION_ADD_ROWS, method = RequestMethod.POST)
    public ModelAndView addRows(@ModelAttribute(DEFAULT_COMMAND_NAME) AutoLoadPerformedCmd cmd) {
        return new ModelAndView(AUTO_LOAD_PERFORMED_VIEW, DEFAULT_COMMAND_NAME,
                this.autoLoadPerformedService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = TARGET_ACTION_GET_CSV, method = RequestMethod.POST)
    public void getCsv(@ModelAttribute(DEFAULT_COMMAND_NAME) AutoLoadPerformedCmd cmd, HttpServletResponse response,
                       Writer writer) {
        String csv = this.autoLoadPerformedService.serialiseToCsv(cmd.getAutoLoadsPerformed());
        setContentTypeToCsvOnResponseHeader(response);
        setAttachedFileNameOnResponseHeader(response, "wap_00000_0000.dat");
        try {
            writer.write(csv);
            writer.flush();
        } catch (IOException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}
