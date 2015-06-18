package com.novacroft.nemo.tfl.common.constant;

/**
 * Journey History output file formats
 */
public enum JourneyHistoryOutput {
    PDF("pdf"),
    CSV("csv"),
    CSV_AND_PDF("pdfAndCsv");

    private JourneyHistoryOutput(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }

    public String contentType() {
        return "application/" + this.code;
    }
}
