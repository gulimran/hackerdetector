package com.imrang.detector.convert;

import com.imrang.detector.domain.Action;
import com.imrang.detector.domain.ActivityLog;
import com.imrang.detector.exception.HackerDetectorException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ActivityLogConverterTest {

    private ActivityLogConverter classToTest = new ActivityLogConverter();

    @Test(expected = HackerDetectorException.class)
    public void shouldThrowExceptionForNullInput() {
        // when
        classToTest.convert(null);

        // then
        fail("should throw exception");
    }

    @Test(expected = HackerDetectorException.class)
    public void shouldThrowExceptionForEmptyStringInput() {
        // when
        classToTest.convert("");

        // then
        fail("should throw exception");
    }

    @Test(expected = HackerDetectorException.class)
    public void shouldThrowExceptionForPartialActivityLogInput() {
        // when
        classToTest.convert("80.238.9.179");

        // then
        fail("should throw exception");
    }

    @Test
    public void shouldReturnActivityLogForGivenSuccessInput() {
        // given
        ActivityLog expectedValue = new ActivityLog("80.238.9.179", 133612947, Action.SIGNIN_SUCCESS,"Dave.Branning");

        // when
        ActivityLog actualValue = classToTest.convert("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");

        // then
        assertThat(expectedValue.equals(actualValue), is(true));
    }

    @Test
    public void shouldReturnActivityLogForGivenFailureInput() {
        // given
        ActivityLog expectedValue = new ActivityLog("80.238.9.179", 133612947, Action.SIGNIN_FAILURE,"Dave.Branning");

        // when
        ActivityLog actualValue = classToTest.convert("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning");

        // then
        assertThat(expectedValue.equals(actualValue), is(true));
    }
}
