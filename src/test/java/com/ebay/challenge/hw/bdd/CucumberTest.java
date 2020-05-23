package com.ebay.challenge.hw.bdd;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author andrey.dodon
 */

@RunWith(CucumberReportRunner.class)
@CucumberOptions(features = "classpath:features", plugin = {"pretty",
        "json:target/cucumber-report.json"})
public class CucumberTest {

}