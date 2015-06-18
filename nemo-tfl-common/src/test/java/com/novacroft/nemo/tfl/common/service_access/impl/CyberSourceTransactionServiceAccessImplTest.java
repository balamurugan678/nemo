package com.novacroft.nemo.tfl.common.service_access.impl;

import com.novacroft.cyber_source.web_service.model.transaction.ObjectFactory;
import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceTransactionServiceAccess;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * CyberSourceTransactionServiceAccess unit tests
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:nemo-tfl-common-cyber-source-service-test.xml"})
//@Profile("nemo-tfl-common-cyber-source-service-test")
public class CyberSourceTransactionServiceAccessImplTest {

    @Autowired
    private CyberSourceTransactionServiceAccess cyberSourceTransactionServiceAccess;

    //    @BeforeClass
    public static void classSetUp() {
        System.setProperty("spring.profiles.active", "nemo-tfl-common-cyber-source-service-test");
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("java:comp/env/spring.profiles.active", "nemo-tfl-common-cyber-source-service-test");
        try {
            builder.activate();
        } catch (NamingException e) {
            e.printStackTrace();
            fail();
        }
    }

    // dummy test to keep junit happy when test below is not active
    @Test
    public void noop() {
    }

    /**
     * This test calls a real service.  Recommend that it is not ordinarily included in the suite and only run on an ad-hoc
     * basis.
     */
//    @Test
    public void testRunTransactionAgainstRealService() {
        ObjectFactory objectFactory = new ObjectFactory();
        RequestMessage requestMessage = objectFactory.createRequestMessage();
        requestMessage.setCustomerID("0");
        requestMessage.setMerchantID("test-merchant-id");

        ReplyMessage replyMessage = this.cyberSourceTransactionServiceAccess.runTransaction(requestMessage);

        assertEquals("ACCEPT", replyMessage.getDecision());
    }
}
