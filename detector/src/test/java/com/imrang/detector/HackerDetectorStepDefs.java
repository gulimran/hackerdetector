package com.imrang.detector;

import com.imrang.detector.convert.ActivityLogConverter;
import com.imrang.detector.env.DefaultConfig;
import com.imrang.detector.repository.MemoryActivityLogRepository;
import com.imrang.detector.service.ActivityTracker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class HackerDetectorStepDefs {

    private HackerIPAddressDetector detector;
    private List<String> lines = newArrayList();

    public HackerDetectorStepDefs() {
        ActivityTracker service = new ActivityTracker();
        service.setRepository(new MemoryActivityLogRepository());

        detector = new HackerIPAddressDetector();
        detector.setConverter(new ActivityLogConverter());
        detector.setActivityLogService(service);
    }

    @Given("^less than (\\d+) failed login attempts are allowed within (\\d+) minutes$")
    public void setConfigurationParameters(int  failedAttemptCount, int timeInterval) throws Throwable {
        detector.setConfiguration(new DefaultConfig());
    }

    @When("^(\\d+) failed activity logs are recorded for the same IP address$")
    public void numberOfFailedLoginAttemptsMade(int logCount) throws Throwable {
        for (int i=0; i<logCount; i++) {
            detector.parseLine("80.238.9.179," + (133612947 + i) + ",SIGNIN_FAILURE,Dave.Branning");
        }
    }

    @Then("^HackerDetector returns that suspicious IP address$")
    public void shouldReturnSuspiciousIPAddress() throws Throwable {
        String ip = detector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning");
        assertThat(ip, is("80.238.9.179"));
    }

    @Then("^HackerDetector returns no IP address$")
    public void shouldReturnNullIPAddress() throws Throwable {
        String ip = detector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning");
        assertNull(ip);
    }
}
