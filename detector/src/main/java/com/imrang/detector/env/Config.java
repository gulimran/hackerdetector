package com.imrang.detector.env;

public interface Config {

    static final String FAILED_LOGIN_COUNT_THRESHOLD = "failed.login.count.threshold";
    static final String FAILED_LOGIN_TIME_THRESHOLD = "failed.login.time.threshold";

    int getIntValue(String property);
}
