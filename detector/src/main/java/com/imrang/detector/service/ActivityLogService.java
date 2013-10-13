package com.imrang.detector.service;

public interface ActivityLogService {

    boolean has(String ipAddress);

    boolean has(String ipAddress, int count, long interval);

    void add(String ipAddress, long time);

    void clearAllLogsFor(String ipAddress);

    void clearAllLogs();

}
