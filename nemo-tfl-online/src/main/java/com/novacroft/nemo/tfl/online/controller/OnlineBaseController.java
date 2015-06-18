package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.SINGLE_SIGN_ON_AUTHENTICATION_FLAG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Base controller for online application
 */
public abstract class OnlineBaseController extends BaseController {

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.DASHBOARD));
    }

    protected Long getLoggedInUserCustomerId() {
        return this.securityService.getLoggedInCustomer().getId();
    }

    @Override
    public String getLoggedInUsername() {
        return this.securityService.getLoggedInUsername();
    }
    
    protected CustomerDTO getCustomer() { 
        return customerDataService.findById(this.securityService.getLoggedInCustomer().getId());
    }
    
    protected Boolean isSingleSignOnAuthenticationOn() {
        return systemParameterService.getBooleanParameterValue(SINGLE_SIGN_ON_AUTHENTICATION_FLAG.code());
    }
    
}
