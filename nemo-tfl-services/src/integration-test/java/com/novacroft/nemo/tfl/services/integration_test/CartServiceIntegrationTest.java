package com.novacroft.nemo.tfl.services.integration_test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:com/novacroft/nemo/tfl/services/integration_test/CartService.feature" }, glue = {
        "cucumber.api.spring", "com.novacroft.nemo.tfl.services.integration_test.step_definition"}, format = {"pretty"})
public class CartServiceIntegrationTest {
}
