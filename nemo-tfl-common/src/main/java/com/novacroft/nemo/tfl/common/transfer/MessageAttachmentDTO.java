package com.novacroft.nemo.tfl.common.transfer;

import java.sql.Blob;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class MessageAttachmentDTO extends AbstractBaseDTO {
    private static final long serialVersionUID = 5248106729611708581L;

    private Long messageId;
    private Blob content;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

}
