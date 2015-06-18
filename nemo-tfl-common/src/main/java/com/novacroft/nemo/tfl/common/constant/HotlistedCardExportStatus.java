package com.novacroft.nemo.tfl.common.constant;

/**
 *  Hotlisted card export status
 */
public enum HotlistedCardExportStatus {
    HOTLIST_STATUS_READYTOEXPORT("readytoexport"), 
    HOTLIST_STATUS_EXPORTED("exported");

    private HotlistedCardExportStatus(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return this.code;
    }
}
