package com.novacroft.nemo.tfl.services.integration_test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

import com.novacroft.nemo.tfl.services.integration_test.util.IntegrationTestConstants;

@RunWith(Cucumber.class)
@CucumberOptions(features = { IntegrationTestConstants.STATION_SERVICE_FEATURE }, glue = { IntegrationTestConstants.CUCUMBER_API_SPRING,
                IntegrationTestConstants.STEP_DEFINITION_PACKAGE }, format = { IntegrationTestConstants.CUCUMBER_FORMAT_PRETTY })
public class StationServiceIntegrationTest {
}
