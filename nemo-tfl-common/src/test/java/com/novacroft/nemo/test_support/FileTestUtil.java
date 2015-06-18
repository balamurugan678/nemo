package com.novacroft.nemo.test_support;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fixtures and utilities for unit tests that use files
 */
public final class FileTestUtil {
    public static final String DUMMY_RESOURCE_NAME = "dummy-resource.txt";

    public static final String TEST_FILE_NAME_1 = "test-file-1.dat";
    public static final String TEST_FILE_NAME_2 = "test-file-2.dat";
    public static final String EMPTY_FILE_NAME_3 = "test-emptyFile-3.dat";
    public static final String TEST_DIRECTORY_NAME_1 = "/test-directory-1/";
    public static final String TEST_FILE_SUFFIX_1 = "abc";
    public static final String TEST_FILE_NAME_3 = " File-importAutoLoadChangesCubicFileJob.dat";
    public static final String TEST_FILE_NAME_4 = " File-ImportAdHocDistributionCubicFileJob.dat";
    public static final String TEST_FILE_NAME_5 = " File-importAutoLoadsPerformedCubicFileJob.dat";
    
    public static final Long TEST_JOB_ID_1 = 1L;
    public static final Long TEST_JOB_ID_3 = 1L;
    public static final Long TEST_JOB_ID_4 = 2L;
    public static final Long TEST_JOB_ID_5 = 3L;

    public static final String TEST_DATA_VAL1 = "First-Index-Test-Val";
    public static final String TEST_DATA_VAL2 = "Second-Index-Test-Val";
    public static final String TEST_DATA_VAL3 = "Third-Index-Test-Val";
    public static final String TEST_DATA_VAL4 = "Fourth-Index-Test-Val";
    public static final String JOB_NAME = "File-importAutoLoadsPerformedCubicFileJob";
    
    public static File getTestFile1() {
        return new File(TEST_DIRECTORY_NAME_1, TEST_FILE_NAME_1);
    }

    public static File getTestFile2() {
        return new File(getTmpDir(), TEST_FILE_NAME_2);
    }

    public static String[] getTestStringArrayVal1() {
        return new String[] {TEST_DATA_VAL1, TEST_DATA_VAL2, TEST_DATA_VAL3, TEST_DATA_VAL4};
    }

    public static String[] getTestStringArrayVal2() {
        return new String[0];
    }
    
    public static String[] getTestStringArrayVal3() {
    	return new String[] {TEST_DATA_VAL1, TEST_DATA_VAL2};
    }

    public static List<File> getTestFileList1() {
        List<File> files = new ArrayList<File>();
        files.add(getTestFile1());
        return files;
    }

    public static File getTestDirectory1() {
        return new File(TEST_DIRECTORY_NAME_1);
    }

    public static File getTmpDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    public static boolean isTmpDirOk() {
        File tmpDir = getTmpDir();
        return (tmpDir.exists() && tmpDir.isDirectory());
    }
    
    public static List<String[]> getTestStringArrayList1() {
        List<String[]> strArrayVals = new ArrayList<String[]>();
        strArrayVals.add(getTestStringArrayVal1());
        return strArrayVals;
    }
    public static List<String[]> getTestStringArrayList2() {
        List<String[]> strArrayVals = new ArrayList<String[]>();
        strArrayVals.add(getTestStringArrayVal3());
        return strArrayVals;
    }

    public static File getTestFile3() {
        return new File(getTmpDir(), EMPTY_FILE_NAME_3);
    }

    private FileTestUtil() {
    }
}
