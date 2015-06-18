package com.novacroft.nemo.tfl.common.application_service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.novacroft.nemo.test_support.FileTestUtil.*;
import static org.junit.Assert.*;

/**
 * ProcessLockService unit tests
 */
public class ProcessLockServiceTest {

    @Before
    public void setUp() {
        File testFile = getTestFile2();
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @After
    public void tearDown() {
        setUp();
    }

    @Test
    public void shouldGetLockFile() {
        ProcessLockServiceImpl service = new ProcessLockServiceImpl();
        File result = service.getLockFile(getTestDirectory1(), TEST_FILE_NAME_1);
        assertEquals(getTestFile1(), result);
    }

    @Test
    public void isLockedShouldReturnTrue() {
        try {
            getTestFile2().createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        ProcessLockServiceImpl service = new ProcessLockServiceImpl();
        assertTrue(service.isLocked(getTmpDir(), TEST_FILE_NAME_2));
    }

    @Test
    public void shouldAcquireLock() {
        ProcessLockServiceImpl service = new ProcessLockServiceImpl();
        service.acquireLock(getTmpDir(), TEST_FILE_NAME_2);
        assertTrue(getTestFile2().exists());
    }

    @Test
    public void shouldReleaseLock() {
        try {
            getTestFile2().createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        ProcessLockServiceImpl service = new ProcessLockServiceImpl();
        service.releaseLock(getTmpDir(), TEST_FILE_NAME_2);
        assertFalse(getTestFile2().exists());
    }
}
