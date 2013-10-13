package com.imrang.detector.service;

import com.imrang.detector.exception.ActivityLogNotFoundException;
import com.imrang.detector.repository.ActivityLogRepository;

import java.util.List;

import static com.imrang.detector.util.DateUtil.getTimeDifference;

public class ActivityTracker implements ActivityLogService {

    private ActivityLogRepository repository;

    public boolean has(String ipAddress) {
        return repository.exists(ipAddress);
    }

    public boolean has(String ipAddress, int count, long interval) {
        try {
            List<Long> activityTimes = repository.getById(ipAddress);

            if (activityTimes.size() < count) {
                return false;
            }

            long mostRecentActivityTime = activityTimes.get(activityTimes.size()-1);
            long initialActivityTime = activityTimes.get(activityTimes.size() - count);
            long activityInterval = getTimeDifference(mostRecentActivityTime, initialActivityTime);

            if (activityInterval < interval) {
                return true;
            }
        }
        catch (ActivityLogNotFoundException e) {
        }

        return false;
    }

    public void add(String ipAddress, long time) {
        if (repository.exists(ipAddress)) {
            repository.getById(ipAddress).add(time);
        }
        else {
            repository.save(ipAddress, time);
        }

    }

    public void clearAllLogsFor(String ipAddress) {
        repository.removeById(ipAddress);
    }

    public void clearAllLogs() {
        repository.removeAll();
    }

    public void setRepository(ActivityLogRepository repository) {
        this.repository = repository;
    }
}
