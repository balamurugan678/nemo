package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.common.application_service.ChecksumService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base Service
 */
public class BaseCubicBatchFileService {
    @Autowired
    protected ChecksumService crc16ChecksumServiceImpl;

    // not sure if this is ok
    protected String calculateChecksum(byte[] data) {
        int checksumInt = this.crc16ChecksumServiceImpl.calculateChecksum(data, data.length);
        byte[] checksumBytes = new byte[2];
        for (int i = 0; i < 2; i++) {
            checksumBytes[i] = (byte) (checksumInt >>> (i * 8));
        }
        return new String(checksumBytes);
    }
}
