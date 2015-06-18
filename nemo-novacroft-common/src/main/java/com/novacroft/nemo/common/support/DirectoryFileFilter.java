package com.novacroft.nemo.common.support;

import java.io.File;
import java.io.FileFilter;

/**
 * File Filter for directories.
 */
public class DirectoryFileFilter implements FileFilter {
    public boolean accept(File file) {
        return file.isDirectory();
    }
}
