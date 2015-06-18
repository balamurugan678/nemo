package com.novacroft.nemo.tfl.common.constant.financial_services_centre;

/**
 * Export File Types
 */
public enum ExportFileType {
    FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE("fscChequeRequestFile"),
    FINANCIAL_SERVICES_CENTRE_BACS_REQUEST_FILE("fscBacsRequestFile");

    private String code;

    ExportFileType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
