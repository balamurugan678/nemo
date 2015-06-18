package com.novacroft.nemo.tfl.innovator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageView;

@Controller
@RequestMapping(value = Page.INV_CONTENT)
public class ContentController {
    @Autowired
    protected ContentDataService contentDataService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadAllContent(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_CONTENT);
        modelAndView.addObject("content", contentDataService.findAll());
        return modelAndView;
    }
    
   
    
}
