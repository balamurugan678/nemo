package com.novacroft.nemo.tfl.batch.integration_test;

import org.junit.runner.RunWith;

import com.novacroft.nemo.tfl.batch.integration_test.util.IntegrationTestConstants;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {
        "classpath:com/novacroft/nemo/tfl/batch/integration_test/ImportAdHocDistributionCubicFile.feature"}, glue = {
                IntegrationTestConstants.CUCUMBER_API_SPRING, IntegrationTestConstants.STEP_DEFINITION_PACKAGE}, format = {
                IntegrationTestConstants.CUCUMBER_FORMAT_PRETTY})
public class ImportAdHocDistributionCubicFileIntegrationTest {
}
