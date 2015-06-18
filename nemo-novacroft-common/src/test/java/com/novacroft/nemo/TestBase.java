package com.novacroft.nemo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Enumeration;

public class TestBase {
    private FileSystemXmlApplicationContext applicationContext;
    protected SessionFactory sessionFactory;
    protected StatelessSession statelessSession;
    protected SessionFactory mSsessionFactory;
    protected StatelessSession mSstatelessSession;

    public void setup(boolean stateless) {
        applicationContext =
                new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/appServlet/test-servlet-context.xml");
        sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
        statelessSession = (stateless) ? sessionFactory.openStatelessSession() : null;
        mSsessionFactory = (SessionFactory) applicationContext.getBean("MSsessionFactory");
        mSstatelessSession = (stateless) ? mSsessionFactory.openStatelessSession() : null;
        if (!isConfigured()) {
            Log4jConfigurator.configure();
        }
    }

    public void tearDownMaster() {
        Log4jConfigurator.shutdown();
    }

    @SuppressWarnings("rawtypes")
    protected static boolean isConfigured() {

        @SuppressWarnings("deprecation") Enumeration appenders = Logger.getRoot().getAllAppenders();
        if (appenders.hasMoreElements()) {
            return true;
        } else {
            Enumeration loggers = LogManager.getCurrentLoggers();
            while (loggers.hasMoreElements()) {
                Logger c = (Logger) loggers.nextElement();
                if (c.getAllAppenders().hasMoreElements()) {
                    return true;
                }
            }
        }
        return false;
    }

    public FileSystemXmlApplicationContext getApplicationContext() {
        return applicationContext;
    }

}

