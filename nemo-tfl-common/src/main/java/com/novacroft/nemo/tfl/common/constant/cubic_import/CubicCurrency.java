package com.novacroft.nemo.tfl.common.constant.cubic_import;

/**
 * CUBIC Currencies
 */
public enum CubicCurrency {
    GBP(0, "GBP");

    private Integer cubicCode;
    private String isoCode;

    private CubicCurrency(Integer cubicCode, String isoCode) {
        this.cubicCode = cubicCode;
        this.isoCode = isoCode;
    }

    public Integer cubicCode() {
        return this.cubicCode;
    }

    public String isoCode() {
        return this.isoCode;
    }

    public static final CubicCurrency lookUpCurrencyByCubicCode(Integer value) {
        for (CubicCurrency cubicCurrency : CubicCurrency.values()) {
            if (value.equals(cubicCurrency.cubicCode)) {
                return cubicCurrency;
            }
        }
        return null;
    }
}
