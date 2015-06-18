package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.ProcessLockService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.formatDateAsISO8601;

/**
 * Implementation for service to manage process lock.
 *
 * <p>An empty "lock" file is used to indicate that a process is running.  The file is created at the start of the process and
 * removed at the end.  The process should not start if the lock file already exists.</p>
 */
@Service("processLockService")
public class ProcessLockServiceImpl implements ProcessLockService {
    @Override
    public Boolean isLocked(File lockFileDirectory, String lockFileName) {
        return getLockFile(lockFileDirectory, lockFileName).exists();
    }

    @Override
    public void acquireLock(File lockFileDirectory, String lockFileName) {
        File lockFile = getLockFile(lockFileDirectory, lockFileName);
        FileWriter fileWriter = null;
        try {
            lockFile.createNewFile();
            fileWriter = new FileWriter(lockFile);
            fileWriter.write(formatDateAsISO8601(new Date()));
            fileWriter.close();
        } catch (IOException e) {
            throw new ApplicationServiceException(PrivateError.UNABLE_TO_CREATE_LOCK_FILE.message(), e);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                // Ignore
            }
        }
    }

    @Override
    public void releaseLock(File lockFileDirectory, String lockFileName) {
        getLockFile(lockFileDirectory, lockFileName).delete();
    }

    protected File getLockFile(File lockFileDirectory, String lockFileName) {
        return new File(lockFileDirectory, lockFileName);
    }
}
