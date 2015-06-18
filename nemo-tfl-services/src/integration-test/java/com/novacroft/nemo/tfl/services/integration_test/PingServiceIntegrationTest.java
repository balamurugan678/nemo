package com.novacroft.nemo.tfl.services.integration_test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:com/novacroft/nemo/tfl/services/integration_test/PingService.feature"}, glue = {
        "cucumber.api.spring", "com.novacroft.nemo.tfl.services.integration_test.step_definition"}, format = {"pretty"})
public class PingServiceIntegrationTest {
}
