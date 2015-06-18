package com.novacroft.nemo.tfl.common.application_service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.novacroft.nemo.common.application_service.ChecksumService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

public class CubicChecksumVerificationServiceImplTest {
    private static final Integer MOCK_CHECK_SUM = 10;
    private static final String CHECK_SUM_FILE_CONTENT = "test long contents";
    private static final String ONT_CHAR_FILE_CONTENT = "1";
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private File validChecksumFile;
    private File oneCharFile;
    
    private CubicChecksumVerificationServiceImpl service;
    private ChecksumService mockChecksumService;
    
    @Before
    public void setUp() {
        validChecksumFile = createFile(CHECK_SUM_FILE_CONTENT);
        oneCharFile = createFile(ONT_CHAR_FILE_CONTENT);
        
        service = new CubicChecksumVerificationServiceImpl();
        mockChecksumService = mock(ChecksumService.class);
        service.crc16ChecksumService = mockChecksumService;
        
        when(mockChecksumService.calculateChecksum(any(byte[].class), any(Integer.class)))
            .thenReturn(MOCK_CHECK_SUM);
    }
    
    @Test(expected=AssertionError.class)
    public void isChecksumVerifiedShouldThrowAssertErrorIfFileIsNull() {
        service.isChecksumVerified(null);
    }
    
    @Test(expected=ApplicationServiceException.class)
    public void isChecksumVerifiedShouldThrowExceptionIfContentTooShort() {
        service.isChecksumVerified(oneCharFile);
    }
    
    @Test
    public void isChecksumVerifiedShouldReturnTrue() {
        assertTrue(service.isChecksumVerified(validChecksumFile));
    }
    
    private File createFile(String contents) {
        File file = null;
        try {
            file = testFolder.newFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            file = null;
        }
        return file;
    }
}
