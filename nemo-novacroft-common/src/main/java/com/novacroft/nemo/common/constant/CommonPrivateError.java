package com.novacroft.nemo.common.constant;

/**
 * Error messages for internal use only, ie not for display on the UI.
 */
public enum CommonPrivateError {
    TRANSFORMING_XML("Transforming XML Document [%s]"),
    UNABLE_TO_CONVERT_STRING("Unable to convert String: [%s]"),
    ERROR_FINDING_METHOD("Error finding method: [%s]"),
    CREATE_MIME_MESSAGE_FAILED("Error: create mime message failed"),
    EMAIL_MESSAGE_FAILED("Error: unable to create email"),
    URL_CREATION_FAILED("Failed to create URL."),
    FILE_READER_FAILED("File reader failed."),
    CHECKSUM_MISSING("The data file does not appear to have a checksum line."),
    INVALID_DIRECTORY("Error: Invalid directory [%s]."),
    INVALID_DATE("Invalid date [%s]."),
    INVALID_DATE_AND_TIME("Invalid date and time [%s]."),
    INVALID_INTEGER("Invalid integer [%s]."),
    QUERY_RETURNED_MORE_THAN_ONE_RECORD("Found more than one record: entity [%s],  criteria [%s]."),
    MORE_THAN_ONE_RECORD_FOR_LOCALE_CODE("Found more than one record for locale [%s] and code [%s]."),
    MORE_THAN_ONE_RECORD_FOR_NAME("Found more than one record for name [%s]."),
    MORE_THAN_ONE_PARAMETER_FOR_CODE("Found more than one parameter for code [%s]."),
    UNSUPPORTED_ENCODING("Unsupported encoding [%s]"),
    INVALID_TWELVE_DIGIT_CARD_NUMBER("A 12 digit card number was expected."),
    INVALID_CARD_NUMBER_LENGTH("Unrecognised card number length.");

    private CommonPrivateError(String message) {
        this.message = message;
    }

    private String message;

    public String message() {
        return this.message;
    }
}
