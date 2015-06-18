package com.novacroft.nemo.common.application_service;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * Find files under a root directory that match a filter service specification
 */
public interface FileFinderService {
    List<File> findFiles(File rootDirectory, FileFilter filter);
}
