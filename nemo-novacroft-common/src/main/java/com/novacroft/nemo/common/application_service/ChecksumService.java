package com.novacroft.nemo.common.application_service;

/**
 * Specification for CRC16 checksum service (used for CUBIC import files)
 */
public interface ChecksumService {
    int calculateChecksum(byte[] data, int dataLength);
}
