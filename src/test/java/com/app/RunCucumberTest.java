package com.app;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/com/app",
    plugin = {"pretty"},
    glue = "com.app"
)
public class RunCucumberTest {
}
