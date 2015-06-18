package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

import java.util.Date;

/**
 * Log/audit file export transfer class
 */
public class FileExportLogDTO extends AbstractBaseDTO {
    protected Long version;
    protected String fileName;
    protected String userId;
    protected Date exportedAt;

    public FileExportLogDTO() {
    }

    public FileExportLogDTO(String fileName, String userId) {
        this.fileName = fileName;
        this.userId = userId;
        this.exportedAt = new Date();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExportedAt() {
        return exportedAt;
    }

    public void setExportedAt(Date exportedAt) {
        this.exportedAt = exportedAt;
    }
}
