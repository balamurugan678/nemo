package com.novacroft.nemo.tfl.innovator.workflow;

/**
 * NOTES
 *
 * You can provide any name you like as the resourceName, but only resource names ending with ".bpmn20.xml" are parsed as
 * process definitions.
 */

import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/activiti.cfg.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
@Profile("nemo-tfl-workflow-test")
public class WorkflowTest {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowTest.class);

    @BeforeClass
    public static void classSetUp() {
        System.setProperty("spring.profiles.active", "nemo-tfl-workflow-test");
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("java:comp/env/spring.profiles.active", "nemo-tfl-workflow-test");
        try {
            builder.activate();
        } catch (NamingException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    @Rule
    public ActivitiRule activitiRule;

    @Test
    @Ignore
    @Deployment(resources = {"RefundProcess.bpmn20.xml"})
    public void refundProcessTest() {

        Long preTestTaskCount = runtimeService.createProcessInstanceQuery().count();

        Map<String, Object> variables = new HashMap<String, Object>();

        RefundDetailDTO refundBean = new RefundDetailDTO();
        refundBean.setName("TestUser");
        refundBean.setCardId(4999L);
        refundBean.setTicketCorrupt(Boolean.TRUE);
        refundBean.setAgentInterventionRequired(Boolean.TRUE);
        refundBean.setSupervisorResolved(Boolean.TRUE);
        refundBean.setChargeAdminFee(Boolean.TRUE);
        refundBean.setGoodwillPaymentRequired(Boolean.TRUE);
        refundBean.setCaseOwnerRequiresApproval(Boolean.TRUE);
        refundBean.setAbnormalRefundTrends(Boolean.TRUE);
        refundBean.setApprovalRequired(Boolean.TRUE);              //Approval required
        refundBean.setRefundRejected(Boolean.TRUE);
        refundBean.setBacsChequePaymentRequired(Boolean.FALSE);
        refundBean.setAdhocLoadRequired(Boolean.TRUE);
        refundBean.setCardPaymentRequired(Boolean.FALSE);

        refundBean.setFirstStageApprovalGiven(Boolean.TRUE);
        refundBean.setSecondStageApprovalRequired(Boolean.TRUE); //SECOND STAGE REQUIRED
        refundBean.setSecondStageApprovalGiven(Boolean.FALSE);
        refundBean.setAcceptedByGate(Boolean.FALSE);
        refundBean.setFirstFailure(Boolean.FALSE);
        refundBean.setSupervisorAssignsToQueue(Boolean.FALSE);

        logger.debug(refundBean.toString());

        variables.put("refundBean", refundBean);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("RefundProcess", variables);
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        assertEquals("Agent claims Refund Task", task.getName());

        taskService.complete(task.getId());
        runtimeService.deleteProcessInstance(processInstance.getId(), "UnitTest");
        assert (preTestTaskCount.equals(runtimeService.createProcessInstanceQuery().count()));

    }

}
