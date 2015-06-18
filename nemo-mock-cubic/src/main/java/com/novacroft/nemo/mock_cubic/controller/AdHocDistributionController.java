package com.novacroft.nemo.mock_cubic.controller;

import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.command.AdHocDistributionCmd;
import com.novacroft.nemo.mock_cubic.service.AdHocDistributionService;
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
 * Controller for creating Ad Hoc Distribution files
 */
@Controller
@RequestMapping(value = "AdHocDistribution.htm")
public class AdHocDistributionController extends MockCubicBaseController {

    @Autowired
    AdHocDistributionService adHocDistributionService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAdHocPage() {
        AdHocDistributionCmd cmd = new AdHocDistributionCmd();
        return new ModelAndView("AdHocDistributionView", DEFAULT_COMMAND_NAME,
                this.adHocDistributionService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = "targetAction=addRows", method = RequestMethod.POST)
    public ModelAndView addRows(@ModelAttribute(DEFAULT_COMMAND_NAME) AdHocDistributionCmd cmd) {
        return new ModelAndView("AdHocDistributionView", DEFAULT_COMMAND_NAME,
                this.adHocDistributionService.addEmptyRecords(cmd, NUM_ROWS));
    }

    @RequestMapping(params = "targetAction=getCsv", method = RequestMethod.POST)
    public void getCsv(@ModelAttribute(DEFAULT_COMMAND_NAME) AdHocDistributionCmd cmd, HttpServletResponse response,
                       Writer writer) {
        String csv = this.adHocDistributionService.serialiseToCsv(cmd.getAdHocDistributions());
        setContentTypeToCsvOnResponseHeader(response);
        setAttachedFileNameOnResponseHeader(response, "wad_00000_0000.dat");
        try {
            writer.write(csv);
            writer.flush();
        } catch (IOException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}
