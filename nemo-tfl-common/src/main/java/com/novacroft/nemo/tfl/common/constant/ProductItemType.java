package com.novacroft.nemo.tfl.common.constant;

public enum ProductItemType {
    TRAVEL_CARD("Travelcard", "Travelcard"), BUS_PASS("Bus", "Bus"), PAY_AS_YOU_GO("Pay as you go", "PAYG"), AUTO_TOP_UP("Auto top up", "AutoTopUp");

    private String code;
    private String databaseCode;

    ProductItemType(String code, String databaseCode) {
        this.code = code;
        this.databaseCode = databaseCode;
    }

    public String code() {
        return code;
    }

    public String databaseCode() {
        return databaseCode;
    }

    public static ProductItemType lookUpOptionType(String code) {
        if (code != null) {
            for (ProductItemType optionType : ProductItemType.values()) {
                if (code.equalsIgnoreCase(optionType.code)) {
                    return optionType;
                }
            }
        }
        return null;
    }
}