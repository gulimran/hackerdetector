package com.imrang.detector.service;

import com.imrang.detector.repository.ActivityLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActivityTrackerTest {

    private static final String IP_ADDRESS = "existing-ip-address";
    private static final long time = 1366819087;

    @InjectMocks
    private ActivityTracker classToTest;

    @Mock
    private ActivityLogRepository repository;

    @Test
    public void shouldReturnFalseForNonExistingIpAddress() {
        // when, then
        assertThat(classToTest.has("non-existing-ip-address"), is(false));
    }

    @Test
    public void shouldReturnTrueForExistingIpAddress() {
        // given
        given(repository.exists(IP_ADDRESS)).willReturn(true);

        // when, then
        assertThat(classToTest.has(IP_ADDRESS), is(true));
    }

    @Test
    public void shouldAddExistingToRepository() {
        // given
        given(repository.exists(IP_ADDRESS)).willReturn(true);

        // when
        classToTest.add(IP_ADDRESS, time);

        // then
        verify(repository).getById(eq(IP_ADDRESS));
    }

    @Test
    public void shouldAddNonExistingToRepository() {
        // given
        given(repository.exists(IP_ADDRESS)).willReturn(false);

        // when
        classToTest.add(IP_ADDRESS, time);

        // then
        verify(repository).save(eq(IP_ADDRESS), eq(time));
    }

    @Test
    public void shouldClearAllLogsForGivenIpAddress() {
        // when
        classToTest.clearAllLogsFor(IP_ADDRESS);

        // then
        verify(repository).removeById(eq(IP_ADDRESS));
    }

    @Test
    public void shouldClearAllLogs() {
        // when
        classToTest.clearAllLogs();

        // then
        verify(repository).removeAll();
    }

    @Test
    public void shouldReturnTrueForGivenIpAddressWithGivenCountAndInterval() {
        // given
        List<Long> activityTimes = newArrayList(1366819087L, 1366819187L, 1366819287L);
        given(repository.getById(IP_ADDRESS)).willReturn(activityTimes);

        // when, then
        assertThat(classToTest.has(IP_ADDRESS, 3, 500), is(true));
    }
}
