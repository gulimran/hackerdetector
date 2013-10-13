package com.imrang.detector;

import com.imrang.detector.convert.Converter;
import com.imrang.detector.domain.ActivityLog;
import com.imrang.detector.env.Config;
import com.imrang.detector.service.ActivityLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.imrang.detector.domain.ActivityLogTest.buildActivityLog;
import static com.imrang.detector.env.Config.FAILED_LOGIN_COUNT_THRESHOLD;
import static com.imrang.detector.env.Config.FAILED_LOGIN_TIME_THRESHOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HackerIPAddressDetectorTest {

    private static final String TEST_IP_ADDRESS = "80.238.9.179";
    private static final String SUCCESS_ACTIVITY_LOG = "80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning";
    private static final String FAILURE_ACTIVITY_LOG = "80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning";

    @InjectMocks
    private HackerIPAddressDetector classToTest;

    @Mock
    private Converter<ActivityLog, String> converter;

    @Mock
    private ActivityLogService activityLogService;

    @Mock
    private Config configuration;

    @Before
    public void setup() {
        given(converter.convert(SUCCESS_ACTIVITY_LOG)).willReturn(buildActivityLog(SUCCESS_ACTIVITY_LOG));
        given(converter.convert(FAILURE_ACTIVITY_LOG)).willReturn(buildActivityLog(FAILURE_ACTIVITY_LOG));
    }


    @Test
    public void shouldReturnNullOnSigninSuccess() {
        // when, then
        assertNull(classToTest.parseLine(SUCCESS_ACTIVITY_LOG));
    }

    @Test
    public void shouldClearAllActivityLogsOnSigninSuccess() {
        // given
        given(activityLogService.has(TEST_IP_ADDRESS)).willReturn(true);

        // when
        classToTest.parseLine(SUCCESS_ACTIVITY_LOG);

        // then
        verify(activityLogService).clearAllLogsFor(eq(TEST_IP_ADDRESS));
    }

    @Test
    public void shouldAddToActivityLogOnSigninFailure() {
        // when
        classToTest.parseLine(FAILURE_ACTIVITY_LOG);

        // then
        verify(activityLogService).add(eq(TEST_IP_ADDRESS), any(Long.class));
    }

    @Test
    public void shouldReturnIpAddressOnFiveSigninFailureInFiveMinutes() {
        // given
        given(configuration.getIntValue(FAILED_LOGIN_COUNT_THRESHOLD)).willReturn(5);
        given(configuration.getIntValue(FAILED_LOGIN_TIME_THRESHOLD)).willReturn(300000);
        given(activityLogService.has(TEST_IP_ADDRESS, 5, 300000)).willReturn(true);
        classToTest.setConfiguration(configuration);

        // when, then
        assertThat(classToTest.parseLine(FAILURE_ACTIVITY_LOG), is(TEST_IP_ADDRESS));
    }
}
