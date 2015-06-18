package com.novacroft.nemo.tfl.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSessionService;

public class SingleSignOnSessionListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        if (session != null) {
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            SingleSignOnSessionService sessionService = (SingleSignOnSessionService) context.getBean("singleSignOnSessionService");
            sessionService.removeSessionById(session.getId());
        }
    }
}
