package com.novacroft.nemo.tfl.common.application_service;

import java.io.File;

/**
 * Specification for service to manage process lock.
 *
 * <p>An empty "lock" file is used to indicate that a process is running.  The file is created at the start of the process and
 * removed at the end.  The process should not start if the lock file already exists.</p>
 */
public interface ProcessLockService {
    Boolean isLocked(File lockFileDirectory, String lockFileName);

    void acquireLock(File lockFileDirectory, String lockFileName);

    void releaseLock(File lockFileDirectory, String lockFileName);
}
