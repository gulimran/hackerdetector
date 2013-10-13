package com.imrang.detector.env;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static com.imrang.detector.env.Config.*;

public class DefaultConfigTest {

    private DefaultConfig classToTest = new DefaultConfig();

    @Test
    public void shouldReturnDefaultFailedLoginCount() {
        // when, then
        assertThat(classToTest.getIntValue(FAILED_LOGIN_COUNT_THRESHOLD), is(5));
    }

    @Test
    public void shouldReturnDefaultFailedLoginInterval() {
        // when, then
        assertThat(classToTest.getIntValue(FAILED_LOGIN_TIME_THRESHOLD), is(300000));
    }


}
