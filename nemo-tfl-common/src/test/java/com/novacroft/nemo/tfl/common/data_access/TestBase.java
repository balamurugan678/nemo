package com.novacroft.nemo.tfl.common.data_access;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Enumeration;

public class TestBase {
    private FileSystemXmlApplicationContext applicationContext;
    protected SessionFactory sessionFactory;

    public void setup(boolean stateless) {
        applicationContext = new FileSystemXmlApplicationContext("src/test/resources/nemo-tfl-common-servlet-test.xml");
        sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");

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

