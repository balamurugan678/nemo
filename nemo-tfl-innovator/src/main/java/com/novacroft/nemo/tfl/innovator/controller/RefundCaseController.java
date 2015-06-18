package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.RefundCaseService;
import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;

@Controller
@RequestMapping(value = Page.INV_REFUND_CASE)
public class RefundCaseController {

    @Autowired
    protected RefundCaseService refundCaseService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_REFUND_CASE);
        RefundCaseCmd cmd = refundCaseService.getRefundCase(caseNumber);
        modelAndView.addObject(PageCommand.REFUND_CASE, cmd);
        return modelAndView;
    }
}
