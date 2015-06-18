package com.novacroft.nemo.tfl.innovator.command;

import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NovaCroftRefundExample {
    private static final Logger logger = LoggerFactory.getLogger(NovaCroftRefundExample.class);

    public static void main(String[] args) {

        try {
            ProcessEngine processEngine =
                    ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

            RepositoryService repositoryService = processEngine.getRepositoryService();
            repositoryService.createDeployment().addClasspathResource("RefundProcess.bpmn").deploy();

            logger.debug("Process deployed.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        for (int i = 0; i < 4; i++) {
            try {
                ProcessEngine processEngine =
                        ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

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

//            
//            variables.put("name", "TestUser");
//            variables.put("cardId", "4999");
//            variables.put("ticketCorrupt", true);
//            variables.put("agentInterventionRequired", true);
//            variables.put("supervisorResolved", true);
//            variables.put("chargeAdminFee", true);
//            variables.put("goodwillPaymentRequired", true);
//            variables.put("caseOwnerRequiresApproval", true);
//            variables.put("abnormalRefundTrends", true);
//            variables.put("approvalRequired", true);
//            variables.put("adminFeeChanged", true);
//            
//            variables.put("refundRejected", true);
//            
//            variables.put("emailAddress","piggy@muppets.com");
//            variables.put("leo","testVariable");

                RuntimeService runtimeService = processEngine.getRuntimeService();
                runtimeService.startProcessInstanceByKey("RefundProcess", variables);

                logger.debug("Started Process");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
