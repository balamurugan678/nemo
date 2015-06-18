package com.novacroft.nemo.tfl.batch.file_filter.impl;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter import files base.
 */
public abstract class BaseImportFileFilter implements FileFilter {

    protected abstract String getFileNamePattern();

    public boolean accept(File file) {
        assert (file != null);
        return matches(file);
    }

    public boolean matches(File file) {
        return file.getName().matches(getFileNamePattern());
    }
}
