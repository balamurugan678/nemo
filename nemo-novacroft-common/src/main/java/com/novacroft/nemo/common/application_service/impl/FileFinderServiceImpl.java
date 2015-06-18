package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.FileFinderService;
import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.support.DirectoryFileFilter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Find files under a root directory that match a filter service implementation
 */
@Service("fileFinderService")
public class FileFinderServiceImpl implements FileFinderService {
    @Override
    public List<File> findFiles(File rootDirectory, FileFilter filter) {
        checkDirectoryAccessRights(rootDirectory);
        List<File> files = new ArrayList<File>();
        findFilesInDirectory(rootDirectory, files, filter);
        return files;
    }

    protected void checkDirectoryAccessRights(File directory) {
        if (!directory.exists() || !directory.isDirectory() || !directory.canRead() || !directory.canWrite() ||
                !directory.canExecute()) {
            throw new ApplicationServiceException(
                    String.format(CommonPrivateError.INVALID_DIRECTORY.message(), directory.getAbsolutePath()));
        }
    }

    protected void findFilesInDirectory(File directory, List<File> files, FileFilter filter) {
        File[] inboundFiles = directory.listFiles(filter);
        files.addAll(Arrays.asList(inboundFiles));
        for (File subDirectory : directory.listFiles(new DirectoryFileFilter())) {
            findFilesInDirectory(subDirectory, files, filter);
        }
    }
}

