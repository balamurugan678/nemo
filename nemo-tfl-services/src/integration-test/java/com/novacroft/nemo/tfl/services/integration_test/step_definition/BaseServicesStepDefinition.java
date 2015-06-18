package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.integration_test.BaseStepDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class BaseServicesStepDefinition extends BaseStepDefinition {
    @Autowired
    protected TestContext testContext;
    @Autowired
    protected WebApplicationContext webApplicationContext;

    public BaseServicesStepDefinition() {
    }

    protected void testSetUp() {
        if (null == this.testContext.getMockMvc()) {
            this.testContext.setMockMvc(MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build());
        }
    }

}
