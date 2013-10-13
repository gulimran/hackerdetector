package com.imrang.detector.domain;

import com.imrang.detector.convert.ActivityLogConverter;
import com.imrang.detector.convert.Converter;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ActivityLogTest {

    private static Converter<ActivityLog, String> converter = new ActivityLogConverter();

    @Test
    public void shouldFulfillEqualsHashCodeContract() {
        ActivityLog activityLog = new ActivityLog("80.238.9.179", 133612947, Action.SIGNIN_SUCCESS,"Dave.Branning");

        EqualsVerifier.forClass(ActivityLog.class)
                .withPrefabValues(ActivityLog.class, activityLog, new ActivityLog())
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
                .verify();
    }

    public static ActivityLog buildActivityLog(String ip, Long time, Action action, String userName) {
        return new ActivityLog(ip, time, action,userName);
    }

    public static ActivityLog buildActivityLog(String line) {
        return converter.convert(line);
    }
}
