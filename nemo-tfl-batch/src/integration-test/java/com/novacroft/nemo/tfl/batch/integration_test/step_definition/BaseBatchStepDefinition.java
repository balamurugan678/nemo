package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.integration_test.BaseStepDefinition;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseBatchStepDefinition extends BaseStepDefinition {

    @Autowired
    protected TestContext testContext;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;

    public BaseBatchStepDefinition() {
    }
}
