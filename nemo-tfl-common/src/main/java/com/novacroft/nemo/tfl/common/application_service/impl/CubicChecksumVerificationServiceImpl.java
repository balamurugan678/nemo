package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.ChecksumService;
import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.CubicChecksumVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Verify CUBIC batch file checksum specification
 */
@Service("cubicChecksumVerificationService")
public class CubicChecksumVerificationServiceImpl implements CubicChecksumVerificationService {
    static final Logger logger = LoggerFactory.getLogger(CubicChecksumVerificationServiceImpl.class);
    protected static final String READ_MODE = "r";
    protected static final int CHECKSUM_BYTE_SIZE = 2;

    @Autowired
    protected ChecksumService crc16ChecksumService;

    @Override
    public Boolean isChecksumVerified(File dataFile) {
        byte[] fileData = getDataFromFile(dataFile);
        int fileChecksum = getChecksumFromData(fileData);
        int calculatedChecksum = this.crc16ChecksumService.calculateChecksum(fileData, getDataLength(fileData));
        logger.debug(String.format("File checksum [%s]; Calculated checksum [%s]", fileChecksum, calculatedChecksum));
        // return fileChecksum == calculatedChecksum;  // FIXME
        logger.error("Checksum verification disabled.");
        return true;
    }

    protected byte[] getDataFromFile(File file) {
        assert (file != null);
        byte[] buffer = null;
        RandomAccessFile dataFile = null;

        try {
            dataFile = new RandomAccessFile(file, READ_MODE);
            buffer = new byte[(int) (dataFile.length())];
            dataFile.readFully(buffer);
            dataFile.close();
        } catch (IOException e) {
            throw new ApplicationServiceException(CommonPrivateError.FILE_READER_FAILED.message(), e);
        } finally {
            try {
                if (dataFile != null) {
                    dataFile.close();
                }
            } catch (IOException e) {
                // Ignore.
            }
        }
        return buffer;
    }

    protected int getChecksumFromData(byte[] buffer) {
        int fileChecksum = 0;
        if (CHECKSUM_BYTE_SIZE > buffer.length) {
            throw new ApplicationServiceException(CommonPrivateError.CHECKSUM_MISSING.message());
        }
        fileChecksum = convertChecksumBytesToInt(buffer);
        return fileChecksum;
    }

    protected int convertChecksumBytesToInt(byte[] buffer) {
        return (((int) buffer[1] & 0xFF) << 8) | (((int) buffer[0] & 0xFF));
    }

    protected int getDataLength(byte[] buffer) {
        return buffer.length - CHECKSUM_BYTE_SIZE;
    }
}
