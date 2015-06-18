package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.UserService;
import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.domain.User;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.RandomApprovalRulesDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowRandomApprovalSampleService;
import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service(value = "workflowRandomApprovalSampleService")
public class WorkflowRandomApprovalSampleServiceImpl implements WorkflowRandomApprovalSampleService{

    static final Logger logger = LoggerFactory.getLogger(WorkflowRandomApprovalSampleServiceImpl.class);
        
    private static final String DEFAULT = "DEFAULT";
    private static final int RANDOM_PERCENTAGE_SEED = 101;
    private static final Integer DEFAULT_BASE_VALUE = 0;
    private static final Integer DEFAULT_CEILING_VALUE = 100000000;
    private final String FLAGGED_FOR_RANDOM_APPROVAL_KEY = "flaggedForRandomApproval";
    protected String FLAGGED_FOR_RANDOM_APPROVAL_MESSAGE;

    protected Integer oyster_goodwill_percentage;
    protected Integer oyster_other_percentage;

    protected List<RandomApprovalSampleThresholdRuleDTO> randomSampleRulesList = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();
    protected List<RandomApprovalSampleThresholdRuleDTO> exampleTeamAndOrganisationList = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();
    protected List<RandomApprovalSampleThresholdRuleDTO> randomApprovalSampleThresholdsList = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();

    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected RandomApprovalRulesDataService rulesService;
    @Autowired
    protected ContentDataService content;
    
    protected Random rand;
    protected Integer rulePercentageThreshold;
    
    WorkflowRandomApprovalSampleServiceImpl(){
    }

    protected void getRulesForLoggedInUser(WorkflowItemDTO workflow) {
        randomSampleRulesList.clear();
        randomApprovalSampleThresholdsList.clear();
        rulePercentageThreshold = 0;
        rand = new Random();
        
        loadRulesForUser();
        
        if(randomApprovalSampleThresholdsList.size() > 0){
    
            randomSampleRulesList.addAll(randomApprovalSampleThresholdsList);
            for (RandomApprovalSampleThresholdRuleDTO rule : randomSampleRulesList) {
                rule.setMinValueThreshold(rule.getMinValueThreshold() == null ? DEFAULT_BASE_VALUE: rule.getMinValueThreshold());
                rule.setMaxValueThreshold(rule.getMaxValueThreshold() == null ? DEFAULT_CEILING_VALUE: rule.getMaxValueThreshold());
            }
        } else {
            logger.warn("No random sample rules found for an organisation or team ");
        }
        
        FLAGGED_FOR_RANDOM_APPROVAL_MESSAGE = content.findByLocaleAndCode(FLAGGED_FOR_RANDOM_APPROVAL_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
    }

    private void loadRulesForUser() {
        String loggedInUsername = securityService.getLoggedInUsername();
        if(StringUtils.isNotEmpty(loggedInUsername)){
            User user = userService.findUserById(loggedInUsername);
            exampleTeamAndOrganisationList = rulesService.findOrganisationAndTeamById(user.getId());
            
            findRulesByOrganisationAndTeam();
            if (randomApprovalSampleThresholdsList.size() < 1){
                findRulesByOrganisation();
            }
        }

        if (randomApprovalSampleThresholdsList.size() < 1) {
            logger.warn("No random sample rules found for an organisation or team - looking for generic rules");
            findDefaultRules();
        }
    }

    protected void findDefaultRules() {
        randomApprovalSampleThresholdsList = rulesService.findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(DEFAULT,DEFAULT);
    }

    protected void findRulesByOrganisation() {
        randomApprovalSampleThresholdsList = rulesService.findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(exampleTeamAndOrganisationList.get(0).getOrganisation(), DEFAULT);
    }

    protected void findRulesByOrganisationAndTeam() {
        randomApprovalSampleThresholdsList = rulesService.findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(exampleTeamAndOrganisationList.get(0).getOrganisation(), exampleTeamAndOrganisationList.get(0).getTeam());
    }
    
    @Override
    public List<String> processForRandomSampleFlagging(WorkflowItemDTO workflow){
        getRulesForLoggedInUser(workflow);
        assert workflow.getRefundDetails() != null;
        assert workflow.getRefundDetails().getRefundDepartment() != null;
        assert (workflow.getRefundDetails().getRefundScenario() != null || workflow.getRefundDetails().getRefundBasis() != null);
        
        List<String> reason = new ArrayList<String>();
        
        findMatchingRulePercentage(workflow);
        int randomNum = rand.nextInt(RANDOM_PERCENTAGE_SEED);
        
        if (randomNum  <= rulePercentageThreshold ){
            reason.add(FLAGGED_FOR_RANDOM_APPROVAL_MESSAGE);
        }
        
        
        return reason;
    }

    private void findMatchingRulePercentage(WorkflowItemDTO workflow) {
        Long totalRefundPrice = workflow.getRefundDetails().getTotalRefundAmount();
        int goodwillRefundTotal = getGoodwillTotal(workflow);

        for (RandomApprovalSampleThresholdRuleDTO rule : randomSampleRulesList) {
            if (rule.getDepartment().equals(workflow.getRefundDetails().getRefundDepartment().name()))
            {
                if (findMatchingRulesByBasis(workflow, rule)
                                || findMatchingRulesByScenario(workflow.getRefundDetails().getRefundBasis(),
                                                getRelevantAmount(rule.getScenario(), totalRefundPrice, goodwillRefundTotal), rule)) {
                    rulePercentageThreshold = (rule.getPercentage() > rulePercentageThreshold) ? rule.getPercentage() : rulePercentageThreshold;
                }
            }
        }
    }

    private int getGoodwillTotal(WorkflowItemDTO workflow) {
        int goodwillRefundTotal = 0;
        for (GoodwillPaymentItemCmd goodwillItem : workflow.getRefundDetails().getGoodwillItems()) {
            goodwillRefundTotal += Integer.valueOf(goodwillItem.getPrice());
        }
        return goodwillRefundTotal;
    }

    private Long getRelevantAmount(String scenario, Long totalRefundPrice, int goodwillRefundTotal) {
        return (RefundScenarioEnum.GOODWILL.code().equalsIgnoreCase(scenario)) ? new Long(goodwillRefundTotal) : totalRefundPrice;
    }

    private boolean findMatchingRulesByScenario(RefundCalculationBasis refundBasis, Long amount, RandomApprovalSampleThresholdRuleDTO rule) {
        return (refundBasis != RefundCalculationBasis.SPECIAL) && (rule.getScenario() != null && amount <= rule.getMaxValueThreshold())
                        && (rule.getMinValueThreshold() <= amount);
    }

    private boolean findMatchingRulesByBasis(WorkflowItemDTO workflow, RandomApprovalSampleThresholdRuleDTO rule) {
        return (rule.getScenario() == null) && (rule.getBasis() != null && workflow.getRefundDetails().getRefundBasis() != null)
                        && (rule.getBasis().equals(workflow.getRefundDetails().getRefundBasis().code()));

    }
}
   
