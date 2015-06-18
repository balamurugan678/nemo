package com.novacroft.nemo.tfl.common.constant;

public enum FailedAutoTopUpStatus {
	OPEN("Open"),
	IN_PROGRESS("In progress"),
	CLOSED("Closed");
	
	private FailedAutoTopUpStatus(String code) {
		this.code = code;
	}

    private String code;

    public String code() {
        return this.code;
    }
    
    public static FailedAutoTopUpStatus find(String code) {
        if (code != null) {
            for (FailedAutoTopUpStatus fatuEnum : FailedAutoTopUpStatus.values()) {
                if (code.equalsIgnoreCase(fatuEnum.code)) {
                    return fatuEnum;
                }
            }
        }
        return null;
    }
}
