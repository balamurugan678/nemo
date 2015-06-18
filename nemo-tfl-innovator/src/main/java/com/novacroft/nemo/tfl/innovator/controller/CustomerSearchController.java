package com.novacroft.nemo.tfl.innovator.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.CustomerSearchValidator;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;

@Controller
@RequestMapping(value = Page.INV_CUSTOMER_SEARCH)
public class CustomerSearchController extends BaseController {
    protected static final int GSON_EMPTY = 3;

    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CustomerSearchValidator customerSearchValidator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage(@ModelAttribute(PageCommand.CUSTOMER_SEARCH) CustomerSearchCmdImpl cmd) {
        return new ModelAndView(PageView.INV_CUSTOMER_SEARCH, PageCommand.CUSTOMER_SEARCH, cmd);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String getResults(CustomerSearchCmdImpl cmd, BindingResult result) {
        String messages = checkSearchCriteria(cmd, result);
        if (messages.length() > GSON_EMPTY) {
            return messages;
        } else {
            return search(cmd);
        }
    }

    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    @ResponseBody
    public String checkSearch(CustomerSearchCmdImpl cmd, BindingResult result) {
        return checkSearchCriteria(cmd, result);
    }

    protected String search(CustomerSearchCmdImpl cmd) {
        if (cmd.getOrderNumber() != null) {
            return new Gson().toJson(customerService.findCustomerByOrderNumber(cmd.getOrderNumber()));
        } else {
            return new Gson().toJson(customerDataService.findByCriteria(
                    new CustomerSearchArgumentsDTO((cmd.getExact() != null), cmd.getCustomerId(), cmd.getCardNumber(),
                            cmd.getFirstName(), cmd.getLastName(), cmd.getPostcode(), cmd.getEmailAddress(), cmd.getUserName(),
                            cmd.getStartCount(), cmd.getEndCount())));
        }
    }

    protected String checkSearchCriteria(CustomerSearchCmdImpl cmd, BindingResult result) {
        Map<String, String> messages = new HashMap<String, String>();

        customerSearchValidator.validate(cmd, result);

        for (ObjectError objectError : result.getAllErrors()) {
            FieldError error = (FieldError) objectError;
            messages.put(error.getField(), getContent(error.getField() + "." + error.getCode()));
        }

        return new Gson().toJson(messages);
    }
}
