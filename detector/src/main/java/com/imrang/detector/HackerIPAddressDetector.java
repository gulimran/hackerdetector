package com.imrang.detector;

import com.imrang.detector.convert.Converter;
import com.imrang.detector.domain.ActivityLog;
import com.imrang.detector.env.Config;
import com.imrang.detector.service.ActivityLogService;

import static com.imrang.detector.env.Config.FAILED_LOGIN_COUNT_THRESHOLD;
import static com.imrang.detector.env.Config.FAILED_LOGIN_TIME_THRESHOLD;

public class HackerIPAddressDetector implements HackerDetector {

    private Config configuration;
    private Converter<ActivityLog, String> converter;
    private ActivityLogService activityLogService;

    private int failedLoginAttemptsThreshold;
    private int failedLoginIntervalThreshold;

    public String parseLine(String line) {
        ActivityLog activityLog = converter.convert(line);

        String ipAddress = activityLog.getIp();

        switch (activityLog.getAction()) {
            case SIGNIN_SUCCESS:
                if (activityLogService.has(ipAddress)) {
                    activityLogService.clearAllLogsFor(ipAddress);
                }
                break;
            case SIGNIN_FAILURE:
                activityLogService.add(ipAddress, activityLog.getTime());

                if (activityLogService.has(ipAddress, failedLoginAttemptsThreshold, failedLoginIntervalThreshold)) {
                    return ipAddress;
                }
                break;
        }

        return null;
    }

    public void setConverter(Converter<ActivityLog, String> converter) {
        this.converter = converter;
    }

    public void setActivityLogService(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    public void setConfiguration(Config configuration) {
        this.configuration = configuration;
        this.failedLoginAttemptsThreshold = configuration.getIntValue(FAILED_LOGIN_COUNT_THRESHOLD);
        this.failedLoginIntervalThreshold = configuration.getIntValue(FAILED_LOGIN_TIME_THRESHOLD);
    }

    @Override
    public void finalize() throws Throwable {
        activityLogService.clearAllLogs();
        super.finalize();
    }
}
