package com.novacroft.nemo.tfl.services.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

@Controller
public class CustomerController extends BaseServicesController {

    static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected com.novacroft.nemo.tfl.services.application_service.CustomerService applicationCustomerService;
    @Autowired
    protected SecurityService securityService;

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public Customer getCustomer(@PathVariable Long customerId) {
        return applicationCustomerService.getCustomerByExternalId(customerId);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.PUT, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable Long customerId, HttpServletRequest request) {
        String username = securityService.getUsernameFromAuthorizationHeader(request);
        return applicationCustomerService.updateCustomer(customer, customerId, username);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.POST, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public WebServiceResult deleteCustomer(@RequestBody DeleteCustomer deleteCustomer, @PathVariable Long customerId, HttpServletRequest request) {
        String username = securityService.getUsernameFromAuthorizationHeader(request);
        return applicationCustomerService.deleteCustomer(deleteCustomer, customerId, username);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public Customer createCustomer(@RequestBody Customer customer, HttpServletRequest request) {
        String username = securityService.getUsernameFromAuthorizationHeader(request);
        return applicationCustomerService.createCustomer(customer, username);
    }
}
