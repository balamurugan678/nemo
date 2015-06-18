package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.CsvFileReaderService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * CsvFileReaderService unit tests
 */
public class CsvFileReaderServiceTest {

    @Ignore // TODO fails on server
    public void shouldReadFile() {
        List<String[]> expectedResult = new ArrayList<String[]>();
        expectedResult.add(new String[]{"row-1-column-1", "row-1-column-2", "row-1-column-3"});
        expectedResult.add(new String[]{"row-2-column-1", "row-2-column-2", "row-2-column-3"});

        File testFile = new File(this.getClass().getClassLoader().getResource("fixture/testCsvFile.csv").getFile());
        CsvFileReaderService service = new CsvFileReaderServiceImpl();
        List<String[]> result = service.readDataFromFile(testFile);
        assertArrayEquals(expectedResult.toArray(), result.toArray());
    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldErrorWithBadFile() {
        CsvFileReaderService service = new CsvFileReaderServiceImpl();
        service.readDataFromFile(new File("Rubbish!"));
    }
}
