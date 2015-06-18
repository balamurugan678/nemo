package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.application_service.WebCreditStatementService;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for web credit statement
 */
@Controller
@RequestMapping(value = PageUrl.WEB_ACCOUNT_CREDIT_STATEMENT)
public class WebCreditStatementController extends OnlineBaseController {
    @Autowired
    protected WebCreditStatementService webCreditStatementService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
        return new ModelAndView(PageView.WEB_ACCOUNT_CREDIT_STATEMENT, PageCommand.WEB_ACCOUNT_CREDIT_STATEMENT,
                this.webCreditStatementService.getStatement(getLoggedInUserCustomerId()));
    }
}
