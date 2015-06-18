package com.novacroft.nemo.tfl.common.application_service;

import java.io.File;

/**
 * Verify CUBIC batch file checksum specification
 */
public interface CubicChecksumVerificationService {
    Boolean isChecksumVerified(File dataFile);
}
