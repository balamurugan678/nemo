package com.novacroft.nemo.tfl.common.constant;

public enum MessageStatus {

    REQUESTED("Requested"), RETRY("Retry"), ABORTED("Aborted"), SENT("Sent");
    private String code;

    private MessageStatus(String code) {
        this.code = code;
    }

    public final String code() {
        return code;
    }

    public static MessageStatus lookUpMessageStatus(String code) {
        if (code != null) {
            for (MessageStatus status : MessageStatus.values()) {
                if (code.equalsIgnoreCase(status.code)) {
                    return status;
                }
            }
        }
        return null;
    }

}
