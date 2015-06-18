package com.novacroft.nemo.tfl.batch.constant;

/**
 * Messages for batch job logs
 */
public enum LogMessage {
    FILE_ANNOUNCEMENT("Import: file [%s]."),
    ALREADY_PROCESSED("Error: file has already been processed [%s]."),
    CHECKSUM_ERROR("Error: bad file checksum [%s]."),
    PROCESS_LOCKED("Error: file is locked [%s]."),
    INVALID_FILEDATA("Error: File has invalid or corrupted data [%s]."),
    EMPTY_FILE("Info: File with Empty data [%s].");

    private LogMessage(String message) {
        this.message = message;
    }

    private String message;

    public String message() {
        return this.message;
    }
}
