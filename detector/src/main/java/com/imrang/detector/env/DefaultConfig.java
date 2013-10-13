package com.imrang.detector.env;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfig implements Config {

    private static final String NUMBER_OF_FAILED_LOGIN = "5";
    private static final String INTERVAL_OF_FAILED_LOGIN = "300000";  // 5 mins

    static final Map<String, String> CONFIGURATION = new HashMap<String, String>();

    public DefaultConfig() {
        CONFIGURATION.put(FAILED_LOGIN_COUNT_THRESHOLD, NUMBER_OF_FAILED_LOGIN);
        CONFIGURATION.put(FAILED_LOGIN_TIME_THRESHOLD, INTERVAL_OF_FAILED_LOGIN);
    }

    public int getIntValue(String property) {
        return Integer.parseInt(CONFIGURATION.get(property));
    }
}
