package com.novacroft.nemo.tfl.innovator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.controller.BaseController;

/**
 * Controller for system variables which can be manipulated.
 */
@Controller
@RequestMapping(value = Page.INV_SYSTEM)
public class SystemController extends BaseController {

    @Autowired
    SystemParameterService systemParameterService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_SYSTEM);
        List<SystemParameterDTO> parameters = systemParameterService.getAllProperties();
        modelAndView.addObject("parameters", parameters);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView saveProperties(@RequestBody final String body) {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_SYSTEM);
        List<SystemParameterDTO> parameters = getParametersFromURL(body);
        List<String> errorList = new ArrayList<String>();
        validate(parameters, errorList);
        if (errorList.size() != 0) {
            modelAndView.addObject("parameters", parameters);
            modelAndView.addObject("errors", errorList);
            return modelAndView;
        }
        systemParameterService.saveProperties(parameters);
        List<SystemParameterDTO> reloadedParameters = systemParameterService.getAllProperties();
        modelAndView.addObject("parameters", reloadedParameters);
        return modelAndView;
    }

    private void validate(List<SystemParameterDTO> parameters, List<String> errorList) {
        for (SystemParameterDTO systemParameterDTO : parameters) {
            if (SystemParameterCode.lookUp(systemParameterDTO.getCode()) != null) {
                switch (SystemParameterCode.lookUp(systemParameterDTO.getCode())) {
                case AUTOTOPUP_RESETTLEMENT_DAYS_FOR_WEEKLY_TRAVELCARD:
                case AUTOTOPUP_RESETTLEMENT_DAYS_FOR_MONTHLY_TRAVELCARD:
                case AUTOTOPUP_RESETTLEMENT_DAYS_FOR_3MONTH_TRAVELCARD:
                case AUTOTOPUP_RESETTLEMENT_DAYS_FOR_6MONTH_TRAVELCARD:
                case AUTOTOPUP_RESETTLEMENT_DAYS_FOR_ODD_PERIOD_TRAVELCARD:
                case AUTOTOPUP_RESETTLEMENT_DAYS_DEFAULT:
                    if (!StringUtil.isInteger(systemParameterDTO.getValue())) {
                        errorList.add(getContent("autoTopUpResettlementPeriodDays.error", systemParameterDTO.getCode()));
                    }
                    break;
                default:
                    break;
                }
            }
        }
    }

    protected List<SystemParameterDTO> getParametersFromURL(String body) {
        String decoded = decodeURL(body);
        String[] split = decoded.split("&");
        List<SystemParameterDTO> parameters = new ArrayList<SystemParameterDTO>();
        for (String string : split) {
            String name = string.substring(0, string.indexOf("="));
            String value = string.substring(string.indexOf("=") + 1, string.length());
            SystemParameterDTO systemParameter = new SystemParameterDTO(name, value);
            parameters.add(systemParameter);
        }
        return parameters;
    }
}
