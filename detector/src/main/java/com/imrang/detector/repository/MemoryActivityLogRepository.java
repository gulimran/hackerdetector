package com.imrang.detector.repository;

import com.imrang.detector.exception.ActivityLogExistsException;
import com.imrang.detector.exception.ActivityLogNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryActivityLogRepository implements ActivityLogRepository {

    private Map<String, List<Long>> store = new ConcurrentHashMap<String, List<Long>>(50000, 0.9F);

    public boolean exists(String id) {
        return store.containsKey(id);
    }

    public List<Long> getById(String id) {
        if (exists(id)) {
            return store.get(id);
        }

        throw new ActivityLogNotFoundException("Not found: " + id);
    }

    public void save(String id, long value) {
        if (exists(id)) {
            throw new ActivityLogExistsException("Already exists: " + id);
        }

        List<Long> values = new ArrayList<Long>();
        values.add(value);

        store.put(id, values);
    }

    public List<Long> removeById(String id) {
        return store.remove(id);
    }

    public Map<String, List<Long>> removeAll() {
        for (String id : store.keySet()) {
            store.remove(id);
        }

        return null;
    }

    /*
     * For Unit testing
     */
    void setStore(Map<String, List<Long>> store) {
        this.store = store;
    }
}
