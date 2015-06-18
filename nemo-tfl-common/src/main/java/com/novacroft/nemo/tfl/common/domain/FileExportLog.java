package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Log/audit file exports
 */
@Entity
public class FileExportLog extends AbstractBaseEntity {
    protected Long version;
    protected String fileName;
    protected String userId;
    protected Date exportedAt;

    @SequenceGenerator(name = "FILEEXPORTLOG_SEQ", sequenceName = "FILEEXPORTLOG_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEEXPORTLOG_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    @Version
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
