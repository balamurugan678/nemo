package com.novacroft.nemo.tfl.innovator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.common.application_service.RefundSearchService;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.form_validator.RefundSearchValidator;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;

@Controller
@RequestMapping(value = Page.INV_REFUND_SEARCH)
public class RefundSearchController extends BaseController {
    static final int GSON_EMPTY = 3;

    @Autowired
    protected RefundSearchService refundSearchService;
    @Autowired
    protected RefundSearchValidator refundSearchValidator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(@ModelAttribute(PageCommand.REFUND_SEARCH) RefundSearchCmd search) {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_REFUND_SEARCH);
        search.setRefunds(refundSearchService.getAllRefunds());
        modelAndView.addObject(PageCommand.REFUND_SEARCH, search);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String getResults(RefundSearchCmd search, BindingResult result, HttpServletRequest request) {
        String messages = checkSearch(search, result, request);
        if (messages.length() > GSON_EMPTY) {
            return messages;
        } else {
            return search(search);
        }
    }

    protected String search(RefundSearchCmd search) {
        List<RefundSearchResultDTO> results = refundSearchService.findBySearchCriteria(search);

        return new Gson().toJson(results);
    }

    @RequestMapping(value = "/refundValid", method = RequestMethod.POST)
    @ResponseBody
    public String checkSearch(RefundSearchCmd search, BindingResult result, HttpServletRequest request) {
        return checkSearchCriteria(search, result);
    }

    private String checkSearchCriteria(RefundSearchCmd search, BindingResult result) {
        Map<String, String> messages = new HashMap<String, String>();

        refundSearchValidator.validate(search, result);

        List<ObjectError> errorList = result.getAllErrors();

        for (ObjectError objectError : errorList) {
            FieldError error = (FieldError) objectError;
            messages.put(error.getField(), getContent(error.getField() + "." + error.getCode()));
        }

        return new Gson().toJson(messages);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ResponseBody
    public String getAll() {
        List<RefundSearchResultDTO> results = refundSearchService.getAllRefunds();
        return new Gson().toJson(results);
    }
}
