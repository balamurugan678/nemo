package com.novacroft.nemo.tfl.common.integration_test;

import com.novacroft.nemo.tfl.common.security.TflUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.NamingException;

import static org.junit.Assert.fail;

public abstract class BaseStepDefinition {
    @Autowired(required = false)
    protected WebApplicationContext webApplicationContext;
    @Autowired(required = false)
    protected TflUserDetailsService userDetailsService;

    public BaseStepDefinition() {
        initialiseJndiContext();
    }

    protected void initialiseJndiContext() {
        try {
            SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        } catch (NamingException e) {
            e.printStackTrace();
            fail();
        }
    }
}
