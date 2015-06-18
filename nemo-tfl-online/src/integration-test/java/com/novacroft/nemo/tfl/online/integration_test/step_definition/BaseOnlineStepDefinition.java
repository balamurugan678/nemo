package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.integration_test.BaseStepDefinition;
import com.novacroft.nemo.tfl.common.security.TflUser;
import com.novacroft.nemo.tfl.common.security.TflUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_USER_EMAIL;

public abstract class BaseOnlineStepDefinition extends BaseStepDefinition {
    @Autowired
    protected TestContext testContext;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected TflUserDetailsService userDetailsService;

    public BaseOnlineStepDefinition() {
    }

    protected void testSetUp() {
        if (null == this.testContext.getMockMvc()) {
            this.testContext.setMockMvc(MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build());
        }

        if (null == this.testContext.getUserDetails()) {
            this.testContext.setUserDetails(this.userDetailsService.loadUserByUsername(TEST_USER_EMAIL));
        }
    }

    protected void securitySetUp() {
        TflUser user =
                new TflUser(this.testContext.getUserDetails().getUsername(), this.testContext.getUserDetails().getPassword(),
                        this.testContext.getUserDetails().getAuthorities());
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }
}
