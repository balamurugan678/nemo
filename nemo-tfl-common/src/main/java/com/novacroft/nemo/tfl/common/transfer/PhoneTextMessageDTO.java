package com.novacroft.nemo.tfl.common.transfer;

public class PhoneTextMessageDTO extends MessageDTO {
    private static final long serialVersionUID = -566541650743714923L;

    private Long mobileNumber;

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
