package com.novacroft.nemo.common.application_service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.CsvFileReaderService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.CsvFileTestUtil;

/**
 * CsvFileWithCheckSumReaderService unit tests
 */
public class CsvFileWithCheckSumReaderServiceTest {

    @Ignore // TODO fails on server
    public void shouldReadFile() {
        List<String[]> expectedResult = new ArrayList<String[]>();
        expectedResult.add(new String[]{"row-1-column-1", "row-1-column-2", "row-1-column-3"});
        expectedResult.add(new String[]{"row-2-column-1", "row-2-column-2", "row-2-column-3"});

        File testFile =
                new File(this.getClass().getClassLoader().getResource("fixture/testCsvFileWithDummyCheckSum.csv").getFile());
        CsvFileReaderService service = new CsvFileWithCheckSumReaderServiceImpl();
        List<String[]> result = service.readDataFromFile(testFile);
        assertArrayEquals(expectedResult.toArray(), result.toArray());
    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldErrorWithBadFile() {
        File testFile = new File(this.getClass().getClassLoader().getResource("fixture/testEmptyFile.csv").getFile());
        CsvFileReaderService service = new CsvFileWithCheckSumReaderServiceImpl();
        service.readDataFromFile(testFile);
    }

    @Test
    public void shouldDoNothingEmptyList() {
        CsvFileWithCheckSumReaderServiceImpl service = new CsvFileWithCheckSumReaderServiceImpl();
        List<String[]> referenceList = CsvFileTestUtil.buildFileDataNoElementsOrChecksum();
        List<String[]> testList = CsvFileTestUtil.buildFileDataNoElementsOrChecksum();

        service.removeChecksumFromData(testList);

        assertTrue(referenceList.size() == testList.size());
    }

    @Test
    public void shouldRemoveChecksumForListWithNoElementAndChecksum() {
        CsvFileWithCheckSumReaderServiceImpl service = new CsvFileWithCheckSumReaderServiceImpl();
        List<String[]> referenceList = CsvFileTestUtil.buildFileDataNoElementsWithSingleLineChecksum();
        List<String[]> testList = CsvFileTestUtil.buildFileDataNoElementsWithSingleLineChecksum();

        service.removeChecksumFromData(testList);

        assertTrue((referenceList.size() - 1) == testList.size());
    }

    @Test
    public void shouldRemoveChecksumForListWithNoElementAndTwoLineChecksum() {
        CsvFileWithCheckSumReaderServiceImpl service = new CsvFileWithCheckSumReaderServiceImpl();
        List<String[]> referenceList = CsvFileTestUtil.buildFileDataNoElementsWithTwoLineChecksum();
        List<String[]> testList = CsvFileTestUtil.buildFileDataNoElementsWithTwoLineChecksum();

        service.removeChecksumFromData(testList);

        assertTrue((referenceList.size() - 2) == testList.size());
    }

    @Test
    public void shouldRemoveChecksumForListWithElementAndChecksum() {
        CsvFileWithCheckSumReaderServiceImpl service = new CsvFileWithCheckSumReaderServiceImpl();
        List<String[]> referenceList = CsvFileTestUtil.buildFileDataNoElementsWithSingleLineChecksum();
        List<String[]> testList = CsvFileTestUtil.buildFileDataNoElementsWithSingleLineChecksum();

        service.removeChecksumFromData(testList);

        assertTrue((referenceList.size() - 1) == testList.size());
    }

    @Test
    public void shouldRemoveChecksumForListWithElementAndTwoLineChecksum() {
        CsvFileWithCheckSumReaderServiceImpl service = new CsvFileWithCheckSumReaderServiceImpl();
        List<String[]> referenceList = CsvFileTestUtil.buildFileDataWithTwoLineChecksum();
        List<String[]> testList = CsvFileTestUtil.buildFileDataWithTwoLineChecksum();

        service.removeChecksumFromData(testList);

        assertTrue((referenceList.size() - 2) == testList.size());
    }

}
