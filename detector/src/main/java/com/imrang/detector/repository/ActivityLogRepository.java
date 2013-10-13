package com.imrang.detector.repository;

import java.util.List;
import java.util.Map;

public interface ActivityLogRepository {

    boolean exists(String id);

    List<Long> getById(String id);

    void save(String id, long  value);

    List<Long> removeById(String id);

    Map<String, List<Long>> removeAll();
}
