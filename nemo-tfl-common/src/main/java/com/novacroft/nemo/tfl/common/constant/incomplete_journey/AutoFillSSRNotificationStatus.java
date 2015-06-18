package com.novacroft.nemo.tfl.common.constant.incomplete_journey;

public enum AutoFillSSRNotificationStatus {
    AUTOFILL_NOT_NOTIFIED("AutofillNotNotified"),
    AUTOFILL_NOTIFIED_COMMENCED("AutofillNotifiedCommenced"),
    SSR_COMMENCED_BUT_FAILED("SSRCommencedButFailed"),
    AUTOFILL_NOTIFIED_OF_COMPLETION("AutofillNotifiedOfCompletion"),
    SSR_VOIDED("SSRVoided");
    private final String value;

    AutoFillSSRNotificationStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static AutoFillSSRNotificationStatus fromValue(String statusValue) {
        for (AutoFillSSRNotificationStatus autoFillSSRNotificationStatus : AutoFillSSRNotificationStatus.values()) {
            if (autoFillSSRNotificationStatus.value.equals(statusValue)) {
                return autoFillSSRNotificationStatus;
            }
        }
        throw new IllegalArgumentException(statusValue);
    }
}
