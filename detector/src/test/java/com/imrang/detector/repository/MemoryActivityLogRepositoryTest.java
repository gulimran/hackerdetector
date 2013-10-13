package com.imrang.detector.repository;

import com.imrang.detector.exception.ActivityLogExistsException;
import com.imrang.detector.exception.ActivityLogNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MemoryActivityLogRepositoryTest {

    private static final String ID = "192.168.192.1";
    private static final List<Long> VALUE = newArrayList(1366819659L, 1366820659L, 1366821659L, 1366822659L, 1366823659L, 1366824659L);
    Map<String, List<Long>> store = newHashMap();

    private MemoryActivityLogRepository classToTest;

    @Before
    public void setup() {
        store.put(ID, VALUE);
        classToTest = new MemoryActivityLogRepository();
        classToTest.setStore(store);

    }

    @Test
    public void shouldReturnTrueForExistingId() {
        // when, then
        assertThat(classToTest.exists(ID), is(true));
    }

    @Test
    public void shouldReturnListOfLongsForExistingId() {
        // when, then
        assertEquals(VALUE, classToTest.getById(ID));
    }

    @Test(expected = ActivityLogNotFoundException.class)
    public void shouldThrowExceptionForNonExistingId() {
        // when, then
        classToTest.getById("non-existing-id");
    }

    @Test(expected = ActivityLogExistsException.class)
    public void shouldThrowExceptionForExistingIdOnSave() {
        // when, then
        classToTest.save(ID, 1366819859L);
    }

    @Test
    public void shouldSaveNewId() {
        // when
        classToTest.save("new-id", 1366819959L);

        // then
        assertThat(store.size(), is(2));
    }

    @Test
    public void shouldRemoveValueForExistingId() {
        // when, then
         assertEquals(VALUE, classToTest.removeById(ID));
    }

    @Test
    public void shouldRemoveAll() {
        // when
        classToTest.removeAll();

        // then
        assertThat(store.size(), is(0));
    }
}
