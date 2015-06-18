package com.novacroft.nemo.common.transfer;

/**
 * DTO for passing around email attachments
 */
public class EmailAttachmentDTO {
    protected String fileName;
    protected String mimeType;
    protected byte[] content;

    public EmailAttachmentDTO() {
    }

    public EmailAttachmentDTO(String fileName, String mimeType, byte[] content) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.content = (content != null) ? content.clone() : null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = (content != null) ? content.clone() : null;
    }
}
