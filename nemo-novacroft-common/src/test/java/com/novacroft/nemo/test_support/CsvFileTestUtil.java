package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

/**
 * Fixtures and utilities for use in tests that work with CSV files
 */
public final class CsvFileTestUtil {

    private CsvFileTestUtil() {
    }

    public static final String[] line1 = { "record1", "element 1", "element 2", "element 3", "element 4", "element 5" };
    public static final String[] line2 = { "record2", "element 1", "element 2", "element 3", "element 4", "element 5" };
    public static final String[] line3 = { "record3", "element 1", "element 2", "element 3", "element 4", "element 5" };
    public static final String[] checksum1 = { "CHKSUM1" };
    public static final String[] checksum2 = { "CHKSUM2" };

    public static List<String[]> buildFileDataNoElementsOrChecksum() {
        List<String[]> data = new ArrayList<>();
        return data;
    }

    public static List<String[]> buildFileDataNoElementsWithSingleLineChecksum() {
        List<String[]> data = new ArrayList<>();
        data.add(checksum1);
        return data;
    }

    public static List<String[]> buildFileDataNoElementsWithTwoLineChecksum() {
        List<String[]> data = new ArrayList<>();
        data.add(checksum1);
        data.add(checksum2);
        return data;
    }

    public static List<String[]> buildFileDataWithSingleLineChecksum() {
        List<String[]> data = new ArrayList<>();
        data.add(line1);
        data.add(line2);
        data.add(line3);
        data.add(checksum1);
        return data;
    }

    public static List<String[]> buildFileDataWithTwoLineChecksum() {
        List<String[]> data = new ArrayList<>();
        data.add(line1);
        data.add(line2);
        data.add(line3);
        data.add(checksum1);
        data.add(checksum2);
        return data;
    }

}
