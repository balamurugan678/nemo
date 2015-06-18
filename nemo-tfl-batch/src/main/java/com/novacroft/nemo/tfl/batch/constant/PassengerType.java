package com.novacroft.nemo.tfl.batch.constant;


public enum PassengerType {
    Adult("0", "Adult"), Child("1", "Child"), Student("5", "16+");

    private String code;
    private String desc;

    private PassengerType(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public static PassengerType getFromCode(String code) {
        for (PassengerType passengerType : values()) {
            if (passengerType.code.equalsIgnoreCase(code)) {
                return passengerType;
            }
        }
        return null;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
