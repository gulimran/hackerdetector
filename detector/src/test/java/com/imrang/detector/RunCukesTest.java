package com.imrang.detector;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(
        format = {"pretty", "html:target/cucumber-html-report", "json-pretty:target/cucumber-json-report.json"},
        tags = "~@wip")
public class RunCukesTest { }
