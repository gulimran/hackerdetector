package com.imrang.detector.convert;

import com.imrang.detector.domain.Action;
import com.imrang.detector.domain.ActivityLog;
import com.imrang.detector.exception.HackerDetectorException;

public class ActivityLogConverter implements Converter<ActivityLog, String> {

    public ActivityLog convert(String line) {
        if (line == null || line.isEmpty()) {
            throw new HackerDetectorException("Invalid activity log line");
        }

        final String[] tokens = line.split(",");

        if (tokens.length != 4) {
            throw new HackerDetectorException("Incomplete activity log line");
        }

        return new ActivityLog(tokens[0], Long.parseLong(tokens[1]), Action.valueOf(tokens[2]), tokens[3]);
    }
}
